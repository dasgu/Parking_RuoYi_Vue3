package com.ruoyi.parking.service;

import java.util.List;
import com.ruoyi.parking.domain.AParkingBilling;

/**
 * 停车计费Service接口
 * 
 * @author hr
 * @date 2024-11-20
 */
public interface IAParkingBillingService 
{
    /**
     * 查询停车计费
     * 
     * @param billingId 停车计费主键
     * @return 停车计费
     */
    public AParkingBilling selectAParkingBillingByBillingId(Long billingId);

    /**
     * 查询停车计费列表
     * 
     * @param aParkingBilling 停车计费
     * @return 停车计费集合
     */
    public List<AParkingBilling> selectAParkingBillingList(AParkingBilling aParkingBilling);

    /**
     * 新增停车计费
     * 
     * @param aParkingBilling 停车计费
     * @return 结果
     */
    public int insertAParkingBilling(AParkingBilling aParkingBilling);

    /**
     * 修改停车计费
     * 
     * @param aParkingBilling 停车计费
     * @return 结果
     */
    public int updateAParkingBilling(AParkingBilling aParkingBilling);

    /**
     * 批量删除停车计费
     * 
     * @param billingIds 需要删除的停车计费主键集合
     * @return 结果
     */
    public int deleteAParkingBillingByBillingIds(Long[] billingIds);

    /**
     * 删除停车计费信息
     * 
     * @param billingId 停车计费主键
     * @return 结果
     */
    public int deleteAParkingBillingByBillingId(Long billingId);
}
