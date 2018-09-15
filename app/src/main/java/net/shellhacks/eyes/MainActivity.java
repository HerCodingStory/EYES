package net.shellhacks.eyes;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.Manifest;
import android.content.Intent;
import android.provider.AlarmClock;
import android.service.voice.VoiceInteractionService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private final String RECOGNIZE_TEXT_INTENT = "net.shellhacks.eyes.RECOGNIZE_TEXT";

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button tutorialButton = findViewById(R.id.button5);

        player = MediaPlayer.create(MainActivity.this, R.raw.tutorial);

        if(player != null && !player.isPlaying())
        {
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

        Button objectRecognitionButton = findViewById(R.id.button6);
        objectRecognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                To Do:
                Add Object Recognition
                 */
            }
        });

        Button faceRecognitionButton = findViewById(R.id.button7);
        faceRecognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                To Do:
                Add Face Recognition
                 */
            }
        });

        Button textRecognitionButton = findViewById(R.id.button8);
        textRecognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                To Do:
                Add Text Recognition
                 */
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent.getAction().equals(RECOGNIZE_TEXT_INTENT)) {
            Log.d("TextRecognition","Some text recognition should be done now");
        }
    }

    public void onTokenReceived(String token) {

    }
}
