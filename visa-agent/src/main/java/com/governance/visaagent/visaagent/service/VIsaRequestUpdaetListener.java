package com.governance.visaagent.visaagent.service;

import com.governance.visaagent.visaagent.dal.VisaRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class VIsaRequestUpdaetListener {
    private final VisaRequestRepository visaRequestRepository;

    @Transactional
    @KafkaListener(topics = "visa.requests.update", groupId = "visa-agent")
    public void processUpdate(String in) {
        String[] split    = in.split(":");
        long     ticketId = Long.parseLong(split[0]);
        String   status   = split[1];

        visaRequestRepository.findById(ticketId)
                                     .ifPresent(request -> request.setStatus(status));

        visaRequestRepository.updateStatusById(status, ticketId);

        //!!!! MAY WRONG
//        visaRequestRepository.save(
//                VisaRequest.builder()
//                           .id(ticketId)
//                           .status(status)
//                           .build()
//        );
    }
}
