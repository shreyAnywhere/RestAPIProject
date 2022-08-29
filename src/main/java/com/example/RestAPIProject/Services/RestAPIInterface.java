package com.example.RestAPIProject.Services;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Value;

import java.util.List;

public interface RestAPIInterface {

    public QueryResults<Entity> getShowDetails(String email);
    public boolean register(String name, String email, String password);
    public boolean login(String email, String password);
    public boolean delete(String email);
    public Entity updateName(String email, String newname);
    public Entity updatePassword(String email, String newpassword);
}
