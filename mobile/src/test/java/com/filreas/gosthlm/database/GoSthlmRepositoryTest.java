package com.filreas.gosthlm.database;

import com.filreas.gosthlm.database.helpers.ICrud;
import com.filreas.gosthlm.database.model.TransportationOfChoice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GoSthlmRepositoryTest {

    @Mock
    private ICrud<TransportationOfChoice> transportationOfChoice;
    private IGoSthlmRepository sut;

    @Before
    public void setup() {
        sut = new GoSthlmRepository(transportationOfChoice);
    }

    @Test
    public void getTransportationOfChoice_should_return_from_db_when_exists(){
        TransportationOfChoice expected = new TransportationOfChoice();
        when(transportationOfChoice.read()).thenReturn(expected);
        TransportationOfChoice actual = sut.getTransportationOfChoice();

        assertThat(actual, is(expected));
    }

    @Test
    public void getTransportationOfChoice_should_return_new_object_when_not_in_db(){
        when(transportationOfChoice.read()).thenReturn(null);
        TransportationOfChoice actual = sut.getTransportationOfChoice();

        assertThat(actual, not(nullValue()));
    }

    @Test
    public void getTransportationOfChoice_should_save_new_object_when_not_in_db(){
        when(transportationOfChoice.read()).thenReturn(null);
        TransportationOfChoice actual = sut.getTransportationOfChoice();

        verify(transportationOfChoice).create(actual);
    }
}
