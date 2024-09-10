package com.userManagement.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class LoginFormDto {

	private String email;
	
	private String pwd;

}
