package com.example.bhavya.contentresolver;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
TextToSpeech textToSpeech;
String sms;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textToSpeech=new TextToSpeech(MainActivity.this,this);
        Uri tableName = Uri.parse("content://sms/inbox");



        //Step1: connect to the content provider

        ContentResolver contentResolver = getContentResolver();

        //step 2: query the content provider

        Cursor cursor = contentResolver.query(tableName,null,null,null,null);

        cursor.moveToFirst();

        int bodyColumnIndex = cursor.getColumnIndexOrThrow("body");

         sms = cursor.getString(bodyColumnIndex);

        //step3: get the data from cursor and show in the textview

        TextView smsTextView = findViewById(R.id.textViewsms);
        smsTextView.setText(sms);

        smsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(sms,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

    }

    @Override
    public void onInit(int i) {
if(i==textToSpeech.SUCCESS)
{
    int result=textToSpeech.setLanguage(Locale.ENGLISH);
    if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED)
    {

    }
}
    }
}
