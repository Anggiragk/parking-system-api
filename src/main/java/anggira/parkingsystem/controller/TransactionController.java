package anggira.parkingsystem.controller;

import anggira.parkingsystem.model.request.TransactionRequest;
import anggira.parkingsystem.model.response.CommonResponse;
import anggira.parkingsystem.model.response.PagingResponse;
import anggira.parkingsystem.model.response.ParkingResponse;
import anggira.parkingsystem.model.response.TransactionResponse;
import anggira.parkingsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request){
        TransactionResponse newTransaction = transactionService.createNewTransaction(request);

        CommonResponse<TransactionResponse> commonResponse = CommonResponse.<TransactionResponse>builder()
                .message("OK")
                .statusCode(HttpStatus.CREATED.value())
                .data(newTransaction)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllTransaction(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size
    ){
        Page<TransactionResponse> allTransaction =transactionService.getAllTransaction(page, size);
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all transaction")
                .data(allTransaction.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(page)
                        .totalPage(allTransaction.getTotalPages())
                        .size(size)
                        .build())
                .build();
        return ResponseEntity.status((HttpStatus.OK)).body(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        TransactionResponse transactionById = transactionService.getTransactionById(id);
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get transaction by id")
                .data(transactionById)
                .build();
        return ResponseEntity.status((HttpStatus.OK)).body(commonResponse);
    }
}
