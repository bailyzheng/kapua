/*******************************************************************************
 * Copyright (c) 2011, 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.service.job.targets.internal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eclipse.kapua.commons.model.AbstractKapuaUpdatableEntity;
import org.eclipse.kapua.commons.model.id.KapuaEid;
import org.eclipse.kapua.model.id.KapuaId;
import org.eclipse.kapua.service.job.targets.JobTarget;
import org.eclipse.kapua.service.job.targets.JobTargetStatus;

/**
 * {@link JobTargetDefinition} entity.
 * 
 * @since 1.0
 *
 */
@Entity(name = "JobTarget")
@Table(name = "jobTarget_jobTarget")
public class JobTargetImpl extends AbstractKapuaUpdatableEntity implements JobTarget {

    private static final long serialVersionUID = -5686107367635300337L;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "eid", column = @Column(name = "job_id", nullable = false, updatable = false))
    })
    public KapuaEid jobId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "eid", column = @Column(name = "job_target_id", nullable = false, updatable = false))
    })
    public KapuaEid jobTargetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, updatable = true)
    public JobTargetStatus status;

    @Basic
    @Column(name = "step_index", nullable = false, updatable = true)
    public int stepIndex;

    @Transient
    public Exception e;

    public JobTargetImpl(KapuaId scopeId) {
        super(scopeId);
    }

    @Override
    public KapuaId getJobId() {
        return jobId;
    }

    @Override
    public void setJobId(KapuaId jobId) {
        this.jobId = KapuaEid.parseKapuaId(jobId);
    }

    @Override
    public KapuaId getJobTargetId() {
        return jobTargetId;
    }

    @Override
    public void setJobTargetId(KapuaId jobTargetId) {
        this.jobTargetId = KapuaEid.parseKapuaId(jobTargetId);
    }

    @Override
    public JobTargetStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(JobTargetStatus status) {
        this.status = status;

    }

    @Override
    public int getStepIndex() {
        return stepIndex;
    }

    @Override
    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;

    }

    @Override
    public Exception getException() {
        return e;
    }

    @Override
    public void setException(Exception e) {
        this.e = e;
    }

}
