package com.userManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userManagement.entity.CountryEntity;

public interface CountryRepo extends JpaRepository<CountryEntity, Integer>{

}
