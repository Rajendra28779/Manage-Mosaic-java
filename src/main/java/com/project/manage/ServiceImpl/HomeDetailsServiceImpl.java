/**
 * 
 */
package com.project.manage.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.HomeDetails;
import com.project.manage.Repository.HomeDetailsRepository;
import com.project.manage.Service.HomeDetailsService;

/**
 * 
 */
@Service
public class HomeDetailsServiceImpl implements HomeDetailsService{

	@Autowired
	private HomeDetailsRepository homeDetailsrepo;
	
	@Override
	public ResponseBean addhomedetails(HomeDetails homeDetails) throws Exception {
		ResponseBean bean=new ResponseBean();
		try {
			homeDetails.setStatutsFlag(0);
			homeDetailsrepo.save(homeDetails);
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
		}catch (Exception e) {
			throw new Exception(e);
		}
		return bean;
	}

	@Override
	public ResponseBean gethomedetails(Long userid) throws Exception {
		ResponseBean bean=new ResponseBean();
		try {
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
			bean.setRecord(homeDetailsrepo.getroomdetails(userid));
		}catch (Exception e) {
			throw new Exception(e);
		}
		return bean;
	}

	@Override
	public ResponseBean inactiveroomdetails(Long detailsid) throws Exception {
		ResponseBean bean=new ResponseBean();
		try {
			HomeDetails details=homeDetailsrepo.findById(detailsid).get();
			details.setStatutsFlag(1);
			homeDetailsrepo.save(details);
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("Success");
		}catch (Exception e) {
			throw new Exception(e);
		}
		return bean;
	}

}
