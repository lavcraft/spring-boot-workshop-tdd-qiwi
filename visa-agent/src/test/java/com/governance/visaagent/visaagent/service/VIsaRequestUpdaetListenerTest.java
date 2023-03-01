package com.governance.visaagent.visaagent.service;

import com.governance.visaagent.visaagent.dal.VisaRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VIsaRequestUpdaetListenerTest {
    @InjectMocks VIsaRequestUpdaetListener vIsaRequestUpdaetListener;
    @Mock VisaRequestRepository visaRequestRepository;

    @Test
    void should_update_visa_status() {
        vIsaRequestUpdaetListener.processUpdate("100:processing");
    }
}