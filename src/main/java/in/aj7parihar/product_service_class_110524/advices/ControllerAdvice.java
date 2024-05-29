package in.aj7parihar.product_service_class_110524.advices;

import in.aj7parihar.product_service_class_110524.dtos.ErrorDTO;
import in.aj7parihar.product_service_class_110524.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    // There are two ways of exception handling
    // 1. Exception Handler in Controller class i.e. Product Controller class or any other controller class
    // 2. Controller Advice - Recommended one.
    // So, Controller Advice is placed right before the Controller class, and it intercepts the response
    // message from Controller, and if there is something wrong it can change & modify the response, if it wants.
    // If it wants means, it will only handle the exception but not the normal/happy response codes.

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException productNotFoundException){
        // ResponseEntity is used for HTTP response code, and it is provided by Spring Web
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(productNotFoundException.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

}
