package com.example.RestAPIProject.Services;

import com.google.cloud.datastore.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.cloud.datastore.StructuredQuery;

@Service
public class RestAPIService implements RestAPIInterface {
    @Override
    public List<String> getShowDetails() {

        List<String> stringList = new ArrayList<>();
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        builder.setFilter(StructuredQuery.PropertyFilter.eq("email", "mno@gmail.com"));

        Query<Entity> query = builder.build();
        QueryResults<Entity> results = datastore.run(query);

        while (results.hasNext()) {
            Entity entity = results.next();
            String name = entity.getString("name");
            String email = entity.getString("email");
            stringList.add(name + " " + email);
        }
        return stringList;
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

    @Override
    public String login(String name, String email) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("StudentDetails")
                .build();
        QueryResults<Entity> results = datastore.run(query);

        while (results.hasNext()) {
            Entity entity = results.next();
            String nameDatastore = entity.getString("name");
            String emailDatastore = entity.getString("email");
            if (Objects.equals(name, nameDatastore) && Objects.equals(email, emailDatastore))
                return ("You are logged in with name:" + name + " and email:" + email);
        }
        return ("The name and email is not registered...");
    }

    @Override
    public void delete(String name, String email) {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("StudentDetails")
                .build();
        QueryResults<Entity> results = datastore.run(query);

        while (results.hasNext()) {
            Entity entity = results.next();
            String nameDatastore = entity.getString("name");
            String emailDatastore = entity.getString("email");
            if (Objects.equals(name, nameDatastore) && Objects.equals(email, emailDatastore))
            {
                datastore.delete(entity.getKey());
                break;
            }
        }
    }

    @Override
    public String update(String name, String email, String newName, String newEmail) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind("StudentDetails")
                .build();
        QueryResults<Entity> results = datastore.run(query);

        while (results.hasNext()) {
            Entity entity = results.next();
            String nameDatastore = entity.getString("name");
            String emailDatastore = entity.getString("email");
            if (Objects.equals(name, nameDatastore) && Objects.equals(email, emailDatastore))
            {
                entity = Entity.newBuilder(entity).set("name", newName).set("email", newEmail).build();
                datastore.update(entity);
                break;
            }
        }
        return "Update method with " + newName + " and " + newEmail;
    }

}
