/**
 * 
 */
package com.project.manage.Service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.project.manage.Bean.ResponseBean;
import com.project.manage.Model.RepairRequest;
import com.project.manage.Util.CustomCheckedException;

/**
 * Rajendra
 */
public interface CommenService {

	ResponseBean rqstforcontact(Map<String, Object> mapobj) throws CustomCheckedException ;

	String saveroomimage(MultipartFile image1, Long ownerId, Long houseId) throws CustomCheckedException;

	String savetenantDoc(MultipartFile image1) throws CustomCheckedException;

	void downloadcommondoc(String fileName, HttpServletResponse response) throws CustomCheckedException;

}
