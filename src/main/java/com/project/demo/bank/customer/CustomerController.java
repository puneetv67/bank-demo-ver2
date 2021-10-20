package com.project.demo.bank.customer;

import java.math.BigDecimal;
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
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "request to return all customers");
		if (customers != null && !customers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).headers(header).body(customers);
		} else
			return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).build();
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerByID(@PathVariable String id) {
		Customer customerByID = customerService.getCustomerByID(id);
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "request to return customer associated with provided id");
		if (customerByID != null) {
			return new ResponseEntity<Customer>(customerByID, header, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).build();
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/customers")
	public ResponseEntity<Void> addCustomer(@RequestBody Customer customer) {
		customerService.addCustomer(customer);
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "request to add a customer");
		return ResponseEntity.status(HttpStatus.CREATED).headers(header).build();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/customers/{id}")
	public ResponseEntity<Void> updateCustomer(@RequestBody Customer customer, @PathVariable String id) {
		customerService.updateCustomer(customer, id);
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "this request will either update existing customer or create a new customer");
		return ResponseEntity.status(HttpStatus.CREATED).headers(header).build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/customers")
	public ResponseEntity<Void> deleteAll() {
		customerService.deleteAll();
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "request to delete all customers");
		return ResponseEntity.status(HttpStatus.OK).headers(header).build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/customers/{id}")
	public ResponseEntity<Void> deleteCustomerByID(@PathVariable String id) {
		customerService.deleteCustomerByID(id);
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "request to delete customer associated with provided id");
		return ResponseEntity.status(HttpStatus.OK).headers(header).build();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customers/transfers/{fromCustomerId}/{toCustomerId}/{amount}")
	public ResponseEntity<Void> transferFunds(@PathVariable String fromCustomerId, @PathVariable String toCustomerId,
			@PathVariable BigDecimal amount) {
		customerService.transferFunds(fromCustomerId, toCustomerId, amount);
		HttpHeaders header = new HttpHeaders();
		header.add("desc", "request to transfer funds between customers provided");
		return ResponseEntity.status(HttpStatus.OK).headers(header).build();
	}
}
