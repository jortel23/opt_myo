package com.example.opt.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.example.opt.dao.OtpDAO;
import com.example.opt.dto.GenerateDTO;
import com.example.opt.dto.ResultDTO;
import com.example.opt.dto.ValidateDTO;
import com.example.opt.entities.OTP;
import com.example.opt.services.ToolService;
import com.example.opt.services.impl.OtpServiceImpl;

@ExtendWith(MockitoExtension.class)
 class OtpServiceTest {

	@Mock
	OtpDAO dao;
	
	@Mock
	ToolService toolService;
	
	@InjectMocks
	OtpServiceImpl service;
	
	GenerateDTO generateDTO;
	ResultDTO dto;
	ValidateDTO valDto;
	OTP otp;
	

	
	@BeforeEach
	void setup() {
		generateDTO = new GenerateDTO();
	generateDTO.setTelephone(237634546576L);
		
		dto = new ResultDTO();
		
		dto.setResult("failed");
		
		valDto = new ValidateDTO();
		valDto.setCode(12345678);
		valDto.setOtpid("123456789def");
		
		otp = new OTP();
		otp.setCode(12345678);
		otp.setOtpid("123456789def");
		otp.setCreationDate(LocalDateTime.now());
		otp.setExpiredDate(LocalDateTime.now().plusMinutes(5));
		otp.setPhoneNumber(generateDTO.getTelephone());
		otp.setId(new SecureRandom().nextLong());
	}
	
	
  @Test
  void mustReturnHttpStatusBadRequest_WhenGenerateDTO_isNUll() {
	  assertEquals(HttpStatus.BAD_REQUEST,service.generate(new GenerateDTO()).getStatusCode());
  }
  
  @Test 
  void mustReturnHttpStatusBadRequest_WhenGenerateDTO_NotMatches2376x() {
	  generateDTO.setTelephone(345654L);
	  assertEquals(HttpStatus.BAD_REQUEST,service.generate(generateDTO).getStatusCode());
  }
		  
  @Test
  @DisplayName("must return resultDto otpid when generateDTO is ok") 
 void mustReturnResultDTO_WhenGenerateDTO_isNotNUll() {
	  when(toolService.generateOtpid()).thenReturn(otp.getOtpid());
	  when(toolService.generateCode()).thenReturn(otp.getCode());
	  when(dao.save(ArgumentMatchers.any())).thenReturn(otp);
	  
	  dto.setResult(otp.getOtpid());
	  assertEquals(dto,service.generate(generateDTO).getBody());
  }
  
  @Test
  @DisplayName("must return resultDto success when validateDTO is ok") 
  void mustReturnSuccess_WhenValidateDTO_isNotNUll() {
	  when(dao.findByCodeAndOtpid(ArgumentMatchers.anyInt(),ArgumentMatchers.anyString())).thenReturn(Optional.of(otp));
	  dto.setResult("success");
	  
	  assertEquals(dto,service.validate(valDto).getBody());
  }
 
	
	@Test 
	@DisplayName("must return resultDto failed when validateDTO is ok and code + otpid not found")
	void mustReturnFailed_WhenValidateDTO_isNotNUll() {
		when(dao.findByCodeAndOtpid(ArgumentMatchers.anyInt(),ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		
		assertEquals(dto,service.validate(valDto).getBody()); 
	}
	
	  @Test
	  @DisplayName("must return resultDto success when all in validateDTO is null") 
	  void mustReturnSuccess_WhenValidateDTO_isNUll() {
		valDto.setCode(null);
		valDto.setOtpid(null);
		  assertEquals(HttpStatus.BAD_REQUEST,service.validate(valDto).getStatusCode());
	  }
	  @Test
	  @DisplayName("must return resultDto success when code in validateDTO is null") 
	  void mustReturnSuccess_WhenCodeValidateDTO_isNUll() {
		valDto.setCode(null);
		  assertEquals(HttpStatus.BAD_REQUEST,service.validate(valDto).getStatusCode());
	  }
	  @Test
	  @DisplayName("must return resultDto success when otpid in validateDTO is null") 
	  void mustReturnSuccess_WhenOtpidValidateDTO_isNUll() {
		valDto.setOtpid(null);
		  assertEquals(HttpStatus.BAD_REQUEST,service.validate(valDto).getStatusCode());
	  }
}
