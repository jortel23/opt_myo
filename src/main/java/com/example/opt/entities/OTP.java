package com.example.opt.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTP {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="reference", nullable = false, length=12)
	private Long phoneNumber;
	
	@Column(unique=true, length = 16,nullable = false)
	private String otpid;
	
	@Column(length = 8,nullable = false)
	private Integer code;
	
	@Column(nullable = false)
	private LocalDateTime creationDate = LocalDateTime.now();
	
	@Column(nullable = false)
	private LocalDateTime expiredDate = LocalDateTime.now().plusMinutes(5);
	

	
}
