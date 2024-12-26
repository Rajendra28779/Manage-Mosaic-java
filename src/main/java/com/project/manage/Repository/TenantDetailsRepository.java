/**
 * 
 */
package com.project.manage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.manage.Model.TenantDetails;

/**
 * 
 */
@Repository
public interface TenantDetailsRepository extends JpaRepository<TenantDetails, Long> {

	@Query(value="SELECT \r\n"
			+ "    T.TENANT_ID,T.MOBILE_NO,T.RENT_AMOUNT,T.ADV_AMOUNT,\r\n"
			+ "    TO_CHAR(T.EFFECTIVE_DATE, 'DD-MON-YYYY') AS EFFECTIVE_DATE,\r\n"
			+ "    H.HOUSE_NAME,R.ROOM_NO,U.FULLNAME AS OWNER_NAME,\r\n"
			+ "    U.MOBILE AS OWNER_MOBILE,T.HOUSE_ID,TO_CHAR(P.DUE_DATE,'DD-MON-YYYY'),\r\n"
			+ "	   P.PRV_PENDING_AMOUNT\r\n"
			+ "FROM TBL_MST_HM_TENANTDETAILS T\r\n"
			+ "LEFT JOIN TBL_MST_HM_HOMEDETAILS H ON T.HOUSE_ID = H.HOUSE_ID AND T.OWNER_ID = H.OWNER_ID\r\n"
			+ "LEFT JOIN TBL_MST_HM_ROOMDETAILS R ON T.ROOM_ID = R.ROOM_ID AND T.HOUSE_ID = R.HOUSE_ID AND T.OWNER_ID = R.OWNER_ID\r\n"
			+ "LEFT JOIN TBL_MST_USERDETAILS U ON U.USERID = T.OWNER_ID\r\n"
			+ "LEFT JOIN TBL_MST_HM_PAYMENTDETAILS P ON T.TENANT_ID = P.TENANT_ID AND P.DELETEDFLAG = 0 AND P.STATUSFLAG = 0 AND P.PAID_STATUS IN(0,3)\r\n"
			+ "WHERE T.MOBILE_NO =?1",nativeQuery = true)
	List<Object[]> gethousedetailsforuser(String phoneNo);

	@Query("from TenantDetails where houseId=:houseId and roomId=:roomId and statusFlag=0")
	List<TenantDetails> onChangeroomgettenanrdata(Long roomId, Long houseId);

	@Query( value ="SELECT T.TENANT_ID,H.HOUSE_NAME,R.ROOM_NO,T.MOBILE_NO,\r\n"
			+ "T.ALT_MOBILE_NO,T.NO_OF_MEMBER,T.MEMBER_MOBILE_NO,T.RENT_AMOUNT,\r\n"
			+ "TO_CHAR(T.EFFECTIVE_DATE,'DD-MON-YYYY'),T.ADV_AMOUNT,T.AADHAR_DETAILS,T.AGGREMENT_DOC,T.OTHER_DOC,T.FULL_NAME\r\n"
			+ "FROM TBL_MST_HM_TENANTDETAILS T \r\n"
			+ "LEFT JOIN TBL_MST_HM_HOMEDETAILS H ON H.HOUSE_ID=T.HOUSE_ID AND T.OWNER_ID=H.OWNER_ID\r\n"
			+ "LEFT JOIN TBL_MST_HM_ROOMDETAILS R ON R.HOUSE_ID=T.HOUSE_ID AND R.ROOM_ID=T.ROOM_ID\r\n"
			+ "WHERE T.STATUSFLAG=0 AND T.OWNER_ID=?1\r\n"
			+ "AND T.HOUSE_ID = DECODE(?2,NULL,T.HOUSE_ID,?2) \r\n"
			+ "AND T.ROOM_ID = DECODE(?3,NULL,T.ROOM_ID,?3) \r\n"
			+ "AND T.TENANT_ID = DECODE(?4,NULL,T.TENANT_ID,?4) \r\n"
			+ "ORDER BY T.CREATED_ON DESC", nativeQuery = true)
	List<Object[]> viewtenanttoroom(Long userid, Long houseId, Long roomId, Long tenantId);

	@Query( value ="SELECT TEN.TENANT_ID, ten.house_id,ten.room_id,\r\n"
			+ "        ADD_MONTHS(NVL(PD.DUE_DATE, TEN.EFFECTIVE_DATE), 1) duedate,\r\n"
			+ "        ten.rent_amount,nvl(pd.cur_pending_amt,0) pendingamt,\r\n"
			+ "        nvl(nvl(PD.CURRENT_MTR_READ,rd.last_mtr_read),0) meterreading\r\n"
			+ "FROM tbl_mst_hm_tenantdetails TEN \r\n"
			+ "left join tbl_mst_hm_roomdetails rd on TEN.room_id = rD.room_id AND TEN.house_id = rD.house_id \r\n"
			+ "LEFT JOIN ( SELECT pd.*,  ROW_NUMBER() OVER (\r\n"
			+ "               PARTITION BY pd.tenant_id, pd.room_id, pd.house_id\r\n"
			+ "               ORDER BY pd.payment_id DESC\r\n"
			+ "           ) AS rn\r\n"
			+ "    FROM tbl_mst_hm_paymentdetails pd\r\n"
			+ ") PD ON TEN.tenant_id = PD.tenant_id  AND TEN.room_id = PD.room_id \r\n"
			+ "    AND TEN.house_id = PD.house_id AND PD.rn = 1\r\n"
			+ "WHERE TEN.statusflag = 0\r\n"
			+ "AND ADD_MONTHS(NVL(PD.DUE_DATE, TEN.EFFECTIVE_DATE), 1) < SYSDATE +5", nativeQuery = true)
	List<Object[]> getuserlisttopaid();

	TenantDetails findByroomId(Long roomId);

	@Query( value ="SELECT P.PAYMENT_ID,T.TENANT_ID,T.FULL_NAME,T.MOBILE_NO,\r\n"
			+ "TO_CHAR(P.DUE_DATE,'DD-MON-YYYY'),P.RENT_AMOUNT,H.HOUSE_NAME,R.ROOM_NO,\r\n"
			+ "P.PRV_PENDING_AMOUNT,P.PRV_MTR_READ,T.ADV_AMOUNT,P.PAID_STATUS,P.CURRENT_BILL FROM TBL_MST_HM_PAYMENTDETAILS P\r\n"
			+ "LEFT JOIN TBL_MST_HM_TENANTDETAILS T ON T.TENANT_ID=P.TENANT_ID \r\n"
			+ "LEFT JOIN TBL_MST_HM_ROOMDETAILS R ON T.ROOM_ID=R.ROOM_ID AND T.HOUSE_ID=R.HOUSE_ID\r\n"
			+ "LEFT JOIN TBL_MST_HM_HOMEDETAILS H ON T.HOUSE_ID=H.HOUSE_ID\r\n"
			+ "WHERE P.STATUSFLAG=0 AND P.DELETEDFLAG= 0 AND P.PAID_STATUS IN (0,2)\r\n"
			+ "AND T.OWNER_ID=?1 AND T.HOUSE_ID = DECODE(?2,NULL,T.HOUSE_ID,?2)",nativeQuery = true)
	List<Object[]> gettenantlistforpaymentprocess(Long userid, Long houseId);

	@Query( value ="SELECT \r\n"
			+ "    COUNT(DISTINCT H.HOUSE_ID) AS HOME_COUNT,\r\n"
			+ "    COUNT(DISTINCT R.ROOM_ID) AS ROOM_COUNT,\r\n"
			+ "    COUNT(DISTINCT T.TENANT_ID) AS NO_OF_ROOM,\r\n"
			+ "    (COUNT(DISTINCT R.ROOM_ID) - COUNT(DISTINCT T.TENANT_ID)) AS VACANT_ROOMS,\r\n"
			+ "    0 ADVANCE_BOOKING,\r\n"
			+ "    SUM(DISTINCT T.NO_OF_MEMBER) AS TENANT_COUNT    \r\n"
			+ "FROM TBL_MST_HM_HOMEDETAILS H\r\n"
			+ "LEFT JOIN TBL_MST_HM_ROOMDETAILS R ON R.HOUSE_ID=H.HOUSE_ID AND R.OWNER_ID=H.OWNER_ID AND R.STATUSFLAG=0\r\n"
			+ "LEFT JOIN TBL_MST_HM_TENANTDETAILS T ON T.ROOM_ID=R.ROOM_ID AND T.HOUSE_ID=H.HOUSE_ID \r\n"
			+ "        AND T.OWNER_ID=H.OWNER_ID AND T.STATUSFLAG=0\r\n"
			+ "WHERE H.OWNER_ID=?1 AND H.STATUSFLAG=0",nativeQuery = true)
	List<Object[]> roomcountdata(Long userid);

}
