package edu.sda26.springcourse.controller;

import edu.sda26.springcourse.dto.TransactionDto;
import edu.sda26.springcourse.exception.TransactionNotFoundException;
import edu.sda26.springcourse.model.Customer;
import edu.sda26.springcourse.model.Transaction;
import edu.sda26.springcourse.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/transaction")
    //@GetMapping(path = "/transaction")
    public List<TransactionDto> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping(path = "/account/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getTransactionByAccount(@PathVariable("id") Long accountId) {
        List<TransactionDto> transactionDtoList = transactionService.findTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactionDtoList);
    }

    @GetMapping(path = "/transaction/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id") Long id) throws TransactionNotFoundException {
        TransactionDto transactionDto = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionDto);
    }

    @PostMapping(path = "/account/{id}/transaction")
    public ResponseEntity<TransactionDto> createTransaction(
            @PathVariable("id") Long accountId,
            @RequestBody TransactionDto transaction) {
        TransactionDto transactionDto = transactionService.save(accountId, transaction);
        return ResponseEntity.ok(transactionDto);
    }

    @PatchMapping(path = "/transaction/{id}/refund")
    public ResponseEntity<TransactionDto> refundTransaction(@PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(transactionService.refundTransactionDeposit(id));
    }


}
