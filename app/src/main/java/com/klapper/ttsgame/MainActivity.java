package com.klapper.ttsgame;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;
import java.util.Vector;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener,View.OnClickListener{
    Vector<QA> qaList = new Vector<QA>();
    TextToSpeech textToSpeech;
    EditText input_text;
    Button speak_btn;
    SeekBar voice_speed,voice_pitch;
    float speak_speed = 1;
    float speak_pitch = 1;
    openingLine opLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speak_btn = (Button) findViewById(R.id.speak_btn);
        voice_speed = (SeekBar)findViewById(R.id.voice_speed);
        voice_pitch = (SeekBar)findViewById(R.id.voice_pitch);
        input_text = (EditText)findViewById(R.id.editText2);
        textToSpeech = new TextToSpeech(this,this);
        speak_btn.setOnClickListener(this);
        voice_seekBar_setting();
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = textToSpeech.setLanguage(Locale.TAIWAN);
            opLine = openingLine.getInstance();
            opLine.init();
            try {
                load("chatBotRule.txt");
                //Log.d("MYLOG","Load chatBotRule.txt");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.d("MYLOG","this language is not supported");
            }else{
                speakOut(opLine.getRandomOpeningLine());
            }

        }
    }

    private void speakOut(String text) {
        if(speak_speed>0) {
            textToSpeech.setSpeechRate(speak_speed);
            textToSpeech.setPitch(speak_pitch);
        }else{
            textToSpeech.setSpeechRate(0.01f);
            textToSpeech.setPitch(0.1f);
        }
        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onDestroy() {
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }


        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if(!textToSpeech.isSpeaking()) {
            if (v.getId() == R.id.speak_btn) {

                speakOut(answer(input_text.getText().toString()));
                //Log.d("MYLOG",answer(input_text.getText().toString()));
            }
        }else{
            speakOut("我還在講話你插什麼嘴我不想講了!");
        }
    }

    private void voice_seekBar_setting() {
        voice_speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speak_speed = progress*0.3f;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        voice_pitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speak_pitch = progress*0.1f;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void load(String pFile) throws Exception {
        String kb = STR.file2text(this,pFile, "UTF-8");
        String[] blocks = kb.split("Q\\:");
        for (int i=0; i<blocks.length; i++) {

            String block = blocks[i].trim();
            //Log.d("MYLOG",block);
            if (block.length()==0) continue;
            QA qa = new QA(block);
            qaList.add(qa);
        }
    }

    public String answer(String input) {
        //Log.d("MYLOG","SQ Size: "+qaList.size());
        for (int i=0; i<qaList.size(); i++) {
            QA qa = qaList.get(i);

            String answer = qa.answer(input);
            if (answer != null) {
                Log.d("MYLOG","Q: "+input+" , A: "+answer);
                return answer;
            }
        }
        return "然後呢?";
    }
}
