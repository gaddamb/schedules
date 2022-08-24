package com.gs.schedules.service;

import com.gs.schedules.config.RegexConfiguration;
import com.gs.schedules.entity.Schedule;
import com.gs.schedules.entity.ScheduleItem;
import com.gs.schedules.model.ErrorDTO;
import com.gs.schedules.model.ScheduleDTO;
import com.gs.schedules.repository.ScheduleItemRepository;
import com.gs.schedules.repository.ScheduleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShcheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleItemRepository scheduleItemRepository;

    @Autowired
    private RegexConfiguration regexConfig;

    @Override
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        log.info("Inside create Schedule method.");
        Schedule scheduleEntity = new Schedule();
        scheduleEntity.setName(scheduleDTO.getName());
        List<ScheduleItem> scheduleItemList = new ArrayList<>();

        // check whether the input schedule is  valid
        if(!isScheduleMatchedWithPattern(scheduleDTO.getSchedule())) {
            ScheduleDTO errorSchedule = new ScheduleDTO();
            ErrorDTO errorDTO = new ErrorDTO("Invalid input entry", "Invlaid schedule");
            errorSchedule.setError(errorDTO);
            return errorSchedule;
        }

        // get type of schedule: date range / series / interval and set to schedule items.
        scheduleDTO.getSchedule().stream().forEach(
                schedule -> {
                    ScheduleItem scheduleItem = new ScheduleItem();
                    scheduleItem.setScheduleItem(schedule);
                    scheduleItem.setSchedule(scheduleEntity);
                    scheduleItem.setType(getTypeOfSchedule(schedule));
                    scheduleItemList.add(scheduleItem);
                }
        );

        scheduleRepository.save(scheduleEntity);
        scheduleItemRepository.saveAll(scheduleItemList);
        return scheduleDTO;
    }

    public Boolean isScheduleMatchedWithPattern(List<String> scheduleList) {
        return scheduleList.stream().filter(
                schedule -> regexConfig.regexConfigMap().values().stream().filter(
                        schedulePattern -> {
                            Pattern pattern = Pattern.compile(schedulePattern);
                            Matcher m = pattern.matcher(schedule);
                            return m.find();
                        }
                ).count() >= 1
        ).count() >= 1;
    }

    public String getTypeOfSchedule(String schedule) {
        AtomicReference<String> key = new AtomicReference<>(null);
        regexConfig.regexConfigMap().entrySet().stream().forEach(
                entry -> {
                    Pattern pattern = Pattern.compile(entry.getValue());
                    Matcher m = pattern.matcher(schedule);
                    if(m.find()){
                        key.set(entry.getKey());
                    }
                }
        );

        return key.get();
    }

    @Override
    public ScheduleDTO updateSchedule(ScheduleDTO schModel) {

        /*Schedule scheduleRecord = scheduleRepository.findByName(schModel.getName());
        if(scheduleRecord != null) {
            scheduleItemRepository.find
        }*/

        return null;
    }
}
