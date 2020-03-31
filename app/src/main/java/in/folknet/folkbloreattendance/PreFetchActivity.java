package in.folknet.folkbloreattendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

///////////////////////////////
//////////////////////////////

public class PreFetchActivity extends AppCompatActivity {

    String[] zones = new String[]{};
    String[] programs = new String[]{};
    String[] categories = new String[]{};
    String[] locations = new String[]{};
    String[] sessions = new String[]{};
    final String[] empty_array = new String[]{};
    List<String> programsList;
    List<String> zonesList;
    List<String> categoriesList;
    List<String> locationsList;
    List<String> sessionsList;
    final ArrayList<String> finalPrograms = new ArrayList<>();
    ArrayAdapter<String> adapterPrograms;
    final ArrayList<String> finalZones = new ArrayList<>();
    ArrayAdapter<String> adapterZones;
    final ArrayList<String> finalCategories = new ArrayList<>();
    ArrayAdapter<String> adapterCategories;
    final ArrayList<String> finalLocations = new ArrayList<>();
    ArrayAdapter<String> adapterLocations;
    final ArrayList<String> finalSessions = new ArrayList<>();
    ArrayAdapter<String> adapterSessions;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference fgboys = db.collection("ProgramDetails");
    private String date = "";
    private String spinnerZones = "";
    private String spinnerPrograms = "";
    private String spinnerCategories = "";
    private String spinnerLocations = "";
    private String spinnerSessions = "";
    TextView mTextView;
    int posZone, posProgram, posCategory, posLocation, posSession;

    Spinner zone, program, category, location, session;


    String printDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    Button b1,b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_fetch);

        mTextView = findViewById(R.id.textView2);
        DateFormat dToday = new SimpleDateFormat("dd MMM yyyy");
        DateFormat dDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calobj = Calendar.getInstance();
//        date = "2020-01-03";
        date = dDate.format(calobj.getTime());
        mTextView.setText("Date: " + dToday.format(calobj.getTime()));
//        mTextView.setText("Date: " + date);

        /////////////////////////////////////////////////
        zone = findViewById(R.id.spinnerZone);
        zonesList = Arrays.asList(zones);
        finalZones.addAll(zonesList);

        adapterZones = new ArrayAdapter<>(this, R.layout.spinner_item,finalZones);
        adapterZones.setDropDownViewResource(R.layout.spinner_item);
        zone.setAdapter(adapterZones);

        fgboys
                .whereEqualTo("date", date)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        TreeMap<String, Integer> count = new TreeMap<>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);
                            if (finalZones.contains(note.getZone())) {
                                continue;
                            } else {
                                finalZones.add(note.getZone());
                                adapterZones.notifyDataSetChanged();
                            }
                        }
                    }
                });

        zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerZones = parent.getItemAtPosition(position).toString();
                posZone = position;

                ////////////////////////////////////////////

                ////////////////////////////////////////////
                populatePrograms(spinnerZones);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////

        b1=(Button)findViewById(R.id.buttonFOLKID);
        b2=(Button)findViewById(R.id.buttonGuest);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (spinnerZones.length() == 0) {
                    Toast.makeText(PreFetchActivity.this, "Zone not selected", Toast.LENGTH_SHORT).show();
                } else if (spinnerPrograms.length() == 0) {
                    Toast.makeText(PreFetchActivity.this, "Program not selected", Toast.LENGTH_SHORT).show();
                } else if (spinnerCategories.length() == 0) {
                    Toast.makeText(PreFetchActivity.this, "Category not selected", Toast.LENGTH_SHORT).show();
                } else if (spinnerLocations.length() == 0) {
                    Toast.makeText(PreFetchActivity.this, "Location not selected", Toast.LENGTH_SHORT).show();
                } else if (spinnerSessions.length() == 0) {
                    Toast.makeText(PreFetchActivity.this, "Session not selected", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TAG", "onClick: " + spinnerLocations);
                    Intent intent = new Intent(getApplicationContext(), FOLK.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Zone", spinnerZones);
                    bundle.putString("Program", spinnerPrograms);
                    bundle.putString("Category", spinnerCategories);
                    bundle.putString("Location", spinnerLocations);
                    bundle.putString("Session", spinnerSessions);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnerZones.length() == 0){
                    Toast.makeText(PreFetchActivity.this, "Zone not selected", Toast.LENGTH_SHORT).show();
                }else if(spinnerPrograms.length() == 0){
                    Toast.makeText(PreFetchActivity.this, "Program not selected", Toast.LENGTH_SHORT).show();
                }else if(spinnerCategories.length() == 0){
                    Toast.makeText(PreFetchActivity.this, "Category not selected", Toast.LENGTH_SHORT).show();
                }else if(spinnerLocations.length() == 0){
                    Toast.makeText(PreFetchActivity.this, "Location not selected", Toast.LENGTH_SHORT).show();
                }else if(spinnerSessions.length() == 0){
                    Toast.makeText(PreFetchActivity.this, "Session not selected", Toast.LENGTH_SHORT).show();
                }else{
                Intent intent = new Intent(getApplicationContext(), FOLK4.class);
                Bundle bundle = new Bundle();
                bundle.putString("Zone",spinnerZones);
                bundle.putString("Program",spinnerPrograms);
                bundle.putString("Category",spinnerCategories);
                bundle.putString("Location",spinnerLocations);
                bundle.putString("Session",spinnerSessions);
                intent.putExtras(bundle);
                startActivity(intent);          }  }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        zone = findViewById(R.id.spinnerZone);
//        zonesList = Arrays.asList(zones);
//        finalZones.addAll(zonesList);
//
//        adapterZones = new ArrayAdapter<>(this, R.layout.spinner_item,finalZones);
//        adapterZones.setDropDownViewResource(R.layout.spinner_item);
//        zone.setAdapter(adapterZones);
//
//        fgboys
//                .whereEqualTo("date", date)
//                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            return;
//                        }
//
//                        TreeMap<String, Integer> count = new TreeMap<>();
//
//                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                            Note note = documentSnapshot.toObject(Note.class);
//                            if (finalZones.contains(note.getZone())) {
//                                continue;
//                            } else {
//                                finalZones.add(note.getZone());
//                                adapterZones.notifyDataSetChanged();
//                            }
//                        }
//                    }
//                });
//
//        zone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                spinnerZones = parent.getItemAtPosition(position).toString();
//                posZone = position;
//
//                ////////////////////////////////////////////
//
//                ////////////////////////////////////////////
//                populatePrograms(spinnerZones);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//    }

    public void populatePrograms(String spinnerZones) {
        program = findViewById(R.id.spinnerProgram);
//        final String[] programs = new String[]{"ALL"};
        programsList = Arrays.asList(programs);
        finalPrograms.clear();
        finalPrograms.addAll(programsList);
        final String zone = spinnerZones;
//        Toast.makeText(this, "Populating programs for " + zone, Toast.LENGTH_SHORT).show();
        Log.d("TAG", "onEvent: Entered if inside PopulateProgram2");

        adapterPrograms = new ArrayAdapter<String>(
                this,R.layout.spinner_item,finalPrograms);
        adapterPrograms.setDropDownViewResource(R.layout.spinner_item);
        program.setAdapter(adapterPrograms);



        fgboys
                .whereEqualTo("date", date)
                .whereEqualTo("zone", zone)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        TreeMap<String, Integer> count = new TreeMap<>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);
                            if (finalPrograms.contains(note.getProgram())) {
                                Log.d("TAG", "onEvent: Entered if inside PopulateProgram " + note.getProgram().toString());
                                continue;
                            } else {

                                finalPrograms.add(note.getProgram());
                                adapterPrograms.notifyDataSetChanged();
                            }
                        }
                    }
                });

        program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPrograms = parent.getItemAtPosition(position).toString();
                populateCategories(zone, spinnerPrograms);
                posProgram = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populateCategories(String spinnerZones, String spinnerPrograms) {
        Spinner category = findViewById(R.id.spinnerCategory);
//        final String[] programs = new String[]{"ALL"};
        categoriesList = Arrays.asList(categories);
        finalCategories.clear();
        finalCategories.addAll(categoriesList);
        final String program = spinnerPrograms;
        final String zone = spinnerZones;

        adapterCategories = new ArrayAdapter<String>(
                this,R.layout.spinner_item,finalCategories);
        adapterCategories.setDropDownViewResource(R.layout.spinner_item);
        category.setAdapter(adapterCategories);

        fgboys
                .whereEqualTo("date", date)
                .whereEqualTo("zone",zone)
                .whereEqualTo("program",program)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        TreeMap<String, Integer> count = new TreeMap<>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);
                            if (finalCategories.contains(note.getCategory())) {
                                continue;
                            } else {
                                finalCategories.add(note.getCategory());
                                adapterCategories.notifyDataSetChanged();
                            }
                        }
                    }
                });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCategories = parent.getItemAtPosition(position).toString();
                populateLocations(zone,program,spinnerCategories);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populateLocations(String zone, String program, String category) {
        Spinner location = findViewById(R.id.spinnerLocation);
//        final String[] programs = new String[]{"ALL"};
        locationsList = Arrays.asList(locations);
        finalLocations.clear();
        finalLocations.addAll(locationsList);
        final String programs = program;
        final String zones = zone;
        final String categories = category;

        adapterLocations = new ArrayAdapter<String>(
                this,R.layout.spinner_item,finalLocations);
        adapterLocations.setDropDownViewResource(R.layout.spinner_item);
        location.setAdapter(adapterLocations);

        fgboys
                .whereEqualTo("date", date)
                .whereEqualTo("zone",zone)
                .whereEqualTo("program",program)
                .whereEqualTo("category",category)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        TreeMap<String, Integer> count = new TreeMap<>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);

                            if (note.getProgram_location() == null) {
                                note.setProgram_location("MVT");
                            }

                            if (finalLocations.contains(note.getProgram_location())) {
                                continue;
                            } else {
                                finalLocations.add(note.getProgram_location());
                                adapterLocations.notifyDataSetChanged();
                            }
                        }
                    }
                });

        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerLocations = parent.getItemAtPosition(position).toString();
                populateSessions(zones,programs,categories,spinnerLocations);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void populateSessions(String zone, String program, String category, String location) {
        Spinner session = findViewById(R.id.spinnerSession);
//        final String[] programs = new String[]{"ALL"};
        sessionsList = Arrays.asList(sessions);
        finalSessions.clear();
        finalSessions.addAll(sessionsList);
        final String programs = program;
        final String zones = zone;
        final String categories = category;
        final String locations = location;

        adapterSessions = new ArrayAdapter<String>(
                this,R.layout.spinner_item,finalSessions);
        adapterSessions.setDropDownViewResource(R.layout.spinner_item);
        session.setAdapter(adapterSessions);

        fgboys
                .whereEqualTo("date", date)
                .whereEqualTo("zone",zone)
                .whereEqualTo("program",program)
                .whereEqualTo("category",category)
//                .whereEqualTo("location",location)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        TreeMap<String, Integer> count = new TreeMap<>();

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);
                            if (finalSessions.contains(note.getSession())) {
                                continue;
                            } else {
                                finalSessions.add(note.getSession());
                                adapterSessions.notifyDataSetChanged();
                            }
                        }
                    }
                });

        session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSessions = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        zone.setSelection(posZone);
//        program.setSelection(posProgram);

    }

    public void select(View v) {
        Intent intent = new Intent(PreFetchActivity.this,FOLK.class);
        Bundle bundle = new Bundle();
        bundle.putString("Zone",spinnerZones);
        bundle.putString("Program",spinnerPrograms);
        bundle.putString("Category",spinnerCategories);
        bundle.putString("Location",spinnerLocations);
        bundle.putString("Session",spinnerSessions);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
