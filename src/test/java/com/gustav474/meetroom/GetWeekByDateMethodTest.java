package com.gustav474.meetroom;

import com.gustav474.meetroom.DTO.WeekDTO;
import com.gustav474.meetroom.services.IndexPageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetWeekByDateMethodTest {

    @Autowired
    private IndexPageService indexPageService;

    private List<String> expected = new ArrayList();;
    private WeekDTO weekDTO = new WeekDTO();

    @Test
    public void test() {
        expected.add("понедельник");
        expected.add("вторник");
        expected.add("среда");
        expected.add("четверг");
        expected.add("пятница");
        expected.add("суббота");

        for (int y = 1; y < 31; y++) {
            weekDTO = indexPageService.getWeek(LocalDate.of(2022, 1, y));
            for (int i = 0; i < 6; i++) {
                String actual = weekDTO.getDaysOfWeek().get(i).getDayOfWeek();
                assertThat(actual).isEqualTo(expected.get(i));
            }
        }
    }
}