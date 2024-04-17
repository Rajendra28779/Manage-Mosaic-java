/**
 * 
 */
package com.project.manage.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rajendra
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class CommonController {
	
	@GetMapping("/check")
	public void check() throws Exception {	
		System.out.println("Ohk Done");
	}
}
