package net.shellhacks.eyes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
    private CameraDevice.StateCallback stateCallback;
    private CameraDevice cameraDevice;
    private HandlerThread backgroundHandlerThread;
    private Handler backgroundHandler;
    private CaptureRequest captureRequest;
    private CameraCaptureSession cameraCaptureSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speakButton = findViewById(R.id.button8);
        speakButton.setOnClickListener(this);

        player = MediaPlayer.create(MainActivity.this, R.raw.tutorial);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        setUpStateCallback();
        openBackgroundThread();
        openCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        openCamera();
    }

    private void setUpStateCallback() {
        stateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice cameraDevice) {
                MainActivity.this.cameraDevice = cameraDevice;
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                cameraDevice.close();
                MainActivity.this.cameraDevice = null;
            }

            @Override
            public void onError(@NonNull CameraDevice cameraDevice, int i) {
                cameraDevice.close();
                MainActivity.this.cameraDevice = null;
            }
        };
    }

    private void openCamera() {
        try {
            for (String id : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(id);
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    cameraManager.openCamera(id, stateCallback, backgroundHandler);
                    final CaptureRequest.Builder captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                    cameraDevice.createCaptureSession(null, new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            if (cameraDevice == null) {
                                return;
                            }
                            try {
                                captureRequest = captureRequestBuilder.build();
                                MainActivity.this.cameraCaptureSession = cameraCaptureSession;
                                MainActivity.this.cameraCaptureSession.setRepeatingRequest(captureRequest, null, backgroundHandler);
                            }
                            catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                        }
                    }, backgroundHandler);
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openBackgroundThread() {
        backgroundHandlerThread = new HandlerThread("camera_background_thread");
        backgroundHandlerThread.start();
        backgroundHandler = new Handler(backgroundHandlerThread.getLooper());
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