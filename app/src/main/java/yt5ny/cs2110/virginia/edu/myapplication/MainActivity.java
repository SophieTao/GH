package yt5ny.cs2110.virginia.edu.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
//import android.text.Layout;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;


public class MainActivity extends ActionBarActivity {
    RelativeLayout layout;
    ImageView enemy;
    ImageView self;
    ImageView ghost;
    MediaPlayer logoMusic;
    Random random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();

        MediaPlayer logoMusic = MediaPlayer.create(MainActivity.this, R.raw.splash_sound);
        logoMusic.start();

    }



    public void setUp() {
        layout = (RelativeLayout) findViewById(R.id.layout);
        ghost = (ImageView) findViewById(R.id.ghost);
        ghost.setImageDrawable(getResources().getDrawable(R.drawable.ghost));
//        enemy = (ImageView) findViewById(R.id.enemy);
//        enemy.setImageDrawable(getResources().getDrawable(R.drawable.enemy));
        self = (ImageView) findViewById(R.id.self);
        self.setImageDrawable(getResources().getDrawable(R.drawable.self));
        layout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                self.setX(event.getX());
                self.setY(event.getY());
                return true;}});



          GhostHandler gh = new GhostHandler();
        gh.execute(ghost);

        ghost.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view){
//                gh.onCancelled();
                ghost.setVisibility(View.GONE);
            }
        });

    }





    class GhostHandler extends AsyncTask<ImageView, ImageView, Void> {
        float x, y;
        float v_x, v_y;
        float THRESH = 60;
        Random rand = new Random();

        public GhostHandler() {
            x = 0;
            y = 0;
            v_x = 5;
            v_y = 5;
        }


        protected Void doInBackground(ImageView... ghosts) {
            ImageView ghost = ghosts[0];
            x = ghost.getX();
            y = ghost.getY();

            while (true) {
                move(ghost);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(ghost);
            }

        }

        public void move(ImageView ghost) {

            if (x < (MainActivity.this.layout.getWidth() - THRESH)) {
                x = x + rand.nextFloat() * 50;
                y = y + rand.nextFloat() * 50;
            } else {
                if (x >= MainActivity.this.layout.getWidth()) {
                    x -= rand.nextFloat() * 50;
                    Random rand2 = new Random();
                    y -= (rand2.nextFloat());
                    //y -= (rand.nextFloat()- 0.5) * 50;
                }



            }
//            if ()

        }


        protected void onProgressUpdate(ImageView... ghosts) {
            ImageView ghost = ghosts[0];
            ghost.setX(x);
            ghost.setY(y);
        }

        protected void onPostExecute(Void v) {

        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        logoMusic.release();
    }
}