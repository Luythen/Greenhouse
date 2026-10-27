package com.github.Luythen.GreenHouseServer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.github.Luythen.GreenHouseServer.Dto.ResponseDto;
import com.github.Luythen.GreenHouseServer.Exceptions.EmailAlreadyExistsException;
import com.github.Luythen.GreenHouseServer.Exceptions.FindByException;
import com.github.Luythen.GreenHouseServer.Exceptions.GreenHouseAlreadyExitsException;
import com.github.Luythen.GreenHouseServer.Exceptions.InvalidFieldException;
import com.github.Luythen.GreenHouseServer.Exceptions.InvalidRefreshToken;
import com.github.Luythen.GreenHouseServer.Exceptions.PasswordNotMatchException;
import com.github.Luythen.GreenHouseServer.Exceptions.PlantCrateHasActivePlantException;


@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    ResponseEntity<ResponseDto> emailConflict (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDto(409, e.getMessage()));
    }

    @ExceptionHandler(GreenHouseAlreadyExitsException.class)
    ResponseEntity<ResponseDto> greenHouseAlreadyExitsException (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDto(409, e.getMessage()));
    }

    @ExceptionHandler(PlantCrateHasActivePlantException.class)
    ResponseEntity<ResponseDto> plantCrateHasActivePlant (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDto(409, e.getMessage()));
    }

    @ExceptionHandler(InvalidFieldException.class)
    ResponseEntity<ResponseDto> fieldEmpty (RuntimeException e) {
        return ResponseEntity.badRequest().body(new ResponseDto(400, e.getMessage()));
    }

    @ExceptionHandler(FindByException.class)
    ResponseEntity<ResponseDto> findByException (RuntimeException e) {
        return ResponseEntity.badRequest().body(new ResponseDto(400, e.getMessage()));
    }

    @ExceptionHandler(InvalidRefreshToken.class)
    ResponseEntity<ResponseDto> refreshFailed (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(401, e.getMessage()));
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    ResponseEntity<ResponseDto> passwordNotMatch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(401, e.getMessage()));
    }

    @ExceptionHandler(NotFound.class)
    ResponseEntity<ResponseDto> notFound (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(400, e.getMessage()));
    }

}
