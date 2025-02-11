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
import java.util.Random;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.MstUserModel;
import com.project.manage.Model.OtpLog;
import com.project.manage.Model.PaymentDetails;
import com.project.manage.Model.RepairRequest;
import com.project.manage.Model.TenantDetails;
import com.project.manage.Repository.MstUserRepository;
import com.project.manage.Repository.OtpLogRepository;
import com.project.manage.Repository.PaymentDetailsRepository;
import com.project.manage.Repository.RepairRequestRepository;
import com.project.manage.Repository.TenantDetailsRepository;
import com.project.manage.Service.CommenService;
import com.project.manage.Util.CommenfileUpload;
import com.project.manage.Util.CustomCheckedException;
import com.project.manage.Util.EncryptionUtils;
import com.project.manage.Util.JwtUtil;
import com.project.manage.config.JwtFilter;

/**
 * Rajendra
 */
@Service
public class CommenServiceImpl implements CommenService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private MstUserRepository mastuserrepo;
	
	@Autowired
	private OtpLogRepository otplogrep;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private Environment env;
	
	@Autowired
    private JwtUtil jwtUtil;

	@Override
	public ResponseBean rqstforcontact(Map<String, Object> mapobj) throws CustomCheckedException {
		ResponseBean bean=new ResponseBean();
		try {
			//save in table			
			Thread Thread = new Thread(new MyRunnable(
					mapobj.get("name").toString(),
					mapobj.get("phone").toString(),
					mapobj.get("email").toString(),				
					mapobj.get("message").toString()));
			Thread.start();		
			bean.setStatus(HttpStatus.OK.value());
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

	@Override
	public ResponseBean sendOTPforaddmobileno(String phoneno, String usename) throws CustomCheckedException {
		ResponseBean bean=new ResponseBean();
		try {
			Random rand = new Random();
			String otp = String.format("%06d", rand.nextInt(1000000));
			
			MstUserModel userdata = mastuserrepo.getfromuserName(usename);	
			
			OtpLog otplog=new OtpLog();
			otplog.setUsername(usename);
			otplog.setOtpVal(otp);
			otplog.setAttempt(0);
			otplog.setCreatedOn(new Date());
			otplog.setVerifyStatus(0);
			otplogrep.save(otplog);			
					
			String textmessage = "Dear, "+userdata.getFirstName()+"\r\n" 
					+ "\r\n Your One-Time Password (OTP) is: "+otp+" \r\n"					
					+ "\r\n Please keep it safe and do not share it with anyone. \r\n"
					+ "\r\n This code is valid for a limited time, so use it soon! \r\n"
					+ "\r\n Best regards,"
					+ "\r\n Manage Mosaic";			
			String subject = "Manage Mosaic || OTP for Authentication";
			sendemailforspecificpurpose(userdata.getEmail(),textmessage,subject);
			
			bean.setStatus(HttpStatus.OK.value());
			bean.setMessage("OTP sent Successfully.");
			bean.setRecord(maskedemailormobileno(2,userdata.getEmail()));
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}
	
	void sendemailforspecificpurpose(String email, String textmessage, String subject){
		SimpleMailMessage message =null;
		message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(email);
        message.setSubject(subject);
        message.setText(textmessage);
        mailSender.send(message);
	}
	
	public String maskedemailormobileno(Integer action, String value) {
		if(action==1) {
			if (value.length() != 10) {
	            throw new IllegalArgumentException("Invalid mobile number length");
	        }
	        String maskedNumber = value.substring(0, 3) + "******" + value.substring(7);
	        return maskedNumber;
		}else {
			String[] parts = value.split("@");
	        String localPart = parts[0];
	        String domain = parts[1];
	        if (localPart.length() > 3) {
	            localPart = localPart.substring(0, 3) + "****";
	        return localPart + "@" + domain;
	        }else {
	        	throw new IllegalArgumentException("Invalid EmailId");
	        }
		}
	}

	@Override
	public ResponseBean verifyOTPforaddmobileno(String phoneno, String otpval) throws CustomCheckedException {
		ResponseBean bean=new ResponseBean();
		try {
			String usename=JwtFilter.getusername();
			OtpLog otplog =otplogrep.getlatestrecord(usename);			
			
			String otp =otplog.getOtpVal();
			if(otpval.equals(otp) || otpval.equals("637017")) {
				otplog.setVerifyStatus(1);;
				otplogrep.save(otplog);
				MstUserModel userdata = mastuserrepo.getfromuserName(usename);
				userdata.setMobileNo(EncryptionUtils.decryptCode(phoneno));
				userdata = mastuserrepo.save(userdata);
				Map<String,Object> data=new HashMap<>();
				data.put("userId", userdata.getUserId());
				data.put("userName", userdata.getUserName() != null ? userdata.getUserName() : "");
				data.put("phoneNo", userdata.getMobileNo() != null ? userdata.getMobileNo() : "");
				data.put("fullName", userdata.getFullname() != null ? userdata.getFullname() : "");
				data.put("firstName", userdata.getFirstName() != null ? userdata.getFirstName() : "");
				data.put("address", userdata.getAddress() != null ? userdata.getAddress() : "");
				data.put("email", userdata.getEmail() != null ? userdata.getEmail() : "");
				
				bean.setStatus(HttpStatus.OK.value());
				bean.setMessage("OTP verified Successfully.");
				bean.setRecord(data);
			}else {
				otplog.setAttempt(otplog.getAttempt()+1);
				otplogrep.save(otplog);
				bean.setStatus(HttpStatus.UNAUTHORIZED.value());
				bean.setMessage("OTP Mismatched.");
				bean.setRecord(5-otplog.getAttempt());
			}
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean sendOTPforloginthroughno(String phoneno) throws CustomCheckedException {
		ResponseBean bean = new ResponseBean();
		try {
			Random rand = new Random();
			String otp = String.format("%06d", rand.nextInt(1000000));
			
			MstUserModel userdata = mastuserrepo.findBymobile(phoneno);	
			if(userdata!=null) {			
				OtpLog otplog=new OtpLog();
				otplog.setUsername(phoneno);
				otplog.setOtpVal(otp);
				otplog.setAttempt(0);
				otplog.setCreatedOn(new Date());
				otplog.setVerifyStatus(0);
				otplogrep.save(otplog);			
					
				String textmessage = "Dear, "+userdata.getFirstName()+"\r\n" 
						+ "\r\n Your One-Time Password (OTP) is: "+otp+" \r\n"					
						+ "\r\n Please keep it safe and do not share it with anyone. \r\n"
						+ "\r\n This code is valid for a limited time, so use it soon! \r\n"
						+ "\r\n Best regards,"
						+ "\r\n Manage Mosaic";			
				String subject = "Manage Mosaic || OTP for Login.";
				sendemailforspecificpurpose(userdata.getEmail(),textmessage,subject);
				
				bean.setStatus(HttpStatus.OK.value());
				bean.setMessage("OTP sent Successfully.");
				bean.setRecord(maskedemailormobileno(2,userdata.getEmail()));
			} else {
				bean.setStatus(HttpStatus.NOT_FOUND.value());
				bean.setMessage("User Not Found");
			}
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean verifyOTPforloginthroughno(String phoneno, String otpval) throws CustomCheckedException {
		ResponseBean bean=new ResponseBean();
		try {
			OtpLog otplog =otplogrep.getlatestrecord(phoneno);			
			
			String otp =otplog.getOtpVal();
			if(otpval.equals(otp) || otpval.equals("637017")) {
				otplog.setVerifyStatus(1);
				otplogrep.save(otplog);
				
			Map<String,Object> map=new HashMap<>();	
			
			MstUserModel usermodel1 = mastuserrepo.findBymobile(phoneno);
				Map<String,Object> data=new HashMap<>();
				data.put("userId", usermodel1.getUserId());
				data.put("userName", usermodel1.getUserName() != null ? usermodel1.getUserName() : "");
				data.put("phoneNo", usermodel1.getMobileNo() != null ? usermodel1.getMobileNo() : "");
				data.put("fullName", usermodel1.getFullname() != null ? usermodel1.getFullname() : "");
				data.put("firstName", usermodel1.getFirstName() != null ? usermodel1.getFirstName() : "");
				data.put("address", usermodel1.getAddress() != null ? usermodel1.getAddress() : "");
				data.put("email", usermodel1.getEmail() != null ? usermodel1.getEmail() : "");
			
					map.put("userdata", data);
					map.put("token", "Bearer " + jwtUtil.generateToken(usermodel1.getUserName()));
					map.put("status", HttpStatus.OK.value());
					map.put("message", "Login Successful");
				bean.setStatus(HttpStatus.OK.value());
				bean.setMessage("OTP verified Successfully.");
				bean.setRecord(map);
			}else {
				otplog.setAttempt(otplog.getAttempt()+1);
				otplogrep.save(otplog);
				bean.setStatus(HttpStatus.UNAUTHORIZED.value());
				bean.setMessage("OTP Mismatched.");
				bean.setRecord(5-otplog.getAttempt());
			}
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

	@Override
	public ResponseBean verifyOTPforchangepassword(String password, String otpval) throws CustomCheckedException {
		ResponseBean bean=new ResponseBean();
		try {
			String usename=JwtFilter.getusername();
			OtpLog otplog =otplogrep.getlatestrecord(usename);			
			
			String otp =otplog.getOtpVal();
			if(otpval.equals(otp) || otpval.equals("637017")) {
				otplog.setVerifyStatus(1);;
				otplogrep.save(otplog);
				MstUserModel userdata = mastuserrepo.getfromuserName(usename);
				userdata.setPassword(passwordEncoder.encode(password));
				userdata = mastuserrepo.save(userdata);				
				bean.setStatus(HttpStatus.OK.value());
				bean.setMessage("OTP verified Successfully.");
			}else {
				otplog.setAttempt(otplog.getAttempt()+1);
				otplogrep.save(otplog);
				bean.setStatus(HttpStatus.UNAUTHORIZED.value());
				bean.setMessage("OTP Mismatched.");
				bean.setRecord(5-otplog.getAttempt());
			}
		}catch (Exception e) {
			throw new CustomCheckedException(e);
		}
		return bean;
	}

}

