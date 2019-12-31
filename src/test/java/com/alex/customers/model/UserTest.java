package com.alex.customers.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private UserCAN userCAN;
    private UserUS userUS;

    @Before
    public void setUp() throws Exception {
        userCAN = new UserCAN();
        userUS = new UserUS();
    }

    @After
    public void tearDown() throws Exception {
        userCAN = null;
        userUS = null;
    }

    @Test
    public void constructorsTest() {
        userCAN.setUsername("test");
        userCAN.setPassword("test");
        userCAN.setId(-1L);

        userUS = new UserUS(userCAN);
        assertEquals(User.Country.USA, userUS.getCountry());
        assertEquals(userCAN.getId(), userUS.getId());
    }
}