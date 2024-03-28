package com.feuji.employeeservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.employeeservice.entity.UserLoginEntity;

public interface UserInfoRepository extends JpaRepository<UserLoginEntity, Integer> {
    Optional<UserLoginEntity> findByUserName(String userName);

	Optional<UserLoginEntity> findByUserEmail(String userEmail);

}
