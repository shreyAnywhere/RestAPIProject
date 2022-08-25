package com.example.RestAPIProject.Services;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Value;

import java.util.List;

public interface RestAPIInterface {

    public QueryResults<Entity> getShowDetails(String email);
    public String register(String name, String email, String password);
    public String login(String email, String password);
    public String delete(String email);
    public String updateName(String email, String newname);
    public String updatePassword(String email, String newpassword);
}
