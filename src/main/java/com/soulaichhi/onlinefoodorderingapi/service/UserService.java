package com.soulaichhi.onlinefoodorderingapi.service;

import com.soulaichhi.onlinefoodorderingapi.model.User;

public interface UserService {
    public User findUserByJwtToken(String jwt)throws Exception;
    public User findUserByEmail(String email)throws Exception;
}
