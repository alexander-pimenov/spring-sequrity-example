package com.flamexander.spring.security.controllers;

import com.flamexander.spring.security.entities.User;
import com.flamexander.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage(Principal principal) {
        if (principal != null) {
            //Если пользователь залогинился, то тут получим его права.
            System.out.println(((Authentication) principal).getAuthorities());
            //[ROLE_ADMIN]
        }
        return "home";
    }

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String name = a.getName();

        User user = userService.findByUsername(principal.getName());
        return "secured part of web service: " + user.getUsername() + " " + user.getEmail();
    }

    @GetMapping("/read_profile")
    public String pageForReadProfile() {
        return "read profile page";
    }

    @GetMapping("/only_for_admins")
    public String pageOnlyForAdmins() {
        return "admins page";
    }
}
