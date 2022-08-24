package com.gs.schedules.service;

import com.gs.schedules.entity.Offender;
import com.gs.schedules.repository.OffenderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OffenderServiceImplTest {

    @Autowired
    private OffenderService offenderService;

    @MockBean
    private OffenderRepository offenderRepository;

    @BeforeEach
    void setUp() {
        Offender offender = Offender.builder()
                .firstName("Alberto")
                .lastName("Isaq")
                .build();

        Mockito.when(offenderRepository.save(offender)).thenReturn(offender);
    }

    @Test
    void test_saveOffender() {

        Offender offender = Offender.builder()
                .firstName("Alberto")
                .lastName("Isaq")
                .build();

        Offender offenderObj = offenderService.saveOffender(offender);
        org.junit.jupiter.api.Assertions.assertEquals("Alberto", offenderObj.getFirstName());

    }
}