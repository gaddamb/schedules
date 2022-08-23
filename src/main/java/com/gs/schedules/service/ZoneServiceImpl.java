package com.gs.schedules.service;

import com.gs.schedules.entity.Offender;
import com.gs.schedules.entity.Schedule;
import com.gs.schedules.entity.Zone;
import com.gs.schedules.model.OffenderDTO;
import com.gs.schedules.model.ZoneDTO;
import com.gs.schedules.repository.OffenderRepository;
import com.gs.schedules.repository.ScheduleRepository;
import com.gs.schedules.repository.ZoneRepository;
import java.util.Optional;
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
    public Zone configureSchedule(ZoneDTO zone) {

        Optional<Zone> zoneRecord = zoneRepository.findById(Long.parseLong(zone.getZoneId()));

        if(zoneRecord.isPresent()) {
            Schedule scheduleRecord = scheduleRepository.findByName(zone.getScheduleName());
            zoneRecord.get().setSchedule(scheduleRecord);
            return zoneRepository.save(zoneRecord.get());
        }

        return null;
    }

    @Override
    public String verifyStatus(ZoneDTO zone) {
        return null;
    }
}
