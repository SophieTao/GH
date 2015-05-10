package yt5ny.cs2110.virginia.edu.myapplication;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Ghosts extends AsyncTask<ImageView, ImageView, Void> {


    SingleGhost[] ghosts;
    ImageView[] bombpics;
    TextView bombnum, hp, star;
    Boy boy;
    int score = 0, level;
    final long EASY=95,MEDIUM=40,HARD=10;
    int tickbomb=0,tickscore=0,bomb =10, tickadd=0;


    //constructor
    public Ghosts(Boy b, ImageView[] gsi, TextView Hp, TextView bombs, TextView st, ImageView[] bombpic1, Integer l){
        boy = b;
        hp = Hp;
        bombnum = bombs;
        star = st;
        score = 0;
        level = l;
        ghosts = new SingleGhost[10];
        bombpics = bombpic1;
        for (int i = 0; i<gsi.length;i++){
            SingleGhost s = new SingleGhost(boy,gsi[i]);
            ghosts[i]=s;


        }
        ghosts[0].setKilled(false);
        ghosts[1].setKilled(false);
        ghosts[2].setKilled(false);
        ghosts[3].setKilled(false);
        ghosts[4].setKilled(false);

    }


    @Override
    protected Void doInBackground(ImageView... gs) {

        /*
            1. every 10 ticks, a new ghost will appear on the screen
            2. every 100 ticks, the player earn 1 point on score
            3. every 400 ticks, the player earn 1 extra bomb
            4. every time the ghost touch the player, the player will lose 1 point on HP

         */


        while (true) {
            if (isCancelled()){
                break;
            }
            tickscore++;
            tickbomb++;
            tickadd++;


                for (int i = 0;i<gs.length;i++){
                    if (ghosts[i].isKilled()){
                        tickadd++;
                        break;
                    }
                }

                if (tickscore == 100){
                    score++;
                    if (boy.getHp()<100)
                        boy.setHp(boy.getHp()+1);
                    tickscore =0;
                }

                if (tickbomb == 400){
                    bomb++;
                    tickbomb=0;
                }

                for (int i = 0;i<gs.length;i++){


                    if (!ghosts[i].isKilled() ){
                        ghosts[i].setTick(0);
                        ghosts[i].move();
                        if (ghosts[i].collide(boy)){
                            boy.setHp(boy.getHp()-1);
                        }
                    }
                    else if (ghosts[i].isKilled() ) {

                        if (ghosts[i].getTick() == 10 ){
                            ghosts[i].setTick(-1);
                        }else if (ghosts[i].getTick() == -1){
                            ghosts[i].disappear();
                        }else{
                            ghosts[i].setTick(ghosts[i].getTick()+1);
                        }


                        if (tickadd == 50){
                            tickadd=0;
                            ghosts[i].reset();
                            ghosts[i].setKilled(false);
                        }


                    }

                }

                publishProgress(gs);

                try{
                    if (level==1)
                        Thread.sleep(EASY);
                    else if (level==2)
                        Thread.sleep(MEDIUM);
                    else if (level==3)
                        Thread.sleep(HARD);
                }catch (InterruptedException e){

                }


            }

        return null;


    }

        @Override
        protected void onProgressUpdate(ImageView... gs) {

            for (int i = 0;i<gs.length;i++){
                ImageView ghost = gs[i];
                ghost.setY(ghosts[i].getY());
                ghost.setX(ghosts[i].getX());

                if (!ghosts[i].isKilled()){
                    gs[i].setVisibility(View.VISIBLE);

                }
                else{
                    gs[i].setVisibility(View.INVISIBLE);
                    if (ghosts[i].getTick()<10 && ghosts[i].getTick()>-1){
                        bombpics[i].setVisibility(View.VISIBLE);
                        bombpics[i].setY(ghosts[i].getY());
                        bombpics[i].setX(ghosts[i].getX());

                    }else{

                        bombpics[i].setVisibility(View.INVISIBLE);
                    }


                }

            }
            hp.setText(""+boy.getHp());
            star.setText(""+score);
            bombnum.setText(""+bomb);
        }



        @Override
        protected void onPostExecute(Void v) {
            if (isCancelled()){
                onCancelled();
            }else
                super.onPostExecute(v);

        }


        //getters
        public SingleGhost[] getGhosts() {
            return ghosts;
        }

    public Boy getBoy() {
        return boy;
    }

    public int getTickscore() {
        return tickscore;
    }

    public int getTickbomb() {
        return tickbomb;
    }

    public int getScore() {
        return score;
    }

    public int getBomb() {
        return bomb;
    }

    public ImageView[] getBombpics() {
        return bombpics;
    }

    public TextView getHp() {
        return hp;
    }

    public TextView getBombnum() {
        return bombnum;
    }

    public int getTickadd() {
        return tickadd;
    }

    //setters

    public void setGhosts(SingleGhost[] ghosts) {
        this.ghosts = ghosts;
    }

    public void setBoy(Boy boy) {
        this.boy = boy;
    }

    public void setTickscore(int tick) {
        this.tickscore = tick;
    }

    public void setTickbomb(int tickbomb) {
        this.tickbomb = tickbomb;
    }

    public void setBombpics(ImageView[] bombpics) {
        this.bombpics = bombpics;
    }

    public void setHp(TextView hp) {
        this.hp = hp;
    }

    public void setBombnum(TextView bombnum) {
        this.bombnum = bombnum;
    }

    public void setTickadd(int tickadd) {
        this.tickadd = tickadd;
    }

    public void setBomb(int bomb) {
        this.bomb = bomb;
    }

    public void setScore(int score) {
        this.score = score;
    }
}