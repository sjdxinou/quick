package cn.sjd.it.quick.framework.services.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class BaseServiceImpl {
	public boolean valid(Object obj){
		boolean flag = true;
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Object>> set = validator.validate(obj);
		if(null != set && set.size() > 0){
			flag = false;
		}
		return flag;
	}
}
