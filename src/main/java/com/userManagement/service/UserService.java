package com.userManagement.service;

import java.util.Map;

import com.userManagement.dto.LoginFormDto;
import com.userManagement.dto.RegisterFormDto;
import com.userManagement.dto.ResetPwdFormDto;
import com.userManagement.dto.UserDto;

public interface UserService {

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getState(Integer countryId);

	public Map<Integer, String> getCity(Integer stateId);

	public boolean duplicateEmailCheck(String email);

	public boolean saveUser(RegisterFormDto registerForm);

	public UserDto login(LoginFormDto loginFormDto);

	public boolean resetPwd(ResetPwdFormDto resetPwdFormDto);

}
