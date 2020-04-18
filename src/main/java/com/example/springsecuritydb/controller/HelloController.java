package com.example.springsecuritydb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/admin/hello")
    public String admin(){
        return " Hello admin";
    }
    @GetMapping("/user/hello")
    public String user(){
        return " Hello admin";
    }
    @GetMapping("/dba/hello")
    public String dba(){
        return " Hello dba";
    }
    @GetMapping("/home")
    public String hello(){
        return "this is  Home";
    }

}
