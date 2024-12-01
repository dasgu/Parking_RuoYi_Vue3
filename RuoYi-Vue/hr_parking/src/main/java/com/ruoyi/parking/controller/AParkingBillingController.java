package com.ruoyi.parking.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.parking.domain.AParkingBilling;
import com.ruoyi.parking.service.IAParkingBillingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 停车计费Controller
 * 
 * @author hr
 * @date 2024-11-20
 */
@RestController
@RequestMapping("/parking/billing")
public class AParkingBillingController extends BaseController
{
    @Autowired
    private IAParkingBillingService aParkingBillingService;

    /**
     * 查询停车计费列表
     */
    @PreAuthorize("@ss.hasPermi('parking:billing:list')")
    @GetMapping("/list")
    public TableDataInfo list(AParkingBilling aParkingBilling)
    {
        startPage();
        List<AParkingBilling> list = aParkingBillingService.selectAParkingBillingList(aParkingBilling);
        return getDataTable(list);
    }

    /**
     * 导出停车计费列表
     */
    @PreAuthorize("@ss.hasPermi('parking:billing:export')")
    @Log(title = "停车计费", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AParkingBilling aParkingBilling)
    {
        List<AParkingBilling> list = aParkingBillingService.selectAParkingBillingList(aParkingBilling);
        ExcelUtil<AParkingBilling> util = new ExcelUtil<AParkingBilling>(AParkingBilling.class);
        util.exportExcel(response, list, "停车计费数据");
    }

    /**
     * 获取停车计费详细信息
     */
    @PreAuthorize("@ss.hasPermi('parking:billing:query')")
    @GetMapping(value = "/{billingId}")
    public AjaxResult getInfo(@PathVariable("billingId") Long billingId)
    {
        return success(aParkingBillingService.selectAParkingBillingByBillingId(billingId));
    }

    /**
     * 新增停车计费
     */
    @PreAuthorize("@ss.hasPermi('parking:billing:add')")
    @Log(title = "停车计费", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AParkingBilling aParkingBilling)
    {
        return toAjax(aParkingBillingService.insertAParkingBilling(aParkingBilling));
    }

    /**
     * 修改停车计费
     */
    @PreAuthorize("@ss.hasPermi('parking:billing:edit')")
    @Log(title = "停车计费", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AParkingBilling aParkingBilling)
    {
        return toAjax(aParkingBillingService.updateAParkingBilling(aParkingBilling));
    }

    /**
     * 删除停车计费
     */
    @PreAuthorize("@ss.hasPermi('parking:billing:remove')")
    @Log(title = "停车计费", businessType = BusinessType.DELETE)
    @DeleteMapping("/{billingIds}")
    public AjaxResult remove(@PathVariable Long[] billingIds)
    {
        return toAjax(aParkingBillingService.deleteAParkingBillingByBillingIds(billingIds));
    }
}
