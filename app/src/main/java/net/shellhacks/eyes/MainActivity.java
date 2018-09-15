package net.shellhacks.eyes;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
 MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1=findViewById(R.id.button5);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player=MediaPlayer.create(MainActivity.this,R.raw.tutorial);
                player.start();
            }
        });

        button1.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View v){
                player.pause();
                return true;
            }
        });

    }
}
