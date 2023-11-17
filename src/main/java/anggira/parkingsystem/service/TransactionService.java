package anggira.parkingsystem.service;

import anggira.parkingsystem.model.request.TransactionRequest;
import anggira.parkingsystem.model.response.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionResponse createNewTransaction (TransactionRequest request);
    TransactionResponse getTransactionById(String transactionId);
    Page<TransactionResponse> getAllTransaction(Integer page, Integer size);
}
