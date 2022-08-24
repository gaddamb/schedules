package com.gs.schedules.controller;

import com.gs.schedules.entity.Schedule;
import com.gs.schedules.entity.ScheduleItem;
import com.gs.schedules.model.ScheduleDTO;
import com.gs.schedules.service.ScheduleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/")
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO schModel) {
        return scheduleService.createSchedule(schModel);
    }


    @PutMapping("/")
    public ScheduleDTO updateSchedule(@RequestBody ScheduleDTO schModel) {
        return scheduleService.updateSchedule(schModel);
    }
}
