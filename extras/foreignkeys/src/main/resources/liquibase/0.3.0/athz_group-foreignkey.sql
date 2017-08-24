-- *******************************************************************************
-- Copyright (c) 2017 Eurotech and/or its affiliates and others
--
-- All rights reserved. This program and the accompanying materials
-- are made available under the terms of the Eclipse Public License v1.0
-- which accompanies this distribution, and is available at
-- http://www.eclipse.org/legal/epl-v10.html
--
-- Contributors:
--     Eurotech - initial API and implementation
-- *******************************************************************************

-- liquibase formatted sql

-- changeset account-foreignkeys:1

ALTER TABLE athz_group
	ADD CONSTRAINT fk_athz_group_scopeId
		FOREIGN KEY (scope_id) REFERENCES act_account(id)
			ON DELETE CASCADE;
		
