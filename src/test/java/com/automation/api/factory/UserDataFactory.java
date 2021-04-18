package com.automation.api.factory;

import com.automation.api.pojo.UserAuth;

public class UserDataFactory {

    public static UserAuth createUserAdmin(){
        UserAuth useradmin = new UserAuth();
        useradmin.setUsername("admin");
        useradmin.setPassword("password123");
        return useradmin;


    }
}
