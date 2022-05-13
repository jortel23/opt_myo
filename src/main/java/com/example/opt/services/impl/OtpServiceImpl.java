package com.example.opt.services.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.opt.dao.OtpDAO;
import com.example.opt.dto.GenerateDTO;
import com.example.opt.dto.ResultDTO;
import com.example.opt.dto.ValidateDTO;
import com.example.opt.entities.OTP;
import com.example.opt.services.OtpService;
import com.example.opt.services.ToolService;

@Service
public class OtpServiceImpl implements OtpService {
	
	private OtpDAO dao;
	private ToolService toolService;

    public OtpServiceImpl(OtpDAO dao, ToolService toolService) {
		this.dao = dao;
		this.toolService = toolService;
	}
	
	@Override
	public ResponseEntity<ResultDTO> generate(GenerateDTO phone) {
		if (phone ==null || !String.valueOf(phone.getTelephone()).matches("^2376\\d{8}"))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		OTP otp = new OTP();
		otp.setPhoneNumber(phone.getTelephone());
		otp.setOtpid(toolService.generateOtpid());
		otp.setCode(toolService.generateCode());
		
		OTP otpInsert = dao.save(otp);
		
		//send sms to user with phoneNumber
		
		
		return new ResponseEntity<ResultDTO>(new ResultDTO(otpInsert.getOtpid()), HttpStatus.ACCEPTED);
	}
	
	@Override
	public ResponseEntity<ResultDTO> validate(ValidateDTO validateDTO) {
		if(validateDTO.getCode()==null || validateDTO.getOtpid().isEmpty())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Optional<OTP> otp = dao.findByCodeAndOtpid(validateDTO.getCode(), validateDTO.getOtpid());
		
		if(otp.isPresent()) {
			return new ResponseEntity<ResultDTO>(new ResultDTO("success"), HttpStatus.ACCEPTED);
		}
		
		return new ResponseEntity<ResultDTO>( new ResultDTO("failed"), HttpStatus.ACCEPTED);
	}
	
	
}
