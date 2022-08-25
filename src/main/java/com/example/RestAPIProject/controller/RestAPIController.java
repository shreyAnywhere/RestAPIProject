package com.example.RestAPIProject.controller;

import com.example.RestAPIProject.Services.RestAPIInterface;
import com.google.api.client.json.Json;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.Header;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RestAPIController {

    @Autowired
    private RestAPIInterface restAPIInterface;

    @GetMapping("/getStudentDetails/{email}")
    public QueryResults<Entity> showDetails(@PathVariable("email") String email){
        return restAPIInterface.getShowDetails(email);
    }

    @GetMapping("/register/{name}/{email}/{password}")
    public String register(@PathVariable("name") String name, @PathVariable("email") String email, @PathVariable("password") String password){
        return restAPIInterface.register(name, email, password);
    }

    @GetMapping("/login/{email}/{password}")
    public String login(@PathVariable("email") String email, @PathVariable("password") String password){
        return restAPIInterface.login(email, password);
    }

    @GetMapping("/delete/{email}")
    public String delete(@PathVariable("email") String email){
       return restAPIInterface.delete(email);
    }

    @RequestMapping(value = "/updatename/{email}/{newname}", method = RequestMethod.PUT)
    public String updateName(@PathVariable("email") String email, @PathVariable("newname") String newname){
        return restAPIInterface.updateName(email, newname);
    }

    @RequestMapping(value = "/updatepassword/{email}/{newpassword}", method = RequestMethod.PUT)
    public String updatePassword(@PathVariable("email") String email, @PathVariable("newpassword") String newpassword){
        return restAPIInterface.updatePassword(email, newpassword);
    }
}
