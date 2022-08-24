package com.gs.schedules.service;

import com.gs.schedules.entity.Schedule;
import com.gs.schedules.entity.ScheduleItem;
import com.gs.schedules.model.ScheduleDTO;
import com.gs.schedules.repository.ScheduleItemRepository;
import com.gs.schedules.repository.ScheduleRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ShcheduleServiceImplTest {

    @Autowired
    private ScheduleService scheduleService;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private ScheduleItemRepository scheduleItemRepository;

    @BeforeEach
    void setUp() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse("2022-08-23");
        Schedule schedule = Schedule.builder()
                .scheduleId(10L)
                .createDate(date)
                .name("scheduleA")
                .zone(null)
                .modifyDate(null)
                .build();


        ScheduleItem scheduleItem1 = ScheduleItem.builder()
                .scheduleItem("from 10:00 to 11:00 each Monday")
                .schedule(schedule)
                .createDate(date)
                .build();
        ScheduleItem scheduleItem2 = ScheduleItem.builder()
                .scheduleItem("from 2021-02-02 to 2022-08-23")
                .schedule(schedule)
                .createDate(date)
                .build();
        ScheduleItem scheduleItem3 = ScheduleItem.builder()
                .scheduleItem("from 13:00 to 13:30 each weekday")
                .schedule(schedule)
                .createDate(date)
                .build();
        List<ScheduleItem> scheduleItemList = new ArrayList<>();
        scheduleItemList.add(scheduleItem1);
        scheduleItemList.add(scheduleItem2);
        scheduleItemList.add(scheduleItem3);

        Mockito.when(scheduleRepository.save(schedule)).thenReturn(schedule);
        Mockito.when(scheduleItemRepository.saveAll(scheduleItemList)).thenReturn(scheduleItemList);

    }

    @Test
    void test_createSchedule() {
        List<String> scheduleItemList = new ArrayList<>();
        scheduleItemList.add("from 10:00 to 11:00 each Monday");
        scheduleItemList.add("from 2021-02-02 to 2022-08-23");
        scheduleItemList.add("from 13:00 to 13:30 each weekday");

        ScheduleDTO scheduleDTO = new ScheduleDTO("scheduleA", scheduleItemList, null);

        ScheduleDTO scheduleDtoObj = scheduleService.createSchedule(scheduleDTO);

        org.junit.jupiter.api.Assertions.assertEquals("scheduleA", scheduleDtoObj.getName());
    }
}