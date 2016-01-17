package com.filreas.gosthlm;

import com.filreas.shared.dto.MetroDto;

import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DepartureListItemByDepartureTimeComparatorTest {

    private DepartureListItemByDepartureTimeComparator sut;
    private ArrayList<DepartureListItem> unsorted;

    @Before
    public void setup() {

        MetroDto testMetro = new MetroDto();

        unsorted = new ArrayList<>();

        testMetro.setDisplayTime("20:00");
        unsorted.add(new DepartureListItem(testMetro));

        testMetro.setDisplayTime("19:59");
        unsorted.add(new DepartureListItem(testMetro));

        testMetro.setDisplayTime("5 min");
        unsorted.add(new DepartureListItem(testMetro));

        testMetro.setDisplayTime("Nu");
        unsorted.add(new DepartureListItem(testMetro));

        testMetro.setDisplayTime("2 min");
        unsorted.add(new DepartureListItem(testMetro));

        testMetro.setDisplayTime("11:00"); // should be sorted last, since it's next day
        unsorted.add(new DepartureListItem(testMetro));

        LocalTime testTime = new LocalTime("12:00");

        sut = new DepartureListItemByDepartureTimeComparator(testTime);
    }

    @Test
    public void compare_should_sort_by_Nu_then_x_min_then_set_times() {
        Collections.sort(unsorted, sut);

        assertThat(unsorted.get(0).getDepartureTimeText(), is("Nu"));
        assertThat(unsorted.get(1).getDepartureTimeText(), is("2 min"));
        assertThat(unsorted.get(2).getDepartureTimeText(), is("5 min"));
        assertThat(unsorted.get(3).getDepartureTimeText(), is("19:59"));
        assertThat(unsorted.get(4).getDepartureTimeText(), is("20:00"));
        assertThat(unsorted.get(5).getDepartureTimeText(), is("11:00"));
    }
}
