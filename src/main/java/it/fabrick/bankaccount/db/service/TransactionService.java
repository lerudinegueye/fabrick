package it.fabrick.bankaccount.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.fabrick.bankaccount.db.entities.Transaction;
import it.fabrick.bankaccount.db.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public void saveTransaction(List<Transaction> transactionList) {
		transactionRepository.saveAllAndFlush(transactionList);
	}

}
