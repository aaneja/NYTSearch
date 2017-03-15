
package com.codepath.aaneja.nytsearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("hits")
    @Expose
    public long hits;
    @SerializedName("time")
    @Expose
    public long time;
    @SerializedName("offset")
    @Expose
    public long offset;

}
