package com.alex.customers.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private User user;
    private UserCAN userCAN;
    private UserUS userUS;

    @Before
    public void setUp() throws Exception {
        user = new User();
        userCAN = new UserCAN();
        userUS = new UserUS();
    }

    @After
    public void tearDown() throws Exception {
        user = null;
        userCAN = null;
        userUS = null;
    }

    @Test
    public void constructorsTest() {
        user.setCountry(User.Country.CANADA);
        user.setUsername("test");
        user.setPassword("test");

        userCAN = new UserCAN(user);
        userCAN.setId(-1L);
        assertEquals(userCAN.getUsername(), user.getUsername());

        userUS = new UserUS(userCAN);
        assertEquals(User.Country.USA, userUS.getCountry());
        assertEquals(userCAN.getId(), userUS.getId());
    }
}