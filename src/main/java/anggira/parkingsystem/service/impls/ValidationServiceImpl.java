package anggira.parkingsystem.service.impls;

import anggira.parkingsystem.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {
    private final Validator validator;

    public void validate(Object request){
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);

        //check validasi
        if (constraintViolations.size() != 0){
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
