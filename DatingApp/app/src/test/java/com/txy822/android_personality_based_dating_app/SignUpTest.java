package com.txy822.android_personality_based_dating_app;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SignUpTest {
    private String fullName="testFullName";
    private String email="testemail@gmail.com";
    private String password="1234567";

    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private FirebaseUser user;
    private SignUp signUp;

    @Before
    public void setUp() throws Exception {
//        signUp= new SignUp()
        mAuth= mock(FirebaseAuth.class);
        mStore =mock(FirebaseFirestore.class);
//         user= mock(FirebaseUser.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void signup() {

        user= signUp.createAccount(mAuth,mStore,fullName,email,password);
        when(signUp.createAccount(mAuth,mStore,fullName,email,password)).thenReturn(user);
        assertEquals(mAuth.getCurrentUser(), user);
    }

    @Test
    public void cancel() {
    }

    @Test
    public void login() {
    }
}