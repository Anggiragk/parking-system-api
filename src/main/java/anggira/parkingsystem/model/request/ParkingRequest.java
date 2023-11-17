package anggira.parkingsystem.model.request;

import anggira.parkingsystem.entity.constant.VehiclesType;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ParkingRequest {
    private String id;

    @Range(min = 1, max = 50, message = "slot must greather than 0 and less or equal then 100")
    private Integer slot;

    @NotBlank(message = "licensePlate cannot be blank")
    private String licensePlate;

    @NotBlank(message = "vehicleBrand cannot be blank")
    private String vehicleBrand;

    @NotBlank(message = "vehicleColor cannot be blank")
    private String vehicleColor;

    @NotNull(message = "wrong vehicles type")
    private VehiclesType vehiclesType;
}
