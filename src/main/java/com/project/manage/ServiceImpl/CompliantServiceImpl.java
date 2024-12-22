package com.project.manage.ServiceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.RepairRequest;
import com.project.manage.Repository.RepairRequestRepository;
import com.project.manage.Repository.TenantDetailsRepository;
import com.project.manage.Service.CompliantService;
import com.project.manage.Util.CustomCheckedException;

@Service
public class CompliantServiceImpl implements CompliantService{
	
	@Autowired
	private RepairRequestRepository repairrqstrepo;
	
	@Autowired
	private TenantDetailsRepository tenantdetailsRepo;

	@Override
	public ResponseBean savehousemaintancerqst(RepairRequest repaisrqst) throws CustomCheckedException {
		ResponseBean bean = new ResponseBean();
		try {
			repaisrqst.setCreatedOn(Calendar.getInstance().getTime());
			repaisrqst.setStatusFlag(0);
			repaisrqst.setActionFlag(0);
			repaisrqst.setReminderCount(0);
			repairrqstrepo.save(repaisrqst);
			bean.setStatus(200);
			bean.setMessage("Success");
		} catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean getrqstdetailsfortrackt(Long userid) throws CustomCheckedException {
		ResponseBean bean = new ResponseBean();
		try {
			List<Object[]> objarr=repairrqstrepo.getrqstdetailsfortrackt(userid);	
			List<Object> list = new ArrayList<>();
			for(Object[] obj:objarr) {				
				Map<String,Object> roomdata = new HashMap<>();
				roomdata.put("rqstId", obj[0]);
				roomdata.put("rqstfor", obj[1]);
				roomdata.put("rqstdesc", obj[2]);
				roomdata.put("tenantId", obj[3]);
				roomdata.put("fullName", obj[4]);
				roomdata.put("houseName", obj[5]);
				roomdata.put("roomName", obj[6]);
				roomdata.put("rqstOn", obj[7]);
				roomdata.put("status", obj[8]);
				roomdata.put("tanantPhone", obj[9]);
				list.add(roomdata);
			}
			bean.setRecord(list);
			bean.setStatus(200);
			bean.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	public ResponseBean getrequestdetailsForowner(Long userid) throws CustomCheckedException {
		ResponseBean bean = new ResponseBean();
		Map<String,Object> map=new HashMap<>();
		try {
			List<Object[]> sumlist = repairrqstrepo.getcompliantsumdata(userid);
			Map<String,Object> objmap = new HashMap<>();
			for(Object[] obj:sumlist) {
				objmap.put("total", obj[0]);
				objmap.put("pending", obj[1]);
				objmap.put("resolve", obj[2]);
				objmap.put("inprogress", obj[3]);
			}
			map.put("sumdata", objmap);
			
			List<Object[]> pendingobjlist = repairrqstrepo.getpendingcompliant(userid);
			List<Object> pendingList = new ArrayList<>();
			for(Object[] obj:pendingobjlist) {
				Map<String,Object> objmapdata = new HashMap<>();
				objmapdata.put("rqstId", obj[0]);
				objmapdata.put("rqstfor", obj[1]);
				objmapdata.put("rqstdesc", obj[2]);
				objmapdata.put("tenantId", obj[3]);
				objmapdata.put("fullName", obj[4]);
				objmapdata.put("houseName", obj[5]);
				objmapdata.put("roomName", obj[6]);
				objmapdata.put("rqstOn", obj[7]);
				objmapdata.put("status", obj[8]);
				objmapdata.put("tanantPhone", obj[9]);
				pendingList.add(objmapdata);
			}
			map.put("pendingList", pendingList);
			
			bean.setRecord(map);
			bean.setStatus(200);
			bean.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean takeactionagainestrequest(Integer actiontype, Long userid, Long rqstId) throws CustomCheckedException {
		ResponseBean bean = new ResponseBean();
		try {
			RepairRequest rqsrdata=repairrqstrepo.findById(rqstId).get();
			rqsrdata.setActionFlag(actiontype);
			rqsrdata.setActionBy(userid);
			rqsrdata.setActionDate(Calendar.getInstance().getTime());
			repairrqstrepo.save(rqsrdata);
			bean.setStatus(200);
			bean.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomCheckedException(e);
		}
		return bean;
	}

}
