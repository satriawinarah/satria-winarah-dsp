package com.satriawinarah.dsp.repository;

import com.satriawinarah.dsp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

}
