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

    //f=first
    //s=second
    //t=third

    //Sunday Cursor Variable
    Cursor sun_f = null;
    Cursor sun_s = null;
    Cursor sun_t = null;

 //Monday Cursor Variable
    Cursor mon_f = null;
    Cursor mon_s = null;
    Cursor mon_t = null;

 //Tuesday Cursor Variable
    Cursor Tues_f = null;
    Cursor Tues_s = null;
    Cursor Tues_t = null;
    Cursor Tues_fourth = null;


   //Wednesday Cursor Variable
    Cursor w_f = null;
    Cursor w_s = null;
    Cursor w_t = null;
    Cursor w_fourth = null;

    //Thursday Cursor Variable
    Cursor Th_f = null;
    Cursor Th_s = null;
    Cursor Th_t = null;






    //Day Variable- (a)
    protected TextView today;
    protected  String day;
    private   BroadcastReceiver minuteUpdate;

    //CLass scedule Area
    protected TextView CLASS_NAME;
    protected TextView CLASS_TIME;
    protected TextView AM_PM;

    //Day Area
    public String day_of_Sunday = "Sunday";
    public String day_of_Monday = "Monday";
    public String day_of_Tuesday = "Tuesday";
    public String day_of_Wednesday = "Wednesday";
    public String day_of_Thursday = "Thursday";

  //Main Function Area
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

                //Diagonistic Toast to Test that minute is actually Updating or not
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





      //Class Time Ato Update Scedule
      String first_class_up = "06:00 AM";
      String second_class_up = "08:45 AM";
      String third_class_up = "09:45 AM";
      String fourth_class_up = "11:30 AM";
      String fifth_class_up = "12:30 PM";

      // Eaxt Class
        String first_class_time = "08:15 AM";
        String second_class_time = "09:15 AM";
        String third_class_time = "10:15 AM";
        String fourth_class_time = "11:40 AM";
        String fifth_class_time = "12:40 PM";






        //Time Pull
        Calendar cal = Calendar.getInstance();
        String cur_Hour = String.valueOf(cal.get(Calendar.HOUR));
        String cur_Minute = String.valueOf(cal.get(Calendar.MINUTE));
        int am_pm = cal.get(Calendar.AM_PM);
        String cur_fullTime;
        TimePull tpull  = new TimePull();
        cur_fullTime = tpull.TimePull();


         ///////******
        //Current Day Pulling
        TimePull day_of_day = new TimePull();
        String reallyDy = day_of_day.DayPull();
        //Current Day Pulling
        //////******

        ///////Sunday Class Time Update//////////
        ///////////////////////////////////////
        if(reallyDy.equals(day_of_Sunday))
        {
            //Sunday First Class
            if(cur_fullTime.equals(first_class_up))
            {
                sun_f = myDbHelper.sunday_first_cursor("sundayfirst", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Sunday First Class", Toast.LENGTH_SHORT).show();
                if (w_f.moveToFirst()) {
                    clock_text.setText(sun_f.getString(0));
                    am_pm_text.setText(sun_f.getString(1));
                    class_text.setText(sun_f.getString(2));
                }
            }
             //Sunday Second Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                sun_s = myDbHelper.sunday_second_cursor("sundaysecond", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Sunday Second Class", Toast.LENGTH_SHORT).show();
                if (w_f.moveToFirst()) {
                    clock_text.setText(sun_s.getString(0));
                    am_pm_text.setText(sun_s.getString(1));
                    class_text.setText(sun_s.getString(2));
                }
            }

             //Sunday Third Class
            if(cur_fullTime.equals(fifth_class_up))
            {
                sun_t = myDbHelper.sunday_third_cursor("sundaythird", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Sunday Third Class", Toast.LENGTH_SHORT).show();
                if (w_f.moveToFirst()) {
                    clock_text.setText(sun_t.getString(0));
                    am_pm_text.setText(sun_t.getString(1));
                    class_text.setText(sun_t.getString(2));
                }
            }



        }

        ///////Monday Class Time Update//////////
        ///////////////////////////////////////

        if(reallyDy.equals(day_of_Monday))
        {
            //Monday First Class
            if(cur_fullTime.equals(first_class_up))
            {
                mon_f = myDbHelper.monday_first_cursor("mondayfirst", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Monday First Class", Toast.LENGTH_SHORT).show();
                if (mon_f.moveToFirst()) {
                    clock_text.setText(mon_f.getString(0));
                    am_pm_text.setText(mon_f.getString(1));
                    class_text.setText(mon_f.getString(2));
                }
            }

             //Monday Second Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                mon_s = myDbHelper.monday_second_cursor("mondaysecond", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Monday Second Class", Toast.LENGTH_SHORT).show();
                if (mon_s.moveToFirst()) {
                    clock_text.setText(mon_s.getString(0));
                    am_pm_text.setText(mon_s.getString(1));
                    class_text.setText(mon_s.getString(2));
                }
            }

            //Monday Third Class
            if(cur_fullTime.equals(fifth_class_up))
            {
                mon_t = myDbHelper.monday_third_cursor("mondaythird", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Monday Third Class", Toast.LENGTH_SHORT).show();
                if (mon_t.moveToFirst()) {
                    clock_text.setText(mon_t.getString(0));
                    am_pm_text.setText(mon_t.getString(1));
                    class_text.setText(mon_t.getString(2));
                }
            }




        }






         //////Tuesday Class Time Update//////////
        ///////////////////////////////////////

        if(reallyDy.equals(day_of_Tuesday))
        {
            //Tuesday First Class
            if(cur_fullTime.equals(first_class_up))
            {
                Tues_f = myDbHelper.tuesday_first_cursor("tuesdayfirst", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Tuesday First Class", Toast.LENGTH_SHORT).show();
                if (Tues_f.moveToFirst()) {
                    clock_text.setText(Tues_f.getString(0));
                    am_pm_text.setText(Tues_f.getString(1));
                    class_text.setText(Tues_f.getString(2));
                }
            }
           //Tuesday Second Class
            if(cur_fullTime.equals(second_class_up))
            {
                Tues_s = myDbHelper.tuesday_second_cursor("tuesdaysecond", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Tuesday Second Class", Toast.LENGTH_SHORT).show();
                if (Tues_s.moveToFirst()) {
                    clock_text.setText(Tues_s.getString(0));
                    am_pm_text.setText(Tues_s.getString(1));
                    class_text.setText(Tues_s.getString(2));
                }
            }
            //Tuesday Third Class
            if(cur_fullTime.equals(third_class_up))
            {
                Tues_t = myDbHelper.tuesday_third_cursor("tuesdaythird", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Tuesday Third Class", Toast.LENGTH_SHORT).show();
                if (Tues_t.moveToFirst()) {
                    clock_text.setText(Tues_t.getString(0));
                    am_pm_text.setText(Tues_t.getString(1));
                    class_text.setText(Tues_t.getString(2));
                }
            }
            //Tuesday Fourth Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                Tues_fourth = myDbHelper.tuesday_fourth_cursor("tuesdayfourth", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Tuesday Fourth Class", Toast.LENGTH_SHORT).show();
                if (Tues_fourth.moveToFirst()) {
                    clock_text.setText(Tues_fourth.getString(0));
                    am_pm_text.setText(Tues_fourth.getString(1));
                    class_text.setText(Tues_fourth.getString(2));
                }
            }






        }

        //////Wednesday Class Time Update//////////
        ///////////////////////////////////////
        if(reallyDy.equals(day_of_Wednesday))
        {
            //Wednesday First Class
            if(cur_fullTime.equals(first_class_up))
            {
                w_f = myDbHelper.wednesday_first_cursor("wednesdayfirst", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Wednesday First Class", Toast.LENGTH_SHORT).show();
                if (w_f.moveToFirst()) {
                    clock_text.setText(w_f.getString(0));
                    am_pm_text.setText(w_f.getString(1));
                    class_text.setText(w_f.getString(2));
                }
            }
             //Wednesday Second Class
            if(cur_fullTime.equals(third_class_up))
            {
                w_s = myDbHelper.wednesday_second_cursor("wednesdaysecond", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Wednesday Second Class ", Toast.LENGTH_SHORT).show();
                if (w_s.moveToFirst()) {
                    clock_text.setText(w_s.getString(0));
                    am_pm_text.setText(w_s.getString(1));
                    class_text.setText(w_s.getString(2));
                }
            }

            //Wednesday Third Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                w_t = myDbHelper.wednesday_third_cursor("wednesdaythird", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Wednesday Third Class ", Toast.LENGTH_SHORT).show();
                if (w_t.moveToFirst()) {
                    clock_text.setText(w_t.getString(0));
                    am_pm_text.setText(w_t.getString(1));
                    class_text.setText(w_t.getString(2));
                }
            }

            //Wednesday Fourth Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                w_fourth = myDbHelper.wednesday_fourth_cursor("wednesdayfourth", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Wednesday Fourth Class ", Toast.LENGTH_SHORT).show();
                if (w_fourth.moveToFirst()) {
                    clock_text.setText(w_fourth.getString(0));
                    am_pm_text.setText(w_fourth.getString(1));
                    class_text.setText(w_fourth.getString(2));
                }
            }



        }






















         //////////Thursday Class////////////
        ////////////////////////////////////
        if(reallyDy.equals(day_of_Thursday))
        {
            //Thursday First Class
            if(cur_fullTime.equals(first_class_up))
            {
                Th_f = myDbHelper.thursday_first_cursor("thursdayfirst", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Thursday First Class", Toast.LENGTH_SHORT).show();
                if (Th_f.moveToFirst()) {
                    clock_text.setText(Th_f.getString(0));
                    am_pm_text.setText(Th_f.getString(1));
                    class_text.setText(Th_f.getString(2));
                }
            }

            //Thursday Second Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                Th_s = myDbHelper.thursday_second_cursor("thursdaysecond", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Thursday Second Class", Toast.LENGTH_SHORT).show();
                if (Th_s.moveToFirst()) {
                    clock_text.setText(Th_s.getString(0));
                    am_pm_text.setText(Th_s.getString(1));
                    class_text.setText(Th_s.getString(2));
                }
            }

             //ThursdayThird Class
            if(cur_fullTime.equals(fifth_class_up))
            {
                Th_t = myDbHelper.thursday_third_cursor("thursday_third", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Thursday Third Class", Toast.LENGTH_SHORT).show();
                if (Th_t.moveToFirst()) {
                    clock_text.setText(Th_t.getString(0));
                    am_pm_text.setText(Th_t.getString(1));
                    class_text.setText(Th_t.getString(2));
                }
            }



        }



    }





}