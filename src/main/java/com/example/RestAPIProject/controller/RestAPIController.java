package com.example.RestAPIProject.controller;

import com.example.RestAPIProject.Services.RestAPIInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RestAPIController {

    @Autowired
    private RestAPIInterface restAPIInterface;

    @GetMapping("/restapi")
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
    public String delete(@PathVariable("name") String name, @PathVariable("email") String email){
       return restAPIInterface.delete(name, email);
    }
    @RequestMapping(value = "/login/update", method = RequestMethod.POST)
    public String update(@RequestHeader(value = "name") String name, @RequestHeader(value = "email") String email, @RequestHeader(value = "newName") String newName, @RequestHeader(value = "newEmail") String newEmail){
        return restAPIInterface.update(name, email, newName, newEmail);
    }
    @GetMapping("/login")
    public String login(){
        return "hello from login...";
    }

    @GetMapping("/register/{name}/{email}")
    public String register(@PathVariable("name") String name, @PathVariable("email") String email){

        return restAPIInterface.register(name, email);
    }
}
