package com.userManagement.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "user_name")
	private String uname;

	@Column(name = "email")
	private String email;

	@Column(name = "pwd")
	private String pwd;

	@Column(name = "pwd_update")
	private String pwdUpdated;

	@Column(name = "phno")
	private Long phno;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryEntity country;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private StateEntity stateId;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private CityEntity cityId;

	@CreationTimestamp
	@Column(name = "create_date", updatable = false)
	private LocalDate createdDate;

	@UpdateTimestamp
	@Column(name = "updated_date", insertable = false)
	private LocalDate updatedDate;
}
