package com.project.demo.bank.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;

	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public List<Account> getAllAccounts() {

		List<Account> accounts = new ArrayList<>();
		accountRepository.findAll().forEach(accounts::add);
		return accounts;
	}

	public Account getAccountByID(int id) {
		return accountRepository.findById(id).get();
	}

	public void addAccount(Account account) {
		accountRepository.save(account);
	}

	public void updateAccount(Account account, int id) {
		accountRepository.save(account);
	}

	public void deleteAll() {
		accountRepository.deleteAll();
	}

	public void deleteAccountByID(int id) {
		accountRepository.deleteById(id);
	}

}
