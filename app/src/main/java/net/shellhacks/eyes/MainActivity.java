package net.shellhacks.eyes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button speakButton;
    final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private MediaPlayer player;
    private CameraManager cameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speakButton = findViewById(R.id.button8);
        speakButton.setOnClickListener(this);

        voiceinputbuttons();

        player = MediaPlayer.create(MainActivity.this, R.raw.tutorial);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            for (String id : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(id);
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK) {
                    cameraManager.openCamera(id, CameraDevice.StateCallback.);
                    return;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onClick(View view) {
        startVoiceRecognitionActivity();
    }
}