package anggira.parkingsystem.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionResponse {
    private String transactionId;
    private Long paymentAmount;
    private Long totalPrice;
    private Long change;
    private ParkingResponse parkingResponse;
    private LocalDateTime transactionDate;
}
