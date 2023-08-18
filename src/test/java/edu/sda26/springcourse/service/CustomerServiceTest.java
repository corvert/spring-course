package edu.sda26.springcourse.service;

import edu.sda26.springcourse.dto.CustomerDto;
import edu.sda26.springcourse.exception.AccountNotFoundException;
import edu.sda26.springcourse.exception.CustomerNotFoundException;
import edu.sda26.springcourse.model.Customer;
import edu.sda26.springcourse.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Test
    public void testGetAllCustomers_returnSuccessfully() {
        Customer testCustomer = new Customer(
                1L, "John", 22, "1234567", "john@john.com", true, "");
        Customer customer1 = new Customer(
                2L, "Tina", 33, "7654321", "tina@tina.com", true, "");
        List<Customer> customerArrayList = new ArrayList<>();
        customerArrayList.add(testCustomer);
        customerArrayList.add(customer1);
        when(customerRepository.findAll()).thenReturn(customerArrayList);
        CustomerService customerService = new CustomerService(customerRepository);
        //when
        List<CustomerDto> customer = customerService.getAllCustomers();

        //then
        assert customer.size() == 2;
        assertTrue(customer.get(0).getActive());
        assertEquals(customer.get(0).getEmail(), "john@john.com");

    }

    @Test
    public void testGetCustomerById_returnSuccessfully() throws CustomerNotFoundException {
        //Given
        Customer testCustomer = new Customer(
                1L, "Alex", 22, "1234567", "alex@alex.com", true, "");
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(testCustomer));
        CustomerService customerService = new CustomerService(customerRepository);
        //when
        Customer selectedCustomer = customerService.getCustomerById(1L);
        //then
        assertEquals(1L, selectedCustomer.getId());
        assertEquals("Alex", selectedCustomer.getName());
        assertEquals("1234567", selectedCustomer.getPhone());
        assertTrue(selectedCustomer.getActive());
        assertEquals(22, selectedCustomer.getAge());

    }

    @Test
    public void testGetCustomerById_expectedException() throws CustomerNotFoundException {
        //given
        CustomerService customerService = new CustomerService(customerRepository);

        //when and then
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(anyLong()));
        verify(customerRepository).findById(anyLong());
    }

    @Test
    public void testGetActiveCustomerByNamed_returnSuccessfully() throws CustomerNotFoundException {
        //given
        Customer testCustomer = new Customer(
                1L, "Alex", 22, "1234567", "alex@alex.com", true, "");
        Customer testCustomer1 =
                new Customer(2L, "Tina", 32, "7654321", "tina@tina.com", true, "");
        List<Customer> customerList = new ArrayList<>();
        customerList.add(testCustomer);
        customerList.add(testCustomer1);
        //when
        when(customerRepository.findAllByNameAndActive(anyString(), eq(true))).thenReturn(customerList);
        CustomerService customerService = new CustomerService(customerRepository);

        List<Customer> allTestCustomers = customerService.getActiveCustomerByName(anyString(), eq(true));
        //then
        assert allTestCustomers.size() > 0;
        assertEquals(allTestCustomers.get(0).getActive(), true);
        verify(customerRepository).findAllByNameAndActive(anyString(), eq(true));
    }

    @Test
    public void testSave_returnSuccessfully() {
        //given
        Customer testCustomer = new Customer(
                1L, "Alex", 22, "1234567", "alex@alex.com", true, "");

        //when
        when(customerRepository.save(testCustomer)).thenReturn(testCustomer);
        CustomerService customerService = new CustomerService(customerRepository);

        Customer selectedCustomer = customerService.save(testCustomer);
        //then
        assertEquals(1L, selectedCustomer.getId());
        assertEquals("Alex", selectedCustomer.getName());
        assertEquals("1234567", selectedCustomer.getPhone());
        assertTrue(selectedCustomer.getActive());
        assertEquals(22, selectedCustomer.getAge());

    }

    @Test
    public void testRemoveCustomer_returnSuccessfully() {
        //given
        Customer testCustomer = new Customer(
                1L, "Alex", 22, "1234567", "alex@alex.com", true, "");
        //when
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(testCustomer)).thenReturn(null);
        CustomerService customerService = new CustomerService(customerRepository);

        customerService.removeCustomer(1L);
        //then
        Mockito.verify(customerRepository, times(1)).delete(testCustomer);

    }
}
