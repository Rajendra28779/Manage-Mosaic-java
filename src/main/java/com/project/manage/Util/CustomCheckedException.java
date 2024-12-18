/**
 * 
 */
package com.project.manage.Util;

/**
 * Rajendra
 */
public class CustomCheckedException extends Exception{

	 public CustomCheckedException() {
	        super();
	    }

	    // Constructor with error message
	    public CustomCheckedException(String message) {
	        super(message);
	    }

	    // Constructor with error message and cause
	    public CustomCheckedException(String message, Throwable cause) {
	        super(message, cause);
	    }

	    // Constructor with cause
	    public CustomCheckedException(Throwable cause) {
	        super(cause);
	    }
}
