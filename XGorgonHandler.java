package com.h1code2.tiktok.api.params.callback;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.h1code2.tiktok.api.params.utils.Store;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.XposedHelpers;


// tiktok 10.1.7

public class XGorgonParamCallback implements HttpServerRequestCallback {
    private Gson gson = new Gson();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequest(AsyncHttpServerRequest request, final AsyncHttpServerResponse response) {
        String url = request.getQuery().getString("url");
        if (url == null || url.equals("")) {
            response.send("{\"msg\":\"url参数没有传\"}");
        }
        String mapJson = "{\"accept-encoding\":[\"gzip, deflate, br\"],\"sdk-version\":[\"1\"],\"user-agent\":[\"com.ss.android.ugc.trill/100107 (Linux; U; Android 7.1.2; zh_CN_#Hans; Pixel; Build/NJH47F; Cronet/TTNetVersion:79d23018 2020-02-03 QuicVersion:ac58aac6 2020-01-20)\"],\"x-ss-req-ticket\":[\"" + new Date().getTime() + "\"],\"x-ss-dp\":[\"1180\"]}\n";
        Type type = new TypeToken<Map<String, List<String>>>() {
        }.getType();
        Map<String, List<String>> map = gson.fromJson(mapJson, type);
        Class<?> tt$1 = XposedHelpers.findClass("com.ss.sys.ces.gg.tt$1", Store.appClassLoader.get("tk"));
        Object tt1 = XposedHelpers.newInstance(tt$1);
        Object mapResult = XposedHelpers.callMethod(tt1, "a", url, map);
        response.send(gson.toJson(mapResult));
    }
}
