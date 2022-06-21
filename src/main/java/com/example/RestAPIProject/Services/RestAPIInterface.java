package com.example.RestAPIProject.Services;

import java.util.List;

public interface RestAPIInterface {

    public List<String> getShowDetails();
    public void register(String name, String email);
    public String login(String name, String email);
    public void delete(String name, String email);
    public String update(String name, String email);
}
