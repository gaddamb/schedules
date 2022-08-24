package com.gs.schedules.repository;

import com.gs.schedules.entity.Schedule;
import com.gs.schedules.entity.ScheduleItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {


    @Query("select si from ScheduleItem si join fetch si.schedule where si.schedule = ?1")
    List<ScheduleItem> findSchedule(Schedule schedule);
}
