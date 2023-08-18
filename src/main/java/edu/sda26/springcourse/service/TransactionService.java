package edu.sda26.springcourse.service;

//import edu.sda26.springcourse.model.BalanceDtoResponse;
import edu.sda26.springcourse.dto.TransactionDto;
import edu.sda26.springcourse.exception.TransactionNotFoundException;
import edu.sda26.springcourse.model.Account;
import edu.sda26.springcourse.model.BalanceDtoResponse;
import edu.sda26.springcourse.model.Transaction;
import edu.sda26.springcourse.model.enums.TransactionType;
import edu.sda26.springcourse.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service

public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }




    public List<TransactionDto> findTransactionsByAccountId(Long accountID) {
        List<Transaction> transactions = transactionRepository.findAllByAccountId(accountID);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setId(transaction.getId());
            transactionDto.setAmount(transaction.getAmount());
            transactionDto.setTransactionType(transaction.getTransactionType());
            transactionDto.setTransactionDate(transaction.getTransactionDate());
            transactionDtoList.add(transactionDto);
        }
        return transactionDtoList;
    }

    public TransactionDto getTransactionById(Long transactionId) throws TransactionNotFoundException {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(()-> new TransactionNotFoundException("Transaction not found", 5));

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionDate(transaction.getTransactionDate());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setTransactionType(transaction.getTransactionType());
        return transactionDto;
    }

    public TransactionDto save(Long accountId, TransactionDto transactionDto) {
        Transaction transaction = toTransaction(accountId, transactionDto);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return toTransactionDto(savedTransaction);
    }

    public TransactionDto refundTransactionDeposit(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        TransactionDto transactionDto = null;
        if (transaction != null) {
            if (transaction.getTransactionType().equalsIgnoreCase("withdraw")) {
                return null;
            } else {
                transaction.setTransactionType(TransactionType.WITHDRAW.getName());
                transaction.setAmount(transaction.getAmount() * (-1));
                transactionDto = toTransactionDto(transactionRepository.save(transaction));
            }
        }
        return transactionDto;
    }

    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return toTransactionDto(transactions);
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public BalanceDtoResponse save(Transaction transaction, Account account) {
        Transaction transaction1;
        Account account1;
        if (transaction.getTransactionType().equalsIgnoreCase("Deposit")) {
            transaction1 = transactionRepository.save(transaction);
            account1 = calculateAccountBalance(transaction, account, "Deposit");
        } else {
            if (transaction.getAmount() > account.getBalance()) {
                throw new IllegalStateException("Transaction amount exceeded Account Balance");
            }
            transaction1 = transactionRepository.save(transaction);
            account1 = calculateAccountBalance(transaction, account, "Withdraw");
        }
        return new BalanceDtoResponse(transaction1, account1);
    }

    private Account calculateAccountBalance(Transaction transaction, Account account, String type) {
        double total;

        if (type.equalsIgnoreCase("Deposit"))
            total = account.getBalance() + transaction.getAmount();
        else
            total = account.getBalance() - transaction.getAmount();

        account.setBalance(total);
        return accountService.save(account.getCustomerId(), account);
    }







    private TransactionDto toTransactionDto(Transaction savedTrn) {
        TransactionDto savedTransactionDto = new TransactionDto();
        savedTransactionDto.setTransactionDate(savedTrn.getTransactionDate());
        savedTransactionDto.setAmount(savedTrn.getAmount());
        savedTransactionDto.setTransactionType(savedTrn.getTransactionType());
        savedTransactionDto.setId(savedTrn.getId());
        return savedTransactionDto;
    }

    private Transaction toTransaction(Long accountId, TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setAccountId(accountId);
        return transaction;
    }

    private List<TransactionDto> toTransactionDto(List<Transaction> transactions){
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for(Transaction transaction : transactions) {
            transactionDtoList.add(toTransactionDto(transaction));
        }
        return transactionDtoList;
    }



}
