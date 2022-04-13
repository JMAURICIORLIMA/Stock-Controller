package com.stock.movement.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO {

	private Map<Object, Object> headers;
	private Map<Object, Object> claims;
	private String tokenValue;

}
