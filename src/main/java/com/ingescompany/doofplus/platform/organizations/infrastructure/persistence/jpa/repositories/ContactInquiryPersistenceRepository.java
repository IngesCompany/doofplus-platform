package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.repositories;

import com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.entities.ContactInquiryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactInquiryPersistenceRepository extends JpaRepository<ContactInquiryPersistenceEntity, Long> {

    List<ContactInquiryPersistenceEntity> findAllByOrderByReceivedAtDesc();
}
