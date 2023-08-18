package edu.sda26.springcourse.controller;

import edu.sda26.springcourse.dto.CustomerCreateRequestDto;
import edu.sda26.springcourse.dto.CustomerDto;
import edu.sda26.springcourse.dto.CustomerResponseDto;
import edu.sda26.springcourse.dto.CustomerUpdateRequestDto;
import edu.sda26.springcourse.exception.AccountNotFoundException;
import edu.sda26.springcourse.exception.CustomerNotFoundException;
import edu.sda26.springcourse.model.Account;
import edu.sda26.springcourse.model.Customer;
import edu.sda26.springcourse.service.AccountService;
import edu.sda26.springcourse.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;
    private final AccountService accountService;

    public CustomerController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    @GetMapping(path = "/customers")
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/customer/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable("id") Long id) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

//    @GetMapping(path = "/customer/{name}")
//    public ResponseEntity<List<Customer>> getCustomerByName(@PathVariable("name") String name){
//        List<Customer> customers = customerService.getCustomerByName(name);
//        if (customers.size() < 1){
//            return ResponseEntity.notFound().build();
//        }else {
//            return ResponseEntity.ok(customers);
//        }
//    }

    @GetMapping(path = "/customer")
    public ResponseEntity<List<Customer>> getCustomerByNameAndStatus(
            @RequestParam("name") String name,
            @RequestParam("status") Boolean status)
            throws CustomerNotFoundException {
        List<Customer> customers =
                customerService.getActiveCustomerByName(name, status);
        return ResponseEntity.ok(customers);

    }

    @GetMapping(path = "/customer/{id}/account")
    public ResponseEntity<Account> getAccountOfCustomer(@PathVariable("id") Long customerId) throws AccountNotFoundException {
        Account account =
                accountService.findAccountByCustomerId(customerId);
        if(account==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @PutMapping(path = "/customer/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long id,
                                                      @RequestBody CustomerUpdateRequestDto customerUpdateRequestDto) {
        return ResponseEntity
                .accepted()
                .body(customerService.updateCustomerData(id, customerUpdateRequestDto));
    }

    // Sometimes RequestDto and ResponseDto can be same class
    // And sometimes has to be different
    // it depends on the api that you are implementing
    @PostMapping(path = "/customer")
    public ResponseEntity<CustomerResponseDto> createCustomer(
            @Valid @RequestBody CustomerCreateRequestDto customerCreateRequestDto) {

        CustomerResponseDto savedCustomer = customerService.save(customerCreateRequestDto);

        return ResponseEntity.ok(savedCustomer);

    }

    @DeleteMapping(path = "/customer/{id}")
    public ResponseEntity<String> removeCustomer(@PathVariable("id") Long customerId) {
        customerService.removeCustomer(customerId);
        return ResponseEntity.ok().build();
    }



    @PatchMapping(path = "/customer/{id}/activate")
    public ResponseEntity<CustomerDto> activateCustomerStatus(@PathVariable("id") Long id)
            throws CustomerNotFoundException {
        return ResponseEntity.accepted().body(customerService.updateCustomerStatus(id));
    }

}
