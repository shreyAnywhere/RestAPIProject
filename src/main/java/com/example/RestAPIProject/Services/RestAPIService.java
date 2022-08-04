package com.example.RestAPIProject.Services;

import com.google.cloud.datastore.*;
import org.springframework.stereotype.Service;

import java.util.*;

import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.cloud.datastore.StructuredQuery;

import static com.google.cloud.datastore.StructuredQuery.CompositeFilter.and;

@Service
public class RestAPIService implements RestAPIInterface {

    private static QueryResults<Entity> getResults(String email){
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        builder.setFilter(StructuredQuery.PropertyFilter.eq("email", email));

        Query<Entity> query = builder.build();

        return datastore.run(query);
    }
    @Override
    public QueryResults<Entity> getShowDetails() {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        //builder.setFilter(StructuredQuery.PropertyFilter.eq("email", "mno@gmail.com"));
        //builder.setLimit(3);
        //builder.setOrderBy(StructuredQuery.OrderBy.desc("name"));
        //Cursor cursor = Cursor.fromUrlSafe(null);
        //builder.setStartCursor(cursor);
        Query<Entity> query = builder.build();

        return datastore.run(query);
    }

    @Override
    public String register(String name, String email) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        //QueryResults<Entity> results = getResults(email);
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("StudentDetails").setProjectId("email");

        FullEntity entity = Entity.newBuilder(keyFactory.newKey())
                .set("name", name)
                .set("email", email)
                .set("isDeleted", false)
                .build();
        datastore.put(entity);

        return "The email has been registered...";
    }

    @Override
    public String login(String email) {
        QueryResults<Entity> results = getResults(email);

        if(results.hasNext()) {
            return ("You are logged in with email:" + email);
        }
        return ("The name and email is not registered...");
    }

    @Override
    public String delete(String email) {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        QueryResults<Entity> results = getResults(email);
        Entity entity = results.next();
        //Map<String, Value<?>> map = entity.getProperties();

        entity = Entity.newBuilder(entity).set("isDeleted", true).build();
        datastore.update(entity);

        return "The entry has been deleted...";
    }

    @Override
    public String update(String name, String email, String newName, String newEmail) {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        QueryResults<Entity> results = getResults(name);

        if(Objects.equals(name, newName) && Objects.equals(email, newEmail))
            return "Your new name and new email are same as the old one...";
        if (results.hasNext()) {
            Entity entity = results.next();
            entity = Entity.newBuilder(entity).set("name", newName).set("email", newEmail).build();
            datastore.update(entity);
            return "Update method with " + newName + " and " + newEmail;
        }

        return "The entry you want to update is not registered...";
    }

}
