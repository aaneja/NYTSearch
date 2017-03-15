
package com.codepath.aaneja.nytsearch.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doc {

    @SerializedName("web_url")
    @Expose
    public String webUrl;
    @SerializedName("snippet")
    @Expose
    public String snippet;
    @SerializedName("lead_paragraph")
    @Expose
    public String leadParagraph;
    @SerializedName("abstract")
    @Expose
    public Object _abstract;
    @SerializedName("print_page")
    @Expose
    public Object printPage;
    @SerializedName("blog")
    @Expose
    public List<Object> blog = null;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("multimedia")
    @Expose
    public List<Multimedium> multimedia = null;
    @SerializedName("headline")
    @Expose
    public Headline headline;
    @SerializedName("keywords")
    @Expose
    public List<Keyword> keywords = null;
    @SerializedName("pub_date")
    @Expose
    public String pubDate;
    @SerializedName("document_type")
    @Expose
    public String documentType;
    @SerializedName("news_desk")
    @Expose
    public Object newsDesk;
    @SerializedName("section_name")
    @Expose
    public String sectionName;
    @SerializedName("subsection_name")
    @Expose
    public Object subsectionName;
    @SerializedName("byline")
    @Expose
    public Byline byline;
    @SerializedName("type_of_material")
    @Expose
    public String typeOfMaterial;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("word_count")
    @Expose
    public String wordCount;
    @SerializedName("slideshow_credits")
    @Expose
    public Object slideshowCredits;

}
