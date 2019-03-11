package ml.rishabhnayak.sworldrays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class EnquiryActivity extends AppCompatActivity {
    EditText nameE, emailE, subjectE, messageE,mobileE;
    String name, email, subject, message,mobile;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        subject = getIntent().getStringExtra("subject");
//        Toast.makeText(this, subject, Toast.LENGTH_SHORT).show();

        back();
        setId();
        getValue();
        submit.setOnClickListener(v -> {
//            Toast.makeText(EnquiryActivity.this, "success", Toast.LENGTH_SHORT).show();
            getValue();
            if (name.length()==0){
                nameE.setError("Enter name");
            }
            else if (email.length()==0){
                emailE.setError("Enter Email");
            }
            else if (mobile.length()!=10){
                mobileE.setError("Enter 10 digit mobile no.");
            }else {
                volley("http://raysitworld.com/RaysAppController/enquiry");
                loading("yes");
            }

        });

        try{
            name=getIntent().getStringExtra("name");
            nameE.setText(name);
            email=getIntent().getStringExtra("email");
            emailE.setText(email);
            mobile=getIntent().getStringExtra("mobile");
            mobileE.setText(mobile);
        }catch (Exception e){

        }
    }

    public void setId() {
        nameE = findViewById(R.id.name);
        emailE = findViewById(R.id.email);
        mobileE=findViewById(R.id.mobile_no1);
        subjectE = findViewById(R.id.subject);
        messageE = findViewById(R.id.message);
        submit = findViewById(R.id.submit);

    }

    public void getValue() {
        name = nameE.getText().toString();
        email = emailE.getText().toString();
        mobile=mobileE.getText().toString();
        subjectE.setText(subject);
        message = messageE.getText().toString();
    }

    public void back() {
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnquiryActivity.super.onBackPressed();
            }
        });
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
                map.put("name", name);
                map.put("email",email);
                map.put("mob",mobile);
                map.put("subject",subject);
                map.put("msg",message);
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
