package com.gs.schedules.service;

import com.gs.schedules.entity.Offender;
import com.gs.schedules.entity.Schedule;
import com.gs.schedules.entity.ScheduleItem;
import com.gs.schedules.entity.Zone;
import com.gs.schedules.model.OffenderDTO;
import com.gs.schedules.model.VerifyZoneResponseDTO;
import com.gs.schedules.model.ZoneDTO;
import com.gs.schedules.repository.OffenderRepository;
import com.gs.schedules.repository.ScheduleItemRepository;
import com.gs.schedules.repository.ScheduleRepository;
import com.gs.schedules.repository.ZoneRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ZoneServiceImplTest {

    @Autowired
    private ZoneService zoneService;

    @MockBean
    private ZoneRepository zoneRepository;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private ScheduleItemRepository scheduleItemRepository;

    @MockBean
    private OffenderRepository offenderRepository;

    @BeforeEach
    void setUp() throws ParseException {
        // zone related data
        Zone zone = new Zone(6L, null, null );

        // schedule related data
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse("2022-08-23");
        Schedule schedule = new Schedule(10L, "scheduleA", zone, date, null);
        ScheduleItem scheduleItem1 =
                new ScheduleItem(2L, "from 10:00 to 11:00 each Monday", "timeslotOnDay", schedule, date, null);
        ScheduleItem scheduleItem2 =
                new ScheduleItem(3L, "from 2021-02-02 to 2022-08-23", "dateRange", schedule, date, null);
        ScheduleItem scheduleItem3 =
                new ScheduleItem(4L, "from 13:00 to 13:30 each weekday", "timeslotOnWeekday", schedule, date, null);
        ScheduleItem scheduleItem4 =
                new ScheduleItem(4L, "from 09:00 to 09:30 everyday", "timeslotOnEveryday", schedule, date, null);
        ScheduleItem scheduleItem5 =
                new ScheduleItem(4L, "from 17:00 to 17:15 only weekends", "timeslotOnWeekends", schedule, date, null);
        List<ScheduleItem> scheduleItemList = new ArrayList<>();
        scheduleItemList.add(scheduleItem1);
        scheduleItemList.add(scheduleItem2);
        scheduleItemList.add(scheduleItem3);
        scheduleItemList.add(scheduleItem4);
        scheduleItemList.add(scheduleItem5);

        // Offender related data
        Offender offender = new Offender(4L, "Alberto", "Izag");

        Mockito.when(zoneRepository.save(zone)).thenReturn(zone);
        Mockito.when(zoneRepository.findById(6L)).thenReturn(Optional.of(zone));

        Mockito.when(offenderRepository.findById(4L)).thenReturn(Optional.of(offender));

        Mockito.when(scheduleRepository.findByName("scheduleA")).thenReturn(schedule);
        Mockito.when(scheduleRepository.save(schedule)).thenReturn(schedule);
        Mockito.when(scheduleRepository.findByZone(zone)).thenReturn(schedule);

        Mockito.when(scheduleItemRepository.findSchedule(schedule)).thenReturn(scheduleItemList);
    }

    @Test
    void test_assignZone_to_offender() {
        OffenderDTO offenderDto = new OffenderDTO("6", "4");
        Zone zoneObj = zoneService.assignZone(offenderDto);

        org.junit.jupiter.api.Assertions.assertEquals(6, zoneObj.getZoneId());
    }


    @Test
    void test_configureSchedule() {
        ZoneDTO zoneDTO = new ZoneDTO("6", "scheduleA", null);

        Schedule schObj = zoneService.configureSchedule(zoneDTO);
        org.junit.jupiter.api.Assertions.assertEquals(10, schObj.getScheduleId());

    }

    @Test
    void test_verifyStatus_for_date_range() {
        ZoneDTO zoneDTO = new ZoneDTO("6", "scheduleA", "20220821100205");

        VerifyZoneResponseDTO resObj = zoneService.verifyStatus(zoneDTO);
        org.junit.jupiter.api.Assertions.assertEquals(Boolean.TRUE, resObj.isOffenderAllowedAtZoneOnDate());
        org.junit.jupiter.api.Assertions.assertTrue(resObj.getScheduleNames().contains("from 2021-02-02 to 2022-08-23"));
    }

    @Test
    void test_verifyStatus_on_day() {
        ZoneDTO zoneDTO = new ZoneDTO("6", "scheduleA", "20220829100205");

        VerifyZoneResponseDTO resObj = zoneService.verifyStatus(zoneDTO);
        org.junit.jupiter.api.Assertions.assertEquals(Boolean.TRUE, resObj.isOffenderAllowedAtZoneOnDate());
        org.junit.jupiter.api.Assertions.assertTrue(resObj.getScheduleNames().contains("from 10:00 to 11:00 each Monday"));
    }

    @Test
    void test_verifyStatus_on_weekday() {
        ZoneDTO zoneDTO = new ZoneDTO("6", "scheduleA", "20220829130205");

        VerifyZoneResponseDTO resObj = zoneService.verifyStatus(zoneDTO);
        org.junit.jupiter.api.Assertions.assertEquals(Boolean.TRUE, resObj.isOffenderAllowedAtZoneOnDate());
        org.junit.jupiter.api.Assertions.assertTrue(resObj.getScheduleNames().contains("from 13:00 to 13:30 each weekday"));
    }

    @Test
    void test_verifyStatus_on_non_weekday() {
        ZoneDTO zoneDTO = new ZoneDTO("6", "scheduleA", "20220828130205");

        VerifyZoneResponseDTO resObj = zoneService.verifyStatus(zoneDTO);
        org.junit.jupiter.api.Assertions.assertEquals(Boolean.TRUE, resObj.isOffenderAllowedAtZoneOnDate());
        org.junit.jupiter.api.Assertions.assertTrue(!resObj.getScheduleNames().contains("from 13:00 to 13:30 each weekday"));
    }

    @Test
    void test_verifyStatus_on_everyday() {
        ZoneDTO zoneDTO = new ZoneDTO("6", "scheduleA", "20220830091205");

        VerifyZoneResponseDTO resObj = zoneService.verifyStatus(zoneDTO);
        org.junit.jupiter.api.Assertions.assertEquals(Boolean.TRUE, resObj.isOffenderAllowedAtZoneOnDate());
        org.junit.jupiter.api.Assertions.assertTrue(resObj.getScheduleNames().contains("from 09:00 to 09:30 everyday"));
    }

    @Test
    void test_verifyStatus_on_weekend() {
        ZoneDTO zoneDTO = new ZoneDTO("6", "scheduleA", "20220828170205");

        VerifyZoneResponseDTO resObj = zoneService.verifyStatus(zoneDTO);
        org.junit.jupiter.api.Assertions.assertEquals(Boolean.TRUE, resObj.isOffenderAllowedAtZoneOnDate());
        org.junit.jupiter.api.Assertions.assertTrue(resObj.getScheduleNames().contains("from 17:00 to 17:15 only weekends"));
    }
}
