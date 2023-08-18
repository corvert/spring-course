package edu.sda26.springcourse.service;

import edu.sda26.springcourse.dto.CustomerCreateRequestDto;
import edu.sda26.springcourse.dto.CustomerDto;
import edu.sda26.springcourse.dto.CustomerResponseDto;
import edu.sda26.springcourse.dto.CustomerUpdateRequestDto;
import edu.sda26.springcourse.exception.CustomerNotFoundException;
import edu.sda26.springcourse.model.Customer;
import edu.sda26.springcourse.repository.CustomerRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return toCustomerDto(customers);
    }
    private List<CustomerDto> toCustomerDto(List<Customer> customers){
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for(Customer customer : customers) {
            customerDtoList.add(toCustomerDto(customer));
        }
        return customerDtoList;

    }

    public Customer getCustomerById(Long id) throws CustomerNotFoundException {
        return customerRepository.findById(id).
                orElseThrow(() -> new CustomerNotFoundException("Customer not found", 1));
    }

    public List<Customer> getActiveCustomerByName(String name, Boolean status) throws CustomerNotFoundException {

        // using repository method
        // return  customerRepository.findAllByNameAndActive(name,status);

        //using @Query annotation
        List<Customer> customers = customerRepository.getCustomerByNameAndStatus(name, status);
        if (customers.size() < 1) {
            throw new CustomerNotFoundException("Customer not found", 4);
        }
        return customers;
//        return customerRepository
//                .findAll()
//                .stream()
//                .filter(customer -> customer.getName().equalsIgnoreCase(name))
//                .filter(customer -> customer.getActive().equals(status))
//                .collect(Collectors.toList());

    }

    public CustomerResponseDto save(CustomerCreateRequestDto customerCreateRequestDto) {

        Customer customer = toCustomer(customerCreateRequestDto);

        //Saved customer object
        Customer savedCustomer = customerRepository.save(customer);

        //Creating CustomerResponseDto object and getting the value of
        // saved customer and setting into CustomerResponseDto

        //return CustomerResponseDto
        return toCustomerResponseDto(savedCustomer);
    }

    public void removeCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }

    public CustomerDto updateCustomerData(Long id, CustomerUpdateRequestDto customerUpdateRequestDto) {
        Customer customer = toCustomer(id, customerUpdateRequestDto);

        Customer updatedCustomer = customerRepository.save(customer);

        return toCustomerDto(updatedCustomer);
    }



    public CustomerDto updateCustomerStatus(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("Customer not found", 2));

            customer.setActive(true);
            return toCustomerDto(customerRepository.save(customer));
    }

    private static CustomerDto toCustomerDto(Customer updatedCustomer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setActive(updatedCustomer.getActive());
        customerDto.setAge(updatedCustomer.getAge());
        customerDto.setId(updatedCustomer.getId());
        customerDto.setPhone(updatedCustomer.getPhone());
        customerDto.setName(updatedCustomer.getName());
        customerDto.setEmail(updatedCustomer.getEmail());
        return customerDto;
    }
    private static Customer toCustomer(Long id, CustomerUpdateRequestDto customerUpdateRequestDto) {
        return Customer.builder()
                .age(customerUpdateRequestDto.getAge())
                .email(customerUpdateRequestDto.getEmail())
                .phone(customerUpdateRequestDto.getPhone())
                .active(customerUpdateRequestDto.getActive())
                .name(customerUpdateRequestDto.getName())
                .id(id)
                .build();
    }




    private static Customer toCustomer(CustomerCreateRequestDto customerCreateRequestDto) {
//        Customer customer = new Customer();
//        customer.setName(customerCreateRequestDto.getName());
//        customer.setEmail(customerCreateRequestDto.getEmail());
//        customer.setPhone(customerCreateRequestDto.getPhone());
//        customer.setAge(customerCreateRequestDto.getAge());
//        customer.setActive(true);
        return Customer.builder()
                .age(customerCreateRequestDto.getAge())
                .name(customerCreateRequestDto.getName())
                .email(customerCreateRequestDto.getEmail())
                .phone(customerCreateRequestDto.getPhone())
                .active(true)
                .build();
    }

    private static CustomerResponseDto toCustomerResponseDto(Customer savedCustomer) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setName(savedCustomer.getName());
        customerResponseDto.setId(savedCustomer.getId());
        return customerResponseDto;
    }







}
