
package com.codepath.aaneja.nytsearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("organization")
    @Expose
    public String organization;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("firstname")
    @Expose
    public String firstname;
    @SerializedName("rank")
    @Expose
    public long rank;
    @SerializedName("lastname")
    @Expose
    public String lastname;

}
