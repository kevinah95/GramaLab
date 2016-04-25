package com.example.android.gramalab.utils;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.android.gramalab.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by Farcem on 24-Apr-16.
 */
public class Server extends AsyncTask<String, Void, JSONArray>
{
    String charset = "UTF-8";
    Handler h = new Handler(Looper.getMainLooper());

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected JSONArray doInBackground(String... pUrl)
    {
        try
        {
            URLConnection connection = new URL(pUrl[0]).openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

                OutputStream output = connection.getOutputStream();
                output.write(pUrl[1].getBytes(charset));

                InputStream response = connection.getInputStream();
                Scanner scanner = new Scanner(response);
                String responseJSON = scanner.useDelimiter("\\n").next();
                return new JSONArray(responseJSON);
        }
        catch (final MalformedURLException e)
        {
            h.post(new Runnable()
            {
                public void run()
                {
                    Toast.makeText(MainActivity.context, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (final IOException e)
        {
            h.post(new Runnable()
            {
                public void run()
                {
                    Toast.makeText(MainActivity.context, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (final JSONException e)
        {
            h.post(new Runnable()
            {
                public void run()
                {
                    Toast.makeText(MainActivity.context, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;
    }
}


