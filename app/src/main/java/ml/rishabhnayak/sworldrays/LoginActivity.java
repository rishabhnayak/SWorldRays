package ml.rishabhnayak.sworldrays;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import net.cachapa.expandablelayout.ExpandableLayout;
import java.util.Arrays;


import cn.pedant.SweetAlert.SweetAlertDialog;
public class LoginActivity extends AppCompatActivity {
    ImageView background;
    FloatingActionButton fab;
    EditText emailReg,passwordReg,nameReg,mobilenoReg,emailLogin,passwordLogin;
    String regEmail,regPassword,regName,regMobileno,loginEmail,loginPassword;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        backgroundImage();
        register();
        signinGoogle();
        signup();
        callbackManager = CallbackManager.Factory.create();
        login();
    }
//background........................................................................................
public void backgroundImage(){

    background = findViewById(R.id.background);
    background.setAlpha(1f);
    //   background.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    scaleView(background, (float) 1.5, 1);

    }
    public void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                1.5f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(2000);
        v.startAnimation(anim);
    }
//background........................................................................................



//register..........................................................................................

    public void register(){

        final ExpandableLayout expandableLayout = findViewById(R.id.expandable_layout);
        expandableLayout.setOrientation(1);

        fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_signup);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //Toast.makeText(LoginActivity.this, String.valueOf(expandableLayout.getOrientation()), Toast.LENGTH_SHORT).show();
                expandableLayout.toggle();
                if (expandableLayout.getState() == 1) {
                    fab.setImageResource(R.drawable.ic_signup);

                } else {
                    fab.setImageResource(R.drawable.ic_close_black_24dp);
                }
                // Toast.makeText(LoginActivity.this, String.valueOf(expandableLayout.getState()), Toast.LENGTH_SHORT).show();
            }
        });

    }
//register..........................................................................................

//google signin.....................................................................................
    public void signinGoogle() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "errror signin", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        findViewById(R.id.google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar(true);
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        // Pass the activity result back to the Facebook SDK
        try{   callbackManager.onActivityResult(requestCode,resultCode,data);}catch (Exception e){}
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                progressbar(false);
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {

            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential1 = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential1)
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        progressbar(false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressbar(false);
                        Toast.makeText(LoginActivity.this, "Auth failed google", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            progressbar(false);
                            updateUI(null);
                        }
                    }
                });
    }

//google signin.....................................................................................

//Facebook signin...................................................................................
    public void buttonClickLoginFb(View v){
        loginButton.performClick();
        progressbar(true);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handleFacebookToken(loginResult.getAccessToken());
                        progressbar(false);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        progressbar(false);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        progressbar(false);
                    }
                });
    }
    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential2 = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
//Facebook signin...................................................................................
//intent............................................................................................
    public void userInfo(){
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            Intent intent=new Intent(getApplicationContext(),LogoutActivity.class);
            intent.putExtra("userId",mAuth.getCurrentUser().getUid());
            startActivity(intent);
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user!=null){
            Intent intent=new Intent(getApplicationContext(),LogoutActivity.class);
            intent.putExtra("userId",user.getUid());
            startActivity(intent);
        }
    }
//intent............................................................................................
//progressbar.......................................................................................
    public void progressbar(Boolean decide){
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        if (decide==true){
            pDialog.show();
        }else {
            pDialog.hide();
        }
    }
//progressbar.......................................................................................

//normal register...................................................................................
    public void signup(){
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailReg=findViewById(R.id.emailReg);
                regEmail=emailReg.getText().toString();
                passwordReg=findViewById(R.id.passwordReg);
                regPassword=passwordReg.getText().toString();
                mobilenoReg=findViewById(R.id.mobilenoReg);
                regMobileno=mobilenoReg.getText().toString();
                nameReg=findViewById(R.id.nameReg);
                regName=nameReg.getText().toString();
                signup();
            }
        });
if (regEmail!=null&&regPassword!=null){

}

    }
//normal register...................................................................................
//normal login......................................................................................
public void login(){

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailLogin=findViewById(R.id.emailLogin);
                loginEmail=emailLogin.getText().toString();
                passwordLogin=findViewById(R.id.passwordLogin);
                loginPassword=passwordLogin.getText().toString();

            }
        });

}
//normal login......................................................................................


    @Override
    protected void onStart() {
        super.onStart();
        try {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfo();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
