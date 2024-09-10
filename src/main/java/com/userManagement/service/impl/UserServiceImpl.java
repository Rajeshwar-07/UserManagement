package com.userManagement.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userManagement.dto.LoginFormDto;
import com.userManagement.dto.RegisterFormDto;
import com.userManagement.dto.ResetPwdFormDto;
import com.userManagement.dto.UserDto;
import com.userManagement.entity.CityEntity;
import com.userManagement.entity.CountryEntity;
import com.userManagement.entity.StateEntity;
import com.userManagement.entity.UserEntity;
import com.userManagement.repository.CityRepo;
import com.userManagement.repository.CountryRepo;
import com.userManagement.repository.StateRepo;
import com.userManagement.repository.UserRepo;
import com.userManagement.service.EmailService;
import com.userManagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CountryRepo countryRepo;

	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private CityRepo cityRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailService emailService;

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryEntity> countriesList = countryRepo.findAll();
		Map<Integer, String> countryMap = new HashMap<>();
		countriesList.stream().forEach(c -> {
			countryMap.put(c.getCountryId(), c.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getState(Integer countryId) {
		Map<Integer, String> statesMap = new HashMap<>();

		List<StateEntity> statesList = stateRepo.findByCountryId(countryId);
		statesList.forEach(s -> {
			statesMap.put(s.getStateId(), s.getStateName());
		});
		return statesMap;
	}

	@Override
	public Map<Integer, String> getCity(Integer stateId) {

		Map<Integer, String> citiesMap = new HashMap<>();

		List<CityEntity> citiesList = cityRepo.findByStateId(stateId);
		citiesList.forEach(c -> {
			citiesMap.put(c.getCityId(), c.getCityName());
		});
		return citiesMap;
	}

	@Override
	public boolean duplicateEmailCheck(String email) {
		UserEntity byEmail = userRepo.findByEmail(email);
		if (byEmail != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean saveUser(RegisterFormDto registerFormDto) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(registerFormDto, userEntity);

		CountryEntity country = countryRepo.findById(registerFormDto.getCityId()).orElse(null);
		userEntity.setCountry(country);

		StateEntity state = stateRepo.findById(registerFormDto.getStateId()).orElse(null);
		userEntity.setStateId(state);

		CityEntity city = cityRepo.findById(registerFormDto.getCityId()).orElse(null);
		userEntity.setCityId(city);

		String randomPwd = generateRandomPwd();

		userEntity.setPwd(randomPwd);
		userEntity.setPwdUpdated("No");

		UserEntity savedUser = userRepo.save(userEntity);

		if (null != savedUser.getUserId()) {

			String subject = "Your Account Created";
			String body = "Your Password To Login : " + randomPwd;
			String to = registerFormDto.getEmail();

			emailService.sendEmail(subject, body, to);

			return true;
		}
		return false;
	}

	@Override
	public UserDto login(LoginFormDto loginFormDto) {
		UserEntity userEntity = userRepo.findByEmailAndPwd(loginFormDto.getEmail(), loginFormDto.getPwd());

		if (userEntity != null) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			return userDto;
		}
		return null;
	}

//	private UserEntity convertToRegisterFoirmDto(RegisterFormDto formDto) {
//		UserEntity user = new UserEntity();
//		user.setUname(formDto.getUname());
//		user.setEmail(formDto.getEmail());
//		user.setPhno(formDto.getPhno());
//		return user;
//	}

	@Override
	public boolean resetPwd(ResetPwdFormDto resetPwdFormDto) {

		String email = resetPwdFormDto.getEmail();

		UserEntity entity = userRepo.findByEmail(email);

		entity.setPwd(resetPwdFormDto.getNewPwd());

		entity.setPwdUpdated("Yes");
		userRepo.save(entity);
		return true;
	}

	private String generateRandomPwd() {
		String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";

		String alphbets = upperCaseLetters + lowerCaseLetters;

		Random random = new Random();

		StringBuffer generatedPwd = new StringBuffer();

		for (int i = 0; i < 5; i++) {
			int randomIndex = random.nextInt(alphbets.length());
			generatedPwd.append(alphbets.charAt(randomIndex));
		}
		return generatedPwd.toString();
	}

}
