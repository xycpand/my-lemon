package com.mossle.workcal.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mossle.api.tenant.TenantHolder;

import com.mossle.core.export.Exportor;
import com.mossle.core.export.TableModel;
import com.mossle.core.hibernate.PropertyFilter;
import com.mossle.core.mapper.BeanMapper;
import com.mossle.core.page.Page;
import com.mossle.core.spring.MessageHelper;

import com.mossle.workcal.persistence.domain.WorkcalPart;
import com.mossle.workcal.persistence.manager.WorkcalPartManager;
import com.mossle.workcal.persistence.manager.WorkcalRuleManager;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("workcal")
public class WorkcalPartController {
    private WorkcalPartManager workcalPartManager;
    private WorkcalRuleManager workcalRuleManager;
    private Exportor exportor;
    private BeanMapper beanMapper = new BeanMapper();
    private MessageHelper messageHelper;
    private TenantHolder tenantHolder;

    @RequestMapping("workcal-part-list")
    public String list(@ModelAttribute Page page,
            @RequestParam Map<String, Object> parameterMap, Model model) {
        String tenantId = tenantHolder.getTenantId();
        List<PropertyFilter> propertyFilters = PropertyFilter
                .buildFromMap(parameterMap);
        propertyFilters.add(new PropertyFilter("EQS_tenantId", tenantId));
        page = workcalPartManager.pagedQuery(page, propertyFilters);
        model.addAttribute("page", page);

        return "workcal/workcal-part-list";
    }

    @RequestMapping("workcal-part-input")
    public String input(@RequestParam(value = "id", required = false) Long id,
            Model model) {
        String tenantId = tenantHolder.getTenantId();

        if (id != null) {
            WorkcalPart workcalPart = workcalPartManager.get(id);
            model.addAttribute("model", workcalPart);
        }

        model.addAttribute("workcalRules",
                workcalRuleManager.findBy("tenantId", tenantId));

        return "workcal/workcal-part-input";
    }

    @RequestMapping("workcal-part-save")
    public String save(@ModelAttribute WorkcalPart workcalPart,
            @RequestParam("workcalRuleId") Long workcalRuleId,
            RedirectAttributes redirectAttributes) {
        String tenantId = tenantHolder.getTenantId();
        Long id = workcalPart.getId();
        WorkcalPart dest = null;

        if (id != null) {
            dest = workcalPartManager.get(id);
            beanMapper.copy(workcalPart, dest);
        } else {
            dest = workcalPart;
            dest.setTenantId(tenantId);
        }

        dest.setWorkcalRule(workcalRuleManager.get(workcalRuleId));

        workcalPartManager.save(dest);
        messageHelper.addFlashMessage(redirectAttributes, "core.success.save",
                "保存成功");

        return "redirect:/workcal/workcal-part-list.do";
    }

    @RequestMapping("workcal-part-remove")
    public String remove(@RequestParam("selectedItem") List<Long> selectedItem,
            RedirectAttributes redirectAttributes) {
        List<WorkcalPart> workcalParts = workcalPartManager
                .findByIds(selectedItem);
        workcalPartManager.removeAll(workcalParts);
        messageHelper.addFlashMessage(redirectAttributes,
                "core.success.delete", "删除成功");

        return "redirect:/workcal/workcal-part-list.do";
    }

    @RequestMapping("workcal-part-export")
    public void export(@ModelAttribute Page page,
            @RequestParam Map<String, Object> parameterMap,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String tenantId = tenantHolder.getTenantId();
        List<PropertyFilter> propertyFilters = PropertyFilter
                .buildFromMap(parameterMap);
        propertyFilters.add(new PropertyFilter("EQS_tenantId", tenantId));

        page = workcalPartManager.pagedQuery(page, propertyFilters);

        List<WorkcalPart> workcalParts = (List<WorkcalPart>) page.getResult();

        TableModel tableModel = new TableModel();
        tableModel.setName("workcalPart");
        tableModel.addHeaders("id", "name");
        tableModel.setData(workcalParts);
        exportor.export(request, response, tableModel);
    }

    @RequestMapping("workcal-part-checkName")
    @ResponseBody
    public boolean checkName(@RequestParam("name") String name,
            @RequestParam(value = "id", required = false) Long id)
            throws Exception {
        String tenantId = tenantHolder.getTenantId();
        String hql = "from WorkcalPart where name=? and tenantId=?";
        Object[] params = { name, tenantId };

        if (id != null) {
            hql = "from WorkcalPart where name=? and tenantId=? and id<>?";
            params = new Object[] { name, tenantId, id };
        }

        WorkcalPart workcalPart = workcalPartManager.findUnique(hql, params);

        boolean result = (workcalPart == null);

        return result;
    }

    // ~ ======================================================================
    @Resource
    public void setWorkcalPartManager(WorkcalPartManager workcalPartManager) {
        this.workcalPartManager = workcalPartManager;
    }

    @Resource
    public void setWorkcalRuleManager(WorkcalRuleManager workcalRuleManager) {
        this.workcalRuleManager = workcalRuleManager;
    }

    @Resource
    public void setExportor(Exportor exportor) {
        this.exportor = exportor;
    }

    @Resource
    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @Resource
    public void setTenantHolder(TenantHolder tenantHolder) {
        this.tenantHolder = tenantHolder;
    }
}
