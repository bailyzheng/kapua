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
package org.eclipse.kapua.service.job.context;

import java.util.List;

import org.eclipse.kapua.service.job.step.definition.JobStepProperty;

public interface KapuaStepContext {

    public int getStepId();

    public void setStepId(int stepId);

    public Integer getNextStepId();

    public void setNextStepId(Integer nextStepId);

    public List<JobStepProperty> getStepProperties();

    public void setStepProperties(List<JobStepProperty> jobStepProperties);

    public <T> T getStepProperty(String stepPropertyName);

}
