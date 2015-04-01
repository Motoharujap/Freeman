package interaction_with_webapp;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Motoharu on 18.10.2014.
 */
public class JSONParser {
    static InputStream is;
    static JSONObject jsonObject;
    static String json;


    public JSONParser() {

    }
    public JSONObject getJSONFromUrl(String url, List<NameValuePair> params)
    {
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
         }
        catch (UnsupportedOperationException e){
            e.printStackTrace();
        }
        catch (ClientProtocolException i){
            i.printStackTrace();
        }
        catch (IOException q){
            q.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e){
            Log.e("Buffer error", "Error converting result " + e.toString());
        }
        try{
            jsonObject = new JSONObject(json);
        }catch (JSONException e){
            Log.e("JSON parser", "Error parsing JSON" + e.toString());
        }
        return jsonObject;

    }
}