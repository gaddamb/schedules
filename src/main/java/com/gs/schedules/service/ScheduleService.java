package com.gs.schedules.service;

import com.gs.schedules.entity.Schedule;
import com.gs.schedules.model.ScheduleDTO;

public interface ScheduleService {

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);

    public Schedule updateSchedule(ScheduleDTO schModel);
}
