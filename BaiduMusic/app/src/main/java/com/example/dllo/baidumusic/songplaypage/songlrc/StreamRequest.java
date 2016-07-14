package com.example.dllo.baidumusic.songplaypage.songlrc;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by dllo on 16/7/5.
 */
public class StreamRequest extends Request<InputStream> {
    private final Response.Listener<InputStream> mListener;

    public StreamRequest(int method, String url, Response.Listener<InputStream> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;

    }

    public StreamRequest(String url, Response.Listener<InputStream> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected Response<InputStream> parseNetworkResponse(NetworkResponse response) {
        InputStream is = new ByteArrayInputStream(response.data);
        return Response.success(is, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(InputStream response) {
        mListener.onResponse(response);
    }
}
