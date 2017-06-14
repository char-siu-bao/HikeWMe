package com.example.judejoseph.bootcamplocator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.judejoseph.bootcamplocator.R;

public class UserFragement extends Fragment {

    private LocationsListFragement mListFragement;
    public int zip;

    public UserFragement() {
        // Required empty public constructor
    }

    public static UserFragement newInstance() {
        UserFragement fragment = new UserFragement();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_fragement, container, false);

        // creating list of trails to display after map
        mListFragement = (LocationsListFragement)getActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.container_locations_list);

        if(mListFragement == null){
            mListFragement = LocationsListFragement.newInstance(zip);
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_locations_list, mListFragement)
                    .commit();
        }

        // grab the editText and observe the keystroke events //
        final EditText zipText = (EditText) view.findViewById(R.id.zip_text);

        zipText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER){

                    //TODO: make sure it is a valid zipcode such as number length and country id//
                    String text = zipText.getText().toString();
                    zip = Integer.parseInt(text);

                    // get rid of keyboard
                    InputMethodManager imm = (InputMethodManager)getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(zipText.getWindowToken(), 0);


                    mListFragement = LocationsListFragement.newInstance(zip);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.container_locations_list, mListFragement)
                            .commit();

                    showList();

                    return true;
                }
                return false;
            }
        });

        hideList();

        return view;
    }

    // HELPER: for list fragement //
    private void hideList(){
        getActivity().getSupportFragmentManager().beginTransaction().hide(mListFragement).commit();
    }

    private void showList(){
        getActivity().getSupportFragmentManager().beginTransaction().show(mListFragement).commit();
    }

}
