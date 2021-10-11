package com.example.lab4;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    private TextView textView2;
    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
    }
    class ExampleRunnable implements Runnable{
        @Override
        public void run(){
            mockFileDownloader();
        }

    }
    public void mockFileDownloader(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("DOWNLOADING...");
            }
        });

        for (int downloadProgress = 0; downloadProgress <= 100; downloadProgress = downloadProgress+10){
            if (stopThread){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("Start");
                        TextView textView2 = (TextView) findViewById(R.id.textView2);
                        textView2.setText("");
                    }
                });
                return;
            } else {
                int finalDownloadProgress = downloadProgress;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView2 = (TextView) findViewById(R.id.textView2);
                        textView2.setText("Download Progress: " + finalDownloadProgress + "%");

                    }
                });
            }

            Log.d(TAG, "Download Progress: " + downloadProgress + "%");


            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Start");
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                textView2.setText("");
            }
        });
    }
    public void startDownload(View view){
        stopThread=false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view){
        stopThread=true;
    }


}
