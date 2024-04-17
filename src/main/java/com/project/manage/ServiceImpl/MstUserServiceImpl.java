/**
 * 
 */
package com.project.manage.ServiceImpl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public ResponseBean createUser(MstUserModel usermodel) throws Exception {
		try {
			usermodel.setUserName("Admin");
			usermodel.setPassword("Admin@123");
			usermodel.setFullName("Rajendra");
			usermodel.setEmail("rajendraprasadsahoo28@gmail.com");
			usermodel.setMobileNo("6370178554");
			usermodel.setGroupId(1);
			usermodel.setCreated_By(1l);
			usermodel.setCreated_On(Calendar.getInstance().getTime());
			usermodel.setStatus_Flag(0);
			usermodel.setAddress("Bhingar Pur");
			mstuserrepo.save(usermodel);
		}catch (Exception e) {
			throw new Exception(e);
		}
		return null;
	}

}
