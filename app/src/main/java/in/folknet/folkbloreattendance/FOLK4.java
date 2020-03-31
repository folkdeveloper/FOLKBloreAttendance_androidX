package in.folknet.folkbloreattendance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class FOLK4 extends AppCompatActivity {
    Button scanbtn;
    private RequestQueue requestQueue;
    String  time, venue, colour, zzone, session, category, program;
    TextView result;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    RequestQueue rq;
    int flag = 0;
    Button btn;
    EditText ename,eid, jp;

    String val,fin;
    ProgressDialog pd;

    String id,name,fg, mjp, sarea, fl, mob, fid, zoneRec, programRec, categoryRec, locationRec, sessionRec;
    Spinner spinner_area, spinner_read ;
    String selectedItemText, selectedItemTextread;
    RadioGroup rg;
    Boolean post_res_state;
    TextView txt;
    int i;
    String ws;
    RadioButton rb;
    String url1 = "https://us-central1-folk-database.cloudfunctions.net/Basic-Info-Exist?&phone=";
//    DatabaseReference database,mid,mfg,mcount,mChack,mfl, mmob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folk4);

        zoneRec = getIntent().getStringExtra("Zone");
        programRec = getIntent().getStringExtra("Program");
        categoryRec = getIntent().getStringExtra("Category");
        locationRec = getIntent().getStringExtra("Location");
        sessionRec = getIntent().getStringExtra("Session");
//        Toast.makeText(this, "Zone: " + zoneRec + "\nProgram: " + programRec + "\nCategory: " + categoryRec + "\nLocation: " + locationRec + "\nSession: " + sessionRec, Toast.LENGTH_SHORT).show();

        Log.v("Check", "Checking ");


// if (isOnline())
// {
// pd.hide();
        jp = (EditText)findViewById(R.id.etjp);
        btn = (Button) findViewById(R.id.btn);
        rq = Volley.newRequestQueue(this);

        spinner_area = (Spinner) findViewById(R.id.spinner_area);
        spinner_read = (Spinner) findViewById(R.id.spinner_read);





//        rg = (RadioGroup)findViewById(R.id.radioGroup);
        eid = (EditText)findViewById(R.id.et);
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long lid) {
                selectedItemText = spinner_area.getItemAtPosition(position).toString();
// Notify the selected item text
//                Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_read.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long lid) {
                selectedItemTextread = spinner_read.getItemAtPosition(position).toString();
// Notify the selected item text
//                Toast.makeText(getApplicationContext(), "Selected : " + selectedItemTextread, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                id = eid.getText().toString();
                mjp = jp.getText().toString();


                if ((id.length() < 10 )||(id.length() > 10 ) ){
                    Toast.makeText(getApplicationContext(), "Enter Correct Mobile Number " + id.length(), Toast.LENGTH_SHORT).show();
                }else if(mjp.isEmpty()){
                    Toast.makeText(getApplicationContext(), "You have not entered Japa Rounds!", Toast.LENGTH_SHORT).show();
                }else if(selectedItemText.equals("Not Selected")){
                    Toast.makeText(getApplicationContext(), "You have not selected any Area near to your Residence!", Toast.LENGTH_SHORT).show();
                }
                else if(selectedItemTextread.equals("Not Selected")){
                    Toast.makeText(getApplicationContext(), "You have not entered Reading Mins!", Toast.LENGTH_SHORT).show();
                }
                else
                {



                    pd = new ProgressDialog(FOLK4.this);
                    pd.setTitle("Please Wait...");
                    pd.setMessage("Fetching Data from Database \n ");
                    pd.show();
                    pd.setCancelable(true);
                    val = "";

                    zzone = zoneRec;
                    session = sessionRec;
                    category = categoryRec;
                    program = programRec;

                    String data = "{"+
                            "\"area\"" + " : " + "\"" + selectedItemText.toString() + "\","+
                            "\"fid\"" + " : "+ "\"" + eid.getText().toString() + "\","+
                            "\"japa\"" + " : "+ "\"" + jp.getText().toString() + "\","+
                            "\"program\"" + " : "+ "\"" + program.toString() + "\","+
                            "\"category\"" + " : "+ "\"" + category.toString() + "\","+
                            "\"program_location\"" + " : "+ "\"" + locationRec.toString() + "\","+
                            "\"zread\"" + " : "+ "\"" + selectedItemTextread.toString() + "\","+
                            "\"zzone\"" + " : "+ "\"" + zzone.toString() + "\","+
                            "\"session\"" + " : "+ "\"" + session.toString() +"\""+

                            "}";
                    Log.d("Main Activity", "onClick: " + data.toString());
                    Submit(data);






//                    sendJsonRequest(id);




                }
            }
        });


    }






    public void sendJsonRequest(String folk_id) {
        Log.d("Test", "Inside json req");




//        String url= url1.concat(folk_id);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    Log.d("Check", "onResponse: " + response);
//                    fid = response.getString("folk_id");
//                    name = response.getString("Name");
//                    fg = response.getString("FOLK_GUIDE");
//                    fl = response.getString("folk_level");
//
//                    mob = response.getString("mobile");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        rq.add(jsonObjectRequest);



        Log.d("Check", "Checking");

        if ( name == null) {
            Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
            Log.d("Check", "name is" + name);



            pd.hide();


        } else{





//            Toast.makeText(getApplicationContext(), year_fb, Toast.LENGTH_SHORT).show();
//
//
//            fin = " FOLK BANGALORE\n **********************\nName : " + name + "\nFOLK ID : " + fid + "\nFOLK Guide : " + fg + "\nTIME : "+ time + "\nFOLK Level : "+fl + "\nMeeting : "+Venuee + "\nCOUPON COLOR : "+c+"\n **********************\n\n\n\n.";
//            Intent intent = new Intent(FOLK4.this, MainActivity.class);
//            intent.putExtra("message", "fin commented");
//            intent.putExtra("name",name);
//            intent.putExtra("mjp",mjp);
//            intent.putExtra("fg",fg);
//            intent.putExtra("fl",fl);
//            intent.putExtra("mob",mob);
//            intent.putExtra("time",time);
//            intent.putExtra("fid",fid);
//            intent.putExtra("ws","Workshop");
//            intent.putExtra("counter",i);
//            intent.putExtra("year_fb",year_fb);
//            intent.putExtra("selected_area", selectedItemText);
//            intent.putExtra("selected_read", selectedItemTextread);
//            String a,b;
//            a = time();
//            b = sort(fg);
//            intent.putExtra("timeNumber",a);
//            intent.putExtra("fgNumber",b);


            eid.setText("");
            jp.setText("");
            pd.hide();
            Log.d("Check", "Calling Main activity");
//            startActivity(intent);

        }

        Log.d("Check", "Out side Name Function");

    }



    private void Submit(String data)
    {
        pd = new ProgressDialog(FOLK4.this);
        pd.setTitle("Please Wait...");
        pd.setMessage("Fetching Data from Database.");
        pd.show();
        pd.setCancelable(false);

        final String savedata= data;
        String URL="https://us-central1-folk-demo.cloudfunctions.net/createAttendanceDoc";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.hide();
                pd.dismiss();
                try {
                    JSONObject objres=new JSONObject(response);
//                    Toast.makeText(FOLK4.this, "Please Submit Now", Toast.LENGTH_SHORT).show();
//
//                    Toast toast = Toast.makeText(getApplicationContext(),"Please Submit Now", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                    Toast.makeText(getApplicationContext(),objres.toString(),Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn.performClick();
                        }
                    }, 0000);

                    fid = objres.getString("folk_id");
                    name = objres.getString("name");
                    fg = objres.getString("fg");
                    fl = objres.getString("folk_level");
                    colour = objres.getString("colour");
                    venue = objres.getString("meetup");
//                    post_res_state = objres("status");
                    time = objres.getString("time");


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Check the Number \nAnd try again...", Toast.LENGTH_SHORT).show();

                //Log.v("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };
        Log.d("Check", "Submit: " + stringRequest.toString());
        requestQueue.add(stringRequest);
        pd.hide();
        pd.dismiss();

        Log.d("Check", "Checking");

        if ( name == null) {

//            Toast.makeText(getApplicationContext(), "Kindly wait... \nFetching details from DB", Toast.LENGTH_SHORT).show();
//
//            Toast toast = Toast.makeText(getApplicationContext(),"Kindly wait... \nFetching details from DB", Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();

            Log.d("Check", "name is" + name);



            pd.hide();


        } else{

             fin = "\n **********************\n" + "     FOLK BANGALORE \n  Attendance Slip v2.0 "+"\n  "+ time +"\n**********************\nName : " + name + "\nFOLK ID : " + fid + "\nFOLK Guide : " + fg + "\nLevel : "+fl + "\nMeeting : "+ venue + "\nCOUPON COLOR : "+colour+"\n **********************\n\n\n\n.";
            Intent intent = new Intent(FOLK4.this, MainActivity.class);
            intent.putExtra("message", fin);
            intent.putExtra("name",name);
            intent.putExtra("mjp",mjp);
            intent.putExtra("fg",fg);
            intent.putExtra("fl",fl);
            intent.putExtra("mob",mob);
            intent.putExtra("time",time);
            intent.putExtra("fid",fid);
            intent.putExtra("ws","Workshop");
//            intent.putExtra("counter",i);

            intent.putExtra("selected_area", selectedItemText);
            intent.putExtra("selected_read", selectedItemTextread);

            eid.setText("");
            jp.setText("");
            pd.hide();
            Log.d("Check", "Calling Main activity");
            startActivity(intent);

    }


}}