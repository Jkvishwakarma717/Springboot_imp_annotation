package service;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

public class MultiPush implements Callable<JSONObject> {
	private final String _response_Writer_path;
    private JSONObject _protocol;
    private String _api_key;
    static String message_url = new String("https://fcm.googleapis.com/fcm/send");
    
    public MultiPush(  JSONObject protocol,String api_key, String response_Writer_path){
    	
        this._protocol = protocol;
        this._api_key=api_key;
		this._response_Writer_path = response_Writer_path;
    }

    @Override
    public JSONObject call() throws Exception {
        return sendPush();
    }

    public JSONObject sendPush(){
        JSONObject _return = null;
        HttpResponse httpResponse;

        try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(message_url);
			request.setHeader("content-type", "application/json");
			request.setHeader("Authorization", new String("key=" + _api_key));
			StringEntity params = new StringEntity(_protocol.toString(), "UTF-8");
			params.setContentType("application/json");
			request.setEntity(params);
		    httpResponse = httpClient.execute(request);
			 JSONObject _temp = parseResponse(httpResponse);
			_return = _temp;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return _return;
    }
    public JSONObject parseResponse(HttpResponse httpResponse) throws ParseException, IOException{
    	 String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	  int status = httpResponse.getStatusLine().getStatusCode();
    	  JSONObject jSONObject = new JSONObject();
    	  
    	  if (status==200) {
        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
		 jSONObject = new JSONObject(responseBody);
			BufferedWriter writer = new BufferedWriter(new FileWriter(_response_Writer_path,true));
			writer.flush();
			writer.write(String.valueOf(datetime+" "+responseBody));
			 writer.append('\n');
			   writer.close();
			   return jSONObject;
    	  }
    	  else if(status==400) {
    		  jSONObject = new JSONObject();
    		  return jSONObject.put("contained invalid fields", status); 		  
    	  }
    	  else if(status==401) {
    		  jSONObject = new JSONObject();
    		  return jSONObject.put(" internal error", status);
    	  }
		return jSONObject;
       
    }
}
