package com.txy822.android_personality_based_dating_app;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {
    private String email;
    private String password;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Login login;

// crl+cmd+T to create test

    @Before
    public void setUp() throws Exception {
        email="tesfahun.y.w@gmail.com";
        password="1234567";
        mAuth= mock(FirebaseAuth.class);
//        login= new Login();
//        login= new Login(mAuth);
//         user= mock(FirebaseUser.class);
    }
    /**
     * Checks login to the data base or not
     */
    @Test
    public void test_signIn_method() {
        user= login.signIn(email,password);
        when(login.signIn(email,password)).thenReturn(user);
        assertEquals(mAuth.getCurrentUser(), user);
    }

    @Test
    public void test_sendPasswordReset_method() {
    }

    @After
    public void tearDown() throws Exception {
    }


}