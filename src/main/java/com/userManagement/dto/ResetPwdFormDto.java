package com.userManagement.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ResetPwdFormDto {

	private String email;

	private String oldPwd;

	private String newPwd;

	private String confirmPwd;
}
