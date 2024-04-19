package com.project.manage.Util;

import java.util.Base64;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Auther : Rajendra Prasad Sahoo
 */

public class EncryptionUtils {

	 private EncryptionUtils() {}
	    private static final Random random = new Random();

	    public static String encrypt(String normalPass) {
	        if (normalPass != null && !normalPass.isEmpty())
	            return makeRandom() + Base64.getEncoder().encodeToString(normalPass.getBytes()) + makeRandom();
	        else
	            return normalPass;
	    }

	    private static String makeRandom() {
	        StringBuilder text = new StringBuilder();
	        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	        for (int i = 0; i < 5; i++) {
	            text.append(possible.charAt(random.nextInt(possible.length())));
	        }
	        return text.toString();
	    }

	    public static String decryptCode(String encryptedData) {
	        if (encryptedData != null && !encryptedData.isEmpty()) {
	            encryptedData = encryptedData.substring(5, encryptedData.length() - 5);
	            return new String(Base64.getDecoder().decode(encryptedData));
	        } else
	            return null;
	    }

}
