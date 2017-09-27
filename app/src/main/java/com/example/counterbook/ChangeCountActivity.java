package com.example.counterbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by XuanyiWu on 2017-09-25.
 */

public class ChangeCountActivity extends ViewCounterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_counter);

        add_name = (EditText) findViewById(R.id.name);
        add_time =(TextView) findViewById(R.id.time);
        add_init = (EditText) findViewById(R.id.initial_value);
        add_current = (TextView) findViewById(R.id.current_value);
        add_comment = (EditText) findViewById(R.id.comment);

        Intent intent = getIntent();
        final int current_pos1 = intent.getIntExtra("MyClass", 0);

        //////////////////////////////  Increment by 1  ////////////////////////////////////////////////

        Button plusBT = (Button) findViewById(R.id.plus);
        plusBT.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                counters.get(current_pos1).Increment();
                counters.get(current_pos1).NewDate();
                saveInFile();
                Toast.makeText(getApplicationContext(), "Increment by one", Toast.LENGTH_SHORT).show();
            }
        });

        //////////////////////////////  Decrement by 1  ////////////////////////////////////////////////

        Button minusBT = (Button) findViewById(R.id.minus);
        minusBT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                if (counters.get(current_pos1).getCurrent() == 0){
                    // URL : http://developer.android.com/guide/topics/ui/dialogs.html
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(ChangeCountActivity.this);
                    builder3.setTitle("Warning")
                            .setMessage("Current value cannot be negative ")
                            .setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing
                                }
                            });
                    builder3.show();
                }
                else {
                    counters.get(current_pos1).Decrement();
                    counters.get(current_pos1).NewDate();
                    saveInFile();
                    Toast.makeText(getApplicationContext(), "Decrement by one", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //////////////////////////////  Reset  ////////////////////////////////////////////////
        Button resetBT = (Button) findViewById(R.id.reset);
        resetBT.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                counters.get(current_pos1).Reset();
                counters.get(current_pos1).NewDate();
                saveInFile();
                Toast.makeText(getApplicationContext(), "Reset", Toast.LENGTH_SHORT).show();
            }
        });

        //////////////////////////////  Update other Info  ////////////////////////////////////////////////

        final Button confirmBtn2 = (Button) findViewById(R.id.sure);
        confirmBtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);


                String name_s1 = add_name.getText().toString();
                String initial_vs1 = add_init.getText().toString();
                String  comment_s1 = add_comment.getText().toString();

                if ((add_name.length() == 0)|| initial_vs1.length() == 0 ) {
                    // URL : http://developer.android.com/guide/topics/ui/dialogs.html
                    AlertDialog.Builder builder4 = new AlertDialog.Builder(ChangeCountActivity.this);
                    builder4.setTitle("Warning")
                            .setMessage("Need name and initial value ")

                            .setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing
                                }
                            });
                    builder4.show();
                }
                else {
                    int initial_v1 = 0;
                    Boolean success = true;
                    try {
                        initial_v1 = Integer.parseInt(initial_vs1);
                    } catch (Exception e) {
                        success = false;

                    }
                    if ( initial_v1 <0){
                        success = false;
                    }


                    if (success) {

                        if ((counters.get(current_pos1).getName().toString().equals(name_s1)) && (String.valueOf(counters.get(current_pos1).getInit()).equals(initial_vs1)) && (counters.get(current_pos1).getComment().equals(comment_s1)) ){
                            // no update
                            //Log.d("AAA", "same");
                        } else {
                            counters.get(current_pos1).new_name(name_s1);
                            if (! String.valueOf(counters.get(current_pos1).getInit()).equals(initial_vs1)) {
                                counters.get(current_pos1).new_init(initial_v1);
                            }
                            counters.get(current_pos1).new_comment(comment_s1);

                            counters.get(current_pos1).NewDate(); ///////////// ? ///////
                            saveInFile();
                            Toast.makeText(getApplicationContext(), "Update information", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        // URL : http://developer.android.com/guide/topics/ui/dialogs.html
                        AlertDialog.Builder builder5 = new AlertDialog.Builder(ChangeCountActivity.this);
                        builder5.setTitle("Warning")
                                .setMessage("Initial value should be a non-negative integer ")

                                .setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing
                                    }
                                });
                        builder5.show();

                    }


                }
            }
        });




    }



    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        int pos1 = intent.getIntExtra("MyClass", 0);


        add_name.setText(counters.get(pos1).getName());
        add_time.setText(counters.get(pos1).getDate());
        add_init.setText(String.valueOf(counters.get(pos1).getInit()));
        add_current.setText(String.valueOf(counters.get(pos1).getCurrent()));
        add_comment.setText(counters.get(pos1).getComment());



    }



}