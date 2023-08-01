package it.fabrick.bankaccount.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponseModel {
	@JsonProperty("code")
	private String code = null;
	@JsonProperty("message")
	private String message = null;

}
