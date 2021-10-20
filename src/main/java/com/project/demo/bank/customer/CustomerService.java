package com.project.demo.bank.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> getAllCustomers() {

		List<Customer> customers = new ArrayList<Customer>();
		customerRepository.findAll().forEach(customers::add);
		return customers;
	}

	public Customer getCustomerByID(String id) {

		return customerRepository.findById(id).get();
	}

	public void addCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public void updateCustomer(Customer customer, String id) {
		customerRepository.save(customer);
	}

	public void deleteAll() {
		customerRepository.deleteAll();
	}

	public void deleteCustomerByID(String id) {
		customerRepository.deleteById(id);
	}

	public void transferFunds(String fromCustomerId, String toCustomerId, BigDecimal amountToTransfer) {
		Customer fromCustomer = customerRepository.findById(fromCustomerId).get();
		Customer toCustomer = customerRepository.findById(toCustomerId).get();
		BigDecimal existingBalance = fromCustomer.getAccount().getAmount();
		BigDecimal amount = toCustomer.getAccount().getAmount();
		if (amountToTransfer.intValue() > 0 && existingBalance.compareTo(amountToTransfer) > 0) {
			System.out.println("before transfer");
			System.out.println("existing balance \t" + existingBalance);
			System.out.println("amount \t" + amount);
			amount=amount.add(amountToTransfer);
			toCustomer.getAccount().setAmount(amount);
			existingBalance=existingBalance.subtract(amountToTransfer);
			fromCustomer.getAccount().setAmount(existingBalance);
			System.out.println("after transfer");
			System.out.println("existing balance \t" + existingBalance);
			System.out.println("amount \t" + amount);
			List<Customer> customers = new ArrayList<>();
			customers.add(fromCustomer);
			customers.add(toCustomer);
			customerRepository.saveAll(customers);

		} else {
			System.out.println("insufficient fund");
		}
	}

}
