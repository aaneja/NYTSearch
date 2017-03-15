
package com.codepath.aaneja.nytsearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Multimedium {

    @SerializedName("width")
    @Expose
    public long width;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("height")
    @Expose
    public long height;
    @SerializedName("subtype")
    @Expose
    public String subtype;
    @SerializedName("legacy")
    @Expose
    public Legacy legacy;
    @SerializedName("type")
    @Expose
    public String type;

}
