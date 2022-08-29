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
        }else{
            map.put("status", 400);
            map.put("data", "No data");
        }

        return map;
    }

    @GetMapping("/login/{email}/{password}")
    public Map<String, Object> login(@PathVariable("email") String email, @PathVariable("password") String password){
        boolean isLoggedIn = restAPIInterface.login(email, password);
        Map<String, Object> map = new HashMap<>();

        if(isLoggedIn){
            map.put("status", 200);
            map.put("data", "successfully logged in");
        }
        else{
            map.put("status", 400);
            map.put("data", "No data");
        }

        return map;
    }

    @GetMapping("/delete/{email}")
    public Map<String, Object> delete(@PathVariable("email") String email){
        boolean isDeleted = restAPIInterface.delete(email);
        Map<String, Object> map = new HashMap<>();

        if(isDeleted){
            map.put("status", 200);
            map.put("data", "successfully deleted the entry");
        }
        else{
            map.put("status", 400);
            map.put("data", "No data");
        }

        return map;
    }

    @RequestMapping(value = "/updatename/{email}/{newname}", method = RequestMethod.PUT)
    public Map<String, Object> updateName(@PathVariable("email") String email, @PathVariable("newname") String newname){
        Entity entity = restAPIInterface.updateName(email, newname);
        Map<String, Object> map = new HashMap<>();

        if(entity != null) {
            map.put("status", 200);
            Map<String, String> childMap = new HashMap<>();
            childMap.put("name", entity.getString("name"));
            childMap.put("email", entity.getString("email"));
            map.put("data", childMap);
        }else{
            map.put("status", 400);
            map.put("data", "No data");
        }

        return map;
    }

    @RequestMapping(value = "/updatepassword/{email}/{newpassword}", method = RequestMethod.PUT)
    public Map<String, Object> updatePassword(@PathVariable("email") String email, @PathVariable("newpassword") String newpassword){
        Entity entity = restAPIInterface.updatePassword(email, newpassword);
        Map<String, Object> map = new HashMap<>();

        if(entity != null) {
            map.put("status", 200);
            Map<String, String> childMap = new HashMap<>();
            childMap.put("name", entity.getString("name"));
            childMap.put("email", entity.getString("email"));
            map.put("data", childMap);
        }else{
            map.put("status", 400);
            map.put("data", "No data");
        }

        return map;
    }
}
