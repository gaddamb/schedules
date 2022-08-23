package com.gs.schedules.service;

import com.gs.schedules.entity.Offender;
import com.gs.schedules.repository.OffenderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OffenderServiceImpl implements OffenderService{

    @Autowired
    private OffenderRepository offenderRepository;

    @Override
    public Offender saveOffender(Offender offender) {
        log.info("Inside save offender service");
        return offenderRepository.save(offender);
    }
}
