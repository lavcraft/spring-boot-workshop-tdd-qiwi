package com.governance.visaagent.visaagent.service;

import com.governance.visaagent.visaagent.dal.VisaRequest;
import com.governance.visaagent.visaagent.dal.VisaRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisaService {
    private final VisaRequestRepository visaRequestRepository;

    public Long createRequest(String userId) {
        List<VisaRequest> requests =
                visaRequestRepository.findAllInStatusProcessingByUserId(userId);

        if (requests.size() == 1) {
            return requests.get(0).getId();
        }

        if (requests.size() == 0) {
            return visaRequestRepository.save(
                                                VisaRequest.builder()
                                                           .userId(userId)
                                                           .status("processing")
                                                           .build())
                                        .getId();
        }

        throw new IllegalStateException("Multiple tickets in processing");
    }

    public Optional<VisaRequest> findById(Long ticketId) {
        return null;
    }
}
