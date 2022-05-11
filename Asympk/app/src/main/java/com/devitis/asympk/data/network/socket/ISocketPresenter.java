package com.devitis.asympkfinalversion.data.network.socket;


import com.devitis.asympkfinalversion.ui.main.IMainContract;

import java.net.URISyntaxException;

/**
 * Created by Diana on 10.05.2019.
 * <p>
 * <p>
 * interface for connect/disconnect web socket
 */


public interface ISocketPresenter {

    /**
     * connect by mtdu code
     *
     * @param mtdu
     * @throws URISyntaxException
     */
    void connect(String mtdu) throws URISyntaxException;

    /**
     * socket listener
     * use after socket connect and get massage
     * ( DATA_END - key word )
     *
     * @throws URISyntaxException
     */
    void response() throws URISyntaxException;

    /**
     * close socket connection
     */
    void disconnect();

    /**
     * check connection
     * @return
     */
    boolean isConnected();


}