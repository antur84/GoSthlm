package com.filreas.slwear.slapi;

import android.test.mock.MockResources;

/**
 * Created by Andreas on 9/6/2015.
 */
public class SLApiKeyResourceMock extends MockResources {

    public static String FakeKey = "123123123123123123";
    private int numberOfCallsToGetString;

    public int numberOfCallsToGetString() {
        return numberOfCallsToGetString;
    }

    @Override
    public String getString(int id) throws NotFoundException {
        numberOfCallsToGetString++;
        return FakeKey;
    }

    @Override
    public int getIdentifier(String name, String defType, String defPackage) {
        return 1;
    }
}
