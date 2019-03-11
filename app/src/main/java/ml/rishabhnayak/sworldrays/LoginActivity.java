package ml.rishabhnayak.sworldrays;


import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;

import android.net.Uri;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import net.cachapa.expandablelayout.ExpandableLayout;


import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import ml.rishabhnayak.sworldrays.ForceUpdate.ForceUpdateChecker;
import ml.rishabhnayak.sworldrays.POJO.Register;


public class LoginActivity extends AppCompatActivity implements ForceUpdateChecker.OnUpdateNeededListener {
    ExpandableLayout homeTution;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.cover_a, R.drawable.web, R.drawable.cover_3, R.drawable.cover_4, R.drawable.cover_5};
    CarouselView carouselView_homeTution;
    int[] sampleImages_homeTution = {R.drawable.h_banner1, R.drawable.h_banner2, R.drawable.h_banner3};
    CardView student,trainer;
    TextView student_text,trainer_text;
    EditText emailRays,passwordRays;
    Button loginRays;
    String email,password;
    SweetAlertDialog pDialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean selector=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        carouselView();
        selector();
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.skip_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LauncherActivity.class);
                startActivity(intent);
            }
        });


        homeTutionButton();

        loginIdSetup();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        String autoselect=pref.getString("autoselect", null);
try{

    switch (autoselect){
        case "0":{
            String name=pref.getString("name",null);
            String mobile=pref.getString("mobile",null);
            String email=pref.getString("email",null);
            Intent intent=new Intent(getApplicationContext(),LauncherActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("email",email);
            intent.putExtra("mobile",mobile);
            startActivity(intent);
        }
        break;
        case "1":{

        }
        break;
        case "2":{

        }
        break;
        default:{

        }
    }
}catch (Exception e){

}


findViewById(R.id.login_button_hometution).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (selector==true){
            Toast.makeText(LoginActivity.this, "student", Toast.LENGTH_SHORT).show();
            volley("http://newsmania.ml/test.html");
        }
        else {
            Toast.makeText(LoginActivity.this, "tutor", Toast.LENGTH_SHORT).show();
            volley("http://newsmania.ml/test.html");
        }
    }
});
findViewById(R.id.sworld_home_tution_web_button).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent httpIntent = new Intent(Intent.ACTION_VIEW);
        httpIntent.setData(Uri.parse("http://sworldhometuition.com/"));

        startActivity(httpIntent);
    }
});
    }



    public void carouselView(){
        carouselView = (CarouselView) findViewById(R.id.carouselView);

        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
          //      Toast.makeText(getApplicationContext(), "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
            }
        });
        carouselView_homeTution = (CarouselView) findViewById(R.id.carouselView_hometution);
        carouselView_homeTution.setPageCount(sampleImages_homeTution.length);
        carouselView_homeTution.setImageListener(imageListener_homeTution);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    ImageListener imageListener_homeTution = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages_homeTution[position]);
        }
    };

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

   public void homeTutionButton(){


    //   Toast.makeText(this, String.valueOf(homeTution.getState()), Toast.LENGTH_SHORT).show();
       homeTution=findViewById(R.id.homeTution);
     findViewById(R.id.sworld_home_tution_login_button).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             homeTution.toggle();

             findViewById(R.id.sworld_home_tution_login_button).setEnabled(false);
             findViewById(R.id.sworld_rays_login_button).setEnabled(false);
             try {
                 Handler handler=new Handler();
                 handler.postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         findViewById(R.id.sworld_home_tution_login_button).setEnabled(true);
                         findViewById(R.id.sworld_rays_login_button).setEnabled(true);
                         findViewById(R.id.sworld_rays).setVisibility(View.GONE);
                     }
                 },600);
             }catch (Exception e){

             }
         }
     });
     findViewById(R.id.sworld_rays_login_button).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             homeTution.toggle();
             findViewById(R.id.sworld_home_tution_login_button).setEnabled(false);
             findViewById(R.id.sworld_rays_login_button).setEnabled(false);
             try {
                 Handler handler=new Handler();
                 handler.postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         findViewById(R.id.sworld_home_tution_login_button).setEnabled(true);
                         findViewById(R.id.sworld_rays_login_button).setEnabled(true);
                     }
                 },600);
                     findViewById(R.id.sworld_rays).setVisibility(View.VISIBLE);
             }catch (Exception e){

             }
         }
     });
   }

    @Override
    public void onUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update app to new version to continue.")
                .setCancelable(false)
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(updateUrl);
                            }
                        }).create();
        dialog.show();
    }
    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();
        super.onResume();

    }




    public void volley(String url){

        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("yhi hai response....."+response);
                        Gson gson=new Gson();
                        Register response1=gson.fromJson(response, Register.class);
                        String detail=response1.getSuccess();
                        switch (detail){
                            case "success":
                                //Toast.makeText(getApplicationContext(), "Sent Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),LauncherActivity.class));
                                editor.putString("autoselect",response1.getCode());
                                editor.putString("email",response1.getEmail());
                                editor.putString("mobile",response1.getMobile());
                                editor.putString("name",response1.getName());
                                editor.commit();

                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Not sent please retry", Toast.LENGTH_SHORT).show();
                        }
                        loading("no");



                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("volley error"+error);
                        loading("no");
                        Toast.makeText(LoginActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map=new HashMap<>();
                map.put("uname",email);
                map.put("pwd",password);
                return map;
            }
        };

        queue.add(postRequest);
    }

//login rays idSetup
    public void loginIdSetup(){
        emailRays=findViewById(R.id.email_rays);
        passwordRays=findViewById(R.id.password_rays);
        loginRays=findViewById(R.id.login_rays);

        loginRays.setOnClickListener(
                 new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailRays.getText().toString();
                 password=passwordRays.getText().toString();
                if (email.length()==0){
                    emailRays.setError("Please Enter Email");
                }
               else if (password.length()==0){
                    passwordRays.setError("Please Enter Password");
                }
                else if (password.length()>0&&email.length()>0){
                  loading("yes");
                          volley("http://raysitworld.com/RaysAppController/login");
                }
            }
        });
    }
public void loading(String show){
        if (show=="yes"){
            findViewById(R.id.sworld_rays).setVisibility(View.GONE);
            findViewById(R.id.homeTution).setVisibility(View.GONE);
            findViewById(R.id.progress).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.progress).setVisibility(View.GONE);
            findViewById(R.id.sworld_rays).setVisibility(View.VISIBLE);
            findViewById(R.id.homeTution).setVisibility(View.VISIBLE);
        }
}
}
