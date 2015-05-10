package yt5ny.cs2110.virginia.edu.myapplication;
import android.app.ActionBar;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.TreeMap;

import static android.media.MediaPlayer.*;


public class MainActivity extends ActionBarActivity {

    RelativeLayout layout;
    ImageView boy, coinBoard, hpboard, sbomb, star,pause, playMusic,muteMusic, friend, coinBag, friend1, bomb1, friend2, heart;
    TextView moneyBoard,hp,bombnum,score;
    int coins, pos=0,level, getBomb, getScore;
    Boy b;
    ImageView[] ghostimages = new ImageView[10];
    ImageView[] bombimages = new ImageView[10];
    Ghosts ghosts;



    MediaPlayer backgroundMusic;


    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
    }


    public void setUp() {

        layout = (RelativeLayout) findViewById(R.id.layout);

//
        level = getIntent().getIntExtra("level",1);
        getBomb = getBomb;
        Log.d("level ",""+level);
        //create a boy on the screen
        boy = (ImageView) findViewById(R.id.self);
        boy.setImageDrawable(getResources().getDrawable(R.drawable.self));
        boy.setX(500);
        boy.setY(500);
        b = new Boy(boy);

        //show how many coins you have
        playMusic = (ImageView)findViewById(R.id.playMusic);
        playMusic.setImageDrawable(getResources().getDrawable(R.drawable.play));
        muteMusic = (ImageView) findViewById(R.id.muteMusic);
        muteMusic.setImageDrawable(getResources().getDrawable(R.drawable.mute));
        coinBoard = (ImageView)findViewById(R.id.count);
        coinBoard.setImageDrawable(getResources().getDrawable(R.drawable.coin));
        friend = (ImageView) findViewById(R.id.friend);
        friend.setImageDrawable(getResources().getDrawable(R.drawable.friend));
        friend.setVisibility(View.INVISIBLE);
        friend1 = (ImageView) findViewById(R.id.friend1);
        friend1.setImageDrawable(getResources().getDrawable(R.drawable.duck));
        friend1.setVisibility(View.INVISIBLE);
        friend2 = (ImageView) findViewById(R.id.friend2);
        friend2.setImageDrawable(getResources().getDrawable(R.drawable.friend2));
        friend2.setVisibility(View.INVISIBLE);
        bomb1 = (ImageView) findViewById(R.id.bomb1);
        bomb1.setImageDrawable(getResources().getDrawable(R.drawable.bomb));
        bomb1.setVisibility(View.INVISIBLE);
        friend = (ImageView) findViewById(R.id.friend);
        friend.setImageDrawable(getResources().getDrawable(R.drawable.friend));
        friend.setVisibility(View.INVISIBLE);
        heart = (ImageView) findViewById(R.id.heart);
        heart.setImageDrawable(getResources().getDrawable(R.drawable.star));
        heart.setVisibility(View.INVISIBLE);
        coinBag = (ImageView) findViewById(R.id.coinbag);
        coinBag.setImageDrawable(getResources().getDrawable(R.drawable.coinbag));
        coinBag.setVisibility(View.INVISIBLE);
        hpboard = (ImageView)findViewById(R.id.heart1);
        hpboard.setImageDrawable(getResources().getDrawable(R.drawable.heart));

        sbomb = (ImageView)findViewById(R.id.bomb);
        sbomb.setImageDrawable(getResources().getDrawable(R.drawable.ball));

        star = (ImageView)findViewById(R.id.star);
        star.setImageDrawable(getResources().getDrawable(R.drawable.star));

        moneyBoard = (TextView)findViewById(R.id.editText);
        moneyBoard.setText(""+coins);
        coins = 0;

        hp = (TextView)findViewById(R.id.healthText);
        bombnum = (TextView)findViewById(R.id.textView2);
        score = (TextView)findViewById(R.id.scorenum);
     backgroundMusic= MediaPlayer.create(MainActivity.this, R.raw.background);
        try{backgroundMusic.prepare();}
        catch(Exception e){

        }


        //pause button
//        pause = (ImageView) findViewById(R.id.pause_during_game);
//        pause.setImageDrawable(getResources().getDrawable(R.drawable.pause));

        //randomly generate 5 ghosts on the screen
        for (int i=0;i<10;i++){
            createGhost(i);
            ImageView imageView = (ImageView) layout.findViewWithTag(""+i);
            ghostimages[i]=imageView;
            bombimages[i]=createBomb(ghostimages[i]);

        }

        ghosts = new Ghosts(b,ghostimages,hp,bombnum,score,bombimages,level);

    }


    protected void onStart(){

        //now game starts and background music plays
        super.onStart();

//        backgroundMusic.start();
//
//        //the music is muted when mute button is clicked
//        muteMusic.setOnClickListener(new View.OnClickListener(){
//                                         @Override
//                                         public void onClick(View view){
//                                             backgroundMusic.release();
//                                         }
//                                     });



        //ghosts move towards the character
        ghosts.execute(ghostimages);

        //touch the screen to let the character move
        layout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                b.move(event);
                boy.setX(b.getX());
                boy.setY(b.getY());
                return true;

            }

        });

        //click on coin picture to buy bombs. 5 coins to buy 1 bomb.
        coinBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coins >= 5){
                    ghosts.setBomb(ghosts.getBomb()+1);
                    coins-= 5;
                    moneyBoard.setText("" + coins);
                }
            }
        });



        //set each ghost onClick listener
        //kill one ghost and collect one coin
        //after kill one ghost, a new ghost will randomly appear on the screen
        //click on a ghost and release a bomb if bomb number is greater than 0
        /*
            I list out all the ghosts since ghosts.getGhosts()[num], here num
            should not change.(which means should be defined as final)
         */
        ghostimages[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ghosts.getBomb()>0){
                    ghosts.getGhosts()[0].setKilled(true);
                    ghosts.getGhosts()[0].setTick(0);
                    coins++;
                    moneyBoard.setText("" + coins);
                    ghosts.setBomb(ghosts.getBomb()-1);


                }
            }
        });
        ghostimages[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ghosts.getBomb()>0){
                    ghosts.getGhosts()[1].setKilled(true);
                    ghosts.getGhosts()[1].setTick(0);
                    coins++;
                    moneyBoard.setText("" + coins);
                    ghosts.setBomb(ghosts.getBomb()-1);
                    friend2.setVisibility(View.VISIBLE);

                }
            }
        });
        ghostimages[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ghosts.getBomb()>0){
                    ghosts.getGhosts()[2].setKilled(true);
                    ghosts.getGhosts()[2].setTick(0);
                    coins++;
                    moneyBoard.setText("" + coins);
                    ghosts.setBomb(ghosts.getBomb()-1);

                }
            }
        });
        ghostimages[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ghosts.getBomb()>0){
                    ghosts.getGhosts()[3].setKilled(true);
                    ghosts.getGhosts()[3].setTick(0);
                    coins++;
                    moneyBoard.setText("" + coins);
                    ghosts.setBomb(ghosts.getBomb()-1);

                }
            }
        });
        ghostimages[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ghosts.getBomb()>0){
                    ghosts.getGhosts()[4].setKilled(true);
                    ghosts.getGhosts()[4].setTick(0);
                    coins++;
                    moneyBoard.setText("" + coins);
                    ghosts.setBomb(ghosts.getBomb()-1);

                }
            }
        });
        ghostimages[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ghosts.getBomb()>0){
                    ghosts.getGhosts()[5].setKilled(true);
//                    mp1.start();
                    ghosts.getGhosts()[5].setTick(0);
                    coins++;
                    moneyBoard.setText("" + coins);
                    ghosts.setBomb(ghosts.getBomb()-1);

                }
            }
        });
        ghostimages[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ghosts.getBomb()>0){
                    ghosts.getGhosts()[6].setKilled(true);
                    ghosts.getGhosts()[6].setTick(0);
                    coins++;
                    moneyBoard.setText("" + coins);
                    ghosts.setBomb(ghosts.getBomb()-1);

                }
            }
        });
        ghostimages[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ghosts.getBomb()>0){
                    ghosts.getGhosts()[7].setKilled(true);
                    ghosts.getGhosts()[7].setTick(0);
                    coins++;
                    moneyBoard.setText("" + coins);
                    ghosts.setBomb(ghosts.getBomb()-1);

                }
            }
        });
        ghostimages[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ghosts.getBomb()>0){
                    ghosts.getGhosts()[8].setKilled(true);
                    ghosts.getGhosts()[8].setTick(0);
                    coins++;
                    moneyBoard.setText("" + coins);
                    ghosts.setBomb(ghosts.getBomb()-1);
                    friend1.setVisibility(View.VISIBLE);


                }
            }
        });
        ghostimages[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ghosts.getBomb()>0){
                    ghosts.getGhosts()[9].setKilled(true);
                    ghosts.getGhosts()[9].setTick(0);
                    coins++;
                    moneyBoard.setText("" + coins);
                    ghosts.setBomb(ghosts.getBomb()-1);
                    ghosts.setBomb(ghosts.getBomb()-1);
                    friend.setVisibility(View.VISIBLE);

                }
            }
        });

        friend2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                friend2.setVisibility(View.INVISIBLE);
                heart.setVisibility(View.VISIBLE);
                pos++;
                hp.setText("" + pos);

                heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, R.string.special, Toast.LENGTH_SHORT).show();
                        getScore += 10;
                        hp.setText("" + getScore);
                        heart.setVisibility(View.INVISIBLE);
                    }
                }); };});

        playMusic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                backgroundMusic.setLooping(true);
                backgroundMusic.start();

            }});
            muteMusic.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    if(backgroundMusic !=null && backgroundMusic.isPlaying()){
                        backgroundMusic.pause();

            }}});

friend.setOnClickListener(new View.OnClickListener() {
    public void onClick(View view) {
        friend.setVisibility(View.INVISIBLE);
        coinBag.setVisibility(View.VISIBLE);
        pos++;
        hp.setText("" + pos);

        coinBag.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Toast.makeText(MainActivity.this, R.string.special, Toast.LENGTH_SHORT).show();
        coins += 10;
        moneyBoard.setText("" + coins);
        coinBag.setVisibility(View.INVISIBLE);
}
        }); };});

        friend1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                friend1.setVisibility(View.INVISIBLE);
                bomb1.setVisibility(View.VISIBLE);
                pos++;
                hp.setText("" + pos);

                bomb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, R.string.special, Toast.LENGTH_SHORT).show();
                        getBomb += 1;
                        bombnum.setText("" + getBomb);
                        bomb1.setVisibility(View.INVISIBLE);
                    }
                });

//        friend1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                friend1.setVisibility(View.INVISIBLE);
//                coinBag.setVisibility(View.VISIBLE);
//                pos++;
//                hp.setText("" + pos);
//
//                coinBag.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(MainActivity.this, R.string.special, Toast.LENGTH_SHORT).show();
//                        coins += 10;
//                        moneyBoard.setText("" + coins);
//                        coinBag.setVisibility(View.INVISIBLE);
//                    }
//
//                });}

        };
});


    }


//
    protected void onPause(){
        finish();
        ghosts.cancel(true);
        ghosts = null;
        super.onPause();
//        mp1.release();
//        if click on the pause button, go to pause page
//        pause.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
                Intent myIntent = new Intent(MainActivity.this, menu.class);
                startActivity(myIntent)
                ;


            }




    protected void onResume(){

        super.onResume();
//
    }


    public void createGhost(int i){
        //ImageView Setup
        ImageView imageView = new ImageView(this);
        //setting image resource
        imageView.setImageResource(R.drawable.ghost);
        imageView.setTag(""+i);
        //setting image position
        imageView.setLayoutParams(new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        //adding view to layout
        layout.addView(imageView);
        imageView.setX(random.nextFloat()*900+30);
        imageView.setY(random.nextFloat()*1500+50);
        imageView.setVisibility(View.INVISIBLE);
    }

    //create a bomb picture
    public ImageView createBomb(ImageView ghostImage) {
        //ImageView Setup
        ImageView imageView = new ImageView(MainActivity.this);
        //setting image resource
        imageView.setImageResource(R.drawable.ball);
        imageView.setTag("bomb");
        //setting image position
        imageView.setLayoutParams(new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        //adding view to layout
        layout.addView(imageView);
        imageView.setX(ghostImage.getX());
        imageView.setY(ghostImage.getY());
        imageView.setVisibility(View.INVISIBLE);
        return imageView;
    }









}