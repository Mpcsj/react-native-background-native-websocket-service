package com.estudo_cod_nativo.modules.toast_module;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.estudo_cod_nativo.service.WebsocketService;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

public class ToastModule extends ReactContextBaseJavaModule {
    public static final String TAG = ToastModule.class.getCanonicalName();
    public static final String DURATION_SHORT_KEY = "CURTINHO";
    public static final String DURATION_LONG_KEY = "GRANDE_LERAS";
    private ReactApplicationContext context;

    ToastModule(ReactApplicationContext context){
        super(context);
        this.context = context;

    }
    @NonNull
    @Override
    public String getName() {
        return "MyToastModule";
    }

    public Map<String,Object> getConstants(){
        final Map<String,Object> res = new HashMap<>();
        res.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        res.put(DURATION_LONG_KEY,Toast.LENGTH_LONG);
        return res;
    }

    @ReactMethod
    public void show(String message,int duration){
        Toast.makeText(getReactApplicationContext(),message,duration).show();
        Log.d(TAG,"show");
        Log.d(TAG,"iniciando a service");
        initBackgroundService(message);
    }
    private void initBackgroundService(String message){
        Intent i = new Intent(context, WebsocketService.class);
        i.putExtra(WebsocketService.REACT_NATIVE_MESSAGE,message);
        context.startService(i);
        System.out.println(TAG+"::service iniciada");
    }
}
