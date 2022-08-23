package com.gs.schedules.repository;

import com.gs.schedules.entity.ScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {
}
