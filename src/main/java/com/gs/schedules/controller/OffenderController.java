package com.gs.schedules.controller;

import com.gs.schedules.entity.Offender;
import com.gs.schedules.entity.Zone;
import com.gs.schedules.model.OffenderDTO;
import com.gs.schedules.service.OffenderService;
import com.gs.schedules.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offender")
public class OffenderController {

    @Autowired
    private OffenderService offenderService;

    @Autowired
    private ZoneService zoneService;

    @PostMapping("/")
    public Offender createOffender(@RequestBody Offender offender) {
        return offenderService.saveOffender(offender);
    }


    @PostMapping("/assignZone")
    public Zone assignZoneToOffender(@RequestBody OffenderDTO offender) {
        return zoneService.assignZone(offender);
    }

}
