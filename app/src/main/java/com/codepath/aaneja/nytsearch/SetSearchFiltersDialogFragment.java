package com.codepath.aaneja.nytsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.aaneja.nytsearch.models.SearchParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by aaneja on 18/03/17.
 */

public class SetSearchFiltersDialogFragment extends DialogFragment  {

    public static final SimpleDateFormat displayDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    //The instance we'll use while the Fragment is active. When the fragment is 'done', this is what we'll parcel back to the calling activity
    private SearchParams searchParams;
    private EditText etBeginDate;
    private Spinner spinnerSortOrder;
    private CheckBox checkBoxArts;
    private CheckBox checkBoxFashion;
    private CheckBox checkBoxSports;

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

        //Since the button is on a fragment, we need to bind the listner using code
        final Button btnDone = (Button) view.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoneButtonClick(v);
            }
        });

        //Set the passed in searchParams to our class instance
        this.searchParams = getArguments().getParcelable("searchParams");
        SetViewUsingSearchParams(view);
    }

    private void SetViewUsingSearchParams(View view) {
        etBeginDate = (EditText) view.findViewById(R.id.etBeginDate);
        if(searchParams.BeginDate != null) {
            etBeginDate.setText(displayDateFormat.format(searchParams.BeginDate));
        }

        spinnerSortOrder = (Spinner) view.findViewById(R.id.spinnerSortOrder);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.sortOrder_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerSortOrder.setAdapter(adapter);
        //Set the passed in searchParams position
        for (int position = 0; position < adapter.getCount(); position++) {
            if(searchParams.SortOrder.compareToIgnoreCase(adapter.getItem(position).toString()) == 0 ) {
                spinnerSortOrder.setSelection(position);
            }
        }

        checkBoxArts = (CheckBox) view.findViewById(R.id.checkBoxArts);
        checkBoxFashion = (CheckBox) view.findViewById(R.id.checkBoxFashion);
        checkBoxSports = (CheckBox) view.findViewById(R.id.checkBoxSports);
        if(searchParams.NewsDeskValues!= null && searchParams.NewsDeskValues.size() > 0) {
            for (String newsDeskItem :
                    searchParams.NewsDeskValues) {
                switch (newsDeskItem) {
                    case "Arts" :
                        checkBoxArts.setChecked(true);
                        break;
                    case "Fashion" :
                        checkBoxFashion.setChecked(true);
                        break;
                    case "Sports" :
                        checkBoxSports.setChecked(true);
                        break;
                }
            }
        }


    }

    public SetSearchFiltersDialogFragmentListener Listener = null;

    public interface SetSearchFiltersDialogFragmentListener {
        void onFinishSettingParamsDialog(SearchParams searchParams);
    }

    public void onDoneButtonClick(View view) {

        if(Listener == null) {
            //No listener for the new searchParams, so dismiss the dialog
            dismiss();
            return;
        }

        //Parse and set BeginDate if valid
        //TODO: Replace whole flow with a DateDialogFrament and a TextView instead of an EditView
        try {
            searchParams.BeginDate = displayDateFormat.parse(etBeginDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Set the sortOrder if it's not 'None'
        String sortOrder = spinnerSortOrder.getSelectedItem().toString();
        switch (sortOrder) {
            case "None" :
                searchParams.SortOrder = "";
                break;
            case "Oldest" :
                searchParams.SortOrder = "oldest";
                break;
            case "Newest" :
                searchParams.SortOrder = "newest";
        }

        //Set the NewsDesk values
        searchParams.NewsDeskValues = new ArrayList<>(3); //We always clear the existing items to be safe
        if(checkBoxArts.isChecked()) {
            searchParams.NewsDeskValues.add("Arts");
        }
        if(checkBoxFashion.isChecked()) {
            searchParams.NewsDeskValues.add("Fashion");
        }
        if(checkBoxSports.isChecked()) {
            searchParams.NewsDeskValues.add("Sports");
        }


        Listener.onFinishSettingParamsDialog(searchParams);
        dismiss();

    }
}
