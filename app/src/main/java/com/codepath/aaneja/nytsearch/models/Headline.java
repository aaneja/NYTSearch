
package com.codepath.aaneja.nytsearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Headline {

    @SerializedName("main")
    @Expose
    public String main;
    @SerializedName("print_headline")
    @Expose
    public String printHeadline;

}
