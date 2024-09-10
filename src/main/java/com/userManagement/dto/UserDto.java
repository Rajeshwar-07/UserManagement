package com.userManagement.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UserDto {

	private Integer userId;

	private String uname;

	private String email;

	private String pwd;

	private String pwdUpdated;

	private Long phno;

	private Integer countryId;

	private Integer stateId;

	private Integer cityId;
}
