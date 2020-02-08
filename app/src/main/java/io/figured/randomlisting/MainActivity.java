package io.figured.randomlisting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaCas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.text.TextUtils.concat;

public class MainActivity extends AppCompatActivity {

    Button mkRandbtn, saveThisbtn;
    ArrayList randomList;
    EditText totalUSUs, sampleSize;
    TextView randomSelection;
    Boolean hasTotal, hasSample, isDuplicate;
    Integer totalUSUs_i, sampleSize_i, selection;
    String randomList_s;

    //  create a textWatcher member
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForValidValues();
        }
    };

    // Create the function to send user to a the saveselection activity
    public void openSaveSelection() {
        Intent intent = new Intent(this, SaveSelection.class);
        startActivity(intent);
    }

    // Check to see if the entered values are valid - if so, then add new 
    void checkFieldsForValidValues(){
        Button mkRandbtn = findViewById(R.id.mkRandbtn);

        if(!totalUSUs.getText().toString().equals("")) {
            String totalUSUs_s = totalUSUs.getText().toString();
            totalUSUs_i = Integer.parseInt(totalUSUs_s);
            hasTotal = true;

        }
        if(!sampleSize.getText().toString().equals("")) {
            String sampleSize_s = sampleSize.getText().toString();
            sampleSize_i = Integer.parseInt(sampleSize_s);
            hasSample = true;
        }

        if(hasTotal && hasSample && totalUSUs_i >= 0 && !sampleSize_i.equals(null) && sampleSize_i >= 0 && sampleSize_i <= totalUSUs_i){
            mkRandbtn.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Data entered", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Check run & failed", Toast.LENGTH_SHORT).show();
            mkRandbtn.setEnabled(false);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mkRandbtn = findViewById(R.id.mkRandbtn);
        saveThisbtn = findViewById(R.id.saveThisbtn);
        totalUSUs = findViewById(R.id.totalUSUs);
        sampleSize = findViewById(R.id.sampleSize);
        randomSelection = findViewById(R.id.random_selection);
        hasTotal = false;
        hasSample = false;
        mkRandbtn.setEnabled(false);

//        totalUSUs_i = -1;
//        sampleSize_i = -1;

        // Add text listeners
        totalUSUs.addTextChangedListener(mTextWatcher);
        sampleSize.addTextChangedListener(mTextWatcher);

        //run the validator once to set login to off
//        checkFieldsForValidValues();


        mkRandbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the value of the total USUs button and convert it to a string. This happens
                // after the click

                randomList_s = "";
                randomList = new ArrayList<Integer>();


                for (int i = 0; i < sampleSize_i; i++) {
                    isDuplicate = true;
                    Random r = new Random();
                    while (isDuplicate) {
                        selection = r.nextInt(totalUSUs_i) + 1;
                        if (!randomList.contains(selection)) {
                            isDuplicate = false;
                        }
                    }
                    randomList.add(selection);
                }

                Collections.sort(randomList);

                for (int i = 0; i < randomList.size(); i++) {
                    if (randomList_s.equals("")) {
                        randomList_s = randomList.get(i).toString();
                    } else {
                        randomList_s = (String) concat(randomList_s, "\n", randomList.get(i).toString());
                    }
                }

                randomSelection.setText(randomList_s);
            }
        });

        saveThisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSaveSelection();
            }
        });



    }

}
