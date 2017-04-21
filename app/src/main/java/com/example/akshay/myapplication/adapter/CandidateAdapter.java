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

        final CandidateEntity pollObject = pollData.get(position);

        TextView fname = (TextView) convertView.findViewById(R.id.txtFname);
        TextView lname = (TextView) convertView.findViewById(R.id.txtLname);
        TextView email = (TextView) convertView.findViewById(R.id.txtEmail);

        if (pollObject != null) {

            if (fname != null) {
                fname.setText(pollObject.getFirstName());
            }
            if (lname != null) {
                lname.setText(pollObject.getLastName());
            }
            if (email != null) {
                email.setText(pollObject.getUTAEmailID());
            }

            // Listener for the icons


            ImageView imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Preparing Paramaneters to pass in Async Thread
                    String url ="/deletePoll?pollId="+pollObject.getCandidateID();
                    //Async Runner
                    CandidateAdapter.AsyncTaskRunner runner = new CandidateAdapter.AsyncTaskRunner();
                    runner.execute(url);
                    //Toast.makeText(ctx, "Poll Deleted Succesfully"+ pollObject.getPollId(), Toast.LENGTH_SHORT).show();
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
            } else if (resp.equalsIgnoreCase("Poll Reminder Sent") ||
                    resp.equalsIgnoreCase("Result Sent") ||
                    resp.equalsIgnoreCase("Poll Deleted")) {
                Toast.makeText(ctx, resp.toString(), Toast.LENGTH_LONG).show();
                if(resp.equalsIgnoreCase("Poll Deleted")){
                    //displayPollReTrigger();
                    //refreshEvents();
                    CandidateAdapter.MyNextAsyncTask myNextAsyncTask = new CandidateAdapter.MyNextAsyncTask();
                    myNextAsyncTask.execute("/displayPoll");
                }
            }
            /* Only to be allowed at success case */
            //Intent intent = new Intent(context, PollManagementActivity.class);
            //startActivity(intent);
        }
    }

    //Update Poll UI
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
            ArrayList<CandidateEntity> pollObjects = new ArrayList<>();
//                pollObjects.add(new PollEntity("UTA Ambassador President", "Start Date: 02/04/2017", "End Date: 02/06/2017" ));
//                pollObjects.add(new PollEntity("UTA Mascot Men", "Start Date: "+"02/07/2017", "End Date: "+"02/09/2017" ));
//                pollObjects.add(new PollEntity("UTA Mascot Women", "Start Date: "+"02/11/2017", "End Date: "+"02/13/2017" ));
//                pollObjects.add(new PollEntity("UTA CS Nerd", "Start Date: "+"02/17/2017", "End Date: "+"02/19/2017" ));
//                pollObjects.add(new PollEntity("UTA Ambassador VC", "Start Date: "+"02/21/2017", "End Date: "+"02/23/2017" ));

            //Log.d("POLLS ", resp);
            String[] responsePolls = resp.split(lineSeperator);
           /* for(int i = 0; i< responsePolls.length; i++){
                Log.d("POLLS "+i, responsePolls[i]);
                String[] individualPollColumns = responsePolls[i].split(columentSeperator);
                pollObjects.add(new CandidateEntity(Integer.parseInt(individualPollColumns[0]), individualPollColumns[1], "Start Date: "+individualPollColumns[2], "End Date: "+ individualPollColumns[3] ));
            }*/
            pollObjects.add(new CandidateEntity(1, "akshay","sarkar", "aa@a.c","12.12.12", "Male", "CSE", "captain",
                    "tech", "NA","4"));
            pollObjects.add(new CandidateEntity(2, "shayam","gopal", "xyz@a.c","12.12.12", "Male", "CSE", "captain",
                    "tech", "NA","4"));
                    refreshEvents(pollObjects);
        }
    }
}
