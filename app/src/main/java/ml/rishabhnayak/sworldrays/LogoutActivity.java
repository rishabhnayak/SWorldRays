package ml.rishabhnayak.sworldrays;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LogoutActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseFirestore db;
    @Override
    public void onBackPressed() {
        exitPopup();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
       // String userId=getIntent().getStringExtra("userId");
    //    Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
      try {
          user = FirebaseAuth.getInstance().getCurrentUser();
          user.getDisplayName();
         String s= user.getEmail();
          System.out.println("ihi haray.........."+user.getEmail());
          DocumentReference docRef  =  db.collection("users").document(s);
          docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  if (task.isSuccessful()) {
                      DocumentSnapshot document = task.getResult();
                      if (document.exists()) {
                          System.out.println(document.getData());
                          ((TextView)findViewById(R.id.data)).setText(String.valueOf(document.getData()));
                      } else {

                      }
                  } else {

                  }
              }
          });




          Toast.makeText(this, String.valueOf(user.getDisplayName()), Toast.LENGTH_SHORT).show();
      }catch (Exception e){}
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                user.delete();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void exitPopup(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to exit this app?")
                .setCancelText("No,cancel")
                .setConfirmText("Yes,exit it!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                     //   Toast.makeText(LogoutActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }

                })
                .show();
    }
}
