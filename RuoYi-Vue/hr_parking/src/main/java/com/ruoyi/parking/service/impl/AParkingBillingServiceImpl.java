package com.ruoyi.parking.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.parking.mapper.AParkingBillingMapper;
import com.ruoyi.parking.domain.AParkingBilling;
import com.ruoyi.parking.service.IAParkingBillingService;

/**
 * 停车计费Service业务层处理
 * 
 * @author hr
 * @date 2024-11-20
 */
@Service
public class AParkingBillingServiceImpl implements IAParkingBillingService 
{
    @Autowired
    private AParkingBillingMapper aParkingBillingMapper;

    /**
     * 查询停车计费
     * 
     * @param billingId 停车计费主键
     * @return 停车计费
     */
    @Override
    public AParkingBilling selectAParkingBillingByBillingId(Long billingId)
    {
        return aParkingBillingMapper.selectAParkingBillingByBillingId(billingId);
    }

    /**
     * 查询停车计费列表
     * 
     * @param aParkingBilling 停车计费
     * @return 停车计费
     */
    @Override
    public List<AParkingBilling> selectAParkingBillingList(AParkingBilling aParkingBilling)
    {
        return aParkingBillingMapper.selectAParkingBillingList(aParkingBilling);
    }

    /**
     * 新增停车计费
     * 
     * @param aParkingBilling 停车计费
     * @return 结果
     */
    @Override
    public int insertAParkingBilling(AParkingBilling aParkingBilling)
    {
        return aParkingBillingMapper.insertAParkingBilling(aParkingBilling);
    }

    /**
     * 修改停车计费
     * 
     * @param aParkingBilling 停车计费
     * @return 结果
     */
    @Override
    public int updateAParkingBilling(AParkingBilling aParkingBilling)
    {
        return aParkingBillingMapper.updateAParkingBilling(aParkingBilling);
    }

    /**
     * 批量删除停车计费
     * 
     * @param billingIds 需要删除的停车计费主键
     * @return 结果
     */
    @Override
    public int deleteAParkingBillingByBillingIds(Long[] billingIds)
    {
        return aParkingBillingMapper.deleteAParkingBillingByBillingIds(billingIds);
    }

    /**
     * 删除停车计费信息
     * 
     * @param billingId 停车计费主键
     * @return 结果
     */
    @Override
    public int deleteAParkingBillingByBillingId(Long billingId)
    {
        return aParkingBillingMapper.deleteAParkingBillingByBillingId(billingId);
    }
}
