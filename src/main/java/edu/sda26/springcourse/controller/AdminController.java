package edu.sda26.springcourse.controller;

import edu.sda26.springcourse.dto.AccountDto;
import edu.sda26.springcourse.dto.CustomerDto;
import edu.sda26.springcourse.dto.TransactionDto;
import edu.sda26.springcourse.dto.UsersDto;
import edu.sda26.springcourse.exception.AccountExistException;
import edu.sda26.springcourse.exception.AccountNotFoundException;
import edu.sda26.springcourse.exception.CustomerNotFoundException;
import edu.sda26.springcourse.exception.TransactionNotFoundException;
import edu.sda26.springcourse.model.Account;
import edu.sda26.springcourse.model.Customer;
import edu.sda26.springcourse.model.Transaction;
import edu.sda26.springcourse.model.Users;
import edu.sda26.springcourse.service.AccountService;
import edu.sda26.springcourse.service.CustomerService;
import edu.sda26.springcourse.service.TransactionService;
import edu.sda26.springcourse.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    private final CustomerService customerService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final UsersService usersService;

    public AdminController(CustomerService customerService, AccountService accountService, TransactionService transactionService, UsersService usersService) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.usersService = usersService;
    }

    @GetMapping("/")
    public String getCustomers(final ModelMap modelMap) {
        List<CustomerDto> customerList = customerService.getAllCustomers();
        modelMap.addAttribute("customersDtoList", customerList);
        return "index";
    }

    @GetMapping("/admin/accounts")
    public String getAccounts(final ModelMap modelMap) {
        List<AccountDto> accountList = accountService.getAllAccounts();
        modelMap.addAttribute("accountDtoList", accountList);
        return "account";
    }

    @GetMapping("/admin/transactions")
    public String getTransactions(final ModelMap modelMap) {
        List<TransactionDto> transactionList = transactionService.getAllTransactions();
        modelMap.addAttribute("transactionDtoList", transactionList);
        return "transactions";
    }

    @GetMapping("/admin/customer/{id}/accounts")
    public String getAccountByCustomer(final ModelMap modelMap, @PathVariable("id") Long id) throws AccountNotFoundException, CustomerNotFoundException, AccountExistException {
        Account account = accountService.findAccountByCustomerId(id);
        modelMap.addAttribute("account", account);
        return "customer-account";
    }

    @GetMapping("/admin/customer/{id}/account")
    public String showCreateAccountPage(final ModelMap modelMap,
                                        @PathVariable("id") Long id) {

        Account account = null;
        try {
            account = accountService.findAccountByCustomerId(id);
        } catch (AccountNotFoundException ignored) {
        }

        if(account != null)
            return "internal-error";

        AccountDto accountDto =  new AccountDto();
        accountDto.setCustomerId(id);
        modelMap.addAttribute("accountDto", accountDto);
        return "create-account";
    }

    @GetMapping("/admin/customer/{id}/account/transactions")
    public String getTransactionsByAccount(final ModelMap modelMap, @PathVariable("id") Long id) throws AccountNotFoundException, CustomerNotFoundException {
        //Customer customer = customerService.getCustomerById(id);
        Account account = accountService.findAccountByCustomerId(id);
        List<TransactionDto> transactionDtoList = transactionService.findTransactionsByAccountId(account.getId());
        modelMap.addAttribute("transactionDtoList", transactionDtoList);
        return "transactions";
    }

    //Create customer
    @GetMapping("/admin/customer/create")
    public String showCreateCustomerForm(ModelMap modelMap) {
        Customer customer = new Customer();
        modelMap.addAttribute("customer", customer);
        return "create-customer";
    }
    @PostMapping("/admin/customer")
    public String createCustomer(@ModelAttribute("customer") Customer customer) {
        customer.setActive(true);
        customerService.save(customer);
        return "redirect:/";
    }


    // Create Account
    @GetMapping("/admin/account/create")
    public String showCreateAccountForm(ModelMap modelMap) throws CustomerNotFoundException {
        AccountDto accountDto = new AccountDto();
        modelMap.addAttribute("accountDto", accountDto);
        return "create-account";
    }

    @PostMapping("/admin/accounts")
    public String createAccount(@ModelAttribute("accountDto") AccountDto accountDto) throws AccountExistException {
        accountService.save(accountDto.getCustomerId(), accountDto);
        return "redirect:/admin/accounts";
    }

    @GetMapping("/admin/customer/{id}/account/transaction")
    public String showCreateTransaction(ModelMap modelMap,
                                        @PathVariable("id") Long customerId) throws AccountNotFoundException {
        Transaction transaction = new Transaction();
        Account account = accountService.findAccountByCustomerId(customerId);
        transaction.setAccountId(account.getId());
        modelMap.addAttribute("transaction", transaction);
        return "create-transaction";
    }

    @PostMapping("/admin/transaction/create")
    public String createTransaction(@ModelAttribute("transaction") Transaction transaction) throws AccountNotFoundException {


        Account account = accountService.findAccountById(transaction.getAccountId());
        transactionService.save(transaction, account);
        return "redirect:/";
    }


    @GetMapping("/admin/transaction/create")
    public String showCreateTransactionForm(final ModelMap modelMap) throws TransactionNotFoundException {
        Transaction transaction = new Transaction();
        modelMap.addAttribute("transaction", transaction);
        return "create-transaction";
    }



















    @GetMapping("/admin/customer/account/transaction")
    public String showCreateTransaction(ModelMap modelMap
    ) throws AccountNotFoundException {
        Transaction transaction = new Transaction();
        modelMap.addAttribute("transaction", transaction);
        return "create-blank-transaction";
    }







}
