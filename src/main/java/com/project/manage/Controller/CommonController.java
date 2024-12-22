/**
 * 
 */
package com.project.manage.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.shaded.json.writer.BeansMapper.Bean;
import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.RepairRequest;
import com.project.manage.Service.CommenService;

/**
 * Rajendra
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class CommonController {	
	
	@Autowired
	private CommenService commenserv;
	
	@PostMapping(value = "/contactrqst")
	@ResponseBody
	public ResponseBean rqstforcontact(@RequestBody Map<String,Object> mapobj) {
		ResponseBean map=new ResponseBean();
		try {
			map=commenserv.rqstforcontact(mapobj);
		}catch (Exception e) {
			map.setStatus(400);
			map.setMessage("Something Went Wrong !");
			map.setErrorMessage(e.getMessage());
		}
		return map;
	}
	
	
}
