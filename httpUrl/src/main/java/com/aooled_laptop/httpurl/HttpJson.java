package com.aooled_laptop.httpurl;

import android.content.Context;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpJson extends Thread {

    private String url;
    private Context context;
    private ListView listView;
    private JsonAdapter adapter;
    private Handler handler;

    public HttpJson(String url, ListView listView, JsonAdapter adapter, Handler handler){
        this.listView = listView;
        this.handler = handler;
        this.url = url;
        this.adapter = adapter;

    }
    @Override
    public void run() {
        super.run();
        URL httpUrl;
        try {
            httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setReadTimeout(5000);
//            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while((str = reader.readLine()) != null){
                sb.append(str);
            }
            final List<Person> data = parseJson(sb.toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.setData(data);
                    listView.setAdapter(adapter);
                }
            });
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private List<Person> parseJson(String json){
        try {
            JSONObject object = new JSONObject(json);
            List<Person> persons = new ArrayList<Person>();
            int result = object.getInt("result");
            if(result == 1) {
                JSONArray personData = object.getJSONArray("personData");

                for (int i = 0; i < personData.length(); i++){
                    Person personObject = new Person();
                    persons.add(personObject);
                    JSONObject person = personData.getJSONObject(i);
                    String name = person.getString("name");
                    int age = person.getInt("age");
                    String url = person.getString("url");
                    personObject.setName(name);
                    personObject.setAge(age);
                    personObject.setUrl(url);
                    JSONArray schoolInfos = person.getJSONArray("schoolInfo");
                    List<SchoolInfo> schInfos = new ArrayList<SchoolInfo>();
                    personObject.setSchoolInfo(schInfos);
                    for (int j = 0; j < schoolInfos.length(); j++) {
                        JSONObject school = schoolInfos.getJSONObject(i);
                        String schoolName = school.getString("school_name");
                        SchoolInfo info = new SchoolInfo();
                        info.setSchool_name(schoolName);
                        schInfos.add(info);
                    }

                }
                return persons;
            }else{
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
