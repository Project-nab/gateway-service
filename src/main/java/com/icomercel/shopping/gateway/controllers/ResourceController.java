package com.icomercel.shopping.gateway.controllers;

import com.icomercel.shopping.gateway.payload.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ResourceController {
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserResponse> getUser() {
        List<UserResponse> userResponses = new ArrayList<>();
        UserResponse userResponse = new UserResponse("baonc93@gmail.com", "0375961181");
        userResponses.add(userResponse);

        UserResponse userResponse1 = new UserResponse("test@gmail.com", "0375921103");
        userResponses.add(userResponse1);
        return userResponses;
    }
}
