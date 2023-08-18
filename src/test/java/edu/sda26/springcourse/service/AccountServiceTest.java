package edu.sda26.springcourse.service;

import edu.sda26.springcourse.exception.AccountNotFoundException;
import edu.sda26.springcourse.model.Account;
import edu.sda26.springcourse.repository.AccountRepository;
import edu.sda26.springcourse.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;

    @Test
    public void testFindAccountByCustomerId_thenResponseSuccessfully()
        throws AccountNotFoundException{
        //given
        Account testAccount = new Account(
                1L,
                555.5,
                1L,
                true);

        when(accountRepository.findByCustomerId(anyLong())).thenReturn(testAccount);
        AccountService accountService = new AccountService(accountRepository);
        //when
        Account selectedAccount = accountService.findAccountByCustomerId(1L);

        //then
        assertEquals(1L, selectedAccount.getCustomerId());
        assertTrue(selectedAccount.getActive());
        assertEquals(555.5, selectedAccount.getBalance());
    }

    @Test
    public void testFindAccountByCustomerId_expectedException(){
        //given
        when(accountRepository.findByCustomerId(anyLong())).thenReturn(null);
        AccountService accountService = new AccountService(accountRepository);

        //when and then
        assertThrows(AccountNotFoundException.class, ()-> accountService.findAccountByCustomerId(anyLong()));
        verify(accountRepository).findByCustomerId(anyLong());
    }
}
