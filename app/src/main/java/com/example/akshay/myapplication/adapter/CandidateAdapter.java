package com.example.akshay.myapplication.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.akshay.myapplication.AddCandidateActivity;
import com.example.akshay.myapplication.EditCandidateActivity;
import com.example.akshay.myapplication.PollManagementActivity;
import com.example.akshay.myapplication.R;
import com.example.akshay.myapplication.VoteScreenActivity;
import com.example.akshay.myapplication.configuration.ConfigurationFile;
import com.example.akshay.myapplication.dao.CandidateEntity;
import com.example.akshay.myapplication.dao.PollEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CandidateAdapter extends ArrayAdapter<CandidateEntity> {

    private LayoutInflater mInflater;
    private ArrayList<CandidateEntity> pollData;
    private int mViewResourceId;
    private Context ctx;
    private final String base_url = ConfigurationFile.base_url;
    HttpURLConnection connection;
    String lineSeperator="#&#";
    String columentSeperator = "@&@";

    public CandidateAdapter(Context context, int textViewResourceId, ArrayList<CandidateEntity> bagData) {
        super(context, textViewResourceId, bagData);
        ctx = context;
        pollData = bagData;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public void refreshEvents(ArrayList<CandidateEntity> bagData) {
        this.pollData.clear();
        this.pollData.addAll(bagData);
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        final CandidateEntity candidateObject = pollData.get(position);
        final ImageView imgViewDownArrow = (ImageView) convertView.findViewById(R.id.down_arrow_image);
        TextView fname = (TextView) convertView.findViewById(R.id.txtFname);
        TextView lname = (TextView) convertView.findViewById(R.id.txtLname);
        final TextView email = (TextView) convertView.findViewById(R.id.txtEmail);
        TextView gender = (TextView) convertView.findViewById(R.id.txtGender);
        TextView dob = (TextView) convertView.findViewById(R.id.txtDOB);
        final TextView dept = (TextView) convertView.findViewById(R.id.txtCourse);
        final TextView qualities = (TextView) convertView.findViewById(R.id.txtQualities);
        final TextView interests = (TextView) convertView.findViewById(R.id.txtInterest);
        final TextView studentorg = (TextView) convertView.findViewById(R.id.txtStudentOrganisation);
        final TextView commhrs = (TextView) convertView.findViewById(R.id.txtCommhrs);

        if (candidateObject != null) {

            if (fname != null) {
                fname.setText(candidateObject.getFirstName());
            }
            if (lname != null) {
                lname.setText(candidateObject.getLastName());
            }
            if (gender != null) {
                gender.setText(candidateObject.getGender());
            }
            if (email != null) {
                email.setText(candidateObject.getUTAEmailID());
            }

            if (dob != null) {
                dob.setText(candidateObject.getDOB());
            }
            if (dept != null) {
                dept.setText(candidateObject.getDepartment());
            }
            if (qualities != null) {
                qualities.setText(candidateObject.getQualities());
            }
            if (interests != null) {
                interests.setText(candidateObject.getInterests());
            }
            if (studentorg != null) {
                studentorg.setText(candidateObject.getStudentOrganization());
            }
            if (commhrs != null) {
                commhrs.setText(candidateObject.getCommunityServiceHours());
            }


            // Listener for the icons
            imgViewDownArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imgViewDownArrow.getTag()!= null && imgViewDownArrow.getTag().equals("true")){
                        dept.setVisibility(View.GONE);
                        qualities.setVisibility(View.GONE);
                        interests.setVisibility(View.GONE);
                        studentorg.setVisibility(View.GONE);
                        commhrs.setVisibility(View.GONE);
                        //email.setVisibility(View.GONE);
                        imgViewDownArrow.setTag("false");
                        imgViewDownArrow.setRotation(0);
                    }else{
                        dept.setVisibility(View.VISIBLE);
                        qualities.setVisibility(View.VISIBLE);
                        interests.setVisibility(View.VISIBLE);
                        studentorg.setVisibility(View.VISIBLE);
                        commhrs.setVisibility(View.VISIBLE);
                        //email.setVisibility(View.VISIBLE);
                        imgViewDownArrow.setTag("true");
                        imgViewDownArrow.setRotation(180);
                    }
                }
            });

            ImageView imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Preparing Paramaneters to pass in Async Thread
                    String url ="/deleteCandidate?candidateId="+candidateObject.getCandidateID();
                    //Async Runner
                    CandidateAdapter.AsyncTaskRunner runner = new CandidateAdapter.AsyncTaskRunner();
                    runner.execute(url);
                    //Toast.makeText(ctx, "Poll Deleted Succesfully"+ candidateObject.getPollId(), Toast.LENGTH_SHORT).show();
                }
            });
            ImageView imageViewEdit = (ImageView) convertView.findViewById(R.id.imageViewEdit);
            imageViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, EditCandidateActivity.class);
                    intent.putExtra("candidateID", candidateObject.getCandidateID());
                    ctx.startActivity(intent);
                }
            });


        }
        return convertView;
    }

    /* Thread for Server Interation - Pass paramenter and URL */
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        @Override
        protected String doInBackground(String... params) {
            try {
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

            if (resp.equalsIgnoreCase("Unsuccessfull")) {
                Toast.makeText(ctx, "Login Unsuccessfull!!", Toast.LENGTH_LONG).show();
            } else if (resp.equalsIgnoreCase("Candidate Deleted")) {
                Toast.makeText(ctx, resp.toString(), Toast.LENGTH_LONG).show();
                CandidateAdapter.MyNextAsyncTask myNextAsyncTask = new CandidateAdapter.MyNextAsyncTask();
                myNextAsyncTask.execute("/displayCandidate?pollID="+ConfigurationFile.pollId);
            }
        }
    }

    //Update Candidate UI
    private class MyNextAsyncTask extends AsyncTask<String, String, String> {

        private String resp;
        @Override
        protected String doInBackground(String... params) {
            try {
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
            ArrayList<CandidateEntity> candidateObjects = new ArrayList<>();
            //Log.d("POLLS ", resp);
            String[] responsePolls = resp.split(lineSeperator);
            for(int i = 0; i< responsePolls.length; i++){
                Log.d("POLLS "+i, responsePolls[i]);
                String[] individualPollColumns = responsePolls[i].split(columentSeperator);
                //pollObjects.add(new CandidateEntity(Integer.parseInt(individualPollColumns[0]), individualPollColumns[1], "Start Date: "+individualPollColumns[2], "End Date: "+ individualPollColumns[3] ));
                candidateObjects.add(new CandidateEntity(Integer.parseInt(individualPollColumns[0]), "First Name : "+individualPollColumns[1], "Last Name : "+individualPollColumns[2],"Email : "+individualPollColumns[3]
                        ,"DOB : "+individualPollColumns[4], "Gender : "+individualPollColumns[5],"Department : "+individualPollColumns[6],"Qualities : "+individualPollColumns[7],"Interests : "+ individualPollColumns[8],
                        "Student Organizations : "+individualPollColumns[9],"Community Service Hours : "+individualPollColumns[10]));
            }

            refreshEvents(candidateObjects);

        }
    }
}