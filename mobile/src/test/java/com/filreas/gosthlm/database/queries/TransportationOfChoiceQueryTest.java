package com.filreas.gosthlm.database.queries;

import com.filreas.gosthlm.database.helpers.ITransportationOfChoiceDbHelper;
import com.filreas.gosthlm.database.model.TransportationOfChoice;
import com.filreas.gosthlm.database.queries.TransportationOfChoiceQuery;

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
public class TransportationOfChoiceQueryTest {

    @Mock
    private ITransportationOfChoiceDbHelper helper;

    private TransportationOfChoiceQuery sut;
    @Before
    public void setup() {
        sut = new TransportationOfChoiceQuery(helper);
    }
    @Test
    public void get_should_return_from_db_when_exists(){
        TransportationOfChoice expected = new TransportationOfChoice();
        when(helper.read()).thenReturn(expected);
        TransportationOfChoice actual = sut.get();

        assertThat(actual, is(expected));
    }

    @Test
    public void get_should_return_new_object_when_not_in_db(){
        when(helper.read()).thenReturn(null);
        TransportationOfChoice actual = sut.get();

        assertThat(actual, not(nullValue()));
    }

    @Test
    public void get_should_save_new_object_when_not_in_db(){
        when(helper.read()).thenReturn(null);
        TransportationOfChoice actual = sut.get();

        verify(helper).create(actual);
    }

}
