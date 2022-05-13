package com.example.opt.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.opt.entities.OTP;

@Repository
public interface OtpDAO extends JpaRepository<OTP, Integer> {

	Optional<OTP> findByOtpid(String otpid);
	
	Optional<OTP> findByCodeAndOtpid(Integer code, String otpid);
}
