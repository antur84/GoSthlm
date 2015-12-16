package com.filreas.gosthlm.slapi;

import android.support.annotation.NonNull;
import android.test.mock.MockResources;

public class SLApiKeyResourceMock extends MockResources {

    public static final String FakeKey = "123123123123123123";
    private int numberOfCallsToGetString;

    public int numberOfCallsToGetString() {
        return numberOfCallsToGetString;
    }

    @NonNull
    @Override
    public String getString(int id) throws NotFoundException {
        numberOfCallsToGetString++;
        return FakeKey;
    }

    @Override
    public int getIdentifier(String name, String defType, String defPackage) {
        if (name.equals("nope")) {
            return 0;
        }

        return 1;
    }
}
