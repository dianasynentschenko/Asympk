package com.devitis.asympkfinalversion.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.devitis.asympkfinalversion.R;
import com.devitis.asympkfinalversion.data.model.Skz;
import com.devitis.asympkfinalversion.data.utils.TextUtils;
import com.devitis.asympkfinalversion.ui.main.IRecyclerItemClickListener;

import java.util.List;


/**
 * Created by Diana on 06.05.2019.
 */

public class SkzAdapter extends RecyclerView.Adapter<SkzAdapter.SkzViewHolder> {

    private List<Skz> skzArrayList;
    private IRecyclerItemClickListener.IRVSkzClickListener irvSkzClickListener;
    private TextUtils textUtils;


    public SkzAdapter(List<Skz> skzArrayList, IRecyclerItemClickListener.IRVSkzClickListener irvSkzClickListener) {
        this.skzArrayList = skzArrayList;
        this.irvSkzClickListener = irvSkzClickListener;
    }


    public SkzAdapter() {
    }


    @Override
    public SkzAdapter.SkzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.skz_view_row, parent, false);

        return new SkzViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final SkzAdapter.SkzViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.txtName.setText(skzArrayList.get(position).getName());
        holder.txtSubjectCode.setText(skzArrayList.get(position).getSubjectCode());
        holder.txtObjectId.setText(skzArrayList.get(position).getObjectId());


        /**
         * overwrite txtKmToObject and txtKm to valid format
         */
        textUtils = new TextUtils();
        holder.txtKm.setText(textUtils.cutStringValue(skzArrayList.get(position).getKm()));
      //  holder.txtKmToObj.setText("( " + textUtils.cutStringValue(skzArrayList.get(position).getKmToObj()) + " ) km");


        /**
         * edit button
         * set all data and get to edit fragment
         */
        holder.skzEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                String beforeEditIdSkz = skzArrayList.get(position).getId();
                String beforeEditNameSkz = skzArrayList.get(position).getName();
                String beforeEditSubjectCodeSkz = skzArrayList.get(position).getSubjectCode();
                String beforeEditKmSkz = textUtils.cutStringValue(skzArrayList.get(position).getKm());
                String beforeEditLatitudeSkz = skzArrayList.get(position).getLatitude();
                String beforeEditLongitudeSkz = skzArrayList.get(position).getLongitude();
                String beforeEditObjectValue = skzArrayList.get(position).getObjectId();

                irvSkzClickListener.onEditButtonClick(
                        beforeEditIdSkz,
                        beforeEditNameSkz,
                        beforeEditKmSkz,
                        beforeEditLatitudeSkz,
                        beforeEditLongitudeSkz,
                        beforeEditSubjectCodeSkz,
                        beforeEditObjectValue);


            }
        });

        /**
         * opros button
         * need to socket connection
         */
        holder.skzOpros.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                /**
                 * mtduText - socket connection code
                 */
                String mtduTxt = skzArrayList.get(position).getMtdu();
                irvSkzClickListener.onOprosButtonClick(mtduTxt);


            }
        });


        /**
         * monitoring button
         * (need for api connection by id)
         */
        holder.skzMonitoring.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                /**
                 * idSkzForMonitoring - id skz for skz monitoring fragment
                 */
                String idSkzForMonitoring = skzArrayList.get(position).getId();
                irvSkzClickListener.onButtonClick(Integer.parseInt(idSkzForMonitoring));

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

                Double lat = Double.parseDouble(skzArrayList.get(position).getLatitude());
                Double lng = Double.parseDouble(skzArrayList.get(position).getLongitude());
                irvSkzClickListener.onItemClick(lat, lng);
            }
        });

    }


    @Override
    public int getItemCount() {

        return skzArrayList.size();


    }


    class SkzViewHolder extends RecyclerView.ViewHolder {


        TextView txtName, txtSubjectCode, txtObjectId, txtKm, txtKmToObj;
        Button skzOpros, skzEdit, skzMonitoring;

        SkzViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name);
            txtSubjectCode = itemView.findViewById(R.id.txt_subjectCode);
            txtObjectId = itemView.findViewById(R.id.txt_objectId);
            txtKm = itemView.findViewById(R.id.txt_km);
            skzOpros = itemView.findViewById(R.id.opros_skz_button);
            skzEdit = itemView.findViewById(R.id.edit_skz_button);
            skzMonitoring = itemView.findViewById(R.id.monitoring_skz_button);
    


        }
    }


}
