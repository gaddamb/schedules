package com.gs.schedules.service;

import com.gs.schedules.entity.Schedule;
import com.gs.schedules.entity.ScheduleItem;
import com.gs.schedules.model.ScheduleDTO;
import java.util.List;

public interface ScheduleService {

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);

    public ScheduleDTO updateSchedule(ScheduleDTO schModel);
}
