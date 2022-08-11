package com.example.RestAPIProject.Services;

import com.google.cloud.datastore.*;
import org.springframework.stereotype.Service;

import java.util.*;

import com.google.cloud.datastore.StructuredQuery;

@Service
public class RestAPIService implements RestAPIInterface {

    private static QueryResults<Entity> getResults(String email){
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        builder.setFilter(StructuredQuery.PropertyFilter.eq("email", email));
        builder.setFilter(StructuredQuery.PropertyFilter.eq("isDeleted", false));

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
    public String register(String name, String email, String password) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        //QueryResults<Entity> results = getResults(email);
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("StudentDetails");

        //if(!results.hasNext()) {
            FullEntity<IncompleteKey> entity = Entity.newBuilder(keyFactory.newKey())
                    .set("name", name)
                    .set("email", email)
                    .set("password", password)
                    .set("isDeleted", false)
                    .build();
            datastore.put(entity);

            return "The name with the given email has been registered...";
        //}

        //return "This email has been already registered...";
    }

    @Override
    public String login(String email, String password) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        builder.setFilter(StructuredQuery.PropertyFilter.eq("email", email));
        builder.setFilter(StructuredQuery.PropertyFilter.eq("password", password));

        Query<Entity> query = builder.build();
        QueryResults<Entity> results = datastore.run(query);
        Entity entity = results.next();

        if(!entity.getBoolean("isDeleted")){

            return entity.getString("name") + " " + "has logged in...";
        }

        return "Entered email or password is incorrect or the entry has not registered...";
    }

    @Override
    public String delete(String email, String password) {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        builder.setFilter(StructuredQuery.PropertyFilter.eq("email", email));
        builder.setFilter(StructuredQuery.PropertyFilter.eq("password", password));

        Query<Entity> query = builder.build();
        QueryResults<Entity> results = datastore.run(query);
        Entity entity = results.next();

        if(entity != null && (!entity.getBoolean("isDeleted"))) {
            entity = Entity.newBuilder(entity).set("isDeleted", true).build();
            datastore.update(entity);

            return "Your entry is deleted...";
        }

        return "Your selected entry has already been deleted...";
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
