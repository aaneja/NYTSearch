package com.codepath.aaneja.nytsearch;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.aaneja.nytsearch.models.SearchParams;

import java.text.SimpleDateFormat;

/**
 * Created by aaneja on 18/03/17.
 */

public class SetSearchFiltersDialogFragment extends DialogFragment  {

    //The instance we'll use while the Fragment is active. When the fragment is 'done', this is what we'll parcel back to the calling activity
    private SearchParams searchParams;

    public SetSearchFiltersDialogFragment() {
    }

    public static SetSearchFiltersDialogFragment newInstance(SearchParams searchParams) {
        SetSearchFiltersDialogFragment frag = new SetSearchFiltersDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("searchParams",searchParams);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_searchoptions, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set the passed in searchParams to our class instance
        this.searchParams = getArguments().getParcelable("searchParams");

        SetViewUsingSearchParams(view);
    }

    private void SetViewUsingSearchParams(View view) {
        final EditText etBeginDate = (EditText) view.findViewById(R.id.etBeginDate);

        if(searchParams.BeginDate != null) {
            etBeginDate.setText(new SimpleDateFormat("MM-dd-yyyy").format(searchParams.BeginDate));
        }

        final Spinner spinnerSortOrder = (Spinner) view.findViewById(R.id.spinnerSortOrder);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.sortOrder_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerSortOrder.setAdapter(adapter);

    }
}
