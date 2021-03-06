package com.project.demo.bank.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "bank-demo")
public class AccountController {

	@Autowired
	private AccountService accountService;

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAllAccounts() {
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "request to return all accounts");
		List<Account> accounts = accountService.getAllAccounts();
		if (accounts != null && !accounts.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(accounts);
		} else
			return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).build();
	}

	@GetMapping("/accounts/{id}")
	public ResponseEntity<Account> getAccountByID(@PathVariable int id) {
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "request to return account associated with provided id");
		Account accountByID = accountService.getAccountByID(id);
		if (accountByID != null) {
			return new ResponseEntity<Account>(accountByID, header, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).build();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/accounts")
	public ResponseEntity<Void> addAccount(@RequestBody Account account) {
		accountService.addAccount(account);
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "request to add an account");
		return ResponseEntity.status(HttpStatus.CREATED).headers(header).build();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/accounts/{id}")
	public ResponseEntity<Void> updateAccount(@RequestBody Account account, @PathVariable int id) {
		accountService.updateAccount(account, id);
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "this request will either update existing account or create a new account");
		return ResponseEntity.status(HttpStatus.CREATED).headers(header).build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/accounts")
	public ResponseEntity<Void> deleteAll() {
		accountService.deleteAll();
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "request to delete all accounts");
		return ResponseEntity.status(HttpStatus.OK).headers(header).build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/accounts/{id}")
	public ResponseEntity<Void> deleteAccountByID(@PathVariable int id) {
		accountService.deleteAccountByID(id);
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "request to delete account associated with provided id");
		return ResponseEntity.status(HttpStatus.OK).headers(header).build();
	}
}
