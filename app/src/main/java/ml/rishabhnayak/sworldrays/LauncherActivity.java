package ml.rishabhnayak.sworldrays;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


import net.cachapa.expandablelayout.ExpandableLayout;




public class LauncherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    ImageView wed_image,app_image,digital_image,soft_image,training_image;
    ExpandableLayout appbar,image,text,appbar1,image1,text1,appbar2,image2,text2,appbar3,image3,text3,appbar4,image4,text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

       drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        cellToggle();
        drawerToggle();



        blurImage();
        toggleall();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void drawerToggle(){
       findViewById(R.id.toggle).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               drawer.openDrawer(Gravity.LEFT,true);
           }
       });
    }
    public Bitmap blur(Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(16);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }
    public void toggleall(){

        appbar=findViewById(R.id.appbar_expandable);
        image=findViewById(R.id.image_extanded);
        text=findViewById(R.id.text_extended);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar.toggle();
                image.toggle();
                text.toggle();
            }
        });
        appbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar.toggle();
                image.toggle();
                text.toggle();
            }
        });

        appbar1=findViewById(R.id.appbar_expandable1);
        image1=findViewById(R.id.image_extanded1);
        text1=findViewById(R.id.text_extended1);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar1.toggle();
                image1.toggle();
                text1.toggle();
            }
        });
        appbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar1.toggle();
                image1.toggle();
                text1.toggle();
            }
        });

        appbar2=findViewById(R.id.appbar_expandable2);
        image2=findViewById(R.id.image_extanded2);
        text2=findViewById(R.id.text_extended2);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar2.toggle();
                image2.toggle();
                text2.toggle();
            }
        });
        appbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar2.toggle();
                image2.toggle();
                text2.toggle();
            }
        });

        appbar3=findViewById(R.id.appbar_expandable3);
        image3=findViewById(R.id.image_extanded3);
        text3=findViewById(R.id.text_extended3);
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar3.toggle();
                image3.toggle();
                text3.toggle();
            }
        });
        appbar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar3.toggle();
                image3.toggle();
                text3.toggle();
            }
        });

        appbar4=findViewById(R.id.appbar_expandable4);
        image4=findViewById(R.id.image_extanded4);
        text4=findViewById(R.id.text_extended4);
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar4.toggle();
                image4.toggle();
                text4.toggle();
            }
        });
        appbar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar4.toggle();
                image4.toggle();
                text4.toggle();
            }
        });




    }
    public void blurImage(){
        training_image = findViewById(R.id.training_image);
        BitmapDrawable drawable = (BitmapDrawable) training_image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap blurredBitmap = blur(bitmap);
        training_image.setImageBitmap(blurredBitmap);

        app_image= findViewById(R.id.training_image1);
        BitmapDrawable drawable1 = (BitmapDrawable) app_image.getDrawable();
        Bitmap bitmap1 = drawable1.getBitmap();
        Bitmap blurredBitmap1 = blur(bitmap1);
        app_image.setImageBitmap(blurredBitmap1);

        soft_image = findViewById(R.id.training_image2);
        BitmapDrawable drawable2 = (BitmapDrawable) soft_image.getDrawable();
        Bitmap bitmap2 = drawable2.getBitmap();
        Bitmap blurredBitmap2 = blur(bitmap2);
        soft_image.setImageBitmap(blurredBitmap2);

        wed_image = findViewById(R.id.training_image3);
        BitmapDrawable drawable3 = (BitmapDrawable) wed_image.getDrawable();
        Bitmap bitmap3 = drawable3.getBitmap();
        Bitmap blurredBitmap3 = blur(bitmap3);
        wed_image.setImageBitmap(blurredBitmap3);

        digital_image = findViewById(R.id.training_image4);
        BitmapDrawable drawable4 = (BitmapDrawable) digital_image.getDrawable();
        Bitmap bitmap4 = drawable4.getBitmap();
        Bitmap blurredBitmap4 = blur(bitmap4);
        digital_image.setImageBitmap(blurredBitmap4);
    }

    public void register(View view) {
        Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
        startActivity(intent);
    }
}
