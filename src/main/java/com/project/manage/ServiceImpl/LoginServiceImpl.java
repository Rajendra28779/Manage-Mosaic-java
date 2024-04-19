/**
 * 
 */
package com.project.manage.ServiceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.manage.Model.MstUserModel;
import com.project.manage.Repository.MstUserRepository;
import com.project.manage.Service.LoginService;
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
		Map<String, Object> user=new HashMap<>();
		try {
			MstUserModel usermodel1=userrepo.findByUserName(usermodel.getUserName().toLowerCase());
			if(usermodel1!=null) {
				if(usermodel1.getStatusFlag()==0) {
					Authentication auth = null;
					try {
						auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
								usermodel.getUserName(), usermodel.getPassword()));
						if(auth!=null) {
							user.put("userid", usermodel1.getUserId());
							user.put("username", usermodel1.getUserName());
							user.put("groupid", usermodel1.getGroupId());
							user.put("fullname", usermodel1.getFullname());
							user.put("mobileno", usermodel1.getMobileNo());
						map.put("user", user);
						map.put("auth", "Bearer " + jwtUtil.generateToken(usermodel1.getUserName()));
						map.put("status", HttpStatus.OK.value());
						map.put("message", "Authentication Successful");
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
					map.put("message", "Your Account was Deactivated !");
				}
			}else {
				map.put("status", HttpStatus.NOT_FOUND.value());
				map.put("message", "User Not Found");
			}		
			
		}catch (Exception e) {
			throw new Exception(e);
		}
		return map;
	}

}
