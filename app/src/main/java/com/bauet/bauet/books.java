package com.bauet.bauet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class books extends AppCompatActivity {

    private String mDisplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


       //setting up user name
      setUpDisplayName();

        ImageButton library_book_01 = (ImageButton) findViewById(R.id.book_01);
        library_book_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksViwer_ActivityOpener();
            }
        });

    }

    protected void booksViwer_ActivityOpener() {
        Intent intent = new Intent(this, booksViewer.class);
        startActivity(intent);
    }

    private void setUpDisplayName()
    {
        TextView booksname_text_field =(TextView) findViewById(R.id.library_user_name);

        SharedPreferences perfs =  getSharedPreferences(Register.CHAT_PREFS, MODE_PRIVATE);
        mDisplayName = perfs.getString(Register.DISPLAY_NAME_KEY,null);
        if(mDisplayName == null)
        {
            mDisplayName ="Anonymous";
        }
        else
        {
            booksname_text_field.setText(mDisplayName);
        }
    }
}