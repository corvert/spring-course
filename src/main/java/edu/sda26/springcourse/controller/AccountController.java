package edu.sda26.springcourse.controller;

import edu.sda26.springcourse.dto.*;
import edu.sda26.springcourse.exception.AccountExistException;
import edu.sda26.springcourse.exception.AccountNotFoundException;
import edu.sda26.springcourse.model.Account;
import edu.sda26.springcourse.model.Customer;
import edu.sda26.springcourse.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/accounts")
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping(path = "/account")
    public ResponseEntity<List<Account>> getAccountBtwMinAndMax(@RequestParam("min") Double min,
                                                                @RequestParam("max") Double max) {
        List<Account> accounts = accountService.findAccountBtwMinAndMax(min, max);
        if (accounts.size() < 1)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(accounts);
    }

    @GetMapping(path = "/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id) throws AccountNotFoundException {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping(path = "/account-with-positive-balance")
    public List<Account> getAccountWithPosBalance() {
        return accountService.findAccountWithPosBalance();
    }


    @PostMapping(path = "/customer/{id}/account")
    public ResponseEntity<AccountDto> createAccount(
            @PathVariable("id") Long customerId,
            @RequestBody AccountDto account) throws AccountExistException {

        AccountDto accountDto = accountService.save(customerId, account);
        return ResponseEntity.ok(accountDto);

    }

    @DeleteMapping(path = "/account/{id}")
    public ResponseEntity<String> removeAccount(@PathVariable("id") Long accountId) {

        accountService.removeAccount(accountId);
        return ResponseEntity.ok("Account with ID: " + accountId + " deleted successfully");
    }

    @PatchMapping(path = "/account/{id}/activate")
    public ResponseEntity<AccountDto> activateAccountStatus(@PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(accountService.updateAccountStatus(id));
    }

    @PutMapping(path = "/customer/{customerId}/account/{accountId}")
    public ResponseEntity<AccountDto> updateAccountBalance(@PathVariable("customerId") Long customerId,
                                                           @PathVariable("accountId") Long accountId,
                                                           @RequestBody AccountDto accountDto) {
        return ResponseEntity.accepted().body(accountService.updateAccountBalance(customerId, accountId, accountDto));
    }



}
