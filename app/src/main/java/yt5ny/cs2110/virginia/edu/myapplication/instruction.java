package yt5ny.cs2110.virginia.edu.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Student on 3/30/2015.
 */
public class instruction extends Activity {
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.instruction);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(instruction.this, MainActivity.class));};

        });}
    @Override
    public void onStart() {
        super.onStart();
        Log.i("GhostHunter", "calling onStart()");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.i("GhostHunter", "calling onRestart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("GhostHunter", "calling onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("GhostHunter", "calling onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("GhostHunter", "calling onDestroy()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("GhostHunter", "calling onStop()");
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




