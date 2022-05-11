package com.devitis.asympkfinalversion.data.network.socket;

import android.util.Log;


import com.devitis.asympkfinalversion.ui.main.IMainContract;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Diana on 10.05.2019.
 */

public class SocketPresenterImpl implements ISocketPresenter {

    private static final String SOCKET_URL = "";
    private static final String SOCKEt_RESPONSE_URL = "";
    private String mtduCode;
    private String message;
    private static WebSocketClient webSocketClient;
    private static ISocketPresenter presenter;

    public SocketPresenterImpl() {
    }


    /**
     * return single instance
     *
     * @return
     */
    public static ISocketPresenter getSocketPresenter() {

        if (presenter == null) {
            presenter = new SocketPresenterImpl();
        }
        return presenter;
    }

    /**
     * get mtdu
     * socket connection
     *
     * @param mtdu
     * @throws URISyntaxException
     */
    @Override
    public void connect(final String mtdu) throws URISyntaxException {

        mtduCode = String.valueOf(mtdu);

        try {
            URI uri = new URI(SOCKET_URL);
            webSocketClient = new WebSocketClient(uri) {

                /**
                 * send mtdu code and command GETDATA
                 *  @param handshakedata
                 */
                @Override
                public void onOpen(ServerHandshake handshakedata) {

                    webSocketClient.send(mtdu + " GETDATA");

                }

                /**
                 *
                 * @param message
                 */
                @Override
                public void onMessage(String message) {

                }

                /**
                 * close socket connect
                 *
                 * @param code
                 * @param reason
                 * @param remote
                 */
                @Override
                public void onClose(int code, String reason, boolean remote) {

                    Log.wtf("сокет ", "соединение закрыто" + reason);

                }

                /**
                 *
                 * @param ex
                 */
                @Override
                public void onError(Exception ex) {

                    ex.printStackTrace();

                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {

            e.printStackTrace();
            return;
        }

    }


    /**
     * socket listener
     * get massage after connect
     *
     * @throws URISyntaxException
     */
    @Override
    public void response() throws URISyntaxException {

        try {
            URI uri = new URI(SOCKEt_RESPONSE_URL);
            webSocketClient = new WebSocketClient(uri) {

                @Override
                public void onOpen(ServerHandshake handshakedata) {

                }

                /**
                 * setMessage after response
                 * @param message
                 */
                @Override
                public void onMessage(String message) {

                    setMessage(message);

                }

                /**
                 * close socket connect
                 *
                 * @param code
                 * @param reason
                 * @param remote
                 */
                @Override
                public void onClose(int code, String reason, boolean remote) {

                    Log.wtf("сокет ", "соединение закрыто" + reason);

                }

                /**
                 *
                 * @param ex
                 */
                @Override
                public void onError(Exception ex) {

                    ex.printStackTrace();

                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {

            e.printStackTrace();
            return;
        }

    }


    /**
     * close connection
     */
    @Override
    public void disconnect() {

        if (webSocketClient != null) webSocketClient.close();

    }

    /**
     * check connection
     * @return
     */
    @Override
    public boolean isConnected() {


        if (webSocketClient != null) {
            return true;
        }

        return false;

    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
