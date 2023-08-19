 package com.example.county;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.county.ui.SaveState;

import java.util.Objects;

 public class MainActivity extends AppCompatActivity {

     Button btnAdd, btnReset, btnInfo;
     TextView txtNum;
     private int counter = 0;
     private Switch switchTheme;
     SaveState saveState;
     Dialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saveState = new SaveState(this);
        if(saveState.getState() == true) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_main);

        dialog = new Dialog(this);

        btnInfo = findViewById(R.id.btnInfo);
        btnAdd = findViewById(R.id.btnAdd);
        btnReset = findViewById(R.id.btnReset);
        txtNum = findViewById(R.id.txtNum);
        switchTheme = findViewById(R.id.switchTheme);

        if(saveState.getState() == true){
            switchTheme.setChecked(true);
        }

        loadData();

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog();
            }
        });


        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    saveState.setState(true);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else {
                    saveState.setState(false);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

     private void infoDialog() {
        dialog.setContentView(R.layout.info_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         ImageView imageViewClose = dialog.findViewById(R.id.imageViewClose);
         ImageView twitterClick = dialog.findViewById(R.id.twitterClick);
         ImageView youtube = dialog.findViewById(R.id.youtube);
         ImageView podcast = dialog.findViewById(R.id.podcast);
         imageViewClose.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialog.dismiss();
             }
         });
         twitterClick.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 gotoUrl("https://twitter.com/yasht05");
             }
         });
         youtube.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 gotoUrl("https://www.youtube.com/channel/UCkKu54gnf5TsUPhA-KlD98Q");
             }
         });
         podcast.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 gotoUrl("https://anchor.fm/creative-bros");
             }
         });
         dialog.show();
     }

     private void gotoUrl(String s) {
         Uri uri = Uri.parse(s);
         startActivity(new Intent(Intent.ACTION_VIEW,uri));
     }

     public void btn_action(View view) {
        counter++;
        txtNum.setText(String.valueOf(counter));
     }

     public void saveData()
     {
        SharedPreferences sharedPreferences = getSharedPreferences("saveCounter",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("counterValue",counter);
        editor.apply();
     }

     public void loadData()
     {
         SharedPreferences sharedPreferences = getSharedPreferences("saveCounter",MODE_PRIVATE);
         counter = sharedPreferences.getInt("counterValue",MODE_PRIVATE);
         txtNum.setText(String.valueOf(counter));
     }

     @Override
     protected void onPause() {
         super.onPause();
         saveData();
     }

     public void btn_reset(View view) {
        counter = 0;
        txtNum.setText(String.valueOf(counter));
     }

     public void btn_sub(View view) {
        if(counter != 0)
        {
            counter--;
            txtNum.setText(String.valueOf(counter));
        }
     }
 }