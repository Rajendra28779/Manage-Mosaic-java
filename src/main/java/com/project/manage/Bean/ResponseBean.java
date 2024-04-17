/**
 * 
 */
package com.project.manage.Bean;

import lombok.Data;

/**
 * 
 */
@Data
public class ResponseBean {
	
	private Integer status;
	private String message;
	private String errorMessage;
	private Object record;

}
