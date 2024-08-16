package com.tinqinacademy.authentication.persistence.repositories;

import com.tinqinacademy.authentication.persistence.entities.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, UUID> {
    boolean existsByCode(String code);
}
