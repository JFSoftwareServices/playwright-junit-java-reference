package com.jfsoftwareservices.tests;

public class AuthenticatedBaseTest extends BaseTest{
    @Override
    protected boolean authenticated() {
        return true;
    }
}
