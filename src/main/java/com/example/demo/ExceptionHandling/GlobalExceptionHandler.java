package com.example.demo.ExceptionHandling;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String,String> handleFieldException(MethodArgumentNotValidException ex)
	{
		Map<String,String> errors=new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
		{
			errors.put(error.getField(), error.getDefaultMessage());
		}
				);
		return errors;
		
	}
	
	@ExceptionHandler(ResourceNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException(ResourceNotFound e)
	{
		return e.getMessage();
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleIllegalArgument(IllegalArgumentException e)
	{
		return e.getMessage();
	}
	
	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String handleIntegrity(IllegalStateException e)
	{
		return e.getMessage();
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleGeneralException(Exception e)
	{
		return "Something went wrong";
	}

}
