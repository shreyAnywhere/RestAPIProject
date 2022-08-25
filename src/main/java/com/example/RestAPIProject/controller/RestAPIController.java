package com.example.RestAPIProject.controller;

import com.example.RestAPIProject.Services.RestAPIInterface;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.api.client.json.Json;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.Header;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RestAPIController {

    @Autowired
    private RestAPIInterface restAPIInterface;

    @GetMapping("/getStudentDetails/{email}")
    public Map<String, String> showDetails(@PathVariable("email") String email){

        Entity entity = restAPIInterface.getShowDetails(email).next();
        Map<String, String> map = new HashMap<>();

        if(entity != null){
            map.put("email", entity.getString("email"));
            map.put("name", entity.getString("name"));

            return map;
        }
        map.put("entity", "not found");

        return map;
    }

    @PostMapping("/register/{name}/{email}/{password}")
    public Map<String, Object> register(@PathVariable("name") String name, @PathVariable("email") String email, @PathVariable("password") String password){
        boolean isRegistered = restAPIInterface.register(name, email, password);
        Map<String, Object> map = new HashMap<>();

        if(isRegistered){
            map.put("status", 200);
            Map<String, String> childMap = new HashMap<>();
            childMap.put("name", name);
            childMap.put("email", email);
            map.put("data", childMap);

            return map;
        }

        map.put("status", 400);
        map.put("data", "No data");
        //return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        return map;
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
