package com.example.RestAPIProject.controller;

import com.example.RestAPIProject.Services.RestAPIInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestAPIController {

    @Autowired
    private RestAPIInterface restAPIInterface;

    @GetMapping("/")
    public String home(){
        return "This is the home page...";
    }

    @GetMapping("/showdetails")
    public List<String> showDetails(){
        return restAPIInterface.getShowDetails();
    }

    @GetMapping("/login/{name}/{email}")
    public String login(@PathVariable("name") String name, @PathVariable("email") String email){
        return restAPIInterface.login(name, email);
    }

    @GetMapping("/register/{name}/{email}")
    public void register(@PathVariable("name") String name, @PathVariable("email") String email){

        restAPIInterface.register(name, email);
    }
}
