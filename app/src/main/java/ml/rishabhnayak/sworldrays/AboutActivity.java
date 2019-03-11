package ml.rishabhnayak.sworldrays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_about);
//        back();
//    }
//    public void back() {
//        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AboutActivity.super.onBackPressed();
//            }
//        });
//    }
//}
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    simulateDayNight(/* DAY */ 0);
//    Element adsElement = new Element();
//    adsElement.setTitle("Advertise with us");

    View aboutPage = new AboutPage(this)
            .isRTL(false)
            .setDescription(getString(R.string.aboutus))
            .setImage(R.drawable.rayslogo)
//            .addItem(new Element().setTitle("Version 1.0.0"))
//            .addItem(adsElement)
            .addGroup("Connect with us")
            .addEmail("info@raysitworld.com")
            .addWebsite("http://raysitworld.com")
            .addFacebook("https://www.facebook.com/raysitworld/")
//            .addTwitter("medyo80")
//            .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
//            .addPlayStore("com.ideashower.readitlater.pro")
            .addInstagram("https://www.instagram.com/s_world_rays/")
//            .addGitHub("medyo")
            .addItem(getCopyRightsElement())
            .create();

    setContentView(aboutPage);
}


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format("THINK IT THINK US", Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
//        copyRightsElement.setIconDrawable(R.drawable.ic_launcher);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}