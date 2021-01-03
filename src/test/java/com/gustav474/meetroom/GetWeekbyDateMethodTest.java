package com.gustav474.meetroom;

import com.gustav474.meetroom.DTO.WeekDTO;
import com.gustav474.meetroom.services.IndexPageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetWeekbyDateMethodTest {

    @Autowired
    private IndexPageService indexPageService;

    private List<String> expected;

    {
        expected = new ArrayList();
        expected.add("понедельник");
        expected.add("вторник");
        expected.add("среда");
        expected.add("четверг");
        expected.add("пятница");
        expected.add("суббота");
    }

    @Test
    public void test() {
        WeekDTO weekDTO = this.indexPageService.getWeek(2);
        for (int i = 1; i > weekDTO.getDaysOfWeek().size(); i++) {
            assertThat(weekDTO.getDaysOfWeek().get(i).getDayOfWeek()).isEqualTo(expected.get(i));
        }
    }
}