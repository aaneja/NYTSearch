
package com.codepath.aaneja.nytsearch.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Byline {

    @SerializedName("person")
    @Expose
    public List<Person> person = null;
    @SerializedName("original")
    @Expose
    public String original;
    @SerializedName("organization")
    @Expose
    public String organization;
    @SerializedName("contributor")
    @Expose
    public String contributor;

}
