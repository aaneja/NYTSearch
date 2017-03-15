
package com.codepath.aaneja.nytsearch.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("meta")
    @Expose
    public Meta meta;
    @SerializedName("docs")
    @Expose
    public List<Doc> docs = null;

}
