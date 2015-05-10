package yt5ny.cs2110.virginia.edu.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by Student on 4/11/2015.
 */
public class menu extends ActionBarActivity {

//    Button easy,medium,hard;
    int level;

    public menu(){
        level = 1;
    }
    RelativeLayout menu;
    private Button button1;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu);
        menu = (RelativeLayout)findViewById(R.id.menu);
//        easy = (Button)findViewById(R.id.easy);
//        medium = (Button)findViewById(R.id.medium);
//        hard = (Button)findViewById(R.id.hard);
                   button1 = (Button) findViewById(R.id.button1);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(menu.this, instruction.class));
                }});
                button2 = (Button) findViewById(R.id.button2);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(menu.this, UserPromptActivity.class));
                    }


                    ;


        });
//        easy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent myIntent = new Intent(menu.this, MainActivity.class);
//                myIntent.putExtra("level",1);
//                startActivity(myIntent);
//
//
//            }
//        });
//
//        medium.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent myIntent = new Intent(menu.this, MainActivity.class);
//                myIntent.putExtra("level",2);
//                startActivity(myIntent);
//
//
//
//
//            }
//        });
//
//        hard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent myIntent = new Intent(menu.this, MainActivity.class);
//                myIntent.putExtra("level",3);
//                startActivity(myIntent);
//
//
//            }
//        });
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("GeoQuiz", "calling onStart()");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.i("GeoQuiz", "calling onRestart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("GeoQuiz", "calling onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("GeoQuiz", "calling onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("GeoQuiz", "calling onDestroy()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("GeoQuiz", "calling onStop()");
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



