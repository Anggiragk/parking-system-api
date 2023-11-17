package anggira.parkingsystem.controller;

import anggira.parkingsystem.entity.constant.VehiclesType;
import anggira.parkingsystem.model.request.ParkingRequest;
import anggira.parkingsystem.model.response.CommonResponse;
import anggira.parkingsystem.model.response.PagingResponse;
import anggira.parkingsystem.model.response.ParkingResponse;
import anggira.parkingsystem.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final ParkingService parkingService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> inputParking(@RequestBody ParkingRequest request){
        ParkingResponse parkingResponse = parkingService.inputParking(request);

        CommonResponse<ParkingResponse> commonResponse = CommonResponse.<ParkingResponse>builder()
                .message("OK")
                .statusCode(HttpStatus.CREATED.value())
                .data(parkingResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(commonResponse);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateParking(@RequestBody ParkingRequest request){
        ParkingResponse parkingResponse = parkingService.updateParking(request);

        CommonResponse<ParkingResponse> commonResponse = CommonResponse.<ParkingResponse>builder()
                .message("Success update parking")
                .statusCode(HttpStatus.OK.value())
                .data(parkingResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponse);
    }

    @GetMapping("/used")
    public ResponseEntity<?> getAllUsedParking(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "vehicle-type", required = false) String vehiclesType

    ){
        Page<ParkingResponse> allUsedParkings = null;
        System.out.println(vehiclesType.toUpperCase());
        if (Objects.nonNull(vehiclesType)){
            if(vehiclesType.toUpperCase().equals("CAR")) {
                allUsedParkings = parkingService.getAllUsedParkingByVehicleType(page,size, VehiclesType.CAR);
            }
            else if(vehiclesType.toUpperCase().equals("MOTORCYCLE")) {
                allUsedParkings = parkingService.getAllUsedParkingByVehicleType(page,size, VehiclesType.MOTORCYCLE);
            }
            else allUsedParkings = parkingService.getAllUsedParking(page, size);
        } else {
            allUsedParkings = parkingService.getAllUsedParking(page, size);
        }

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all used parking")
                .data(allUsedParkings.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(page)
                        .totalPage(allUsedParkings.getTotalPages())
                        .size(size)
                        .build())
                .build();
        return ResponseEntity.status((HttpStatus.OK)).body(commonResponse);
    }

    @GetMapping("/unused")
    public ResponseEntity<?> getAllUnusedParking(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            @RequestParam(name = "vehicle-type", required = false) String vehiclesType
    ){
        System.out.println(vehiclesType.toUpperCase());
        Page<ParkingResponse> allUnusedParkings = null;
        if (Objects.nonNull(vehiclesType)){
            if(vehiclesType.toUpperCase().equals("CAR")) {
                allUnusedParkings = parkingService.getAllUnusedParkingByVehicleType(page,size, VehiclesType.CAR);
            }
            else if(vehiclesType.toUpperCase().equals("MOTORCYCLE")){
                allUnusedParkings = parkingService.getAllUnusedParkingByVehicleType(page,size, VehiclesType.MOTORCYCLE);
            }
            else {
                allUnusedParkings = parkingService.getAllUsedParking(page, size);
            }
        } else {
            allUnusedParkings = parkingService.getAllUsedParking(page, size);
        }

        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all unused parking")
                .data(allUnusedParkings.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(page)
                        .totalPage(allUnusedParkings.getTotalPages())
                        .size(size)
                        .build())
                .build();
        return ResponseEntity.status((HttpStatus.OK)).body(commonResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getParkingById(@PathVariable String id) {
        ParkingResponse parkingById = parkingService.getByParkingId(id);
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get parking by id")
                .data(parkingById)
                .build();
        return ResponseEntity.status((HttpStatus.OK)).body(commonResponse);
    }
}
