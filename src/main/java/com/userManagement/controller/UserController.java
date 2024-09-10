package com.userManagement.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.userManagement.dto.LoginFormDto;
import com.userManagement.dto.QuoteApiResponseDto;
import com.userManagement.dto.RegisterFormDto;
import com.userManagement.dto.ResetPwdFormDto;
import com.userManagement.dto.UserDto;
import com.userManagement.service.DashboardService;
import com.userManagement.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/")
	public String index(Model model) {
		LoginFormDto loginFormDto = new LoginFormDto();
		model.addAttribute("loginForm", loginFormDto);
		return "login";
	}

	@GetMapping("/register")
	public String loadRegisterPage(Model model) {
		Map<Integer, String> countriesMap = userService.getCountries();
		model.addAttribute("countries", countriesMap);

		RegisterFormDto registerFormDto = new RegisterFormDto();
		model.addAttribute("registerForm", registerFormDto);

		return "register";
	}

	@GetMapping("/states/{countryId}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable Integer countryId, Model model) {
		Map<Integer, String> statesMap = userService.getState(countryId);
		return statesMap;
	}

	@GetMapping("/cities/{stateId}")
	@ResponseBody
	public Map<Integer, String> getCities(@PathVariable Integer stateId, Model model) {
		Map<Integer, String> citiesMap = userService.getCity(stateId);
		return citiesMap;
	}

	@PostMapping("/register")
	public String handleRegisteration(RegisterFormDto registerFormDto, Model model) {
		boolean status = userService.duplicateEmailCheck(registerFormDto.getEmail());

		if (status) {
			model.addAttribute("emsg", "Duplicate Email Found");
		} else {
			boolean savedUser = userService.saveUser(registerFormDto);
			if (savedUser) {
				model.addAttribute("smsg", "Registration Successfull, Please check your email..!!");
			} else {
				model.addAttribute("emsg", "Registration Failed");
			}
		}
		model.addAttribute("registerForm", new RegisterFormDto());
		model.addAttribute("countries", userService.getCountries());
		return "register";
	}

	@PostMapping("/login")
	public String handleUserLogin(LoginFormDto loginFormDto, Model model) {

		UserDto userDTO = userService.login(loginFormDto);

		if (userDTO == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			model.addAttribute("loginForm", new LoginFormDto());
		} else {
			String pwdUpdated = userDTO.getPwdUpdated();
			if ("Yes".equals(pwdUpdated)) {
				// display dashboard
				return "redirect:dashboard";
			} else {
				// display reset pwd page
				return "redirect:reset-pwd-page?email=" + userDTO.getEmail();

			}
		}
		return "login";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		QuoteApiResponseDto quoteApiResponseDto = dashboardService.getQuote();
		model.addAttribute("quote", quoteApiResponseDto);
		return "dashboard";
	}

	@GetMapping("/reset-pwd-page")
	public String loadResetPwdPage(@RequestParam String email, Model model) {

		ResetPwdFormDto resetPwdFormDTO = new ResetPwdFormDto();
		resetPwdFormDTO.setEmail(email);

		model.addAttribute("resetPwd", resetPwdFormDTO);
		return "resetPwd";
	}

	@PostMapping("/resetPwd")
	public String handlePwdReset(ResetPwdFormDto resetPwdFormDto, Model model) {

		boolean resetPwd = userService.resetPwd(resetPwdFormDto);
		if (resetPwd) {
			return "redirect:dashboard";
		}
		return "resetPwd";
	}

}
