package com.filreas.slwear.slapi;

import android.test.mock.MockResources;

/**
 * Created by Andreas on 9/6/2015.
 */
public class SLApiKeyResourceMock extends MockResources {

    public static String FakeKey = "123123123123123123";

    @Override
    public String getString(int id) throws NotFoundException {
        return FakeKey;
    }

    @Override
    public int getIdentifier(String name, String defType, String defPackage) {
        return 1;
    }
}
