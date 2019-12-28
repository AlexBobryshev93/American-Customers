package com.alex.customers.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class StateUSTest {
    private StateUS state1;
    private StateUS state2;
    private StateUS state3;

    @Before
    public void setUp() throws Exception {
        state1 = new StateUS("California");
        state2 = new StateUS("Nevada");
        state3 = new StateUS("Utah");
    }

    @After
    public void tearDown() throws Exception {
        state1 = null;
        state2 = null;
        state3 = null;
    }

    @Test
    @Ignore("addNeighborTest() was ignored because of testing in isNeighborTest()")
    public void addNeighborTest() {
        state1.addNeighbor(state2);
    }

    @Test
    public void isNeighborTest() {
        state1.addNeighbor(state2);
        state2.addNeighbor(state3);
        assertTrue(state2.isNeighbor(state1));
        assertFalse(state1.isNeighbor(state3));
    }
}