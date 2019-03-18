/*******************************************************************************
 * Copyright (c) 2010-2017, Andras Szabolcs Nagy and Daniel Varro
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-v20.html.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.viatra.dse.solutionstore;

/**
 * Provides file name when a model is searialzed.
 * @author Andras Szabolcs Nagy
 *
 */
public interface ISolutionNameProvider {
    String getName();
}