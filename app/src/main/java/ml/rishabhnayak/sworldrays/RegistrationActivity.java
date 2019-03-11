package ml.rishabhnayak.sworldrays;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.myhexaville.smartimagepicker.ImagePicker;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class RegistrationActivity extends AppCompatActivity{

    Calendar myCalendar = Calendar.getInstance();
    static int mYear,mMonth,mDay, mHour, mMinute;
    long  jstart_date_millis;
    EditText firstName,middleName,lastname,permanentAddress, temporaryAddress,email,stdCode,mobileNo,profession,education,college,company;
    String firstNameS,middleNameS,lastnameS,permanentAddressS, temporaryAddressS,emailS,stdCodeS,mobileNoS,professionS,educationS,collegeS,companyS,dobS;
    CheckBox sameAddress;
    CardView student,trainer;
    TextView student_text,trainer_text,dob;
    boolean selector=true;
    Bitmap bitmap;
    ImageView pickProfile;
    private ImageView imageView;
    private TextView textView;
    ImagePicker imagePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        actionBar();
        selector();
        try{
            emailS=getIntent().getStringExtra("email");
            email.setText(emailS);
            mobileNoS=getIntent().getStringExtra("mobile");
            mobileNo.setText(mobileNoS);
        }catch (Exception e){

        }

        LinearLayout edittext=  findViewById(R.id.dob_button);
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datePicker();
            }
        });

       setId();
       sameAddress=findViewById(R.id.same_address);
       sameAddress.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               //is chkIos checked?
               if (((CheckBox) v).isChecked()) {
                   permanentAddressS=permanentAddress.getText().toString();
                   temporaryAddress.setText(permanentAddressS);
               }

           }
       });

       findViewById(R.id.submit_form).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (firstNameS.length()==0){
                   firstName.setError("Enter First Name");
               }else if (lastnameS.length()==0){
                   lastname.setError("Enter Last Name");
               }
               else if (permanentAddressS.length()==0){
                   permanentAddress.setError("Enter Last Name");
               }
               else if (dobS.length()==0){
                   Toast.makeText(RegistrationActivity.this, "Please Enter Date of Birth", Toast.LENGTH_SHORT).show();
               }
               else if (mobileNoS.length()==0){
                   mobileNo.setError("Enter Last Name");
               }
               else if (professionS.length()==0){
                   profession.setError("Enter Last Name");
               }
               else if (educationS.length()==0){
                   education.setError("Enter Last Name");
               }else {
                   loading("yes");
                   volley("http://raysitworld.com/RaysAppController/register");
               }
               stringValues();

           }
       });

    }

    private void datePicker(){

        // Get Current Date


        final android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(this,
                new android.app.DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//                        departAt.setText(dayOfMonth + " " + month[monthOfYear] + " " + String.valueOf(year).substring(2));
                        ((TextView)findViewById(R.id.dob)).setText(String.valueOf(dayOfMonth)+"-"+String.valueOf(monthOfYear+1)+"-"+String.valueOf(year));
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.YEAR, year);
                        jstart_date_millis=cal.getTimeInMillis();
                        //*************Call Time Picker Here ********************

                    }
                }, mYear, mMonth, mDay);



           datePickerDialog.getDatePicker().setMinDate(jstart_date_millis);
        //   datePickerDialog.getDatePicker().setMaxDate(jstart_date_millis+5*24*60*60*1000);
        datePickerDialog.show();

    }
    public void actionBar(){
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrationActivity.super.onBackPressed();
            }
        });
    }
    public void selector(){
        student=findViewById(R.id.student);
        trainer=findViewById(R.id.trainer);
        student_text=findViewById(R.id.student_text);
        trainer_text=findViewById(R.id.trainer_text);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selector=true;
                student.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                trainer.setCardBackgroundColor(getResources().getColor(R.color.gray));
                student_text.setTextColor(getResources().getColor(R.color.white));
                trainer_text.setTextColor(getResources().getColor(R.color.darkgray));
            }
        });
        trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selector=false;
                student.setCardBackgroundColor(getResources().getColor(R.color.gray));
                trainer.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                student_text.setTextColor(getResources().getColor(R.color.darkgray));
                trainer_text.setTextColor(getResources().getColor(R.color.white));
            }
        });
    }

    public void setId(){
         firstName=findViewById(R.id.first_name);
         middleName=findViewById(R.id.middle_name);
        lastname=findViewById(R.id.last_name);
        permanentAddress=findViewById(R.id.permanent_address);
        temporaryAddress=findViewById(R.id.temporary_address);
        email=findViewById(R.id.email);
        stdCode=findViewById(R.id.std_code);
        mobileNo=findViewById(R.id.mobile_no_);
        profession=findViewById(R.id.profession);
        education=findViewById(R.id.education);
        college=findViewById(R.id.college);
        company=findViewById(R.id.company);
        pickProfile=findViewById(R.id.pick_profile);
        dob=findViewById(R.id.dob);


    }

    public void stringValues(){
        firstNameS=firstName.getText().toString();
        middleNameS=middleName.getText().toString();
        lastnameS=lastname.getText().toString();
        permanentAddressS=permanentAddress.getText().toString();
        temporaryAddressS=temporaryAddress.getText().toString();
        emailS=email.getText().toString();
        stdCodeS=stdCode.getText().toString();
        mobileNoS=mobileNo.getText().toString();
        professionS=profession.getText().toString();
        educationS=education.getText().toString();
        collegeS=college.getText().toString();
        companyS=company.getText().toString();
        dobS=dob.getText().toString();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

    public void pickImage(View view) {
        imagePicker = new ImagePicker(this /*activity non null*/,
                null /*fragment nullable*/,
                imageUri -> { /*on image picked*/
                    Toast.makeText(this, "image picked", Toast.LENGTH_SHORT).show();
                    Glide.with(getApplicationContext())
                            .load(imageUri)
                            .into(pickProfile);
                });
        imagePicker.choosePicture(true);
    }

    public void volley(String url) {

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    System.out.println("yhi hai response....." + response);
                    Gson gson=new Gson();
                    ml.rishabhnayak.sworldrays.POJO.Response j=gson.fromJson(response, ml.rishabhnayak.sworldrays.POJO.Response.class);
                    System.out.println("ihi haray......................."+j.getSuccess());
                    String detail=j.getSuccess();
                    switch (detail){
                        case "success":
                            Toast.makeText(this, "Sent Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        default:
                            Toast.makeText(this, "Not sent please retry", Toast.LENGTH_SHORT).show();
                    }
                    loading("no");
                },
                error -> {
                    System.out.println("volley error" + error);
                    loading("no");
                    Toast.makeText(getApplicationContext(), String.valueOf(error), Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
//                fname
//                        mname
//                lname
//                        paddr
//                laddr
//                        email
//                stdcode
//                        dob
//                mob
//                        clg
//                sex
                map.put("fname", firstNameS);
                map.put("mname",middleNameS);
                map.put("lname",lastnameS);
                map.put("paddr",permanentAddressS);
                map.put("laddr",temporaryAddressS);
                map.put("email",emailS);
                map.put("stdcode",stdCodeS);
                map.put("dob",dobS);
                map.put("mob",mobileNoS);
                map.put("clg",collegeS);
                map.put("sex",String.valueOf(selector));
                return map;
            }
        };

        queue.add(postRequest);
    }
    public void loading(String show) {
        if (show == "yes") {
            findViewById(R.id.main_layout).setVisibility(View.GONE);
            findViewById(R.id.progress).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.progress).setVisibility(View.GONE);
            findViewById(R.id.main_layout).setVisibility(View.VISIBLE);
        }
    }
}