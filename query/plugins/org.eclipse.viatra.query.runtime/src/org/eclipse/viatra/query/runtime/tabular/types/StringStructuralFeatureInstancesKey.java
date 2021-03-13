package org.eclipse.viatra.query.runtime.tabular.types;

public class StringStructuralFeatureInstancesKey extends BaseStringTypeKey<String> {
    
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
