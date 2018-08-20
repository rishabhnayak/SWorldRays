package ml.rishabhnayak.sworldrays;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import ml.rishabhnayak.sworldrays.RegistrationActivity.*;
import java.util.Calendar;


public class RegistrationActivity extends AppCompatActivity{

    Calendar myCalendar = Calendar.getInstance();
    static int mYear,mMonth,mDay, mHour, mMinute;
    long  jstart_date_millis;
    EditText edittext;
    CardView student,trainer;
    TextView student_text,trainer_text;
    boolean selector=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        actionBar();
        selector();
        LinearLayout edittext=  findViewById(R.id.dob_button);
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datePicker();
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

    

}