package anggira.parkingsystem.entity.constant;

import lombok.Getter;

@Getter
public enum VehiclesType {
    CAR (5000L),
    MOTORCYCLE(3000L);

    private Long price;

    VehiclesType(Long price){
        this.price = price;
    }
}
