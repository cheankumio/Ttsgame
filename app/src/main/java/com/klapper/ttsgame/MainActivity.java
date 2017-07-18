package com.klapper.ttsgame;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.zagum.speechrecognitionview.RecognitionProgressView;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
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
    String alltext = "";
    SpeechRecognizer speechRecognizer;
    RecognitionProgressView recognitionProgressView;
    openingLine opLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TedPermission(this)
                .setDeniedMessage("請賦予應用權限，否則無法正常使用\n\n請按下設定 [設定] > [權限] > 將權限打開")
                .setPermissions(Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.INTERNET)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        speak_btn = (Button) findViewById(R.id.speak_btn);
                        voice_speed = (SeekBar)findViewById(R.id.voice_speed);
                        voice_pitch = (SeekBar)findViewById(R.id.voice_pitch);
                        input_text = (EditText)findViewById(R.id.editText2);
                        textToSpeech = new TextToSpeech(MainActivity.this,MainActivity.this);
                        speak_btn.setOnClickListener(MainActivity.this);
                        voice_seekBar_setting();
                        setRecognition();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(MainActivity.this, "權限獲取失敗\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .check();


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

    private void showResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String[] ssssss = matches.toArray(new String[0]);
        //runSometing(ssssss);
        //Log.d("MYLOG",ssssss[0]);
        input_text.setText(ssssss[0]);
        speakOut(ssssss[0]);
        //Toast.makeText(this, str5+" / "+ Pinyin.toPinyin(str5,""), Toast.LENGTH_LONG).show();
//        for (String s : ssssss) {
//            alltext += s + " / " + Pinyin.toPinyin(s, "") + "\n";
//        }
        //voiceString.setText(alltext);
        recognitionProgressView.stop();
        recognitionProgressView.play();
        //if(record==false)return;
        //startRecognition();
        recognitionProgressView.setVisibility(View.GONE);
    }

    private void setRecognition() {
        int[] colors = {
                ContextCompat.getColor(this, R.color.color1),
                ContextCompat.getColor(this, R.color.color2),
                ContextCompat.getColor(this, R.color.color3),
                ContextCompat.getColor(this, R.color.color4),
                ContextCompat.getColor(this, R.color.color5)
        };

        int[] heights = {60, 76, 58, 80, 55};


        //voiceString = (TextView) findViewById(R.id.voiceString);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognitionProgressView = (RecognitionProgressView) findViewById(R.id.recognition_view);
        recognitionProgressView.setSpeechRecognizer(speechRecognizer);
        recognitionProgressView.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onResults(Bundle results) {
                Log.d("MYLOG", "onResults");
                showResults(results);
            }

            @Override
            public void onError(int error) {
                super.onError(error);
                //if(record==false)return;
                //startRecognition();
            }

        });

        recognitionProgressView.setColors(colors);
        recognitionProgressView.setBarMaxHeightsInDp(heights);
        recognitionProgressView.play();

        AudioManager amanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
        amanager.setStreamMute(AudioManager.STREAM_ALARM, true);
        amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        amanager.setStreamMute(AudioManager.STREAM_RING, true);
        amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
    }

    private void startRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        speechRecognizer.startListening(intent);
        Log.d("MYLOG", "Start Recording");
    }



    @Override
    public void onClick(View v) {
        if(!textToSpeech.isSpeaking()) {
            if (v.getId() == R.id.speak_btn) {

                //speakOut(answer(input_text.getText().toString()));
                //Log.d("MYLOG",answer(input_text.getText().toString()));
                startRecognition();
                recognitionProgressView.setVisibility(View.VISIBLE);
                speakOut("請說");
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
