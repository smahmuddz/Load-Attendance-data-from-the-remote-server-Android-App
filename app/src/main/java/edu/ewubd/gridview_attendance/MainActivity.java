package edu.ewubd.gridview_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    String URL = "https://muthosoft.com/univ/attendance/report.php";
    GridView gv;
    WebView webView;
    ArrayList<Course> courseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.attendanceWebView);
        String[] keys = {"my_courses","sid"};
        String[] values = {"true","2019360112"};
        httpRequest(keys, values);
        gv =findViewById(R.id.grid);
        courseList = new ArrayList<Course>();
    }

    @SuppressLint("StaticFieldLeak")
    private void httpRequest(String[] keys, String[] values) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {

                try {
                    List<NameValuePair> voidss = new ArrayList<NameValuePair>();
                    for (int i = 0; i < keys.length; i++) {
                        voidss.add(new BasicNameValuePair(keys[i], values[i]));
                    }
                    String data = JSONParser.getInstance().makeHttpRequest(URL, "POST", voidss);
                    return data;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute(String data){
                if(data != null){
                    try{
                        courseList.add(new Course(data));

                        gridAdapter adapter = new gridAdapter(MainActivity.this, courseList);
                        gv.setAdapter(adapter);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }.execute();
    }
    public class gridAdapter extends ArrayAdapter<Course> {
        public gridAdapter(@NonNull Context context, ArrayList<Course> courseList) {
            super(context, 0, courseList);
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View courseList = convertView;
            if (courseList == null) {
                courseList = LayoutInflater.from(getContext()).inflate(R.layout.grid_adapter, parent, false);
            }
            Course crc = getItem(position);
            TextView courseName = courseList.findViewById(R.id.textView);
            courseName.setText(crc.getName());
            courseName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, AttendanceActivity.class);
                    startActivity(i);
                }
            });
             return courseList;
        }
    }
}