package com.github.erf88;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;
	private Scanner reader;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.reader = new Scanner(System.in);
		Integer op = 0;
		do {
			System.out.println("------------ MENU ------------");
			System.out.println("1 - FindAll Customer");
			System.out.println("2 - FindOne Customer");
			System.out.println("3 - Create Customer");
			System.out.println("4 - Update Customer");
			System.out.println("5 - Delete Customer");
			System.out.println("0 - Exit");
			System.out.println("------------------------------");

			op = this.reader.nextInt();
			this.reader.nextLine();			

			switch (op) {
			case 1:
				
				findAll();
				break;
			case 2:
				findOne();
				break;
			case 3:
				create();
				break;
			case 4:
				update();	
				break;
			case 5:
				delete();							
				break;
			default:
				System.out.println("Invalid Option");
				break;
			}
			
		} while (op != 0);
		
	}

	private void findAll() {
		repository.findAll().forEach(System.out::println);
	}
	
	private void findOne() {
		System.out.println("Enter customer id: ");
		String id = this.reader.next();
		repository.findById(id).ifPresentOrElse(System.out::println, () -> System.out.println("Customer not found."));
	}

	private void create() {
		System.out.println("Enter customer name: ");
		String name = this.reader.next();
		System.out.println("Enter customer email: ");
		String email = this.reader.next();
		repository.insert(new Customer(name, email));		
	}
	
	private void update() {
		System.out.println("Enter customer id: ");
		String id = this.reader.next();	

		repository.findById(id).ifPresentOrElse(customer -> {
			
			System.out.println("Enter new customer name: ");
			customer.setName(reader.next());
			System.out.println("Enter new customer email: ");
			customer.setEmail(reader.next());
			repository.save(customer);
			
		}, () -> System.out.println("Customer not found."));
	}
	
	private void delete() {
		System.out.println("Enter customer id: ");
		String id = this.reader.next();		
		repository.findById(id).ifPresentOrElse(customer -> repository.delete(customer), () -> System.out.println("Customer not found."));
	}

}
