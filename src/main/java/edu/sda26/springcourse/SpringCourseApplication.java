package edu.sda26.springcourse;

import edu.sda26.springcourse.model.*;
import edu.sda26.springcourse.model.enums.TransactionType;
import edu.sda26.springcourse.repository.AccountRepository;
import edu.sda26.springcourse.repository.CustomerRepository;
import edu.sda26.springcourse.repository.TransactionRepository;
import edu.sda26.springcourse.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;


@SpringBootApplication
public class SpringCourseApplication implements CommandLineRunner {
	@Value("${sda26.description}")
	String description;


	private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
	private final UsersRepository usersRepository;

    public SpringCourseApplication(CustomerRepository customerRepository,
								   TransactionRepository transactionRepository, UsersRepository usersRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
		this.usersRepository = usersRepository;
	}

    @Autowired
    private AccountRepository accountRepository;


    public static void main(String[] args) {
        SpringApplication.run(SpringCourseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
		System.out.println(description);

		Customer customer1 = createCustomer(20, "Alex", "+11111111", "alex@doe.com", true);
		Customer customer2 = createCustomer(30, "Tina", "+22222222", "tina@tina.com", true);
		Customer customer3 = createCustomer(25, "Maxim", "+33333333", "maxim@doe.com", false);
		Customer customer4 = createCustomer(35, "John", "+44444444", "john@doe.com", true);



		Account account1 = createAccount(100.1, customer2.getId());
		Account account2 = createAccount(300.5, customer3.getId());
		Account account3 = createAccount(400.5, customer4.getId());
		Account account4 = createAccount(500.55, customer1.getId());



		Transaction transaction1 = createTransaction(LocalDate.now(), 200.5,
				TransactionType.DEPOSIT, account1.getId());
		Transaction transaction3 = createTransaction(LocalDate.now(), 150.5,
				TransactionType.DEPOSIT, account1.getId());
		Transaction transaction2 = createTransaction(LocalDate.now(), -100.1,
				TransactionType.WITHDRAW, account2.getId());
		Transaction transaction = createTransaction(LocalDate.now(), 505,
				TransactionType.DEPOSIT,account3.getId());
		Transaction transaction4 = createTransaction(LocalDate.now(), -305,
				TransactionType.WITHDRAW, account4.getId());


		System.out.println(customer1);
        System.out.println(account1);
        System.out.println(transaction1);

		System.out.println("list of all customes" + customerRepository.findAll());
		System.out.println(customerRepository.findById(2L).orElse(null));
		System.out.println("Delete custome by id 2" );
		//customerRepository.deleteById(2L);

		System.out.println("List of account" + accountRepository.findAll());
		//System.out.println("account bu id 2" + accountRepository.findById(2L).orElse(null));
		//accountRepository.deleteById(2L);
		System.out.println("List of accounts after deleted account 2" + accountRepository.findAll());

		System.out.println("list of trns" + transactionRepository.findAll());
		//System.out.println(transactionRepository.findById(2L).orElse(null));
		//transactionRepository.deleteById(2L);
		System.out.println(transactionRepository.findAll());
		//System.out.println(transactionRepository.findByTrnType("deposit").get(0));

        System.out.println("Hello Spring");

		Users myUser1 = new Users(1L,"user1", "123","USER", "user1@gmail.com");
		Users myUser2 = new Users(2L,"user2", "123","USER", "user2@gmail.com");
		Users admin1 = new Users(3L,"admin1", "123","ADMIN", "admin1@gmail.com");

		usersRepository.save(myUser1);
		usersRepository.save(myUser2);
		usersRepository.save(admin1);

    }

	private Transaction createTransaction(LocalDate date, double amount, TransactionType type, long accountId) {
		Transaction transaction = new Transaction();
		transaction.setTransactionDate(date);
		transaction.setAmount(amount);
		transaction.setTransactionType(type.getName());
		transaction.setAccountId(accountId);
		return transactionRepository.save(transaction);

	}

	private Account createAccount(double balance, Long customerId) {
		Account account = Account.builder()
				.balance(balance)
				.customerId(customerId)
				.active(true)
				.build();

//		Account account = new Account();
//		account.setBalance(balance);
//		account.setCustomerId(customerId);

		return accountRepository.save(account);

	}

	private Customer createCustomer(int age, String name, String phone, String email, Boolean active) {
		Customer c = Customer.builder()
				.age(age)
				.name(name)
				.phone(phone)
				.email(email)
				.active(active)
				.build();
//        Customer c = new Customer();
//        c.setAge(age);
//        c.setName(name);
//        c.setPhone(phone);
//        c.setEmail(email);
//		c.setActive(active);
        return customerRepository.save(c);
    }




}
