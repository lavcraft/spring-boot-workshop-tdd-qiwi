package com.governance.visaagent.visaagent.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VisaRequestRepository extends JpaRepository<VisaRequest, Long> {
    @Transactional
    @Modifying
    @Query("update VisaRequest v set v.status = ?1 where v.id = ?2")
    void updateStatusById(String status,
                          Long id);

    @Query("select v from VisaRequest v where v.userId = ?1 and v.status = 'processing'")
    List<VisaRequest> findAllInStatusProcessingByUserId(String userId);

    List<VisaRequest> findAllVisaRequestByStatusAndUserId(String status,
                                                 String userId);
}
