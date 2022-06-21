package com.example.RestAPIProject.controller;

import com.example.RestAPIProject.Services.RestAPIInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/login/{name}/{email}/delete")
    public void delete(@PathVariable("name") String name, @PathVariable("email") String email){
        restAPIInterface.delete(name, email);
    }
    @RequestMapping(value = "/login/update", method = RequestMethod.POST)
    public String update(@RequestHeader(value = "name") String name, @RequestHeader(value = "email") String email){
        return restAPIInterface.update(name, email);
    }
    @GetMapping("/login")
    public String login(){
        return "hello from login...";
    }

    @GetMapping("/register/{name}/{email}")
    public void register(@PathVariable("name") String name, @PathVariable("email") String email){

        restAPIInterface.register(name, email);
    }
}
