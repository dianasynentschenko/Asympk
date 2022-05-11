package com.devitis.asympkfinalversion.ui.edit.kip;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.devitis.asympkfinalversion.R;
import com.devitis.asympkfinalversion.data.model.Kip;
import com.devitis.asympkfinalversion.data.model.ObjectModel;
import com.devitis.asympkfinalversion.data.model.Skz;
import com.devitis.asympkfinalversion.data.utils.GPSTracker;
import com.devitis.asympkfinalversion.ui.edit.IEditContract;
import com.devitis.asympkfinalversion.ui.main.GetObjectsInteractorImpl;
import com.devitis.asympkfinalversion.ui.main.IMainContract;
import com.devitis.asympkfinalversion.ui.map.MapKipPresenterImpl;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diana on 23.05.2019.
 */

public class EditKIPFragment extends DialogFragment implements IMainContract.IMainView,
        IEditContract.IEditView {

    private String idKipValue;
    private String nameKipValue;
    private String subjectCodeValue;
    private String objectId;
    private String objectValue;
    private String kmValue;
    private String latitudeValue;
    private String longitudeValue;

    private TextView nameTV;
    private TextView subjectCodeTV;
    private TextView kmTV;
    private TextView latitudeTV;
    private TextView longitudeTV;
    private Button locationButton;
    private Spinner spinner;

    private IMainContract.IPresenter presenter;
    private IEditContract.IEditPresenter editPresenter;
    private List<ObjectModel> objectModelList;
    final List<String> objectValueList = new ArrayList<>();


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        /**
         * init presentor
         * call object Model data in presenter.request()
         */
        presenter = new MapKipPresenterImpl(this, new GetObjectsInteractorImpl());
        presenter.request();


        /**
         * get data from adapter
         */
        idKipValue = getArguments().getString("id");
        nameKipValue = getArguments().getString("name");
        subjectCodeValue = getArguments().getString("subjectCode");
        kmValue = getArguments().getString("km");
        latitudeValue = getArguments().getString("lat");
        longitudeValue = getArguments().getString("lon");
        objectValue = getArguments().getString("objectValue");


        /**
         * init dialog view
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_kip, null);


        /**
         * init text view
         */
        nameTV = (TextView) view.findViewById(R.id.name_et);
        subjectCodeTV = (TextView) view.findViewById(R.id.quipment_number_et);
        kmTV = (TextView) view.findViewById(R.id.km_et);
        latitudeTV = (TextView) view.findViewById(R.id.latitude_et);
        longitudeTV = (TextView) view.findViewById(R.id.longitude_et);
        locationButton = (Button) view.findViewById(R.id.location_button);
        spinner = (Spinner) view.findViewById(R.id.object_value);


        /**
         * set all data in tv
         */
        nameTV.setText(nameKipValue);
        subjectCodeTV.setText(subjectCodeValue);
        kmTV.setText(kmValue);
        latitudeTV.setText(latitudeValue);
        longitudeTV.setText(longitudeValue);


        /**
         * location button
         * call GPS Tracker and get user lat, lng
         */
        locationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                GPSTracker gpsTracker = new GPSTracker(getActivity());
                if (gpsTracker.canGetLocation()) {

                    String lat = String.valueOf(gpsTracker.getLatitude());
                    String lon = String.valueOf(gpsTracker.getLongitude());
                    latitudeTV.setText(lat);
                    longitudeTV.setText(lon);

                    locationButton.setTextColor(Color.GRAY);
                    locationButton.setClickable(false);
                }

            }

        });

        return builder

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    /**
                     * after button click
                     * @param dialogInterface
                     * @param i
                     */
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        /**
                         * init kip object with edit data
                         */
                        Kip kip = new Kip(idKipValue,
                                String.valueOf(nameTV.getText()),
                                String.valueOf(subjectCodeTV.getText()),
                                getObjectId(),
                                String.valueOf(longitudeTV.getText()),
                                String.valueOf(latitudeTV.getText()),
                                String.valueOf(kmTV.getText())
                        );

                        /**
                         * init edit presenter
                         * and get kip object to server
                         */
                        editPresenter = new EditKipPresenterImpl(this, new SetKipInteractorImpl());
                        editPresenter.setKipDataToServer(kip);

                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("ОТМЕНА", null)
                .setView(view)
                .setTitle("Редактирование KIP")
                .create();
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void hideSwipe() {

    }


    @Override
    public void setKipDataToRV(List<Kip> kipList) {

    }


    @Override
    public void setSKZDataToRV(List<Skz> skzList) {

    }


    /**
     * if response failure get Toast
     *
     * @param throwable
     */
    @Override
    public void onResponseFailure(Throwable throwable) {


        Toast.makeText(getActivity(),
                "Ошибка подключения к сети",
                Toast.LENGTH_SHORT)
                .show();
        this.dismiss();


    }

    /**
     * if edit finished correctly get true
     *
     * @param successful
     */
    @Override
    public void onEditFinished(boolean successful) {

        if(successful) {
            Toast.makeText(getActivity(),
                    "редактирование прошло успешно" ,
                    Toast.LENGTH_SHORT)
                    .show();
        }

        else {

            Toast.makeText(getActivity(),
                    "повторите попытку" ,
                    Toast.LENGTH_SHORT)
                    .show();

        }

    }

    @Override
    public void setSKZLocationDataToMap(List<Skz> skzList) {

    }



    @Override
    public void setKIPLocationDataToMap(List<Kip> kipList) {

    }

    /**
     * object list
     * set list in spinner
     * @param objectList
     */
    @Override
    public void setObjectDataToList(List<ObjectModel> objectList) {

        this.objectModelList = objectList;

        objectValueList.add(objectValue);

        /**
         * add first value to list if equals user value
         */
        for (int i = 0; i < objectModelList.size(); i++) {

            if (!objectValue.equals(objectModelList.get(i).getObjectName())) {
                objectValueList.add(objectModelList.get(i).getObjectName());
            }
        }


        /**
         * init spinner adapter
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                objectValueList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getActivity(), objectModelList.get(i).getId(), Toast.LENGTH_SHORT).show();
                setObjectId(objectModelList.get(i).getId());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
