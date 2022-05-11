package com.devitis.asympkfinalversion.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.devitis.asympkfinalversion.R;
import com.devitis.asympkfinalversion.ui.map.MapsFragment;
import com.devitis.asympkfinalversion.ui.map.MapsKipFragment;


/**
 * Created by Diana on 08.05.2019.
 */

public class SettingsFragment extends DialogFragment {

    private RadioButton checkBoxKip;
    private RadioButton checkBoxSkz;



    MapsFragment mapsFragment;
    MapsKipFragment mapsKipFragment;



    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        mapsFragment = new MapsFragment();
        mapsKipFragment = new MapsKipFragment();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_settings, null);


        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupSettings);
        checkBoxKip = (RadioButton) view.findViewById(R.id.checkBoxKIP);
        checkBoxSkz = (RadioButton) view.findViewById(R.id.checkBoxSKZ);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index) {

                    case 0:

                        checkBoxKip.setChecked(true);
                        break;
                    case 1:

                        checkBoxSkz.setChecked(true);
                        break;

                }
            }
        });




        return builder

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (checkBoxKip.isChecked()) {

                            fragmentTransaction.replace(R.id.fragmentMaps, mapsKipFragment);



                        }
                        if (checkBoxSkz.isChecked()) {

                            fragmentTransaction.replace(R.id.fragmentMaps, mapsFragment);

                        }

                        fragmentTransaction.commit();
                    }
                })
                .setNegativeButton("OТМЕНА", null)
                .setView(view)
                .create();
    }
}
