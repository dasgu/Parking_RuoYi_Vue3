package com.ruoyi.parking.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 停车计费对象 a_parking_billing
 * 
 * @author hr
 * @date 2024-11-20
 */
public class AParkingBilling extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 计费编号 */
    @Excel(name = "计费编号")
    private Long billingId;

    /** 每分钟的费用 */
    @Excel(name = "每分钟的费用")
    private Long perMinuteFee;

    /** 额外费用 */
    @Excel(name = "额外费用")
    private Long extraFee;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setBillingId(Long billingId)
    {
        this.billingId = billingId;
    }

    public Long getBillingId() 
    {
        return billingId;
    }
    public void setPerMinuteFee(Long perMinuteFee) 
    {
        this.perMinuteFee = perMinuteFee;
    }

    public Long getPerMinuteFee() 
    {
        return perMinuteFee;
    }
    public void setExtraFee(Long extraFee) 
    {
        this.extraFee = extraFee;
    }

    public Long getExtraFee() 
    {
        return extraFee;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("billingId", getBillingId())
            .append("perMinuteFee", getPerMinuteFee())
            .append("extraFee", getExtraFee())
            .append("remark", getRemark())
            .toString();
    }
}
