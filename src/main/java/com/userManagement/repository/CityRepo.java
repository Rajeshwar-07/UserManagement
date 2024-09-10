package com.userManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userManagement.entity.CityEntity;

public interface CityRepo extends JpaRepository<CityEntity, Integer> {

	public List<CityEntity> findByStateId(Integer stateId);
}
