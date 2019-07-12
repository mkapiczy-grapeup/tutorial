package pl.grapeup.mika.tutorial.validation;

import pl.grapeup.mika.tutorial.dto.ReservationDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartEndDateValidator implements ConstraintValidator<StartEndDateConstraint, ReservationDTO> {

    @Override
    public boolean isValid(ReservationDTO reservation, ConstraintValidatorContext constraintValidatorContext) {
        return reservation.getStartDate().isBefore(reservation.getEndDate());
    }
}
