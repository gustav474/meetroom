package com.gustav474.meetroom.controller;

import com.gustav474.meetroom.DTO.CustomerDTO;
import com.gustav474.meetroom.DTO.EventDTO;
import com.gustav474.meetroom.DTO.WeekDTO;
import com.gustav474.meetroom.entities.Customer;
import com.gustav474.meetroom.entities.Event;
import com.gustav474.meetroom.services.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

/**
 * @author Sergey Lapshin
 */
@Controller("/")
@Data
public class AppController {
    @Autowired
    private IndexPageService indexPage;

    @Autowired
    private EventService eventService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping
    private String getIndexPage(@RequestParam Optional<String> weekNumber, Model model,
                                       Principal principal) {
        WeekDTO weekDTO = null;

        List<Integer> hours = indexPage.getHours();
        List<String> hours24 = indexPage.getHours24();

        if (weekNumber.isPresent()) {
            weekDTO = indexPage.getWeek(Integer.valueOf(weekNumber.get()));
        } else {
            weekDTO = indexPage.getWeek();
        }

        model.addAttribute("eventDTO", new EventDTO());
        model.addAttribute("hours", hours);
        model.addAttribute("hours24", hours24);
        model.addAttribute("weekDTO", weekDTO);
        model.addAttribute("principal", principal);
        return "index";
    }

    @GetMapping("event")
    private String getEvent(@RequestParam String id, Model model) {
        Event event = eventService.getEvent(Long.valueOf(id));
        Customer customer = customerService.findById(event.getCreatedByUserId());

        model.addAttribute("event", event);
        model.addAttribute("customer", customer);
        return "event";
    }

    @GetMapping("makeEvent")
    private String makeEvents(@RequestParam String dateOfMeeting, @RequestParam String hour, Model model) {
        model.addAttribute("eventDTO", new EventDTO());
        model.addAttribute("dateOfBegin", dateOfMeeting);
        model.addAttribute("hour", hour);

        return "makeEvent";
    }

    @PostMapping("makeEvent")
    private String makeEvents(@ModelAttribute @Valid EventDTO eventDTO,
                              BindingResult bindingResult,Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            return "makeEvent";
        }

        Long userId = customerService.findByLogin(principal.getName()).getId();
        eventDTO.setCreatedByUserId(userId);

        try {
            eventService.makeEvents(eventDTO);
            String message = "Created new event successfully";
            model.addAttribute("message", message);
            return "message";
        } catch (CantCreateEventOnPastException e) {
//            String message = "Cant create event on past";
            model.addAttribute("message", e.getMessage());
            return "message";
        } catch (IntersectingEventsException e) {
//            String message = "Intersecting events";
            model.addAttribute("message", e.getMessage());
            return "message";
        }

    }

    @GetMapping("login")
    private String login() {
        return "login";
    }

    @GetMapping("logout")
    private String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/";
    }

    @GetMapping("registration")
    private String registration(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "registration";
    }

    @PostMapping("registration")
    private String signIn(CustomerDTO customerDTO, Model model) {
        customerService.saveCustomer(customerDTO);
        String message = "Registration has been successfully";
        model.addAttribute("message", message);
        return "message";
    }

    @GetMapping("deleteEvent")
    private String deleteEvent(@RequestParam String id, Model model) {
        boolean isDeleted = eventService.deleteById(Long.valueOf(id));
        if (isDeleted) {
            String message = "Event deleted successfully";
            model.addAttribute("message", message);
            return "message";
        } else {
            String message = "Can't delete event";
            model.addAttribute("message", message);
            return "message";
        }
    }
}
