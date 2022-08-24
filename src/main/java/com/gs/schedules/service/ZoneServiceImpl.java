package com.gs.schedules.service;

import com.gs.schedules.entity.Offender;
import com.gs.schedules.entity.Schedule;
import com.gs.schedules.entity.ScheduleItem;
import com.gs.schedules.entity.Zone;
import com.gs.schedules.model.ErrorDTO;
import com.gs.schedules.model.OffenderDTO;
import com.gs.schedules.model.VerifyZoneResponseDTO;
import com.gs.schedules.model.ZoneDTO;
import com.gs.schedules.repository.OffenderRepository;
import com.gs.schedules.repository.ScheduleItemRepository;
import com.gs.schedules.repository.ScheduleRepository;
import com.gs.schedules.repository.ZoneRepository;
import com.gs.schedules.utils.GSUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ZoneServiceImpl implements ZoneService{

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private OffenderRepository offenderRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleItemRepository scheduleItemRepository;

    @Override
    public Zone assignZone(OffenderDTO offender) {
        log.info("Inside assign zone method");
        log.info("Offender id is: " + offender.getOffenderId());
        Optional<Offender> prisoner = offenderRepository.findById(Long.parseLong(offender.getOffenderId()));
        Optional<Zone> zone = zoneRepository.findById(Long.parseLong(offender.getZoneId()));

        if(zone.isPresent() && prisoner.isPresent()) {
            zone.get().setOffender(prisoner.get());
        }
        return zoneRepository.save(zone.get());
    }

    @Override
    public Zone saveZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    @Override
    public Schedule configureSchedule(ZoneDTO zone) {

        Optional<Zone> zoneRecord = zoneRepository.findById(Long.parseLong(zone.getZoneId()));

        if(zoneRecord.isPresent()) {
            Schedule scheduleRecord = scheduleRepository.findByName(zone.getScheduleName());
            scheduleRecord.setZone(zoneRecord.get());
            return scheduleRepository.save(scheduleRecord);
        }

        return null;
    }

    @Override
    public VerifyZoneResponseDTO verifyStatus(ZoneDTO zone) {

        VerifyZoneResponseDTO verifyZoneResponseDTO = new VerifyZoneResponseDTO();
        verifyZoneResponseDTO.setZoneId(zone.getZoneId());
        verifyZoneResponseDTO.setDate(zone.getDate());
        LocalDateTime inputDateInUTC = GSUtils.formatDateInUTC(zone.getDate());
        if( inputDateInUTC != null) {

            verifyZoneResponseDTO.setOffenderAllowedAtZoneOnDate(Boolean.FALSE);
            Zone zoneEntity = new Zone();
            zoneEntity.setZoneId(Long.parseLong(zone.getZoneId()));
            // Optional<Zone> zoneRecord = zoneRepository.findById(Long.parseLong(zone.getZoneId()));
            Schedule scheduleEntity = scheduleRepository.findByZone(zoneEntity);
            if(scheduleEntity != null) {
                // retieve all the schedules of the schedule configured for the zone
                List<ScheduleItem> sItemList = scheduleItemRepository.findSchedule(scheduleEntity);
                List<String> applicableSchedules = sItemList.stream()
                        .filter(sItem -> isZoneValidForGivenDate(inputDateInUTC, sItem))
                        .map(sItem -> sItem.getScheduleItem())
                        .collect(Collectors.toList());

                verifyZoneResponseDTO.setScheduleNames(applicableSchedules);
                if(applicableSchedules.size() >=0) {
                    verifyZoneResponseDTO.setOffenderAllowedAtZoneOnDate(Boolean.TRUE);
                }

            }else {
                ErrorDTO errorDTO = new ErrorDTO("No Zone found", "Invalid Zone ID");
                verifyZoneResponseDTO.setError(errorDTO);
            }

        } else {
            ErrorDTO errorDTO = new ErrorDTO("Expected date format in uuuuMMddHHmmss", "Invalid Date Format");
            verifyZoneResponseDTO.setError(errorDTO);
        }

        return verifyZoneResponseDTO;
    }

    public boolean isZoneValidForGivenDate(LocalDateTime inputDate, ScheduleItem schedule) {

        boolean dateValidOnZone = Boolean.FALSE;
        String type = schedule.getType();

        if(type != null && "dateRange".equalsIgnoreCase(type)) {
            dateValidOnZone = GSUtils.isWithinDateRange(inputDate, schedule.getScheduleItem());
        }
        if(!dateValidOnZone && type !=null && "timeslotOnDay".equalsIgnoreCase(type)) {
            dateValidOnZone = GSUtils.isOnTheDay(inputDate, schedule.getScheduleItem());
        }
        if(!dateValidOnZone && type !=null && "timeslotOnWeekday".equalsIgnoreCase(type)) {
            dateValidOnZone = GSUtils.isOnWeekDay(inputDate, schedule.getScheduleItem());
        }
        if(!dateValidOnZone && type !=null && "timeslotOnEveryday".equalsIgnoreCase(type)) {
            dateValidOnZone = GSUtils.isOnEveryDay(inputDate, schedule.getScheduleItem());
        }
        if(!dateValidOnZone && type !=null && "timeslotOnWeekends".equalsIgnoreCase(type)) {
            dateValidOnZone = GSUtils.isOnWeekends(inputDate, schedule.getScheduleItem());
        }
        if(!dateValidOnZone && type !=null && "timeslotOnWeekdayWithIntervals".equalsIgnoreCase(type)) {
            dateValidOnZone = GSUtils.isOnDayWithInterval(inputDate, schedule.getScheduleItem(), schedule.getCreateDate());
        }

        return dateValidOnZone ;
    }
}
