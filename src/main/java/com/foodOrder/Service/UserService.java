package com.foodOrder.Service;

import com.foodOrder.Model.User;
import org.springframework.http.HttpHeaders;

public interface UserService {

    public User findUserByJwtToken(String jwt)throws Exception;

    public User findUserByEmail(String email)throws Exception;
}
