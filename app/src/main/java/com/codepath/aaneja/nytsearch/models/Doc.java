
package com.codepath.aaneja.nytsearch.models;

import android.util.Log;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doc {

    public static final String DefaultLandingImageUrl = "http://noobsandnerds.com/addons/cache/Addons/plugin.video.nytimes/icon.png";
    public static final String NYTimesBaseUrl = "http://www.nytimes.com/";

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
    /*@SerializedName("byline")
    @Expose
    public Byline byline;*/
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

    //Gets a 'thumbnail' view image for this Doc. If not found, returns a 'wide' one
    //If none of these exist, returns a default NYTImage
    public String getLandingViewImageUrl(){
        String returnUrl = DefaultLandingImageUrl;
        for (Multimedium item: multimedia) {
            switch (item.subtype) {
                case "thumbnail" :
                    return NYTimesBaseUrl+item.url;
                case "wide" :
                    returnUrl = NYTimesBaseUrl+item.url;
                    break;
            }
        }
        return returnUrl;
    }


}
