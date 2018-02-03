package com.abdulbasitmehtab.externalstoragetestapp;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Button write;
    Button read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        write = (Button) findViewById(R.id.btWrite);
        read = (Button) findViewById(R.id.btRead);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state;
                state = Environment.getExternalStorageState();

                if(Environment.MEDIA_MOUNTED.equals(state))
                {
                    File Root = Environment.getExternalStorageDirectory();
                    File Dir = new File(Root.getAbsolutePath() + "/MyAppFile");

                    if(!Dir.exists()){
                        Dir.mkdir();
                    }
                    File file = new File(Dir,"MyMessage.txt");

                    String Message = editText.getText().toString();
                    try {
                        FileOutputStream fOut = new FileOutputStream(file);
                        fOut.write(Message.getBytes());
                        fOut.close();
                        editText.setText("");
                        Toast.makeText(getApplicationContext(),"Message Saved",Toast.LENGTH_LONG).show();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"SD Card Not Found",Toast.LENGTH_LONG).show();
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File Root = Environment.getExternalStorageDirectory();
                File Dir = new File(Root.getAbsolutePath() + "/MyAppFile");
                File file = new File(Dir,"MyMessage.txt");

                String Message;
                try {
                    FileInputStream fIn = new FileInputStream(file);
                    InputStreamReader ISR = new InputStreamReader(fIn);
                    BufferedReader BFR = new BufferedReader(ISR);
                    StringBuffer SBF = new StringBuffer();

                    while((Message = BFR.readLine())!=null){
                        SBF.append(Message + "\n");
                    }
                    textView.setText(SBF.toString());
                    textView.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}