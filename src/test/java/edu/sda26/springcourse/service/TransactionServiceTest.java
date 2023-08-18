package edu.sda26.springcourse.service;

import edu.sda26.springcourse.model.BalanceDtoResponse;
import edu.sda26.springcourse.dto.TransactionDto;
import edu.sda26.springcourse.exception.TransactionNotFoundException;
import edu.sda26.springcourse.model.Account;
import edu.sda26.springcourse.model.Transaction;
import edu.sda26.springcourse.model.enums.TransactionType;
import edu.sda26.springcourse.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    TransactionRepository transactionRepository;

    @Mock
    AccountService accountService;


    @Test
    public void testFindTransactionsById_returnSuccessfully() throws TransactionNotFoundException {
        //given
        Transaction testTransaction = new Transaction(
                1L, 555.5, "deposit", 1L, LocalDate.now());
        Account account = new Account(1L, 111.0, 1L, true);


        //when
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(testTransaction));
        TransactionService transactionService = new TransactionService(transactionRepository, accountService);

        TransactionDto selectedTransaction = transactionService.getTransactionById(anyLong());
        //then
        assertEquals(555.5, selectedTransaction.getAmount());
        assertEquals(TransactionType.DEPOSIT.getName(), selectedTransaction.getTransactionType());
        assertEquals(selectedTransaction.getTransactionDate(), LocalDate.now());

    }
//
//    @Test
//    public void testGetTransactionById_expectedException() {
//        //given
//        when(transactionRepository.findById(anyLong())).thenReturn(null);
//        TransactionService transactionService = new TransactionService(transactionRepository, accountService);
//
//        //when and then
//        assertThrows(TransactionNotFoundException.class, () -> transactionService.getTransactionById(anyLong()));
//        verify(transactionRepository).findById(anyLong());
//    }

    @Test
    public void testGetAllTransactions_returnSuccessfully() {
        //given
        Transaction testTransaction = new Transaction(1L, 555.55, "deposit", 1L, LocalDate.now());
        Transaction testTransaction1 = new Transaction(2L, 444.4, "deposit", 1L, LocalDate.now());
        Account account = new Account(1L, 111.0, 1L, true);
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(testTransaction1);
        transactionList.add(testTransaction);

        //when
        when(transactionRepository.findAll()).thenReturn(transactionList);
        TransactionService transactionService = new TransactionService(transactionRepository, accountService);
        List<TransactionDto> transactions = transactionService.getAllTransactions();

        //then
        assert transactions.size() == 2;
        assertEquals(transactions.get(0).getTransactionDate(), LocalDate.now());
        assertEquals(transactions.get(0).getTransactionType(), "deposit");

        assertEquals(transactions.get(1).getTransactionDate(), LocalDate.now());
        assertEquals(transactions.get(1).getTransactionType(), "deposit");
        assertEquals(transactions.get(1).getAmount(), 555.55);

    }

    @Test
    public void testSaveTransaction_returnSuccessfully() {
        //given
        Transaction testTransaction = new Transaction(
                1L, 444.4, TransactionType.DEPOSIT.getName(), 1L, LocalDate.now());
        Account account = new Account(1L, 111.0, 1L, true);
        Account updateAccount = new Account(1L, 555.4, 1L, true);
        TransactionService transactionService = new TransactionService(transactionRepository, accountService);
        when(transactionRepository.save(testTransaction)).thenReturn(testTransaction);
        when(accountService.save(anyLong(), eq(account))).thenReturn(updateAccount);

        //when
     BalanceDtoResponse balanceResponse  = transactionService.save(testTransaction, account);

        //then
        assertEquals(balanceResponse.getTransaction().getAmount(), 444.4);
        assertEquals("deposit", balanceResponse.getTransaction().getTransactionType());
        assertEquals(1L, balanceResponse.getTransaction().getAccountId());
        assertEquals(balanceResponse.getAccount().getBalance(), 555.4);

    }

    @Test
    public void testSaveTransaction_exception(){
        //given
        Transaction testTransaction = new Transaction(
                1L, 4000.0, TransactionType.WITHDRAW.getName(), 1L, LocalDate.now());
        Account account = new Account(1L, 2000.0, 1L, true);

        TransactionService transactionService = new TransactionService(transactionRepository, accountService);


        //when
        assertThrows(IllegalStateException.class, () -> transactionService.save(testTransaction, account));
        verifyNoInteractions(transactionRepository);
        verifyNoInteractions(accountService);

    }
}
