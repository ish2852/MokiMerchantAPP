package com.example.orderspot_merchant.Util;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.widget.ResourceCursorAdapter;

import com.example.orderspot_merchant.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest extends AsyncTask<String, String, String> {
    HttpURLConnection con = null;

    @Override
    protected String doInBackground(String... values) {
        String result = null;
        try {
            JSONObject postValue = jsonObjectMakeByValues(values);
            con = connectingHttpURLConnection();
            result = httpRequestByJsonObject(postValue).toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return result;
    }

    private JSONObject jsonObjectMakeByValues(String... values) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", values[0]);

        switch (values[0]){
            case "merchant_login":
                jsonObject.put("id", values[1]);
                jsonObject.put("pw", values[2]);
                break;
            case "merchant_sales_search":
                jsonObject.put("muser_ID", values[1]);
                jsonObject.put("start_day", values[2]);
                jsonObject.put("term", values[3]);
                break;
            case"merchant_order_list":
                jsonObject.put("muser_ID", values[1]);
                jsonObject.put("day", values[2]);
                break;
            case "merchant_completed":
            case "merchant_order_cancel":
                jsonObject.put("muser_ID", values[1]);
                jsonObject.put("order_ID", values[2]);
                break;
            case "muser_token_update":
                jsonObject.put("muser_ID", values[1]);
                jsonObject.put("token", values[2]);
                break;
        }
        return jsonObject;
    }

    private HttpURLConnection connectingHttpURLConnection() throws Exception {
        URL url = new URL(Resources.getSystem().getString(R.string.ec2_public_url));
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "text/html");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();

        return httpURLConnection;
    }

    private StringBuffer httpRequestByJsonObject(JSONObject jsonObject) throws IOException {
        OutputStream outStream = con.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();

        BufferedReader reader = makeBufferedReader();
        StringBuffer buffer = makeStringBufferByBuffuredReader(reader);
        reader.close();
        return buffer;
    }

    private BufferedReader makeBufferedReader() throws IOException{
        InputStream stream = con.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader;
    }

    private StringBuffer makeStringBufferByBuffuredReader(BufferedReader reader) throws IOException{
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}