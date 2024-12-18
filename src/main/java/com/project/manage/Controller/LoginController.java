/**
 * 
 */
package com.project.manage.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.project.manage.Model.MstUserModel;
import com.project.manage.Service.LoginService;
import com.project.manage.Util.EncryptionUtils;

/**
 * 
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/login")
public class LoginController {
	
	@Autowired
	private LoginService loginserv;
	
	
	@PostMapping("/google")
	public ResponseEntity<?> verifyToken(@RequestBody Map<String, String> body) {
	    String token = body.get("token");
	    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
	        .setAudience(Collections.singletonList("662111002101-797vmf6dcffvn0vbkr96gj6u01rfsmgd.apps.googleusercontent.com"))
	        .build();
	    try {
	        GoogleIdToken idToken = verifier.verify(token);
	        if (idToken != null) {
	            Payload payload = idToken.getPayload();
	            String email = payload.get("email").toString();
	            String name = payload.get("name").toString();
	            String userId = payload.getSubject();
	            String pictureUrl = payload.get("picture").toString();
	            System.out.println(email);System.out.println(name);
	            System.out.println(userId);
	            System.out.println(pictureUrl);//loginserv.loginfrmgoogle(email,name)
	            return ResponseEntity.ok("ohk");	            
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	    }
	}


	@PostMapping("/loginapi")
	public ResponseEntity<?> loginapi(@RequestBody MstUserModel usermodel) {
		Map<String,Object> response=new HashMap<>();
		try {
			usermodel.setUserName(EncryptionUtils.decryptCode(usermodel.getUserName()));
			usermodel.setPassword(EncryptionUtils.decryptCode(usermodel.getPassword()));
			response=loginserv.loginapi(usermodel);
		}catch (Exception e) {
			response.put("stratus", HttpStatus.BAD_REQUEST);
			response.put("message", "Something Went Wrong !");
			response.put("error", e.getMessage());
		}
		return ResponseEntity.ok(response);
	}
}
