package ml.rishabhnayak.sworldrays;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HashKeyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_key);
//        // Add code to print out the key hash
//    try {
//        new SweetAlertDialog(this)
//                .setContentText("It's pretty, isn't it?")
//                .show();
//    }catch (Exception e){
//
//    }

        EditText ed;
        ed=findViewById(R.id.test);
        String s=ed.getText().toString();
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();



        try {
           PackageInfo info = getPackageManager().getPackageInfo("ml.rishabhnayak.sworldrays", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
                System.out.println("ihi haray................"+something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
}
