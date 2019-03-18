/*******************************************************************************
 * Copyright (c) 2010-2012, Zoltan Ujhelyi, Istvan Rath and Daniel Varro
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-v20.html.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.viatra.query.tooling.generator.model;

/**
 * Initialization support for running Xtext languages without equinox extension registry
 */
public class GeneratorModelStandaloneSetup extends GeneratorModelStandaloneSetupGenerated {

    public static void doSetup() {
        new GeneratorModelStandaloneSetup().createInjectorAndDoEMFRegistration();
    }
}
