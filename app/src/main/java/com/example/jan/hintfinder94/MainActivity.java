package com.example.jan.hintfinder94;

import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;



public class MainActivity extends ActionBarActivity {

    private EditText sLetter, letters;
    private Button display;
    private SeekBar lenBar;
    private TextView textView;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sLetter = (EditText) findViewById(R.id.startingLetter);
        letters = (EditText) findViewById(R.id.letters);
        display = (Button) findViewById(R.id.display);
        lenBar = (SeekBar) findViewById(R.id.lBar);
        textView = (TextView) findViewById(R.id.sliderText);
        context = this.getApplicationContext();


        sLetter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast msg = Toast.makeText(getBaseContext(), "Enter the starting letter",
                        Toast.LENGTH_LONG);
                msg.show();
            }
        });

        textView.setText("Word length: " + lenBar.getProgress() + "/" + lenBar.getMax());
        lenBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar lenBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                textView.setText("Word length: " + progress + "/" + lenBar.getMax());
                //Toast.makeText(getApplicationContext(), "Spreminjam dolzino besede", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar lenBar) {
                textView.setText("Word length: " + progress + "/" + lenBar.getMax());
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar lenBar) {
                textView.setText("Word length: " + progress + "/" + lenBar.getMax());
                //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });

        display.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_sLetter = sLetter.getText().toString();
                String s_letters = letters.getText().toString();
                int s_lenBar = lenBar.getProgress();
                if (s_sLetter.length() != 1) {
                    Toast.makeText(getApplicationContext(), "Enter only one starting letter", Toast.LENGTH_SHORT).show();
                } else if (s_letters.length() < s_lenBar - 1) {
                    Toast.makeText(getApplicationContext(), "Not enough letters available", Toast.LENGTH_SHORT).show();
                } else if (s_lenBar < 1) {
                    Toast.makeText(getApplicationContext(), "Word length must be greater than 0", Toast.LENGTH_SHORT).show();
                } else {
                    Intent zadetki = new Intent(context, resultScreen.class);
                    zadetki.putExtra("sLetter", s_sLetter);
                    zadetki.putExtra("letters", s_letters);
                    zadetki.putExtra("length", s_lenBar);
                    startActivity(zadetki);
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
        //    case R.id.action_settings:
        //        Toast.makeText(this, "Settings are not implemented yet", Toast.LENGTH_SHORT)
        //            .show();
        //        break;
            case R.id.action_help:
                Intent help = new Intent(context, helpScreen.class);
                startActivity(help);
                break;
            case R.id.action_special:
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "dQw4w9WgXcQ"));
                    startActivity(intent);} catch(ActivityNotFoundException ex){
                    Toast.makeText(this, "Youtube not found, you are probably on emulator", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
