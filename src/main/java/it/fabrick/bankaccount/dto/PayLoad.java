package it.fabrick.bankaccount.dto;

import java.util.Date;

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
@JsonPropertyOrder({ "date", "balance", "availableBalance", "currency" })
public class PayLoad {
	@JsonProperty("date")
	private Date date;
	@JsonProperty("balance")
	private double balance;
	@JsonProperty("availableBalance")
	private double availableBalance;
	@JsonProperty("currency")
	private String currency;
}
