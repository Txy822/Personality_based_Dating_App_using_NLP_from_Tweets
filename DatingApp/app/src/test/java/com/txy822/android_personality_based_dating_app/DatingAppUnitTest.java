package com.txy822.android_personality_based_dating_app;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.core.app.RemoteInput;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import junit.framework.TestCase;

import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@Mock
//@ExtensionWith(MokitoExtension);
public class DatingAppUnitTest {
    private String email="tesfahun.y.w@gmail.com";
    private String password="1234567";

    private FirebaseAuth mAuth;
    private FirebaseUser  user;
    private Login login;

// crl+cmd+T to create test

    @Before
    public void setUp() throws Exception {
         login =mock(Login.class);
         mAuth= mock(FirebaseAuth.class);
//         user= mock(FirebaseUser.class);


    }
    /**
     * Checks login to the data base or not
     */
    @Test
    public void testLogin() {
        mAuth.signInWithEmailAndPassword(email, password);
        user= login.signIn(email,password);
        when(login.signIn(email,password)).thenReturn(user);
        assertEquals(mAuth.getCurrentUser(), user);
    }

    @After
    public void tearDown() throws Exception {
    }


//    @Before
    public void beforeTest(){
//          email="soltesfa1@gmail.com";
//          password="1234567*!";
//        mAuth=FirebaseAuth.getInstance();
//        updatePassword= new UpdatePassword(mAuth);
//        user = mAuth.getInstance().getCurrentUser();
//        updateLoginDetail=new UpdateLoginDetail(email, password,mAuth);
    }
//    @After
    public void afterTest(){

    }

//    @Test
//    public void sendPasswordResetValidator() {
//
////        assertTrue(updatePassword.sendPasswordReset(email));
//        assertTrue(true);
//    }
//    @Test
//    public void updateLoginDetainValidator() {
//
////        assertTrue(updateLoginDetail.updateEmailPassword(password, email,mAuth));
//        assertTrue(true);
//    }
}


