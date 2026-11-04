package com.example.demo.ExceptionHandling;

public class ResourceNotFound extends RuntimeException{
	public ResourceNotFound(String message)
	{
		super(message);
	}

}
