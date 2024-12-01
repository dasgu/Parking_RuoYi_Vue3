package com.ruoyi.parking.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 停车记录对象 a_parking_record
 * 
 * @author hr
 * @date 2024-11-20
 */
public class AParkingRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录编号 */
    @Excel(name = "记录编号")
    private Long recordId;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String licensePlateNumber;

    /** 进入时间 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Excel(name = "进入时间", width = 30, dateFormat = "yyyy/MM/dd HH:mm:ss")
    private Date entryTime;

    /** 离开时间 */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @Excel(name = "离开时间", width = 30, dateFormat = "yyyy/MM/dd HH:mm:ss")
    private Date outTime;

    /** 费用 */
    @Excel(name = "费用")
    private Long fee;

    /** 计费编号 */
    @Excel(name = "计费编号")
    private Long billingId;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setLicensePlateNumber(String licensePlateNumber) 
    {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getLicensePlateNumber() 
    {
        return licensePlateNumber;
    }
    public void setEntryTime(Date entryTime) 
    {
        this.entryTime = entryTime;
    }

    public Date getEntryTime() 
    {
        return entryTime;
    }
    public void setOutTime(Date outTime) 
    {
        this.outTime = outTime;
    }

    public Date getOutTime() 
    {
        return outTime;
    }
    public void setFee(Long fee) 
    {
        this.fee = fee;
    }

    public Long getFee() 
    {
        return fee;
    }
    public void setBillingId(Long billingId) 
    {
        this.billingId = billingId;
    }

    public Long getBillingId() 
    {
        return billingId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("licensePlateNumber", getLicensePlateNumber())
            .append("entryTime", getEntryTime())
            .append("outTime", getOutTime())
            .append("fee", getFee())
            .append("billingId", getBillingId())
            .toString();
    }
}
