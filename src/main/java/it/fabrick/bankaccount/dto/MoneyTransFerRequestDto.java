package it.fabrick.bankaccount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransFerRequestDto {
	private Long accountId;
	private String receiverName;
	private String description;
	private String currency;
	private String amount;
	private String executionDate;

}
