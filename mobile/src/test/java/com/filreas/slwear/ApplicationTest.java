package com.filreas.slwear;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApplicationTest {

    @Test
    public void testFramework_Returns_True() {
        assertThat(true, is(true));
    }
}