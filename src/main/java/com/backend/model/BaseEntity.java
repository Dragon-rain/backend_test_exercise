package com.backend.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreatedDate
	@Column(name = "created")
	private String created;
	
	@LastModifiedDate
	@Column(name = "updated")
	private String updated;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	public BaseEntity() {
		// TODO Auto-generated constructor stub
	}

}
