package com.silke.scenefiend;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;

import com.silke.scenefiend.R;

import android.widget.TextView;

public class HelpActivity extends SceneFiendActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/lucindablack.ttf");
        TextView tv = (TextView) findViewById(R.id.CustomFont);
        tv.setTypeface(tf);
        
        InputStream iFile = getResources().openRawResource(R.raw.help);
        try 
        {
            TextView helpText = (TextView) findViewById(R.id.TextView_HelpText);
            String strFile = inputStreamToString(iFile);
            helpText.setText(strFile);
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "InputStreamToString failure", e);
        }
    }
    /**
     * Converts an input stream to a string
     * 
     * @param is
     *            The {@code InputStream} object to read from
     * @return A {@code String} object representing the string for of the input
     * @throws IOException
     *             Thrown on read failure from the input
     */
   
    public String inputStreamToString(InputStream is) throws IOException 
    {
        StringBuffer sBuffer = new StringBuffer();
        DataInputStream dataIO = new DataInputStream(is);
        String strLine = null;
        while ((strLine = dataIO.readLine()) != null) 
        {
            sBuffer.append(strLine + "\n");
        }
        dataIO.close();
        is.close();
        return sBuffer.toString();
    }
}
