/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.center.validators;

import com.almundo.call.center.models.CallCenter;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ADMIN
 */
public class CallCenterValidator implements Validator{
    
    @Override
   public boolean supports(Class<?> clazz) {
      return CallCenter.class.isAssignableFrom(clazz);
   }

   @Override
   public void validate(Object target, Errors errors) {		
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
         "incomingCalls", "required","El número de llamadas entrantes es requerido.");
   }
    
}
