package com.devitis.asympkfinalversion.ui.main;

/**
 * Created by Diana on 08.05.2019.
 */

public interface IRecyclerItemClickListener {

    interface IRVKipClickListener {

        void onItemClick(Double lat, Double lng);

        void onKipMonitoringButtonClick(int kipId);

        void onKipEditButtonClick(
                String beforeEditIdKip,
                String beforeEditNameKip,
                String beforeEditKmKip,
                String beforeEditLatitudeKip,
                String beforeEditLongitudeKip,
                String beforeEditSubjectCodeKip,
                String beforeEditObjectValue);


    }

    interface IRVSkzClickListener {

        /**
         * Skz item click
         * set location data
         *
         * @param lat
         * @param lng
         */
        void onItemClick(Double lat, Double lng);


        /**
         * for monitoring
         *
         * @param skzId
         */
        void onButtonClick(int skzId);

        /**
         * for socket connect
         * opros po mtdu codu
         *
         * @param mtduTxt
         */
        void onOprosButtonClick(String mtduTxt);


        /**
         * set all about edit
         *
         * @param beforeEditIdSkz
         * @param beforeEditNameSkz
         * @param beforeEditKmSkz
         * @param beforeEditLatitudeSkz
         * @param beforeEditLongitudeSkz
         * @param beforeEditSubjectCodeSkz
         * @param beforeEditObjectValue
         */
        void onEditButtonClick(
                String beforeEditIdSkz,
                String beforeEditNameSkz,
                String beforeEditKmSkz,
                String beforeEditLatitudeSkz,
                String beforeEditLongitudeSkz,
                String beforeEditSubjectCodeSkz,
                String beforeEditObjectValue);
    }


}
