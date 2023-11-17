package anggira.parkingsystem.service.impls;

import anggira.parkingsystem.entity.Parking;
import anggira.parkingsystem.entity.Transaction;
import anggira.parkingsystem.model.request.TransactionRequest;
import anggira.parkingsystem.model.response.ParkingResponse;
import anggira.parkingsystem.model.response.TransactionResponse;
import anggira.parkingsystem.repository.ParkingRepository;
import anggira.parkingsystem.repository.TransactionRepository;
import anggira.parkingsystem.service.TransactionService;
import anggira.parkingsystem.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ParkingRepository parkingRepository;
    private final ValidationService validationService;

    @Override
    @Transactional
    public TransactionResponse createNewTransaction(TransactionRequest request) {
        validationService.validate(request);
        Parking currentParking = parkingRepository.findById(request.getParkingId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "parking not found")
        );

        Long parkingHour = Duration.between(currentParking.getCreatedAt(), LocalDateTime.now()).toHours()+1;
        Long totalPrice = currentParking.getParkingFee() * parkingHour;

        Transaction newTransaction = Transaction.builder()
                .paymentAmount(request.getPaymentAmount())
                .totalPrice(totalPrice)
                .paymentChange(request.getPaymentAmount()-totalPrice)
                .parking(currentParking)
                .build();
        transactionRepository.save(newTransaction);

        currentParking.setIsUsed(false);

        if (totalPrice >= request.getPaymentAmount()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment amount must be greater than total amount");
        }

        return getTransactionResponse(newTransaction);
    }

    @Override
    public TransactionResponse getTransactionById(String transactionId) {
        Transaction currentTransaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found")
        );
        return getTransactionResponse(currentTransaction);
    }

    private static TransactionResponse getTransactionResponse(Transaction newTransaction) {
        return TransactionResponse.builder()
                .transactionId(newTransaction.getId())
                .paymentAmount(newTransaction.getPaymentAmount())
                .totalPrice(newTransaction.getTotalPrice())
                .change(newTransaction.getPaymentChange())
                .parkingResponse(ParkingResponse.builder()
                        .id(newTransaction.getParking().getId())
                        .slot(newTransaction.getParking().getSlot())
                        .licensePlate(newTransaction.getParking().getLicensePlate())
                        .vehicleBrand(newTransaction.getParking().getVehicleBrand())
                        .vehicleColor(newTransaction.getParking().getVehicleColor())
                        .vehiclesType(newTransaction.getParking().getVehiclesType())
                        .parkingPricePerHour(newTransaction.getParking().getParkingFee())
                        .createdAt(newTransaction.getParking().getCreatedAt())
                        .build())
                .build();
    }

    @Override
    public Page<TransactionResponse> getAllTransaction(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        List<TransactionResponse> transactionResponses = transactions.stream().map(transaction -> {
            return getTransactionResponse(transaction);
        }).collect(Collectors.toList());
        return new PageImpl<>(transactionResponses, pageable, transactions.getTotalElements());
    }
}
