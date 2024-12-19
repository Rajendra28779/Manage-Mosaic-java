/**
 * 
 */
package com.project.manage.ServiceImpl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.manage.Model.MstUserModel;
import com.project.manage.Repository.MstUserRepository;
import com.project.manage.Service.LoginService;
import com.project.manage.Util.CustomCheckedException;
import com.project.manage.Util.JwtUtil;

/**
 * 
 */
@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private AuthenticationManager authenticationManager = null;
	
	@Autowired
	private MstUserRepository userrepo;
	
	@Autowired
    private JwtUtil jwtUtil;

	@Override
	public Map<String, Object> loginapi(MstUserModel usermodel) throws Exception {
		Map<String, Object> map=new HashMap<>();
		try {
			MstUserModel usermodel1=null;
				usermodel1=userrepo.findByUserName(usermodel.getUserName());
			if(usermodel1!=null) {
				usermodel1=userrepo.findByEmail(usermodel.getUserName());
			}
			if(usermodel1!=null) {
				usermodel1=userrepo.findBymobile(usermodel.getUserName());
			}
			if(usermodel1!=null) {
				if(usermodel1.getStatusFlag()==0) {
					Authentication auth = null;
					try {
						auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
								usermodel.getUserName(), usermodel.getPassword()));
						if(auth!=null) {
							Map<String,Object> data=new HashMap<>();
							data.put("userId", usermodel1.getUserId());
							data.put("userName", usermodel1.getUserName() != null ? usermodel1.getUserName() : "");
							data.put("phoneNo", usermodel1.getMobileNo() != null ? usermodel1.getMobileNo() : "");
							data.put("fullName", usermodel1.getFullname() != null ? usermodel1.getFullname() : "");
							data.put("address", usermodel1.getAddress() != null ? usermodel1.getAddress() : "");
							data.put("email", usermodel1.getEmail() != null ? usermodel1.getEmail() : "");
						
						map.put("userdata", data);
						map.put("token", "Bearer " + jwtUtil.generateToken(usermodel1.getUserName()));
						map.put("stratus", HttpStatus.OK);
						map.put("message", "Login Successful");
						}else {
							map.put("status", HttpStatus.BAD_REQUEST.value());
							map.put("message", "Authentication Failed");
						}
					}catch (Exception e) {
						map.put("status", HttpStatus.BAD_REQUEST.value());
						map.put("message", "Authentication Failed");
					}					
				}else {
					map.put("status", HttpStatus.BAD_REQUEST.value());
					map.put("message", "Your Account was Deactivated ! Please Contact Support Team !");
				}
			}else {
				map.put("status", HttpStatus.NOT_FOUND.value());
				map.put("message", "User Not Found ! / UnAuthorized Access");
			}		
			
		}catch (Exception e) {
			throw new Exception(e);
		}
		return map;
	}
	
	private String createuserName(String name) throws CustomCheckedException{
		String username="";
		try {
			String prifix=name.substring(0,3).toUpperCase();
			String randon2digit=String.format("%02d", new Random().nextInt(100));
			String randon3digit=String.format("%03d", new Random().nextInt(1000));
			String[] chararArray = {
		            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", 
		            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", 
		            "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", 
		            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", 
		            "S", "T", "U", "V", "W", "X", "Y", "Z"
		        };
	        String char1 = chararArray[new Random().nextInt(51)];
	        String char2 = chararArray[new Random().nextInt(51)];
	        String randomChars =char1 + char2;	        
			username=prifix+randon2digit+randomChars+randon3digit;
		}catch (Exception e) {
			throw new CustomCheckedException("Error In Create username : "+e.getMessage());
		}
		return username;
	}

	@Override
	public Map<String, Object> loginfrmgoogle(String email, String name) throws CustomCheckedException {
		Map<String, Object> response = new HashMap<>();
		try {
			MstUserModel userdetails=new MstUserModel();
			Integer emailcheck=userrepo.emailcheck(email);
			if(emailcheck==0) {
				userdetails=createuserthroughgoogle(email,name);
			}else {
				userdetails=verifyuserthroughgoogle(email);
			}
				Map<String,Object> map=new HashMap<>();
				map.put("userId", userdetails.getUserId());
				map.put("userName", userdetails.getUserName() != null ? userdetails.getUserName() : "");
				map.put("phoneNo", userdetails.getMobileNo() != null ? userdetails.getMobileNo() : "");
				map.put("fullName", userdetails.getFullname() != null ? userdetails.getFullname() : "");
				map.put("address", userdetails.getAddress() != null ? userdetails.getAddress() : "");
				map.put("email", userdetails.getEmail() != null ? userdetails.getEmail() : "");
			
			response.put("userdata", map);
			response.put("token", "Bearer " + jwtUtil.generateToken(userdetails.getUserName()));
			response.put("stratus", HttpStatus.OK);
			response.put("message", "Login Successful");
			
		}catch (Exception e) {
			response.put("stratus", HttpStatus.BAD_REQUEST);
			response.put("message", "Something Went Wrong !");
			response.put("error", e.getMessage());
		}
		return response;
	}
	
	private MstUserModel createuserthroughgoogle(String email, String name) throws CustomCheckedException {
		MstUserModel usermodel=new MstUserModel();
		try {
			Integer usernamecheck=1;
			while(usernamecheck!=0) {
				String username=createuserName(name);
				usernamecheck=userrepo.usernamecheck(username.trim());
			}				
			if(usernamecheck==0) {
//				usermodel.setPassword(passwordEncoder.encode(usermodel.getPassword()));
				usermodel.setFullname(name);
				usermodel.setGroupId(2);
				usermodel.setCreatedOn(Calendar.getInstance().getTime());
				usermodel.setStatusFlag(0);
				usermodel=userrepo.save(usermodel);
			}else {
				throw new CustomCheckedException("UserName Taken By Another User !");
			}			
		}catch (Exception e) {
			throw new CustomCheckedException("Error In createuserthroughgoogle : "+e.getMessage());
		}
		return usermodel;
	}
	
	private MstUserModel verifyuserthroughgoogle(String email) throws CustomCheckedException {
		MstUserModel usermodel=new MstUserModel();
		try {
			usermodel=userrepo.findByEmail(email);
			if(usermodel==null) {
				throw new CustomCheckedException("UnAuhorized Access / User Blocked. Please Contact To Supprot Team  !!");
			}
		}catch (Exception e) {
			throw new CustomCheckedException("Error In verifyuserthroughgoogle : "+e.getMessage());
		}
		return usermodel;
	}

}
