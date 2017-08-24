/*******************************************************************************
 * Copyright (c) 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.service.device.management.command.job;

import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;

import org.eclipse.kapua.commons.security.KapuaSecurityUtils;
import org.eclipse.kapua.locator.KapuaLocator;
import org.eclipse.kapua.service.device.management.command.DeviceCommandFactory;
import org.eclipse.kapua.service.device.management.command.DeviceCommandInput;
import org.eclipse.kapua.service.device.management.command.DeviceCommandManagementService;
import org.eclipse.kapua.service.device.management.command.DeviceCommandOutput;
import org.eclipse.kapua.service.job.context.JobContextFactory;
import org.eclipse.kapua.service.job.context.KapuaJobContext;
import org.eclipse.kapua.service.job.context.KapuaStepContext;
import org.eclipse.kapua.service.job.operation.TargetOperation;
import org.eclipse.kapua.service.job.targets.JobTarget;
import org.eclipse.kapua.service.job.targets.JobTargetStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceCommandTargetProcessor implements TargetOperation {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceCommandTargetProcessor.class);

    private static final KapuaLocator LOCATOR = KapuaLocator.getInstance();
    private static final JobContextFactory JOB_CONTEXT_FACTORY = LOCATOR.getFactory(JobContextFactory.class);

    private static final DeviceCommandManagementService COMMAND_MANAGEMENT_SERVICE = LOCATOR.getService(DeviceCommandManagementService.class);
    private static final DeviceCommandFactory COMMAND_FACTORY = LOCATOR.getFactory(DeviceCommandFactory.class);

    @Inject
    JobContext jobContext;

    @Inject
    StepContext stepContext;

    @Override
    public Object processItem(Object item) throws Exception {
        KapuaJobContext kapuaJobContext = JOB_CONTEXT_FACTORY.newJobContext(jobContext);
        KapuaStepContext kapuaStepContext = JOB_CONTEXT_FACTORY.newStepContext(stepContext);
        LOG.info("JOB {} - Processing item...", kapuaJobContext.getJobId());

        JobTarget jobTarget = (JobTarget) item;

        DeviceCommandInput commandInput = COMMAND_FACTORY.newCommandInput();
        commandInput.setCommand("ls");        // kapuaStepContext.getStepProperty(DeviceCommandExecPropertyKeys.COMMAND_INPUT);
        commandInput.setTimeout(30000);
        Long timeout = null; // kapuaStepContext.getStepProperty(DeviceCommandExecPropertyKeys.TIMEOUT);

        try {
            DeviceCommandOutput commandOutput = KapuaSecurityUtils.doPrivileged(() -> COMMAND_MANAGEMENT_SERVICE.exec(jobTarget.getScopeId(), jobTarget.getJobTargetId(), commandInput, timeout));

            jobTarget.setStatus(JobTargetStatus.PROCESS_OK);
            // jobTarget.setOutput(...);
        } catch (Exception e) {
            jobTarget.setStatus(JobTargetStatus.PROCESS_FAILED);
            jobTarget.setException(e);
        }

        LOG.info("JOB {} - Processing item... DONE!", kapuaJobContext.getJobId());
        return jobTarget;
    }

}
