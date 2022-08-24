package com.gs.schedules.service;

import com.gs.schedules.model.ScheduleDTO;

public interface ScheduleService {

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);

    public ScheduleDTO updateSchedule(ScheduleDTO schModel);
}
