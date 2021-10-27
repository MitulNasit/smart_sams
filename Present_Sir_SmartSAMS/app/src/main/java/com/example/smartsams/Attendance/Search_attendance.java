package com.example.smartsams.Attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartsams.Director.Director_home;
import com.example.smartsams.HOD.HOD_home;
import com.example.smartsams.LoginActivity;
import com.example.smartsams.R;
import com.example.smartsams.Student.Student_home;
import com.example.smartsams.Teacher.Teacher_home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search_attendance extends AppCompatActivity {


    private Button btn_search;
    private EditText Senrollment;
    String U_enrollment, U_Status, U_Name, U_Date;
    public ArrayList<String> enrollment;
    public ArrayList<String> status;
    public ArrayList<String> name;
    public ArrayList<String> date;
    RecyclerView AttendanceList;
    RecyclerView.Adapter adapter;

    private static final String URL_SEARCHATTENDANCE = "https://codmans.com/get_attendance.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_attendance);

        Senrollment = findViewById(R.id.search_enroll_num);
        btn_search = findViewById(R.id.btn_search);
        enrollment = new ArrayList<>();
        status = new ArrayList<>();
        name = new ArrayList<>();
        date = new ArrayList<>();

        String VSenrollment =Senrollment.getText().toString().trim();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getAttendance(VSenrollment);
            }
        });
    }

    private void getAttendance(String VSenrollment) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEARCHATTENDANCE,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("login");
                                System.out.println("3");

                                if (success.equals("1")){
                                    for (int i = 0; i < jsonArray.length(); i++)
                                    {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        String name = object.getString("name").trim();
                                        String email = object.getString("email").trim();
                                        String id = object.getString("id").trim();
                                        String role = object.getString("role").trim();

                                    }

                                }
                                else if (success.equals("0")){
                                    Toast.makeText(Search_attendance.this, "Check UserID and password", Toast.LENGTH_SHORT).show();

                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();

                                Toast.makeText(Search_attendance.this, e.getMessage() , Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(Search_attendance.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();


                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("email", VSenrollment);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        }

    }
