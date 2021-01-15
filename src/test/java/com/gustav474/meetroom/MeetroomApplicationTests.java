package com.gustav474.meetroom;

import com.gustav474.meetroom.DTO.WeekDTO;
import com.gustav474.meetroom.services.IndexPageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sergey Lapshin
 */
@SpringBootTest
class MeetroomApplicationTests {

	@Autowired
	private IndexPageService indexPageService;

	private List<String> expected = new ArrayList();;
	private WeekDTO weekDTO = new WeekDTO();

	@Test
	public void getWeekByDateMethodTest() {
		expected.add("понедельник");
		expected.add("вторник");
		expected.add("среда");
		expected.add("четверг");
		expected.add("пятница");
		expected.add("суббота");

		for (int y = 1; y < 31; y++) {
			weekDTO = indexPageService.getWeek(LocalDateTime.of(2021, 1, y, 0, 0, 0, 0));
			for (int i = 0; i < 6; i++) {
				String actual = weekDTO.getDaysOfWeek().get(i).getDayOfWeek();
				assertThat(actual).isEqualTo(expected.get(i));
			}
		}
	}

}
