package com.userManagement.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.userManagement.dto.QuoteApiResponseDto;
import com.userManagement.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	private String quoteApiURL = "https://dummyjson.com/quotes/random";

	@Override
	public QuoteApiResponseDto getQuote() {

		RestTemplate rt = new RestTemplate();

		// send HTTP Get req and store Response into QuoteApiResponseDto object
		ResponseEntity<QuoteApiResponseDto> forEntity = rt.getForEntity(quoteApiURL, QuoteApiResponseDto.class);

		QuoteApiResponseDto body = forEntity.getBody();

		return body;
	}

}
