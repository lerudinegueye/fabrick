package it.fabrick.bankaccount.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "error", "payload" })
public class BankAccountDto {
	@JsonProperty("status")
	private String status;
	@JsonProperty("error")
	private List<Error> error;
	@JsonProperty("payload")
	private PayLoad payload;

}
