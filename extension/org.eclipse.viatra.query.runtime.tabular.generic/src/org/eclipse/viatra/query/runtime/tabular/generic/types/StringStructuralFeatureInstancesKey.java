package org.eclipse.viatra.query.runtime.tabular.generic.types;

public class StringStructuralFeatureInstancesKey extends BaseGenericTypeKey<String> {
    
    public StringStructuralFeatureInstancesKey(String stringKey) {
        super(stringKey);
    }

    @Override
    public String getPrettyPrintableName() {
        return wrappedKey;
    }

    @Override
    public String getStringID() {
        return wrappedKey;
    }

    @Override
    public int getArity() {
        return 2;
    }
    
    @Override
    public boolean isEnumerable() {
        return true;
    }

}
