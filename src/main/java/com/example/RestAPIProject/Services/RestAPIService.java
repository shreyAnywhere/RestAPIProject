package com.example.RestAPIProject.Services;

import com.google.cloud.datastore.*;
import org.springframework.stereotype.Service;

import com.google.cloud.datastore.StructuredQuery;

@Service
public class RestAPIService implements RestAPIInterface {

    @Override
    public QueryResults<Entity> getShowDetails(String email) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        builder.setFilter(StructuredQuery.PropertyFilter.eq("email", email));
//        builder.setLimit(3);
//        builder.setOrderBy(StructuredQuery.OrderBy.desc("name"));
//        Cursor cursor = Cursor.fromUrlSafe(null);
//        builder.setStartCursor(cursor);
        Query<Entity> query = builder.build();
        return datastore.run(query);
    }

    @Override
    public String register(String name, String email, String password) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        builder.setFilter(StructuredQuery.CompositeFilter.and(StructuredQuery.PropertyFilter.eq("email", email), StructuredQuery.PropertyFilter.eq("isDeleted", false)));

        KeyFactory keyFactory = datastore.newKeyFactory().setKind("StudentDetails");
        Query<Entity> query = builder.build();
        QueryResults<Entity> results = datastore.run(query);

        if(!results.hasNext()) {
            FullEntity<IncompleteKey> entity = Entity.newBuilder(keyFactory.newKey())
                    .set("name", name)
                    .set("email", email)
                    .set("password", password)
                    .set("isDeleted", false)
                    .build();
            datastore.put(entity);

            return "Your email is registered...";
        }

        return "This email has been already registered...";
    }

    @Override
    public String login(String email, String password) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        builder.setFilter(StructuredQuery.CompositeFilter.and(StructuredQuery.PropertyFilter.eq("email", email), StructuredQuery.PropertyFilter.eq("password", password)));

        Query<Entity> query = builder.build();
        QueryResults<Entity> results = datastore.run(query);
        Entity entity = results.next();

        if(entity != null && !entity.getBoolean("isDeleted")){

            return entity.getString("name") + " " + "has logged in...";
        }

        return "Entered email or password is incorrect or the entry has not registered...";
    }

    @Override
    public String delete(String email, String password) {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        builder.setFilter(StructuredQuery.CompositeFilter.and(StructuredQuery.PropertyFilter.eq("email", email), StructuredQuery.PropertyFilter.eq("password", password)));

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
    public String updateName(String email, String password, String newname) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        builder.setFilter(StructuredQuery.CompositeFilter.and(StructuredQuery.PropertyFilter.eq("email", email), StructuredQuery.PropertyFilter.eq("password", password)));

        Query<Entity> query = builder.build();
        QueryResults<Entity> results = datastore.run(query);

        if(results.hasNext()){
            Entity entity = results.next();
            entity = Entity.newBuilder(entity).set("name", newname).build();
            datastore.update(entity);

            return "Name is updated...";
        }

        return "The entry you want to update is not registered...";
    }

    @Override
    public String updatePassword(String email, String password, String newpassword) {

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        EntityQuery.Builder builder = Query.newEntityQueryBuilder();
        builder.setKind("StudentDetails");
        builder.setFilter(StructuredQuery.CompositeFilter.and(StructuredQuery.PropertyFilter.eq("email", email), StructuredQuery.PropertyFilter.eq("password", password)));

        Query<Entity> query = builder.build();
        QueryResults<Entity> results = datastore.run(query);

        if(results.hasNext()){
            Entity entity = results.next();
            entity = Entity.newBuilder(entity).set("password", newpassword).build();
            datastore.update(entity);

            return "Password is updated...";
        }

        return "The entry you want to update is not registered...";
    }

}
