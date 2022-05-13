package com.example.opt.services;

import org.springframework.http.ResponseEntity;
import com.example.opt.dto.GenerateDTO;
import com.example.opt.dto.ResultDTO;
import com.example.opt.dto.ValidateDTO;


public interface OtpService {

	public ResponseEntity<ResultDTO> generate(GenerateDTO phoneNumber);
	
	public ResponseEntity<ResultDTO> validate(ValidateDTO validateDTO);
	
}
