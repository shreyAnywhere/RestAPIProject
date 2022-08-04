package com.example.RestAPIProject.controller;

import com.example.RestAPIProject.Services.RestAPIInterface;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RestAPIController {

    @Autowired
    private RestAPIInterface restAPIInterface;

    @GetMapping("/showdetails")
    public QueryResults<Entity> showDetails(){
        return restAPIInterface.getShowDetails();
    }

    @GetMapping("/register/{email}/{name}")
    public String register(@PathVariable("name") String name, @PathVariable("email") String email){

        return restAPIInterface.register(name, email);
    }

    @GetMapping("/login/{email}")
    public String login(@PathVariable("email") String email){
        return restAPIInterface.login(email);
    }

    @GetMapping("/login/{email}/delete")
    public boolean delete(@PathVariable("email") String email){
       return restAPIInterface.delete(email);
    }

    @RequestMapping(value = "/login/update", method = RequestMethod.POST)
    public String update(@RequestHeader(value = "name") String name, @RequestHeader(value = "email") String email, @RequestHeader(value = "newName") String newName, @RequestHeader(value = "newEmail") String newEmail){
        return restAPIInterface.update(name, email, newName, newEmail);
    }
}
