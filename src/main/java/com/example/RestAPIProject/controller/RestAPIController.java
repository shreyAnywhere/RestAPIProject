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

    @GetMapping("/register/{name}/{email}/{password}")
    public String register(@PathVariable("name") String name, @PathVariable("email") String email, @PathVariable("password") String password){

        return restAPIInterface.register(name, email, password);
    }

    @GetMapping("/login/{email}/{password}")
    public String login(@PathVariable("email") String email, @PathVariable("password") String password){
        return restAPIInterface.login(email, password);
    }

    @GetMapping("/login/{email}/{password}/delete")
    public String delete(@PathVariable("email") String email, @PathVariable("password") String password){
       return restAPIInterface.delete(email, password);
    }

    @RequestMapping(value = "/login/{email}/{password}/updatename/{newname}", method = RequestMethod.POST)
    public String updateName(@PathVariable("email") String email, @PathVariable("password") String password, @PathVariable("newname") String newname){
        return restAPIInterface.updateName(email, password, newname);
    }

    @RequestMapping(value = "/login/{email}/{password}/updatepassword/{newpassword}", method = RequestMethod.POST)
    public String updatePassword(@PathVariable("email") String email, @PathVariable("password") String password, @PathVariable("newpassword") String newpassword){
        return restAPIInterface.updateName(email, password, newpassword);
    }
}
