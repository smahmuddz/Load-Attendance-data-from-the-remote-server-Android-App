package edu.ewubd.gridview_attendance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends Activity {
    private WebView webView;

    private String URL="https:/www.muthosoft.com/univ/attendance/report.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        webView =findViewById(R.id.attendanceWebView);
        Button back = findViewById(R.id.buttonExit);
        String[] keys={"CSE489-Lab","year","semester","course","section","sid"};
        String[] values={"true","2022","1","CSE489","1","2019360112"};
        httpRequest(keys,values);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    private void httpRequest(final String[] keys, String[] values) {
        new AsyncTask<Void, Void, String>() {
            protected String doInBackground(Void... param) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    for (int i = 0; i < keys.length; i++) {
                        params.add(new BasicNameValuePair(keys[i], values[i]));
                    }
                    String data = JSONParser.getInstance().makeHttpRequest(URL, "POST", params);
                    return data;
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute(String data){

                if(data!=null)
                {
                    try{
                        webView.loadDataWithBaseURL(null,data,"text/html","UTF-8",null);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }



}