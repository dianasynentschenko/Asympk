package com.devitis.asympkfinalversion.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.devitis.asympkfinalversion.R;
import com.devitis.asympkfinalversion.data.model.Kip;
import com.devitis.asympkfinalversion.data.utils.TextUtils;
import com.devitis.asympkfinalversion.ui.main.IRecyclerItemClickListener;

import java.util.List;

/**
 * Created by Diana on 06.05.2019.
 */

public class KipAdapter extends RecyclerView.Adapter<KipAdapter.KipViewHolder> {

    private List<Kip> kipArrayList;
    private IRecyclerItemClickListener.IRVKipClickListener irvKipClickListener;
    private TextUtils textUtils;


    public KipAdapter(List<Kip> kipArrayList, IRecyclerItemClickListener.IRVKipClickListener irvKipClickListener) {
        this.kipArrayList = kipArrayList;
        this.irvKipClickListener = irvKipClickListener;
    }

    @Override
    public KipAdapter.KipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.kip_view_row, parent, false);

        return new KipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KipAdapter.KipViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.txtName.setText(kipArrayList.get(position).getName());
        holder.txtSubjectCode.setText(kipArrayList.get(position).getSubjectCode());
        holder.txtObjectId.setText(kipArrayList.get(position).getObjectId());

        /**
         * overwrite txtKmToObject and txtKm to valid format
         */
        textUtils = new TextUtils();
        holder.txtKm.setText(textUtils.cutStringValue(kipArrayList.get(position).getKm()));
      //  holder.txtKmToObject.setText("( " + textUtils.cutStringValue(kipArrayList.get(position).getKmToObj()) + " ) km");


        /**
         * monitoring button
         * (need for api connection by id)
         */
        holder.kipMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * set idKipForMonitoring
                 */
                String idKipForMonitoring = kipArrayList.get(position).getId();
                irvKipClickListener.onKipMonitoringButtonClick(Integer.parseInt(idKipForMonitoring));
            }
        });

        /**
         * click to item
         * get lat and lng
         * (need to set Camera position)
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double lat = Double.parseDouble(kipArrayList.get(position).getLatitude());
                Double lng = Double.parseDouble(kipArrayList.get(position).getLongitude());
                irvKipClickListener.onItemClick(lat, lng);
            }
        });

        /**
         * edit button
         * set all data and get to edit fragment
         */
        holder.kipEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                textUtils = new TextUtils();
                String beforeEditIdKip = kipArrayList.get(position).getId();
                String beforeEditNameKip = kipArrayList.get(position).getName();
                String beforeEditSubjectCodeKip = kipArrayList.get(position).getSubjectCode();
                String beforeEditKmKip = textUtils.cutStringValue(kipArrayList.get(position).getKm());
                String beforeEditLatitudeKip = kipArrayList.get(position).getLatitude();
                String beforeEditLongitudeKip = kipArrayList.get(position).getLongitude();
                String beforeEditObjectValue = kipArrayList.get(position).getObjectId();

                irvKipClickListener.onKipEditButtonClick(
                        beforeEditIdKip,
                        beforeEditNameKip,
                        beforeEditKmKip,
                        beforeEditLatitudeKip,
                        beforeEditLongitudeKip,
                        beforeEditSubjectCodeKip,
                        beforeEditObjectValue);


            }
        });

    }

    @Override
    public int getItemCount() {
        return kipArrayList.size();
    }


    class KipViewHolder extends RecyclerView.ViewHolder {


        TextView txtName, txtSubjectCode, txtObjectId, txtKm, txtKmToObject;
        Button kipMonitoring, kipEdit;


        KipViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name);
            txtSubjectCode = itemView.findViewById(R.id.txt_subjectCode);
            txtObjectId = itemView.findViewById(R.id.txt_objectId);
            txtKm = itemView.findViewById(R.id.txt_km);
            kipMonitoring = itemView.findViewById(R.id.monitoring_kip_button);
            kipEdit = itemView.findViewById(R.id.edit_kip_button);
         


        }
    }

}
