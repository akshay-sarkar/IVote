package com.example.akshay.myapplication;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akshay.myapplication.adapter.CandidateAdapter;
import com.example.akshay.myapplication.adapter.PollAdapter;
import com.example.akshay.myapplication.configuration.ConfigurationFile;
import com.example.akshay.myapplication.dao.CandidateEntity;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CandidateManagementActivity extends ListActivity {

    ArrayList<CandidateEntity> candidateObjects;
    Context ctx;
    ListView list;
    // numberOfPollActive should not be greater than 1.
    public static int numberOfPollActive = 0;
    ProgressDialog progressDialog;
    HttpURLConnection connection;
    private final String base_url = ConfigurationFile.base_url;
    String lineSeperator="#&#";
    String columentSeperator = "@&@";
    int pollID;
    String intentPollName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_management);
        ctx  = getApplicationContext();

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatingButtonAddPollScreen);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ctx, AddCandidateActivity.class);
                startActivity(intent);
            }
        });

        pollID = getIntent().getExtras().getInt("DATA");
        ConfigurationFile.pollId = pollID;
        intentPollName = getIntent().getExtras().getString("POLL_NAME");

        TextView textPollName = (TextView) findViewById(R.id.Pollname);
        String pollName = "Poll Name : "+intentPollName;
        textPollName.setText(pollName);

        //Preparing Paramaneters to pass in Async Thread
        String url ="/displayCandidate?pollID="+pollID;
        //Async Runner
        CandidateManagementActivity.AsyncTaskRunner runner = new CandidateManagementActivity.AsyncTaskRunner();
        runner.execute(url);
    }

    //When you come from different Activity to this activity again
    @Override
    protected void onResume() {
        super.onResume();
        //Preparing Paramaneters to pass in Async Thread
        String url ="/displayCandidate?pollID="+pollID;
        //Async Runner

        CandidateManagementActivity.AsyncTaskRunner runner = new CandidateManagementActivity.AsyncTaskRunner();
        runner.execute(url);
    }

    /* Thread for Server Interation - Pass paramenter and URL */
    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            try {
                //Setting Progress Dialog
                //progressDialog = new ProgressDialog(PollManagementActivity.this);
                //progressDialog.show(ctx, "iVote", "Retrieving Poll Data", true, false);

                StringBuffer responseString = null;
                String inputLine;
                URL dataUrl = new URL(base_url + params[0]);
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.setConnectTimeout(ConfigurationFile.connectionTimeout); //'Connection Timeout' is only called at the beginning to test if the server is up or not.
                connection.setReadTimeout(ConfigurationFile.connectionTimeout); //'Read Timeout' is to test a bad network all along the transfer.
                // optional default is GET
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    responseString = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        responseString.append(inputLine);
                    }
                    in.close();
                }
                resp = responseString.toString();

            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            } finally {
                try {
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace(); //If you want further info on failure...
                }
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            //if(progressDialog.isShowing())
            //    progressDialog.dismiss();

            if (resp.trim().isEmpty()) {
                Toast.makeText(ctx, "Not Record Found!!", Toast.LENGTH_LONG).show();
            } else if (!resp.trim().isEmpty()) {
//<<<<<<< HEAD
//                pollObjects = new ArrayList<>();
////                pollObjects.add(new PollEntity("UTA Ambassador President", "Start Date: 02/04/2017", "End Date: 02/06/2017" ));
////                pollObjects.add(new PollEntity("UTA Mascot Men", "Start Date: "+"02/07/2017", "End Date: "+"02/09/2017" ));
////                pollObjects.add(new PollEntity("UTA Mascot Women", "Start Date: "+"02/11/2017", "End Date: "+"02/13/2017" ));
////                pollObjects.add(new PollEntity("UTA CS Nerd", "Start Date: "+"02/17/2017", "End Date: "+"02/19/2017" ));
////                pollObjects.add(new PollEntity("UTA Ambassador VC", "Start Date: "+"02/21/2017", "End Date: "+"02/23/2017" ));
//
//                //Log.d("POLLS ", resp);
//                String[] responsePolls = resp.split(lineSeperator);
//                for(int i = 0; i< responsePolls.length; i++){
//                    Log.d("POLLScnd "+i, responsePolls[i]);
//                    String[] individualPollColumns = responsePolls[i].split(columentSeperator);
//                    pollObjects.add(new CandidateEntity(Integer.parseInt(individualPollColumns[0]), "First Name : "+individualPollColumns[1], "Last Name : "+individualPollColumns[2], "Gender : "+individualPollColumns[5]
//                    ,"DOB : "+individualPollColumns[3],"Email : "+individualPollColumns[4],"Department : "+individualPollColumns[6],"Qualities : "+individualPollColumns[7],"Interests : "+ individualPollColumns[8],
//                            "Student Organizations : "+individualPollColumns[9],"Community Service Hours : "+individualPollColumns[10]));
//                }
//
//               /* pollObjects.add(new CandidateEntity(1, "akshay","sarkar", "aa@a.c","12.12.12", "Male", "CSE", "captain",
//                        "tech", "NA","4"));
//                pollObjects.add(new CandidateEntity(2, "shayam","gopal", "xyz@a.c","12.12.12", "Male", "CSE", "captain",
//                        "tech", "NA","4"));*/
//
//               // CandidateAdapter pollAdapter = new CandidateAdapter()
//                CandidateAdapter pollAdapter = new CandidateAdapter(ctx, R.layout.activity_candidateview, pollObjects);
//=======
                candidateObjects = new ArrayList<>();
                String responsePolls[] = resp.split(ConfigurationFile.lineSeperator);
                for(int i = 0; i< responsePolls.length; i++){
                    String[] individualPollColumns = responsePolls[i].split(columentSeperator);
                    candidateObjects.add(new CandidateEntity(Integer.parseInt(individualPollColumns[0]), "First Name : "+individualPollColumns[1], "Last Name : "+individualPollColumns[2], "Gender : "+individualPollColumns[5]
                            ,"DOB : "+individualPollColumns[3],"Email : "+individualPollColumns[4],"Department : "+individualPollColumns[6],"Qualities : "+individualPollColumns[7],"Interests : "+ individualPollColumns[8],
                            "Student Organizations : "+individualPollColumns[9],"Community Service Hours : "+individualPollColumns[10]));
                }
//                candidateObjects.add(new CandidateEntity(1, "akshay","sarkar", "aa@a.c","12.12.12", "Male", "CSE", "captain",
//                        "tech", "NA","4"));
//                candidateObjects.add(new CandidateEntity(2, "shayam","gopal", "xyz@a.c","12.12.12", "Male", "CSE", "captain",
//                        "tech", "NA","4"));

                CandidateAdapter pollAdapter = new CandidateAdapter(ctx, R.layout.activity_candidateview, candidateObjects);
//>>>>>>> origin/master
                pollAdapter.setNotifyOnChange(true);
                setListAdapter(pollAdapter);
                list = getListView();

            }
        }
    }
    public void Logout(View view){

        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}