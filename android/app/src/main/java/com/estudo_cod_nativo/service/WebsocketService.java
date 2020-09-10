package com.estudo_cod_nativo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class WebsocketService extends Service {
    public static final String TAG = WebsocketService.class.getCanonicalName();
    public static final String WEBSOCKET_EVENT = "chat_message";
    public static final String REACT_NATIVE_MESSAGE = "REACT_NATIVE_MESSAGE";
    private Socket mSocket;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // comeca daqui quando eu inicio a service
        Log.d(TAG,"onStartCommand");
        String message = intent.getStringExtra(REACT_NATIVE_MESSAGE);
        startWebSocket(message);
        return super.onStartCommand(intent, flags, startId);
    }
    private void startWebSocket(@Nullable String message){
        if(mSocket==null){
            try {
                mSocket = IO.socket(General.URL_BACKEND);
                listenWebsocketEvents();
            } catch (URISyntaxException e) {
                Log.e(TAG,"Erro ao se conectar com o websocket");
                e.printStackTrace();
            }
        }else{
            emitWebsocketMessage(message);
        }
    }
    private void listenWebsocketEvents(){
        Log.d(TAG,"listenWebsocketEvents");
        if(mSocket!=null){
            mSocket.connect();
            mSocket.on(WEBSOCKET_EVENT,args -> {
               Log.d(TAG,"Evento de websocket::args:"+new Gson().toJson(args));
            });
            emitWebsocketMessage(null);
        }
    }
    private void emitWebsocketMessage(@Nullable String message){
        JSONObject object = new JSONObject();
        try {
            if(message!=null){
                object.put("ObjA",message);
            }else{
                object.put("ObjA","123");
            }
            mSocket.emit(WEBSOCKET_EVENT,object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
