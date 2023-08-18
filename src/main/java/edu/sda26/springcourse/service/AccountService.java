package edu.sda26.springcourse.service;

import edu.sda26.springcourse.dto.AccountDto;
import edu.sda26.springcourse.dto.CustomerDto;
import edu.sda26.springcourse.exception.AccountExistException;
import edu.sda26.springcourse.exception.AccountNotFoundException;
import edu.sda26.springcourse.model.Account;
import edu.sda26.springcourse.model.Customer;
import edu.sda26.springcourse.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return toAccountDto(accounts);
    }

    public Account getAccountById(Long id) throws AccountNotFoundException {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found", 2));
    }

    public List<Account> findAccountWithPosBalance() {
        return accountRepository.findAllByBalanceGreaterThan(0.0);

//        return accountRepository.findAll()
//                .stream()
//                .filter(account -> account.getBalance() > 0)
//                .collect(Collectors.toList());
    }

    public Account findAccountByCustomerId(Long customerId) throws AccountNotFoundException {
        Account account = accountRepository.findByCustomerId(customerId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found", 20);
        }
        return account;
    }

    public List<Account> findAccountBtwMinAndMax(Double min, Double max) {

        return accountRepository.findAllByBalanceGreaterThanAndBalanceLessThan(min, max);

//        return accountRepository.findAll()
//                .stream()
//                .filter(account -> account.getBalance() > min)
//                .filter(account -> account.getBalance() < max)
//                .collect(Collectors.toList());
    }

    public AccountDto save(Long customerId, AccountDto accountDto) throws AccountExistException {

        Account account = toAccount(customerId, accountDto);
        Account savedAccount = accountRepository.save(account);
        return toAccountDto(savedAccount);
    }

    public Account save(Long customerId, Account account) {
        account.setCustomerId(customerId);
        return accountRepository.save(account);
    }

    private Account toAccount(Long customerId, AccountDto accountDto) {
        Account account = new Account();
        account.setBalance(accountDto.getBalance());
        account.setCustomerId(customerId);
        return account;
    }

    public void removeAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    public AccountDto updateAccountBalance(Long customerId, Long accountId, AccountDto accountDto) {
        Account account = toAccount(customerId, accountId, accountDto);
        Account updatedAccount = accountRepository.save(account);
        return toAccountDto(updatedAccount);
    }

    private Account toAccount(Long customerId, Long accountId, AccountDto accountDto) {
        Account account = new Account();
        account.setBalance(accountDto.getBalance());
        account.setCustomerId(customerId);
        account.setId(accountId);
        return account;
    }

    private AccountDto toAccountDto(Account savedAccount) {
        AccountDto savedAccountDto = new AccountDto();
        savedAccountDto.setBalance(savedAccount.getBalance());
        savedAccountDto.setCustomerId(savedAccount.getCustomerId());
        savedAccountDto.setId(savedAccount.getId());
        savedAccountDto.setActive(savedAccount.getActive());
        return savedAccountDto;
    }

    public AccountDto updateAccountStatus(Long id) {
        Account account = accountRepository.findById(id).orElse(null);

        if (account == null) {
            return null;
        } else {
            account.setActive(true);
        }
        return toAccountDto(accountRepository.save(account));
    }







    public AccountDto save(AccountDto accountDto) {
        Account account = toAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return toAccountDto(savedAccount);
    }

    private Account toAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setBalance(accountDto.getBalance());
        account.setCustomerId(accountDto.getCustomerId());
        account.setActive(accountDto.getActive());
        return account;
    }














    private List<AccountDto> toAccountDto(List<Account> accounts) {
        List<AccountDto> accountDtoList = new ArrayList<>();
        for (Account account : accounts) {
            accountDtoList.add(toAccountDto(account));
        }
        return accountDtoList;
    }


    public Account findAccountById(Long accountId) throws AccountNotFoundException {
        return accountRepository.findById(accountId).orElseThrow(()->new AccountNotFoundException("Account not found", 2));
    }
}
