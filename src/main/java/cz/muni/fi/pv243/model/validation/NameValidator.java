package cz.muni.fi.pv243.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String>{

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		
		return (arg0.length() > 0 &&
				arg0.length() <= 50 &&
				arg0.matches("([A-Z]{1}[a-z]{1,30}[- ]{0,1}|[A-Z]{1}[- \']{1}[A-Z]{0,1}[a-z]{1,30}[- ]{0,1}|[a-z]{1,2}[ -\']{1}[A-Z]{1}[a-z]{1,30}){2,5}"));
		
	}

	@Override
	public void initialize(ValidName arg0) {
		// TODO Auto-generated method stub
	}

}
