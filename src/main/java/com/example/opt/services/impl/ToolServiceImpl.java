package com.example.opt.services.impl;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.opt.dao.OtpDAO;
import com.example.opt.services.ToolService;

@Service
public class ToolServiceImpl implements ToolService {
	private OtpDAO dao;

	
	public ToolServiceImpl(OtpDAO dao) {
		this.dao = dao;
	}

	@Override
	public String generateOtpid() {
		String characters = "abcdefghijklomnpqrstuvxyzABCDEFGHIJKLOMNPQRSTUVWXYZ1234567890";

		String otpIdGenerate;
		
		do {
			
			StringBuilder stringBuilder = new StringBuilder(12);
			SecureRandom random = new SecureRandom();
			
			int limit = characters.length();

			for (int i = 0; i < 12; i++) {
				stringBuilder.append(characters.charAt(random.nextInt(limit)));
			}
			
			otpIdGenerate = stringBuilder.toString();
			
		}while(dao.findByOtpid(otpIdGenerate).isPresent());
		
		return otpIdGenerate;
	}
	
	@Override
	public Integer generateCode() {
		Random random = new Random();
		Integer numbers = null;

		for (int i = 0; i < 8; i++) {
			numbers = random.nextInt(99999999);
		}
		
		return numbers;
			
		
	}
}
