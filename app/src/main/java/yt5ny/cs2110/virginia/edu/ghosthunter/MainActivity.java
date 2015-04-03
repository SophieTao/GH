package yt5ny.cs2110.virginia.edu.ghosthunter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {
    RelativeLayout layout;
    ImageView enemy;
    ImageView self;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
    }

    public void setUp() {
        layout = (RelativeLayout) findViewById(R.id.layout);
        enemy = (ImageView) findViewById(R.id.enemy);
        enemy.setImageDrawable(getResources().getDrawable(R.drawable.enemy));
        self = (ImageView) findViewById(R.id.self);
        self.setImageDrawable(getResources().getDrawable(R.drawable.self));
        layout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                self.setX(event.getX());
                self.setY(event.getY());
                return true;

            }


        });
    }


}
