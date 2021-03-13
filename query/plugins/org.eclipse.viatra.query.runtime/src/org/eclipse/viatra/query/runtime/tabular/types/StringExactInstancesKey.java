package org.eclipse.viatra.query.runtime.tabular.types;

public class StringExactInstancesKey extends BaseStringTypeKey<String> {

	public StringExactInstancesKey(String stringKey) {
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
		return 1;
	}

	@Override
	public boolean isEnumerable() {
		return true;
	}

}
