package com.product.infrastructure.exception.custom;

public class ProductNotFoundException extends RuntimeException{

	public ProductNotFoundException() {
		super();
	}
	
	public ProductNotFoundException(final String errorMessage, final Throwable cause) {
	    super(errorMessage, cause);
	  }
	
	public ProductNotFoundException(final String errorMessage) {
	    super(errorMessage);
	  }
}
