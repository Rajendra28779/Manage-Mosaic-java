/**
 * 
 */
package com.project.manage.ServiceImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.PaymentDetails;
import com.project.manage.Model.RepairRequest;
import com.project.manage.Model.TenantDetails;
import com.project.manage.Repository.PaymentDetailsRepository;
import com.project.manage.Repository.RepairRequestRepository;
import com.project.manage.Repository.TenantDetailsRepository;
import com.project.manage.Service.CommenService;
import com.project.manage.Util.CommenfileUpload;
import com.project.manage.Util.CustomCheckedException;

/**
 * Rajendra
 */
@Service
public class CommenServiceImpl implements CommenService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private TenantDetailsRepository tenantrepo;
	
	@Autowired
	private PaymentDetailsRepository paymentdtlsrepo;
	
	@Autowired
	private Environment env;

	@Override
	public ResponseBean rqstforcontact(Map<String, Object> mapobj) throws CustomCheckedException {
		ResponseBean bean=new ResponseBean();
		try {
			//save in table
			
			
			
			//save in table			
			Thread Thread = new Thread(new MyRunnable(
					mapobj.get("name").toString(),
					mapobj.get("phone").toString(),
					mapobj.get("email").toString(),				
					mapobj.get("message").toString()));
			Thread.start();		
			bean.setStatus(200);
			bean.setMessage("ThanYou ! Our Team Will Connect you Sortly .");
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}
	
	class MyRunnable implements Runnable{
		
		private String name;
		private String mobile;
		private String email;
		private String messsage;

		public MyRunnable(String name, String mobile, String email, String messsage) {
			this.name = name;
			this.mobile = mobile;
			this.email = email;
			this.messsage = messsage;
		}
		List<String> emailId=Arrays.asList(
				"rajendraprasadsahoo28@gmail.com"
				);	

		@Override
		public void run() {
			String subject="Manage Mosaic || Message from User";
			String textmessage="Thank You .";
			SimpleMailMessage message =null;
			
			//send mail to Customer
			message = new SimpleMailMessage();
	        message.setFrom(env.getProperty("spring.mail.username"));
	        message.setTo(email.trim());
			textmessage = "Dear, "+name+"\r\n" 
					+ "\r\n" + "Thank you for reaching out to Manage Mosaic. "
					+ "\r\n We have received your message and will get back to you shortly to assist with your request.\r\n"					
					+ "\r\n Should you need any further assistance in the meantime, please feel free to contact us at the following :"+"\r\n"
					+ "\r\n Mobile No: "+"6370178554"
					+ "\r\n Email: "+"managemosaic@gmail.com"
					+ "\r\n Address: "+"Hanspal , Bhubaneswar,\r\n"
							+ "          Odisha-752115 Near Balianta \r\n"
					+ "\r\n We appreciate your patience and look forward to helping you.\r\n"
					+ "\r\n Best regards,"
					+ "\r\n Manage Mosaic";
	        message.setSubject(subject);
	        message.setText(textmessage);
	        mailSender.send(message);
			
			//send mail to Owner
			for(String sendemail:emailId) {
				message = new SimpleMailMessage();
		        message.setFrom(env.getProperty("spring.mail.username"));
		        message.setTo(sendemail);
				textmessage = "Dear, Manage Mosaic\r\n" + "\r\n" + "You have received a new message from a user. Please see the details below:\r\n"
						+ "\r\n Name: "+name
						+ "\r\n Mobile No: "+mobile
						+ "\r\n Email: "+email
						+ "\r\n Message: "+messsage+"\r\n"
						+ "\r\n Please reach out to the user and assist them as soon as possible."+"\r\n"
						+ "\r\n Best regards,"
						+ "\r\n Manage Mosaic";
		        message.setSubject(subject);
		        message.setText(textmessage);
		        mailSender.send(message);
			}			
		}
		
	}

	@Override
	public String saveroomimage(MultipartFile image1, Long ownerId, Long houseId) throws CustomCheckedException {
		String coustemFilename=null;
		try {
			String fileLocation=env.getProperty("file.roomImage.folder");
			String fileName = image1.getOriginalFilename();
			Timestamp instant = Timestamp.from(Instant.now());
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
			String fileprifx=env.getProperty("file.roomImage.prifix");
			coustemFilename=fileprifx + "_"+ ownerId + "_" + houseId + "_"
					+ instant.toString().replaceAll("[,-.:\\s]", "")+"."+ fileExtension;
			coustemFilename = CommenfileUpload.commenfileUpload(image1, coustemFilename, fileLocation);
		} catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return coustemFilename;
	}

	@Override
	public String savetenantDoc(MultipartFile image1) throws CustomCheckedException {
		String coustemFilename=null;
		try {
			String fileLocation=env.getProperty("file.tenant.doc");
			String fileName = image1.getOriginalFilename();
			Timestamp instant = Timestamp.from(Instant.now());
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
			String fileprifx=env.getProperty("file.tenant.doc.prifix");
			coustemFilename=fileprifx + "_"+instant.toString().replaceAll("[,-.:\\s]", "")+"."+ fileExtension;
			coustemFilename = CommenfileUpload.commenfileUpload(image1, coustemFilename, fileLocation);
		} catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return coustemFilename;
	}

	@Override
	public void downloadcommondoc(String fileName, HttpServletResponse response) throws CustomCheckedException {
	    try {
	        String folderName = null;
	        if (fileName.startsWith(env.getProperty("file.tenant.doc.prifix"))) {
	            folderName = env.getProperty("file.tenant.doc");
	        } else if (fileName.startsWith(env.getProperty("file.roomImage.prifix"))) {
	            folderName = env.getProperty("file.roomImage.folder");
	        } else {
	            throw new Exception("Folder not found");
	        }	 
	        CommenfileUpload.commenfileDownload(fileName, folderName, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new CustomCheckedException("Error while downloading file: " + e.getMessage());
	    }		
	}

	

}
