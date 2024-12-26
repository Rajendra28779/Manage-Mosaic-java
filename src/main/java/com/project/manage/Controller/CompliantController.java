/**
 * 
 */
package com.project.manage.Controller;

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
import com.project.manage.Model.RepairRequest;
import com.project.manage.Repository.MstUserRepository;
import com.project.manage.Service.CompliantService;
import com.project.manage.ServiceImpl.CompliantServiceImpl;
import com.project.manage.config.JwtFilter;

/**
 * Rajendra
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class CompliantController {
	
	@Autowired
	private CompliantService compliantserv ;
	
	@Autowired
	private MstUserRepository mastuserrepo;
	
	@PostMapping(value = "/savehousemaintancerqst")
	@ResponseBody
	public ResponseBean savehousemaintancerqst(@RequestBody RepairRequest repaisrqst) {
		ResponseBean map=new ResponseBean();
		try {
			map=compliantserv.savehousemaintancerqst(repaisrqst);
		}catch (Exception e) {
			map.setStatus(400);
			map.setMessage("Something Went Wrong !");
			map.setErrorMessage(e.getMessage());
		}
		return map;
	} 
	
	@GetMapping(value = "/getmaintanceTrackingRecord")
	@ResponseBody
	public ResponseBean savehousemaintancerqst() {
		ResponseBean map=new ResponseBean();
		try {
			String usename=JwtFilter.getusername();
			Long userid= mastuserrepo.getuserIdfromuserName(usename);
			map=compliantserv.getrqstdetailsfortrackt(userid);
		}catch (Exception e) {
			map.setStatus(400);
			map.setMessage("Something Went Wrong !");
			map.setErrorMessage(e.getMessage());
		}
		return map;
	} 
	
	@GetMapping(value = "/getrequestdetailsForowner")
	@ResponseBody
	public ResponseBean getrequestdetailsForowner() {
		ResponseBean map=new ResponseBean();
		try {
			String usename=JwtFilter.getusername();
			Long userid= mastuserrepo.getuserIdfromuserName(usename);
			map=compliantserv.getrequestdetailsForowner(userid);
		}catch (Exception e) {
			e.printStackTrace();
			map.setStatus(400);
			map.setMessage("Something Went Wrong !");
			map.setErrorMessage(e.getMessage());
		}
		return map;
	} 
	
	@GetMapping(value = "/takeactionagainestrequest")
	@ResponseBody
	public ResponseBean takeactionagainestrequest(@RequestParam(value = "actiontype",required = false) Integer actiontype,
			@RequestParam(value = "rqstId",required = false) Long rqstId) {
		ResponseBean map=new ResponseBean();
		try {
			if(actiontype == 2 || actiontype == 3) {
				String usename=JwtFilter.getusername();
				Long userid= mastuserrepo.getuserIdfromuserName(usename);
				map=compliantserv.takeactionagainestrequest(actiontype,userid,rqstId);
			}else {
				map.setStatus(401);
				map.setMessage("Invalid Action Type !");
				map.setErrorMessage("Invalid Action Type !");
			}
		}catch (Exception e) {
			e.printStackTrace();
			map.setStatus(400);
			map.setMessage("Something Went Wrong !");
			map.setErrorMessage(e.getMessage());
		}
		return map;
	}

}
