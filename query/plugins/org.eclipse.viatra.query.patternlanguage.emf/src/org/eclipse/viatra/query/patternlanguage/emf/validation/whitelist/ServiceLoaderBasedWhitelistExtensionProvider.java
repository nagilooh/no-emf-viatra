/*******************************************************************************
 * Copyright (c) 2010-2018, Zoltan Ujhelyi, IncQuery Labs Ltd.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-v20.html.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.viatra.query.patternlanguage.emf.validation.whitelist;

import java.util.ServiceLoader;

/**
 * @author Zoltan Ujhelyi
 * @since 2.0
 */
public class ServiceLoaderBasedWhitelistExtensionProvider implements IPureWhitelistExtensionProvider {

    @Override
    public Iterable<IPureElementProvider> getPureElementExtensions() {
        return ServiceLoader.load(IPureElementProvider.class);
    }

}
