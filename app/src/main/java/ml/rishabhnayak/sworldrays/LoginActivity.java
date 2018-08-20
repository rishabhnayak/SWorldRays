package ml.rishabhnayak.sworldrays;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import net.cachapa.expandablelayout.ExpandableLayout;

import ml.rishabhnayak.sworldrays.ForceUpdate.ForceUpdateChecker;


public class LoginActivity extends AppCompatActivity implements ForceUpdateChecker.OnUpdateNeededListener {
    ExpandableLayout homeTution;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.cover_a, R.drawable.cover_2, R.drawable.cover_3, R.drawable.cover_4, R.drawable.cover_5};
    CarouselView carouselView_homeTution;
    int[] sampleImages_homeTution = {R.drawable.h_banner1, R.drawable.h_banner2, R.drawable.h_banner3};
    CardView student,trainer;
    TextView student_text,trainer_text;
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

}
