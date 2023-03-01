package com.governance.visaagent.visaagent.dal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VisaRequestRepository extends CrudRepository<VisaRequest, Long> {
    @Query("select v from VisaRequest v where v.userId = ?1 and v.status = 'processing'")
    List<VisaRequest> findAllInStatusProcessingByUserId(String userId);

    List<VisaRequest> findAllVisaRequestByStatusAndUserId(String status,
                                                 String userId);
}
