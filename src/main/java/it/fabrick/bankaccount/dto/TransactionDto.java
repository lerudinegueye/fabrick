package it.fabrick.bankaccount.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import it.fabrick.bankaccount.enumeration.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "transactionId", "operationId", "accountingDate","valueDate" ,"type" ,"amount" ,"currency" ,"description" })
public class TransactionDto {
	@JsonProperty("transactionId")
	private String transactionId;
	
	@JsonProperty("operationId")
	private String operationId;
	
	@JsonProperty("accountingDate")
	private String accountingDate;
	
	@JsonProperty("valueDate")
	private String valueDate;
	
	@JsonProperty("type")
	private TypeEnum type;
	
	@JsonProperty("amount")
	private String amount;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("description")
	private String description;
}
