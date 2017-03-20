package com.codepath.aaneja.nytsearch;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.aaneja.nytsearch.helpers.DatePickerFragment;
import com.codepath.aaneja.nytsearch.models.SearchParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by aaneja on 18/03/17.
 */

public class SetSearchFiltersDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener  {

    public static final SimpleDateFormat displayDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    //The instance we'll use while the Fragment is active. When the fragment is 'done', this is what we'll parcel back to the calling activity
    private SearchParams searchParams;
    private TextView tvBeginDate;
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
        tvBeginDate = (TextView) view.findViewById(R.id.tvBeginDate);
        if(searchParams.BeginDate != null) {
            tvBeginDate.setText(displayDateFormat.format(searchParams.BeginDate));
        }
        tvBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        tvBeginDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvBeginDate.setText("");
                return true;
            }
        });

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
        try {
            searchParams.BeginDate = displayDateFormat.parse(tvBeginDate.getText().toString());
        } catch (ParseException e) {
            Log.w("SEARCHPARAMS/Date",e.getMessage());
            searchParams.BeginDate = null;
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

    public void showDatePickerDialog(View v) {
        DatePickerFragment dpFrag = new DatePickerFragment();
        dpFrag.setOnDateSetListener(this);
        dpFrag.show(getFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//        TextView tvEditDate = (TextView) findViewById(R.id.tvEditDate);
        tvBeginDate.setText(displayDateFormat.format(c.getTime()));
    }


}
