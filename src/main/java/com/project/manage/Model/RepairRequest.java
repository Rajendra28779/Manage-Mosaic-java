/**
 * 
 */
package com.project.manage.Model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

/**
 * RAJENDRA
 * 
 * actionFlag=0 pending/applied
 * actionFlag=1 Resolve
 * actionFlag=2 Reject
 * actionFlag=3 inprogress
 * actionFlag=4 Re-open
 */

@Entity
@Data
@Table(name = "TBL_REPAIR_REQUEST")
public class RepairRequest {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REQUEST_ID")
    private Long requestId;

    @Column(name = "TENANT_ID")
    private Long tenantId;

    @Column(name = "HOUSE_ID")
    private Long houseId;

    @Column(name = "ROOM_ID")
    private Long roomId;

    @Column(name = "REQUEST_FOR")
    private Long requestFor;

    @Column(name = "RQST_DESC")
    private String requestDescription;

    @Column(name = "APPLY_BY")
    private Long applyBy;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @Column(name = "ACTION_FLAG")
    private Integer actionFlag;

    @Column(name = "ACTION_BY")
    private Long actionBy;

    @Column(name = "ACTION_DATE")
    private Date actionDate;

    @Column(name = "REMINDER_COUNT")
    private Integer reminderCount;

    @Column(name = "STATUSFLAG")
    private Integer statusFlag;
}
