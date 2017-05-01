package com.example.akshay.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akshay.myapplication.R;
import com.example.akshay.myapplication.dao.CandidateEntity;
import java.util.ArrayList;

/**
 * Created by Akshay on 4/13/2017.
 */

public class VoteScreenAdapter extends ArrayAdapter<CandidateEntity> {

    private LayoutInflater mInflater;
    private ArrayList<CandidateEntity> candidateData;
    private int mViewResourceId;
    private Context ctx;
    public static ArrayList<Integer> selectedCandidates = new ArrayList<>();

    public VoteScreenAdapter(Context context, int textViewResourceId, ArrayList<CandidateEntity> data) {
        super(context, textViewResourceId, data);
        ctx = context;
        candidateData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        CandidateEntity candidateObject = candidateData.get(position);
        final ImageView imgViewDownArrow = (ImageView) convertView.findViewById(R.id.down_arrow_image);
        final CheckBox chkSelectCandidate = (CheckBox) convertView.findViewById(R.id.checkBox1);

        final TextView Last_First_Candidate = (TextView) convertView.findViewById(R.id.last_first_name);
        final TextView gender_Candidate = (TextView) convertView.findViewById(R.id.gender_candidate);
        final TextView department_candidate = (TextView) convertView.findViewById(R.id.department_candidate);
        final TextView qualities_candidate = (TextView) convertView.findViewById(R.id.qualities_candidate);
        final TextView interest_candidate = (TextView) convertView.findViewById(R.id.interest_candidate);
        final TextView student_organization_candidate = (TextView) convertView.findViewById(R.id.student_organization_candidate);
        final TextView community_service_candidate = (TextView) convertView.findViewById(R.id.community_service_candidate);

        //Name
        if( Last_First_Candidate!=null && !candidateObject.getFirstName().isEmpty()){
            Last_First_Candidate.setText("Last, First Name : "+candidateObject.getLastName()+","+candidateObject.getFirstName());
        }
        // Gender
        if( gender_Candidate!=null && !candidateObject.getGender().isEmpty()){
            gender_Candidate.setText("Gender : "+candidateObject.getGender());
        }
        //department
        if(department_candidate!=null && !candidateObject.getDepartment().isEmpty()){
            department_candidate.setText("Department : "+candidateObject.getDepartment());
        }
        //qualities_candidate
        if(qualities_candidate!=null && !candidateObject.getQualities().isEmpty()){
            qualities_candidate.setText("Qualities : "+candidateObject.getQualities());
        }
        //interest_candidate
        if(interest_candidate!=null && !candidateObject.getInterests().isEmpty()){
            interest_candidate.setText("Interests : "+candidateObject.getInterests());
        }
        //student_organization_candidate
        if(student_organization_candidate!=null && !candidateObject.getStudentOrganization().isEmpty()){
            student_organization_candidate.setText("Student Org : "+candidateObject.getStudentOrganization());
        }
        //community_service_candidate
        if(community_service_candidate!=null && !candidateObject.getCommunityServiceHours().isEmpty()){
            community_service_candidate.setText("Community Service Hour : "+candidateObject.getCommunityServiceHours());
        }

        chkSelectCandidate.setTag(candidateObject.getCandidateID());
        chkSelectCandidate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println( buttonView.getTag()+"  = "+isChecked);
                if(isChecked && selectedCandidates.size()<2){
                    selectedCandidates.add(Integer.parseInt(buttonView.getTag().toString()));
                }else{
                    if(!isChecked && selectedCandidates.size()<2){
                        int pos = selectedCandidates.indexOf(Integer.parseInt(buttonView.getTag().toString()));
                        selectedCandidates.remove(pos);
                    }
                    if(selectedCandidates.size()==2){
                        int pos = selectedCandidates.indexOf(Integer.parseInt(buttonView.getTag().toString()));
                        if(buttonView.isChecked() && pos ==-1){
                            buttonView.toggle();
                            Toast.makeText(ctx, "Max 2 Candidates can be Voted.",Toast.LENGTH_SHORT).show();
                        }
                        else if(!buttonView.isChecked() && pos !=-1){
                            selectedCandidates.remove(pos);
                        }
                    }
                }
                System.out.println( "selectedCandidates  = "+selectedCandidates);
            }
        });

        imgViewDownArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgViewDownArrow.getTag()!= null && imgViewDownArrow.getTag().equals("true")){
                    department_candidate.setVisibility(View.GONE);
                    qualities_candidate.setVisibility(View.GONE);
                    interest_candidate.setVisibility(View.GONE);
                    student_organization_candidate.setVisibility(View.GONE);
                    community_service_candidate.setVisibility(View.GONE);
                    imgViewDownArrow.setTag("false");
                    imgViewDownArrow.setRotation(0);
                }else{
                    department_candidate.setVisibility(View.VISIBLE);
                    qualities_candidate.setVisibility(View.VISIBLE);
                    interest_candidate.setVisibility(View.VISIBLE);
                    student_organization_candidate.setVisibility(View.VISIBLE);
                    community_service_candidate.setVisibility(View.VISIBLE);
                    imgViewDownArrow.setTag("true");
                    imgViewDownArrow.setRotation(180);
                }
            }
        });
        return convertView;
    }
}



