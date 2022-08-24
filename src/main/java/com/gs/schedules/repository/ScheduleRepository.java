package com.gs.schedules.repository;

import com.gs.schedules.entity.Schedule;
import com.gs.schedules.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule findByName(String name);

    Schedule findByZone(Zone zone);

}
