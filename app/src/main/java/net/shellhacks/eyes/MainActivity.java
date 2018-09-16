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
    private final String RECOGNIZE_TEXT_INTENT = "net.shellhacks.eyes.RECOGNIZE_TEXT";
    final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speakButton = findViewById(R.id.button8);
        speakButton.setOnClickListener(this);

        voiceinputbuttons();

        Button tutorialButton = findViewById(R.id.button5);

        player = MediaPlayer.create(MainActivity.this, R.raw.tutorial);

        if (player != null && !player.isPlaying()) {
            tutorialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.start();
                }
            });
        }
        tutorialButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                player.pause();
                return true;
            }
        });

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
        }
    }

    @Override
    public void onClick(View view) {
        startVoiceRecognitionActivity();
    }
}