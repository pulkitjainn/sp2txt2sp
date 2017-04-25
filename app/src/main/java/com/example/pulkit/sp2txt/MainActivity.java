package com.example.pulkit.sp2txt;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tv ;//= (TextView) findViewById(R.id.tv);
    ImageView sp,ls;//= (ImageView) findViewById(R.id.sp);

    TextToSpeech tts;
    String text=null;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);

        tts = new TextToSpeech(MainActivity.this,new TextToSpeech.OnInitListener(){
           @Override
            public void onInit(int status){
               if (status==TextToSpeech.SUCCESS){
                result = tts.setLanguage(Locale.UK);
               }
               else {
                   Toast.makeText(getApplicationContext(), "Feature not Supported", Toast.LENGTH_SHORT).show();
               }
           }
        });

        sp = (ImageView) findViewById(R.id.sp);
        ls= (ImageView) findViewById(R.id.ls);
        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechInput();
           }
        });
        ls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(result==TextToSpeech.LANG_NOT_SUPPORTED || result==TextToSpeech.LANG_MISSING_DATA){
                    Toast.makeText(getApplicationContext(), "Not working", Toast.LENGTH_SHORT).show();
                }
                else {
                    text=tv.getText().toString();
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });



    }



    public void speechInput() {

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "How are you?");


        try {
            startActivityForResult(i, 6);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(MainActivity.this, "bla bla bla", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==6 && resultCode==RESULT_OK && data!=null)
        {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            tv.setText(result.get(0));
        }

    }




}
