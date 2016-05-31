package com.mossle.bpm.cmd;

import com.mossle.api.form.FormDTO;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.form.DefaultFormHandler;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindStartFormCmd implements Command<FormDTO> {
    private static Logger logger = LoggerFactory
            .getLogger(FindStartFormCmd.class);
    private String processDefinitionId;

    public FindStartFormCmd(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public FormDTO execute(CommandContext commandContext) {
        ProcessDefinitionEntity processDefinitionEntity = Context
                .getProcessEngineConfiguration().getDeploymentManager()
                .findDeployedProcessDefinitionById(processDefinitionId);

        if (processDefinitionEntity == null) {
            throw new IllegalArgumentException(
                    "cannot find processDefinition : " + processDefinitionId);
        }

        FormDTO formDto = new FormDTO();
        formDto.setProcessDefinitionId(processDefinitionId);

        if (processDefinitionEntity.hasStartFormKey()) {
            formDto.setAutoCompleteFirstTask(false);

            DefaultFormHandler formHandler = (DefaultFormHandler) processDefinitionEntity
                    .getStartFormHandler();

            if (formHandler.getFormKey() != null) {
                String formKey = formHandler.getFormKey().getExpressionText();
                formDto.setCode(formKey);
                formDto.setActivityId(processDefinitionEntity.getInitial()
                        .getId());
            }
        } else {
            formDto.setAutoCompleteFirstTask(true);

            ActivityImpl startActivity = processDefinitionEntity.getInitial();

            if (startActivity.getOutgoingTransitions().size() != 1) {
                throw new IllegalStateException(
                        "start activity outgoing transitions cannot more than 1, now is : "
                                + startActivity.getOutgoingTransitions().size());
            }

            PvmTransition pvmTransition = startActivity
                    .getOutgoingTransitions().get(0);
            PvmActivity targetActivity = pvmTransition.getDestination();

            if (!"userTask".equals(targetActivity.getProperty("type"))) {
                logger.info("first activity is not userTask, just skip");
            } else {
                String taskDefinitionKey = targetActivity.getId();
                logger.debug("activityId : {}", targetActivity.getId());

                TaskDefinition taskDefinition = processDefinitionEntity
                        .getTaskDefinitions().get(taskDefinitionKey);

                Expression expression = taskDefinition.getAssigneeExpression();

                if (expression != null) {
                    String expressionText = expression.getExpressionText();
                    logger.debug("{}", expressionText);
                    logger.debug("{}", startActivity.getProperties());
                    logger.debug("{}", processDefinitionEntity.getProperties());

                    String initiatorVariableName = (String) processDefinitionEntity
                            .getProperty(BpmnParse.PROPERTYNAME_INITIATOR_VARIABLE_NAME);

                    if (("${" + initiatorVariableName + "}")
                            .equals(expressionText)) {
                        DefaultFormHandler formHandler = (DefaultFormHandler) taskDefinition
                                .getTaskFormHandler();

                        if (formHandler.getFormKey() != null) {
                            String formKey = formHandler.getFormKey()
                                    .getExpressionText();
                            formDto.setCode(formKey);
                        } else {
                            logger.info("cannot formKey : {}, {}",
                                    processDefinitionId, taskDefinitionKey);
                        }

                        formDto.setActivityId(taskDefinitionKey);
                    }
                }
            }
        }

        return formDto;
    }
}
