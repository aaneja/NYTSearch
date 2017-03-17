package com.codepath.aaneja.nytsearch.adapters;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.aaneja.nytsearch.R;
import com.codepath.aaneja.nytsearch.models.Doc;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aaneja on 15/03/17.
 */

public class DocAdapter extends  RecyclerView.Adapter<DocAdapter.ViewHolder> {

    private List<Doc> FetchedArticles;

    public DocAdapter(List<Doc> fetchedArticles) {
        FetchedArticles = fetchedArticles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.layout_article, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Doc doc = FetchedArticles.get(position);

        holder.snippet.setText(doc.snippet);
        //TODO: Figure out which way of getting context is better
        Picasso.with(holder.thumbnail.getContext()).load(doc.getLandingViewImageUrl()).fit().centerCrop().into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return FetchedArticles.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {

        private final Context context;
        private ImageView thumbnail;
        private TextView snippet;

        public ViewHolder(View itemView, Context context) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            snippet = (TextView) itemView.findViewById(R.id.tvSnippet);
            this.context = context;
        }

    }


}
