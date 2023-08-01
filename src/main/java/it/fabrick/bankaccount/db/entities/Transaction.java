package it.fabrick.bankaccount.db.entities;

import java.io.Serializable;

import it.fabrick.bankaccount.enumeration.TypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TRANSACTION")
public class Transaction implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Long transactionId;

	private String operationId;
	
	private String accountingDate;

	private String valueDate;

	private TypeEnum type;

	private String amount;

	private String currency;

	private String description;

}
