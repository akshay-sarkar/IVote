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
import com.example.akshay.myapplication.CandidateManagementActivity;
import com.example.akshay.myapplication.PollManagementActivity;
import com.example.akshay.myapplication.R;
import com.example.akshay.myapplication.VoteScreenActivity;
import com.example.akshay.myapplication.configuration.ConfigurationFile;
import com.example.akshay.myapplication.dao.PollEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PollAdapter extends ArrayAdapter<PollEntity> {

    private LayoutInflater mInflater;
    private ArrayList<PollEntity> pollData;
    private int mViewResourceId;
    private Context ctx;
    private final String base_url = ConfigurationFile.base_url;
    HttpURLConnection connection;
    String lineSeperator="#&#";
    String columentSeperator = "@&@";

    public PollAdapter(Context context, int textViewResourceId, ArrayList<PollEntity> bagData) {
        super(context, textViewResourceId, bagData);
        ctx = context;
        pollData = bagData;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public void refreshEvents(ArrayList<PollEntity> bagData) {
        this.pollData.clear();
        this.pollData.addAll(bagData);
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        final PollEntity pollObject = pollData.get(position);
        //final TextView pollId = (TextView) convertView.findViewById(R.id.textLabelPollID);
        TextView pollName = (TextView) convertView.findViewById(R.id.textLabelPollName);
        TextView pollStartDate = (TextView) convertView.findViewById(R.id.textViewStartDate);
        TextView pollEndDate = (TextView) convertView.findViewById(R.id.textViewEndDate);
        ToggleButton toggleButtonActivatePoll = (ToggleButton) convertView.findViewById(R.id.toggleButtonActivatePoll);

        if (pollObject != null) {
            toggleButtonActivatePoll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        if(PollManagementActivity.numberOfPollActive < 1){ //Flag for count not more than poll to be active.
                            ++PollManagementActivity.numberOfPollActive;
                            Toast.makeText(ctx, "Poll Activated", Toast.LENGTH_SHORT).show();
                        }else{
                            buttonView.setChecked(false);
                            Toast.makeText(ctx, "Only 1 Poll could be Active.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ctx, "Poll Deactivated", Toast.LENGTH_SHORT).show();
                        PollManagementActivity.numberOfPollActive = PollManagementActivity.numberOfPollActive -1;
                    }
                }
            });
            if (pollName != null) {
                pollName.setText(pollObject.getPollName());
            }
            if (pollStartDate != null) {
                pollStartDate.setText(pollObject.getPollStartDate());
            }
            if (pollEndDate != null) {
                pollEndDate.setText(pollObject.getPollEndDate());
            }

            // Listener for the icons
            ImageView imageViewNotification = (ImageView) convertView.findViewById(R.id.imageViewNotification);
            imageViewNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Preparing Paramaneters to pass in Async Thread
                    String url ="/notifyEndDate?pollId="+pollObject.getPollId();
                    //Async Runner
                    PollAdapter.AsyncTaskRunner runner = new PollAdapter.AsyncTaskRunner();
                    runner.execute(url);
                    //Toast.makeText(ctx, "Poll Reminder Sent Successfully"+ pollObject.getPollId(), Toast.LENGTH_SHORT).show();
                }
            });

            ImageView imageViewPublishResult = (ImageView) convertView.findViewById(R.id.imageViewPublish);
            imageViewPublishResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Preparing Paramaneters to pass in Async Thread
                    String url ="/notifyResult?pollId="+pollObject.getPollId();
                    //Async Runner
                    PollAdapter.AsyncTaskRunner runner = new PollAdapter.AsyncTaskRunner();
                    runner.execute(url);
                    //Toast.makeText(ctx, "Poll Result Notified Successfully"+ pollObject.getPollId(), Toast.LENGTH_SHORT).show();
                }
            });

            ImageView imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Preparing Paramaneters to pass in Async Thread
                    String url ="/deletePoll?pollId="+pollObject.getPollId();
                    //Async Runner
                    PollAdapter.AsyncTaskRunner runner = new PollAdapter.AsyncTaskRunner();
                    runner.execute(url);
                    //Toast.makeText(ctx, "Poll Deleted Succesfully"+ pollObject.getPollId(), Toast.LENGTH_SHORT).show();
                }
            });


            TextView textViewPollName = (TextView) convertView.findViewById(R.id.textLabelPollName);
            textViewPollName.setClickable(true);
            textViewPollName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ctx, "Poll Named Clicked - "+ pollObject.getPollId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ctx, CandidateManagementActivity.class);
                    intent.putExtra("DATA", pollObject.getPollId());
                    intent.putExtra("POLL_NAME", pollObject.getPollName());
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
            } else if (resp.equalsIgnoreCase("Poll Reminder Sent") ||
                          resp.equalsIgnoreCase("Result Sent") ||
                            resp.equalsIgnoreCase("Poll Deleted")) {
                Toast.makeText(ctx, resp.toString(), Toast.LENGTH_LONG).show();
                if(resp.equalsIgnoreCase("Poll Deleted")){
                    //displayPollReTrigger();
                    //refreshEvents();
                    PollAdapter.MyNextAsyncTask myNextAsyncTask = new PollAdapter.MyNextAsyncTask();
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
            ArrayList<PollEntity> pollObjects = new ArrayList<>();
//                pollObjects.add(new PollEntity("UTA Ambassador President", "Start Date: 02/04/2017", "End Date: 02/06/2017" ));
//                pollObjects.add(new PollEntity("UTA Mascot Men", "Start Date: "+"02/07/2017", "End Date: "+"02/09/2017" ));
//                pollObjects.add(new PollEntity("UTA Mascot Women", "Start Date: "+"02/11/2017", "End Date: "+"02/13/2017" ));
//                pollObjects.add(new PollEntity("UTA CS Nerd", "Start Date: "+"02/17/2017", "End Date: "+"02/19/2017" ));
//                pollObjects.add(new PollEntity("UTA Ambassador VC", "Start Date: "+"02/21/2017", "End Date: "+"02/23/2017" ));

            //Log.d("POLLS ", resp);
            String[] responsePolls = resp.split(lineSeperator);
            for(int i = 0; i< responsePolls.length; i++){
                Log.d("POLLS "+i, responsePolls[i]);
                String[] individualPollColumns = responsePolls[i].split(columentSeperator);
                pollObjects.add(new PollEntity(Integer.parseInt(individualPollColumns[0]), individualPollColumns[1], "Start Date: "+individualPollColumns[2], "End Date: "+ individualPollColumns[3] ));
            }
            refreshEvents(pollObjects);
        }
    }
}
