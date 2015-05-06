package com.example.jan.hintfinder94;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jan on 6.5.2015.
 */
public class resultScreen extends Activity {
        String sLetter;
        String letters;
        int length;
        String dictionary = "";
        List<String> arrayResults = new ArrayList<String>();
        List<String> arrayResultsClean = new ArrayList<String>();
        String [] listResults;
        String abeceda = "abcdefghijklmnopqrstuwxyz";
        int N = 25;



    	protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.results_activity);
            Bundle extras = getIntent().getExtras();
            readResource();

            sLetter = extras.getString("sLetter");
            letters = extras.getString("letters");
            length = extras.getInt("length");
            String pattern = "(^|\n)" + sLetter + "[" + letters + "]{" + Integer.toString(length-1) +
                                "}\n";

            int[] vIskani;
            int[] vTrenutni;
            vIskani = new int[N];
            vTrenutni = new int[N];

            //Toast.makeText(getApplicationContext(), "Searching for: " + pattern,
            //        Toast.LENGTH_SHORT).show();

            Pattern s_pattern = Pattern.compile(pattern);
            Matcher s_matcher = s_pattern.matcher(dictionary);
            int i = 0;
            while(s_matcher.find()){
                arrayResults.add(s_matcher.group());
                i++;
            }

            for(i = 0; i < N; i++){
                vIskani[i] = 0;
            }
            int indeks;

            for(i = 0; i < letters.length(); i++){
                indeks = abeceda.indexOf(letters.charAt(i));

            //
                if(indeks > 0) {
                    vIskani[indeks] += 1;
                    /*Toast.makeText(getApplicationContext(), "i: " + Integer.toString(i) +
                            "crka: " + letters.charAt(i) + " indeks: " + Integer.toString(indeks),
                            Toast.LENGTH_SHORT).show();
*/
                }

            }
            indeks = abeceda.indexOf(sLetter);
            vIskani[indeks] += 1;



            int flag;
            for(String beseda : arrayResults){
                flag = 0;
                for(i = 0; i < N; i++){
                    vTrenutni[i] = 0;
                }

                for(i = 0; i < beseda.length()-1; i++){
                    indeks = abeceda.indexOf(beseda.charAt(i));
                    if(indeks > 0) {
                        vTrenutni[indeks] += 1;
                    }

                }
                for(i = 0; i < N; i++){
                    if(vTrenutni[i] > vIskani[i]){
                        flag = 1;
                    }
                }
                if(flag == 0){
                    arrayResultsClean.add(beseda);
                }

            }

            listResults = new String[ arrayResultsClean.size() ];
            arrayResultsClean.toArray(listResults);

            ListAdapter adapter = new ArrayAdapter<String>(resultScreen.this, android.R.layout.simple_list_item_1, listResults);
            ListView resultsList = (ListView) findViewById(R.id.listView);
            resultsList.setAdapter(adapter);

        }

        public void readResource(){
            try {
                InputStream fileStream = getResources().openRawResource(
                        R.raw.dict);
                int fileLen = fileStream.available();
                byte[] fileBuffer = new byte[fileLen];
                fileStream.read(fileBuffer);
                fileStream.close();
                dictionary = new String(fileBuffer);

            } catch (IOException e) {
            }

        }
}
