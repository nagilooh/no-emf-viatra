package org.eclipse.viatra.query.runtime.tabular.types;

import org.eclipse.viatra.query.runtime.matchers.context.common.BaseInputKeyWrapper;

public abstract class BaseStringTypeKey<StringKey> extends BaseInputKeyWrapper<StringKey> {

    public BaseStringTypeKey(StringKey stringKey) {
        super(stringKey);
    }

    public StringKey getStringKey() {
        return getWrappedKey();
    }
    
    @Override
    public String toString() {
        return this.getPrettyPrintableName();
    }

}
