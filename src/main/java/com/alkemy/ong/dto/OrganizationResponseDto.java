package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponseDto {
	private String name;
	private String image; 
	private Long phone;
	private String address;
}