package com.mossle.operation.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import com.mossle.api.form.FormDTO;
import com.mossle.api.humantask.HumanTaskConnector;
import com.mossle.api.humantask.HumanTaskDTO;
import com.mossle.api.keyvalue.FormParameter;
import com.mossle.api.keyvalue.KeyValueConnector;
import com.mossle.api.keyvalue.Prop;
import com.mossle.api.keyvalue.Record;
import com.mossle.api.keyvalue.RecordBuilder;
import com.mossle.api.process.ProcessConnector;
import com.mossle.api.store.StoreConnector;
import com.mossle.api.tenant.TenantHolder;

import com.mossle.button.ButtonDTO;
import com.mossle.button.ButtonHelper;

import com.mossle.core.MultipartHandler;
import com.mossle.core.auth.CurrentUserHolder;
import com.mossle.core.mapper.JsonMapper;
import com.mossle.core.spring.MessageHelper;

import com.mossle.operation.service.OperationService;

import com.mossle.xform.Xform;
import com.mossle.xform.XformBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 任务工具条.
 * 
 * @author Lingo
 */
@Controller
@RequestMapping("operation")
public class TaskOperationController {
    private static Logger logger = LoggerFactory
            .getLogger(TaskOperationController.class);
    public static final int STATUS_DRAFT_PROCESS = 0;
    public static final int STATUS_DRAFT_TASK = 1;
    public static final int STATUS_RUNNING = 2;
    private OperationService operationService;
    private KeyValueConnector keyValueConnector;
    private MessageHelper messageHelper;
    private CurrentUserHolder currentUserHolder;
    private ProcessConnector processConnector;
    private HumanTaskConnector humanTaskConnector;
    private MultipartResolver multipartResolver;
    private StoreConnector storeConnector;
    private ButtonHelper buttonHelper = new ButtonHelper();
    private JsonMapper jsonMapper = new JsonMapper();
    private TenantHolder tenantHolder;

    /**
     * 保存草稿.
     */
    @RequestMapping("task-operation-saveDraft")
    public String saveDraft(HttpServletRequest request) throws Exception {
        this.doSaveRecord(request);

        return "operation/task-operation-saveDraft";
    }

    /**
     * 显示任务表单.
     */
    @RequestMapping("task-operation-viewTaskForm")
    public String viewTaskForm(@RequestParam("humanTaskId") String humanTaskId,
            Model model, RedirectAttributes redirectAttributes)
            throws Exception {
        HumanTaskDTO humanTaskDto = humanTaskConnector
                .findHumanTask(humanTaskId);

        if (humanTaskDto == null) {
            messageHelper.addFlashMessage(redirectAttributes, "任务不存在");

            return "redirect:/humantask/workspace-listPersonalTasks.do";
        }

        FormDTO formDto = this.findTaskForm(humanTaskDto);

        if (formDto.isRedirect()) {
            String redirectUrl = formDto.getUrl() + "?humanTaskId="
                    + formDto.getTaskId();

            return "redirect:" + redirectUrl;
        }

        model.addAttribute("formDto", formDto);
        model.addAttribute("humanTaskId", humanTaskId);

        if ((humanTaskId != null) && (!"".equals(humanTaskId))) {
            // 如果是任务草稿，直接通过processInstanceId获得record，更新数据
            // TODO: 分支肯定有问题
            String processInstanceId = humanTaskDto.getProcessInstanceId();
            String json = this.findTaskFormData(processInstanceId);

            if (json != null) {
                model.addAttribute("json", json);
            }

            Record record = keyValueConnector.findByRef(processInstanceId);

            Xform xform = new XformBuilder().setStoreConnector(storeConnector)
                    .setContent(formDto.getContent()).setRecord(record).build();
            model.addAttribute("xform", xform);
        }

        List<ButtonDTO> buttons = new ArrayList<ButtonDTO>();

        for (String button : formDto.getButtons()) {
            buttons.add(buttonHelper.findButton(button));
        }

        if (buttons.isEmpty()) {
            buttons.add(buttonHelper.findButton("saveDraft"));
            buttons.add(buttonHelper.findButton("completeTask"));
        }

        model.addAttribute("buttons", buttons);

        return "operation/task-operation-viewTaskForm";
    }

    /**
     * 完成任务.
     */
    @RequestMapping("task-operation-completeTask")
    public String completeTask(HttpServletRequest request,
            RedirectAttributes redirectAttributes) throws Exception {
        String userId = currentUserHolder.getUserId();
        String tenantId = tenantHolder.getTenantId();
        MultipartHandler multipartHandler = new MultipartHandler(
                multipartResolver);
        Record record = null;
        String humanTaskId = null;
        FormParameter formParameter = null;
        HumanTaskDTO humanTaskDto = null;
        FormDTO formDto = null;

        try {
            multipartHandler.handle(request);
            logger.info("{}", multipartHandler.getMultiValueMap());
            logger.info("{}", multipartHandler.getMultiFileMap());

            formParameter = this.buildFormParameter(multipartHandler);

            humanTaskId = formParameter.getHumanTaskId();
            operationService.saveDraft(userId, tenantId, formParameter);

            formDto = humanTaskConnector.findTaskForm(humanTaskId);

            humanTaskDto = humanTaskConnector.findHumanTask(humanTaskId);

            String processInstanceId = humanTaskDto.getProcessInstanceId();
            record = keyValueConnector.findByRef(processInstanceId);

            record = new RecordBuilder().build(record, multipartHandler,
                    storeConnector, tenantId);

            keyValueConnector.save(record);
        } finally {
            multipartHandler.clear();
        }

        Xform xform = new XformBuilder().setStoreConnector(storeConnector)
                .setContent(formDto.getContent()).setRecord(record).build();
        Map<String, Object> taskParameters = xform.getMapData();
        logger.info("taskParameters : {}", taskParameters);

        try {
            humanTaskConnector.completeTask(humanTaskId,
                    currentUserHolder.getUserId(), taskParameters);
        } catch (IllegalStateException ex) {
            logger.error(ex.getMessage(), ex);
            messageHelper.addFlashMessage(redirectAttributes, ex.getMessage());

            return "redirect:/humantask/workspace-personalTasks.do";
        }

        if (record == null) {
            record = new Record();
        }

        record = new RecordBuilder().build(record, STATUS_RUNNING,
                humanTaskDto.getProcessInstanceId());
        keyValueConnector.save(record);

        return "operation/task-operation-completeTask";
    }

    /**
     * 领取任务.
     */
    @RequestMapping("task-operation-claimTask")
    public String claimTask(@RequestParam("humanTaskId") String humanTaskId) {
        String userId = currentUserHolder.getUserId();
        humanTaskConnector.claimTask(humanTaskId, userId);

        return "redirect:/humantask/workspace-personalTasks.do";
    }

    /**
     * 释放任务.
     */
    @RequestMapping("task-operation-releaseTask")
    public String releaseTask(@RequestParam("humanTaskId") String humanTaskId) {
        humanTaskConnector.releaseTask(humanTaskId);

        return "redirect:/humantask/workspace-personalTasks.do";
    }

    /**
     * 回退任务，前一个任务.
     */
    @RequestMapping("task-operation-rollbackPrevious")
    public String rollbackPrevious(
            @RequestParam("humanTaskId") String humanTaskId) {
        humanTaskConnector.rollbackPrevious(humanTaskId);

        return "redirect:/humantask/workspace-personalTasks.do";
    }

    /**
     * 回退任务，开始事件.
     */
    @RequestMapping("task-operation-rollbackStart")
    public String rollbackStart(@RequestParam("humanTaskId") String humanTaskId) {
        humanTaskConnector.rollbackStart(humanTaskId);

        return "redirect:/humantask/workspace-personalTasks.do";
    }

    /**
     * 撤销任务.
     */
    @RequestMapping("task-operation-withdraw")
    public String withdraw(@RequestParam("humanTaskId") String humanTaskId) {
        humanTaskConnector.withdraw(humanTaskId);

        return "redirect:/humantask/workspace-personalTasks.do";
    }

    /**
     * 转办.
     */
    @RequestMapping("task-operation-transfer")
    public String transfer(@RequestParam("humanTaskId") String humanTaskId,
            @RequestParam("userId") String userId) {
        humanTaskConnector.transfer(humanTaskId, userId);

        return "redirect:/humantask/workspace-delegatedTasks.do";
    }

    /**
     * 协办.
     */
    @RequestMapping("task-operation-delegateTask")
    public String delegateTask(@RequestParam("humanTaskId") String humanTaskId,
            @RequestParam("userId") String userId) {
        humanTaskConnector.delegateTask(humanTaskId, userId);

        return "redirect:/humantask/workspace-delegatedTasks.do";
    }

    /**
     * 链状协办.
     */
    @RequestMapping("task-operation-delegateTaskCreate")
    public String delegateTaskCreate(
            @RequestParam("humanTaskId") String humanTaskId,
            @RequestParam("userId") String userId) {
        humanTaskConnector.delegateTaskCreate(humanTaskId, userId);

        return "redirect:/humantask/workspace-delegatedTasks.do";
    }

    // ~ ======================================================================
    /**
     * 通过multipart请求构建formParameter.
     */
    public FormParameter buildFormParameter(MultipartHandler multipartHandler) {
        FormParameter formParameter = new FormParameter();
        formParameter.setMultiValueMap(multipartHandler.getMultiValueMap());
        formParameter.setMultiFileMap(multipartHandler.getMultiFileMap());
        formParameter.setBusinessKey(multipartHandler.getMultiValueMap()
                .getFirst("businessKey"));
        formParameter.setBpmProcessId(multipartHandler.getMultiValueMap()
                .getFirst("bpmProcessId"));
        formParameter.setHumanTaskId(multipartHandler.getMultiValueMap()
                .getFirst("humanTaskId"));

        return formParameter;
    }

    /**
     * 把数据先保存到keyvalue里.
     */
    public FormParameter doSaveRecord(HttpServletRequest request)
            throws Exception {
        String userId = currentUserHolder.getUserId();
        String tenantId = tenantHolder.getTenantId();
        MultipartHandler multipartHandler = new MultipartHandler(
                multipartResolver);
        FormParameter formParameter = null;

        try {
            multipartHandler.handle(request);
            logger.info("{}", multipartHandler.getMultiValueMap());
            logger.info("{}", multipartHandler.getMultiFileMap());

            formParameter = this.buildFormParameter(multipartHandler);

            String businessKey = operationService.saveDraft(userId, tenantId,
                    formParameter);

            if ((formParameter.getBusinessKey() == null)
                    || "".equals(formParameter.getBusinessKey().trim())) {
                formParameter.setBusinessKey(businessKey);
            }

            Record record = keyValueConnector.findByCode(businessKey);

            record = new RecordBuilder().build(record, multipartHandler,
                    storeConnector, tenantId);

            keyValueConnector.save(record);
        } finally {
            multipartHandler.clear();
        }

        return formParameter;
    }

    /**
     * 读取任务对应的表单数据，转换成json.
     */
    public String findTaskFormData(String processInstanceId) throws Exception {
        Record record = keyValueConnector.findByRef(processInstanceId);

        if (record == null) {
            return null;
        }

        Map map = new HashMap();

        for (Prop prop : record.getProps().values()) {
            map.put(prop.getCode(), prop.getValue());
        }

        String json = jsonMapper.toJson(map);

        return json;
    }

    /**
     * 根据taskId获得formDto.
     */
    public FormDTO findTaskForm(HumanTaskDTO humanTaskDto) {
        FormDTO formDto = humanTaskConnector.findTaskForm(humanTaskDto.getId());

        return formDto;
    }

    // ~ ======================================================================
    @Resource
    public void setKeyValueConnector(KeyValueConnector keyValueConnector) {
        this.keyValueConnector = keyValueConnector;
    }

    @Resource
    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    @Resource
    public void setCurrentUserHolder(CurrentUserHolder currentUserHolder) {
        this.currentUserHolder = currentUserHolder;
    }

    @Resource
    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }

    @Resource
    public void setProcessConnector(ProcessConnector processConnector) {
        this.processConnector = processConnector;
    }

    @Resource
    public void setHumanTaskConnector(HumanTaskConnector humanTaskConnector) {
        this.humanTaskConnector = humanTaskConnector;
    }

    @Resource
    public void setMultipartResolver(MultipartResolver multipartResolver) {
        this.multipartResolver = multipartResolver;
    }

    @Resource
    public void setStoreConnector(StoreConnector storeConnector) {
        this.storeConnector = storeConnector;
    }

    @Resource
    public void setTenantHolder(TenantHolder tenantHolder) {
        this.tenantHolder = tenantHolder;
    }
}
