/**
 * 
 */
package com.project.manage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.manage.Model.RepairRequest;

/**
 * 
 */
@Repository
public interface RepairRequestRepository extends JpaRepository<RepairRequest, Long>{

	@Query(value = "SELECT RT.REQUEST_ID,MT.MAINTAIN_FOR,RT.RQST_DESC,\r\n"
			+ "RT.TENANT_ID,T.FULL_NAME,H.HOUSE_NAME,R.ROOM_NO,\r\n"
			+ "TO_CHAR(RT.CREATED_ON,'DD-MON-YYY HH:MI:SS AM'),\r\n"
			+ "CASE WHEN RT.ACTION_FLAG = 0 THEN 'Pending' \r\n"
			+ "    WHEN RT.ACTION_FLAG = 1 THEN 'Resolved'\r\n"
			+ "    WHEN RT.ACTION_FLAG = 2 THEN 'Rejected'\r\n"
			+ "    WHEN RT.ACTION_FLAG = 3 THEN 'InProgress'\r\n"
			+ "    WHEN RT.ACTION_FLAG = 4 THEN 'Re-Open'\r\n"
			+ "END status, T.MOBILE_NO\r\n"
			+ "FROM TBL_REPAIR_REQUEST RT\r\n"
			+ "LEFT JOIN TBL_MST_HM_HOMEDETAILS H ON H.HOUSE_ID=RT.HOUSE_ID\r\n"
			+ "LEFT JOIN TBL_MST_HM_ROOMDETAILS R ON R.ROOM_ID=RT.ROOM_ID AND R.HOUSE_ID=RT.HOUSE_ID    \r\n"
			+ "LEFT JOIN TBL_MST_HM_TENANTDETAILS T ON T.TENANT_ID = RT.TENANT_ID\r\n"
			+ "LEFT JOIN TBL_MAINTENANCE_TYPE MT ON mt.maintain_id = RT.REQUEST_FOR\r\n"
			+ "WHERE RT.STATUSFLAG=0\r\n"
			+ "AND (RT.APPLY_BY =?1 OR RT.TENANT_ID=?1)" ,nativeQuery = true)
	List<Object[]> getrqstdetailsfortrackt(Long userid);

	@Query(value = "SELECT \r\n"
			+ "    COUNT(rep.REQUEST_ID) AS total,\r\n"
			+ "    COUNT(CASE WHEN rep.ACTION_FLAG = 0 THEN 1 ELSE NULL END) AS pending,\r\n"
			+ "    COUNT(CASE WHEN rep.ACTION_FLAG = 1 THEN 1 ELSE NULL END) AS resolve,\r\n"
			+ "    COUNT(CASE WHEN rep.ACTION_FLAG = 3 THEN 1 ELSE NULL END) AS inprogress\r\n"
			+ "FROM \r\n"
			+ "    tbl_repair_request rep\r\n"
			+ "LEFT JOIN \r\n"
			+ "    tbl_mst_hm_homedetails h \r\n"
			+ "    ON h.house_id = rep.house_id\r\n"
			+ "WHERE \r\n"
			+ "    h.owner_id = 1 \r\n"
			+ "    AND rep.statusflag = 0"  ,nativeQuery = true)
	List<Object[]> getcompliantsumdata(Long userid);

	@Query(value = "SELECT RT.REQUEST_ID,MT.MAINTAIN_FOR,RT.RQST_DESC,\r\n"
			+ "RT.TENANT_ID,T.FULL_NAME,H.HOUSE_NAME,R.ROOM_NO,\r\n"
			+ "TO_CHAR(RT.CREATED_ON,'DD-MON-YYY HH:MI:SS AM'),\r\n"
			+ "CASE WHEN RT.ACTION_FLAG = 0 THEN 'Pending'\r\n"
			+ "WHEN RT.ACTION_FLAG = 3 THEN 'InProgress'\r\n"
			+ "END status, T.MOBILE_NO\r\n"
			+ "FROM TBL_REPAIR_REQUEST RT\r\n"
			+ "LEFT JOIN TBL_MST_HM_HOMEDETAILS H ON H.HOUSE_ID=RT.HOUSE_ID\r\n"
			+ "LEFT JOIN TBL_MST_HM_ROOMDETAILS R ON R.ROOM_ID=RT.ROOM_ID AND R.HOUSE_ID=RT.HOUSE_ID  \r\n"
			+ "LEFT JOIN TBL_MST_HM_TENANTDETAILS T ON T.TENANT_ID = RT.TENANT_ID\r\n"
			+ "LEFT JOIN TBL_MAINTENANCE_TYPE MT ON mt.maintain_id = RT.REQUEST_FOR\r\n"
			+ "WHERE RT.STATUSFLAG=0 and rt.action_flag in (0,3)\r\n"
			+ "AND h.owner_id=1" ,nativeQuery = true)
	List<Object[]> getpendingcompliant(Long userid);

}
