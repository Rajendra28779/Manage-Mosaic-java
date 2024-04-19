/**
 * 
 */
package com.project.manage.ServiceImpl;

import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.MstUserModel;
import com.project.manage.Repository.MstUserRepository;
import com.project.manage.Service.MstUserService;

/**
 * 
 */
@Service
public class MstUserServiceImpl implements MstUserService {
	
	@Autowired
	private MstUserRepository mstuserrepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public ResponseBean createUser(MstUserModel usermodel) throws Exception {
		ResponseBean bean=new ResponseBean();
		try {
			Integer usernamecheck=mstuserrepo.usernamecheck(usermodel.getUserName().toLowerCase());
			if(usernamecheck==0) {
				Integer emailcheck=mstuserrepo.emailcheck(usermodel.getEmail());
				if(emailcheck==0) {
					Integer phonenocheck=mstuserrepo.phonenocheck(usermodel.getMobileNo());
					if(phonenocheck==0) {
						usermodel.setPassword(passwordEncoder.encode(usermodel.getPassword()));
						usermodel.setFullname(usermodel.getFirstName().trim()+" "+usermodel.getLastName().trim());
						usermodel.setGroupId(2);
						usermodel.setCreatedBy(1l);
						usermodel.setCreatedOn(Calendar.getInstance().getTime());
						usermodel.setStatusFlag(0);
						mstuserrepo.save(usermodel);
						bean.setStatus(HttpStatus.OK.value());
						bean.setMessage("Success");
					}else {
						bean.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
						bean.setMessage("MobileNo Taken By Another User !");
					}
				}else {
					bean.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
					bean.setMessage("Email Taken By Another User !");
				}
			}else {
				bean.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
				bean.setMessage("UserName Taken By Another User !");
			}			
		}catch (Exception e) {
			throw new Exception(e);
		}
		return bean;
	}

	@Override
	public ResponseBean checkusername(String username) throws Exception {
		ResponseBean bean=new ResponseBean();
		try {
			MstUserModel usermodel=mstuserrepo.findByUserName(username);
			if(usermodel!=null) {
				bean.setStatus(HttpStatus.UNAUTHORIZED.value());
				bean.setMessage("UserName Already Exist! Try Another One !");
			}else {
				bean.setStatus(200);
				bean.setMessage("Success");
			}
		}catch (Exception e) {
			throw new Exception(e);
		}
		return bean;
	}

}
