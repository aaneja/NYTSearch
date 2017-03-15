
package com.codepath.aaneja.nytsearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keyword {

    @SerializedName("rank")
    @Expose
    public String rank;
    @SerializedName("is_major")
    @Expose
    public String isMajor;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("value")
    @Expose
    public String value;

}
