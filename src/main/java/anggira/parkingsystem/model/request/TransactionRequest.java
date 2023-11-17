package anggira.parkingsystem.model.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionRequest {
    @NotBlank(message = "parkingId cannot be blank")
    private String parkingId;

    @Min(0)
    private Long paymentAmount;
}
