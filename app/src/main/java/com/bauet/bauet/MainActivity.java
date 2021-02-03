package com.bauet.bauet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.SQLException;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Cursor a = null;
    //Day Variable- (a)
    protected TextView today;
    protected  String day;
     private     BroadcastReceiver minuteUpdate;

    //CLass scedule Area
    protected TextView CLASS_NAME;
    protected TextView CLASS_TIME;
    protected TextView AM_PM;
    public String day_of_Sunday = "Sunday";
    public String day_of_Monday = "Monday";
    public String day_of_Tuesday = "Tuesday";
    public String day_of_Wednesday = "Wednesday";
    public String day_of_Thursday = "Thursday";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseLogic();






    }


     //Auto Update of Scedule
    public void startMinuteUpdate()
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        minuteUpdate = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {



                Toast.makeText(MainActivity.this, "Minute is Changing!", Toast.LENGTH_SHORT).show();
                DatabaseLogic();



            }
        };
        registerReceiver(minuteUpdate,intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMinuteUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(minuteUpdate);
    }

     //Retrive from Databas and Showing Logic
    protected  void DatabaseLogic()
    {
        //Day Mechanics-(a)
        TimePull set_get_time = new TimePull();
        day = set_get_time.DayPull();
        today = (TextView) findViewById(R.id.Today_is);
        today.setText(day);
        //Day Showing Mechanics Done-(a)

        TextView clock_text =(TextView) findViewById(R.id.class_time);
        TextView class_text =(TextView) findViewById(R.id.textView8);
        TextView am_pm_text =(TextView) findViewById(R.id.AM_PM_IT);


        DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        Toast.makeText(MainActivity.this, "Successfully Imported", Toast.LENGTH_SHORT).show();
        //String Pull from String XML

        Resources res = getResources();
      int wed_first_hour = 01;
      int wed_first_minute = 00;
      String wednesday_first_class = "06:00 AM";



        //Time Pull
        Calendar cal = Calendar.getInstance();
        String cur_Hour = String.valueOf(cal.get(Calendar.HOUR));
        String cur_Minute = String.valueOf(cal.get(Calendar.MINUTE));
        int am_pm = cal.get(Calendar.AM_PM);
        String cur_fullTime;
        TimePull tpull  = new TimePull();
        cur_fullTime = tpull.TimePull();




        TextView hour = (TextView) findViewById(R.id.textView3);
        TextView min = (TextView) findViewById(R.id.textView4);
        TextView pm = (TextView) findViewById(R.id.textView5);

        TimePull day_of_day = new TimePull();
        String reallyDy = day_of_day.DayPull();


        if(reallyDy.equals(day_of_Wednesday))
        {

              if(cur_fullTime.equals(wednesday_first_class))
              {
                  a = myDbHelper.wednesday_first_cursor("wednesdayfirst", null, null, null, null, null, null);
                  Toast.makeText(MainActivity.this, "Its Wednesday", Toast.LENGTH_SHORT).show();
                  if (a.moveToFirst()) {
                      clock_text.setText(a.getString(0));
                      am_pm_text.setText(a.getString(1));
                      class_text.setText(a.getString(2));

                      //Diagonistic CLock
                      hour.setText(reallyDy);


                      //AM and PM Lohic
                      if(am_pm == 0)
                      {
                          pm.setText("AM");
                      }
                      else
                      {
                          pm.setText("PM");
                      }


                  }
              }
              else
              {
                  Toast.makeText(MainActivity.this, "UnSuccessfull", Toast.LENGTH_SHORT).show();
              }
              }




    }





}