package com.gs.schedules.service;

import com.gs.schedules.entity.Zone;
import com.gs.schedules.model.OffenderDTO;
import com.gs.schedules.model.ZoneDTO;

public interface ZoneService {
    Zone assignZone(OffenderDTO offender);

    Zone saveZone(Zone zone);

    Zone configureSchedule(ZoneDTO zone);

    String verifyStatus(ZoneDTO zone);
}
