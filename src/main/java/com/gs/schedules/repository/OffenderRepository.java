package com.gs.schedules.repository;

import com.gs.schedules.entity.Offender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffenderRepository extends JpaRepository<Offender, Long> {
}
