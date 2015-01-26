package com.fylm.model;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given shot
 */

@ParseClassName("ParseVideoModel")
public class ParseVideoModel extends ParseObject {

    public ParseVideoModel() {
        // A default constructor is required.
    }

    public static ParseQuery<ParseVideoModel> getQuery() {
        return ParseQuery.getQuery(ParseVideoModel.class);
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public void setTitle(String points) {
        put("title", points);
    }

    public String getTitle() {
        return this.getString("title");
    }

    //for gps
    public ParseGeoPoint getLocation() {
        return getParseGeoPoint("location");
    }

    public void setLocation(ParseGeoPoint value) {
        put("location", value);
    }

    public String getDescription() {
        return this.getString("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public void setAwsKey(String id) {
        this.put("aws", id);
    }
    public String getAwsKey() {
        return this.getString("aws");
    }
}