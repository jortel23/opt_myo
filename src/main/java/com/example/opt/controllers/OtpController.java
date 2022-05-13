package com.example.opt.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.opt.dto.GenerateDTO;
import com.example.opt.dto.ResultDTO;
import com.example.opt.dto.ValidateDTO;
import com.example.opt.services.OtpService;

@RestController
public class OtpController {

	private OtpService service;

	public OtpController(OtpService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<ResultDTO> generate(@RequestBody GenerateDTO generateDTO){
		return service.generate(generateDTO);
	}
	
	@GetMapping
	public ResponseEntity<ResultDTO> validate(@RequestBody ValidateDTO validateDTO){
		return service.validate(validateDTO);
	}
		
}
