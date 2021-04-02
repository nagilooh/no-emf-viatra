package org.eclipse.viatra.query.runtime.tabular.generic.types;

import org.eclipse.viatra.query.runtime.matchers.context.common.BaseInputKeyWrapper;

public abstract class BaseGenericTypeKey<GenericKey> extends BaseInputKeyWrapper<GenericKey> {

    public BaseGenericTypeKey(GenericKey stringKey) {
        super(stringKey);
    }

    public GenericKey getGenericKey() {
        return getWrappedKey();
    }
    
    @Override
    public String toString() {
        return this.getPrettyPrintableName();
    }

}
