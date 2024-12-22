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

	@Query(value="SELECT T.TENANT_ID,T.MOBILE_NO,T.RENT_AMOUNT,\r\n"
			+ "        T.ADV_AMOUNT,TO_CHAR(T.EFFECTIVE_DATE, 'DD-MON-YYYY'),\r\n"
			+ "        TO_CHAR(ADD_MONTHS(EFFECTIVE_DATE, 1), 'DD-MON-YYYY') AS ONE_MONTH_AFTER,\r\n"
			+ "        'N/A',H.HOUSE_NAME,R.ROOM_NO,U.FULLNAME,U.MOBILE,T.HOUSE_ID\r\n"
			+ "FROM TBL_MST_HM_TENANTDETAILS T\r\n"
			+ "LEFT JOIN TBL_MST_HM_HOMEDETAILS H ON T.HOUSE_ID =H.HOUSE_ID AND T.OWNER_ID=H.OWNER_ID\r\n"
			+ "LEFT JOIN TBL_MST_HM_ROOMDETAILS R ON T.ROOM_ID = R.ROOM_ID AND T.HOUSE_ID =R.HOUSE_ID AND T.OWNER_ID=R.OWNER_ID\r\n"
			+ "LEFT JOIN TBL_MST_USERDETAILS U ON U.USERID=T.OWNER_ID\r\n"
			+ "WHERE T.MOBILE_NO=?1",nativeQuery = true)
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
			+ "ORDER BY T.CREATED_ON DESC", nativeQuery = true)
	List<Object[]> viewtenanttoroom(Long userid, Long houseId, Long roomId);

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

}
