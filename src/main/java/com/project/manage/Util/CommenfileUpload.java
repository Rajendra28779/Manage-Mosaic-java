/**
 * 
 */
package com.project.manage.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.PropertySource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 */

@PropertySource(value = "classpath:application.properties")
@SuppressWarnings("unused")
public class CommenfileUpload {
	
	private static ResourceBundle bskyAppResourcesBundel = ResourceBundle.getBundle("application");
	public static String windowsRootFolder = bskyAppResourcesBundel.getString("file.upload.directory.windows");
	public static String linuxRootFolder = bskyAppResourcesBundel.getString("file.upload.directory.linux");	
	public static String operatingSystem = System.getProperty("os.name").toLowerCase().trim();
	
	public static String getDocumentPath(String filePath) {
		String docPath = "";
		if (operatingSystem.indexOf("win") >= 0) {
			docPath = windowsRootFolder.trim() + filePath.trim();
		} else if (operatingSystem.indexOf("nix") >= 0 || operatingSystem.indexOf("nux") >= 0
				|| operatingSystem.indexOf("aix") > 0) {
			docPath = linuxRootFolder.trim() + filePath.trim();
		}
		return docPath;
	}

	public static String commenfileUpload(MultipartFile filename,String customFileName,String fileLocation) throws Exception {
		try {				
				String filePath = getDocumentPath(fileLocation);
				File file = new File(filePath + "/" + customFileName);
				boolean mkdirs = file.getParentFile().mkdirs();				
				byte[] bytes;
				try {
					bytes = filename.getBytes();
					Path path = Paths.get(filePath + "/" + customFileName);
					Files.write(path, bytes);
				} catch (IOException e) {
					e.printStackTrace();
				}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return customFileName;
	}
	
	public static void commenfileDownload(String fileName,String fileLocation, HttpServletResponse response) throws Exception {
		String filepath = null;
		try {
			filepath = getDocumentPath(fileLocation + "/" + fileName);
			File file = new File(filepath);
			if (!file.exists()) {
				String errorMessage = "Sorry! File You are Looking For Doesn't Exist";
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(StandardCharsets.UTF_8));
				outputStream.close();
			} else {
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
}
