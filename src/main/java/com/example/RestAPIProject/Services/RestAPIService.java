package com.example.RestAPIProject.Services;

import com.google.cloud.datastore.*;
import org.springframework.stereotype.Service;

@Service
public class RestAPIService implements RestAPIInterface {
    @Override
    public String getShowDetails() {
        return "Hello...";
    }

    @Override
    public void register(String name, String email) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("StudentDetails");

        FullEntity entity = Entity.newBuilder(keyFactory.newKey())
                .set("name", name)
                .set("email", email)
                .build();
        datastore.put(entity);
    }
}
