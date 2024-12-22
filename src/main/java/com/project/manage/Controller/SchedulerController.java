/**
 * 
 */
package com.project.manage.Controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.manage.Service.SchedulerService;
import com.project.manage.Util.CustomCheckedException;

/**
 * 
 */
@Component
public class SchedulerController {
	
	private SchedulerService schedulerserv;
	
	@Scheduled(cron = "0 0 1 * * ?")
	private void addpaymentdata() {
		try {
			schedulerserv.addpaymentdata();
		} catch (CustomCheckedException e) {
			e.printStackTrace();
		}
	}

}
