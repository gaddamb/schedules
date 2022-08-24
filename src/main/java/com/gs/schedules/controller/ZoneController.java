package com.gs.schedules.controller;

import com.gs.schedules.entity.Schedule;
import com.gs.schedules.entity.Zone;
import com.gs.schedules.model.VerifyZoneResponseDTO;
import com.gs.schedules.model.ZoneDTO;
import com.gs.schedules.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zone")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @PostMapping("/")
    public Zone createZone(@RequestBody Zone zone) {
        return zoneService.saveZone(zone);
    }

    @PostMapping("/configureSchedule")
    public Schedule configureZoneToSchedule(@RequestBody ZoneDTO zone) {
        return zoneService.configureSchedule(zone);
    }

    @PostMapping("/verifyStatus")
    public VerifyZoneResponseDTO scheduleForZone(@RequestBody ZoneDTO zone) {
        return zoneService.verifyStatus(zone);
    }
}
