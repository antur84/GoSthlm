package com.filreas.gosthlm.database.commands;

import com.filreas.gosthlm.database.helpers.IFavouriteSiteDbHelper;
import com.filreas.gosthlm.database.model.FavouriteSite;
import com.filreas.gosthlm.database.queries.IDataSourceChanged;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddOrUpdateFavouriteStationCommandTest {

    private AddOrUpdateFavouriteStationCommand sut;

    @Mock
    private IDataSourceChanged dataSourceChanged;

    @Mock
    private IFavouriteSiteDbHelper helper;

    private FavouriteSite item;
    private FavouriteSite item2;

    @Before
    public void setup() {
        item = new FavouriteSite(1, "test", 2, "walla", "111", "222");
        item2 = new FavouriteSite(1, "test2", 2, "walla", "111", "222");
        sut = new AddOrUpdateFavouriteStationCommand(helper, dataSourceChanged, item);
    }

    @Test
    public void execute_should_add_if_does_not_exist() {
        when(helper.getBySiteId(item.getSiteId())).thenReturn(null);

        sut.execute();

        verify(helper).create(item);
    }

    @Test
    public void execute_should_update_listener_if_does_not_exist() {
        when(helper.getBySiteId(item.getSiteId())).thenReturn(null);

        sut.execute();

        verify(dataSourceChanged).dataSourceChanged();
    }

    @Test
    public void execute_should_update_db_with_new_item_if_exists_in_db_and_differs() {
        when(helper.getBySiteId(item.getSiteId())).thenReturn(item2);

        sut.execute();

        verify(helper, never()).create(item2);
        verify(helper).update(item);
    }

    @Test
    public void execute_should_not_update_db_if_exists_in_db_and_is_same() {
        when(helper.getBySiteId(item.getSiteId())).thenReturn(item);

        sut.execute();

        verify(helper, never()).create(item);
        verify(helper, never()).update(item);
    }
}
