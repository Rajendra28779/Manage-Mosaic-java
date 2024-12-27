/**
 * 
 */
package com.project.manage.Controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Service.CommenService;
import com.project.manage.config.JwtFilter;

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
	
	@GetMapping(value = "/sendOTPforaddmobileno")
	@ResponseBody
	public ResponseBean sendOTPforaddmobileno(@RequestParam(value = "phoneno" ,required = false) String phoneno) {
		ResponseBean map=new ResponseBean();
		try {
			String usename=JwtFilter.getusername();
			map=commenserv.sendOTPforaddmobileno(phoneno,usename);
		}catch (Exception e) {
			map.setStatus(400);
			map.setMessage("Something Went Wrong !");
			map.setErrorMessage(e.getMessage());
		}
		return map;
	}
	
	@GetMapping(value = "/verifyOTPforaddmobileno")
	@ResponseBody
	public ResponseBean verifyOTPforaddmobileno(@RequestParam(value = "phoneno" ,required = false) String phoneno,
			@RequestParam(value = "otpval" ,required = false) String otpval) {
		ResponseBean map=new ResponseBean();
		try {
			map=commenserv.verifyOTPforaddmobileno(phoneno,otpval);
		}catch (Exception e) {
			map.setStatus(400);
			map.setMessage("Something Went Wrong !");
			map.setErrorMessage(e.getMessage());
		}
		return map;
	}	
	
	@ResponseBody
	@GetMapping(value = "/downloadcommondoc")
	public String downloadcommondoc(HttpServletResponse response, 
	            @RequestParam("data") String encodedJsonString) throws JSONException {
	    String resp = "";
	    try {
	        byte[] bytes = Base64.getDecoder().decode(encodedJsonString);
	        String jsonString = new String(bytes, StandardCharsets.UTF_8);

	        JSONObject json = new JSONObject(jsonString);
	        String fileName = json.getString("f");

	        if (fileName == null || fileName.trim().isEmpty()) {
	            resp = "File not found";
	        } else {
	        	commenserv.downloadcommondoc(fileName, response);
	        }
	    } catch (Exception e) {
	        resp = "Something went wrong: " + e.getMessage();
	    }
	    return resp;
	}
	
	
	
}
