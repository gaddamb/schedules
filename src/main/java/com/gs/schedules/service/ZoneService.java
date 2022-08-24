package com.gs.schedules.service;

import com.gs.schedules.entity.Schedule;
import com.gs.schedules.entity.Zone;
import com.gs.schedules.model.OffenderDTO;
import com.gs.schedules.model.VerifyZoneResponseDTO;
import com.gs.schedules.model.ZoneDTO;

public interface ZoneService {
    Zone assignZone(OffenderDTO offender);

    Zone saveZone(Zone zone);

    Schedule configureSchedule(ZoneDTO zone);

    VerifyZoneResponseDTO verifyStatus(ZoneDTO zone);
}
