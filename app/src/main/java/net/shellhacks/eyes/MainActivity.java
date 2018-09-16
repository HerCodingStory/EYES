package net.shellhacks.eyes;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.Manifest;
import android.content.Intent;
import android.provider.AlarmClock;
import android.service.voice.VoiceInteractionService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button speakButton;
    final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speakButton = findViewById(R.id.button8);
        speakButton.setOnClickListener(this);

        voiceinputbuttons();


        player = MediaPlayer.create(MainActivity.this, R.raw.tutorial);

    }

    public void voiceinputbuttons() {
        speakButton = findViewById(R.id.button8);
    }

    public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches.contains("tutorial")) {
                player.start();
            }
            if (matches.contains("object recognition"))
            {
                /*
                To do:
                object recognition stuff
                 */
            }
            if (matches.contains("text recognition"))
            {
                /*
                To do:
                text recognition stuff
                 */
            }
            if (matches.contains("location recognition"))
            {
                /*
                To do:
                location recognition stuff
                 */
            }
        }
    }

    public void onTokenReceived(String token) {

    }
}

    @Override
    public void onClick(View view) {
        startVoiceRecognitionActivity();
    }
}