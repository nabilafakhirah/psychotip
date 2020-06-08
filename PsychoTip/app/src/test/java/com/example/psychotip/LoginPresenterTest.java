package com.example.psychotip;

import com.example.psychotip.model.LoginPage;
import com.example.psychotip.presenter.LoginPresenter;
import com.example.psychotip.view.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock LoginPage loginPage;
    @Mock LoginView view;

    private LoginPresenter loginUnderTest;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        loginUnderTest = new LoginPresenter(view, loginPage);
    }

    @Test
    public void checkUsernamePasswordIsNotEmpty(){
        boolean result = loginUnderTest.requestLogin("testUser", "passwordTest");
        assertEquals(true, result);
    }

    @Test
    public void checkUsernamePasswordIsEmpty(){
        boolean result = loginUnderTest.requestLogin("", "");
        assertEquals(false, result);
    }

    @Test
    public void checkUsernameIsEmpty(){
        boolean result = loginUnderTest.requestLogin("", "passwordTest");
        assertEquals(false, result);
    }

    @Test
    public  void checkPasswordIsEmpty(){
        boolean result = loginUnderTest.requestLogin("testUser", "");
        assertEquals(false, result);
    }

}
