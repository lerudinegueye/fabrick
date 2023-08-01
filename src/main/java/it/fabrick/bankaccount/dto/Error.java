package it.fabrick.bankaccount.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
	@JsonProperty("code")
	private Integer code;
	@JsonProperty("description")
	private String description;
	
}
