package com.codepath.aaneja.nytsearch.models;

import java.util.Date;
import java.util.List;

/**
 * Created by aaneja on 15/03/17.
 */
public class SearchParams {
    public int Page = 0;
    public boolean AppendToResults = false;

    public String SearchTerm = "";
    public Date BeginDate;
    public String SortOrder; //oldest, newest, <not-applied>
    public List<String> NewsDeskValues;
}
