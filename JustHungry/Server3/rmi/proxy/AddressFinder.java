package rmi.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;

//import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;


public class AddressFinder {
	
    private static final String REQUEST_URL = "http://api.postcodes.io/postcodes/";
    
    public AddressFinder() {
    	   	
    }

    
    public String getCountry(String postCode) {
        try {
 
            URL url = new URL(REQUEST_URL+ postCode);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           
            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);

            JSONObject genreJsonObject = (JSONObject) JSONValue.parseWithException(reader);
           
            // get the results
            HashMap results = (HashMap) genreJsonObject.get("result");
            return results.get("country").toString();            
     
           
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "null";
        }
    }
    
    public String getParish(String postCode) {
        try {
 
            URL url = new URL(REQUEST_URL+ postCode);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);

            JSONObject genreJsonObject = (JSONObject) JSONValue.parseWithException(reader);
            
            // get the results
            HashMap results = (HashMap) genreJsonObject.get("result");
            return results.get("parish").toString();            
     
           
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "null";
        }
    }
   
    public String getAdminDistrict(String postCode) {
        try {
 
            URL url = new URL(REQUEST_URL+ postCode);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);

            JSONObject genreJsonObject = (JSONObject) JSONValue.parseWithException(reader);
           
            // get the results
            HashMap results = (HashMap) genreJsonObject.get("result");
            return results.get("admin_district").toString();            
     
           
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "null";
        }
    }
    
}
