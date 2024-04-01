package com.foodOrder.Service;

import com.foodOrder.Config.JWTProvider;
import com.foodOrder.Model.User;
import com.foodOrder.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJWTToken(jwt);
        User user=findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user=userRepo.findByEmail(email);
        if(user==null)
        {
            throw new Exception("User not found");
        }
        return user;
    }
}
