package com.filreas.shared.utils;

import com.filreas.shared.dto.MetroDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DtoSerializerTest {

    private MetroDto testObject;

    @Before
    public void before() {
        testObject = new MetroDto();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void convertToBytes_should_throw_if_size_exceeds_100k() throws IOException {
        String longName = "";
        for (int i = 0; i < (10 * 1000 + 1); i++) {
            longName += "0123456789";
        }

        testObject.setStopAreaName(longName);
        DtoSerializer.convertToBytes(testObject);
    }

    @Test()
    public void convertToBytes_should_serialize_all_props_and_convertFromBytes_deserialize_all_props() throws IOException, ClassNotFoundException {
        testObject.setStopAreaName("Huvudsta");
        testObject.setDestination("Centralen");
        testObject.setDisplayTime("5 min");
        testObject.setGroupOfLine(2);
        testObject.setLineNumber("11");
        byte[] serialized = DtoSerializer.convertToBytes(testObject);
        MetroDto deserialized = (MetroDto) DtoSerializer.convertFromBytes(serialized);
        assertThat(deserialized.getStopAreaName(), is(testObject.getStopAreaName()));
        assertThat(deserialized.getDestination(), is(testObject.getDestination()));
        assertThat(deserialized.getDisplayTime(), is(testObject.getDisplayTime()));
        assertThat(deserialized.getGroupOfLine(), is(testObject.getGroupOfLine()));
        assertThat(deserialized.getLineNumber(), is(testObject.getLineNumber()));
    }
}
