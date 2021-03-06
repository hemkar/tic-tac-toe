package com.hemkar.connect3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import static com.hemkar.connect3.R.id.imageView;

public class MainActivity extends AppCompatActivity  {
    //0 is for yellow.
    int activePlayer=0;
    Boolean gameIsActive=true;
    int[] gameState={2,2,2,2,2,2,2,2,2};
    int[][] winingPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    public static final int SCORE_ZERO=0;
    public static final int SCORE_CROSS=0;
    int scoreZero=0;
    int scoreCross=0;
    TextView mscoreZeroBoard;
    TextView mscoreCrossBoard;
    final Context context = this;

    ImageView imageView0;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;

    int i;
    int j;
    int k;

    AlphaAnimation alphaAnimation;


    public void dropIn(View view) {

        ImageView counter = (ImageView) view;

        SharedPreferences.Editor editor= sharedpreferences.edit();
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameIsActive==true) {
            counter.setTranslationY(-1000f);
            gameState[tappedCounter]=activePlayer;
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(100).setDuration(300);
            for(int[] winingPosition: winingPositions){
                if(gameState[winingPosition[0]]==gameState[winingPosition[1]] &&gameState[winingPosition[1]]==gameState[winingPosition[2]] && gameState[winingPosition[0]]!=2){
                    String winner="Cross";
                    i=winingPosition[0];
                    j=winingPosition[1];
                    k=winingPosition[2];
                    Log.i("position",Integer.toString(i)+" "+Integer.toString(j)+" "+Integer.toString(k));
                    if(i==0 && j==1 && k==2){
                        startAnimation(imageView0);
                        startAnimation(imageView1);
                        startAnimation(imageView2);
                    } else if(i==3 && j==4 && k==5){
                        startAnimation(imageView3);
                        startAnimation(imageView4);
                        startAnimation(imageView5);
                    } else if(i==6 && j==7 && k==8){
                        startAnimation(imageView6);
                        startAnimation(imageView7);
                        startAnimation(imageView8);
                    }else if(i==0 && j== 3 && k==6){

                        startAnimation(imageView0);
                        startAnimation(imageView3);
                        startAnimation(imageView6);
                    }else if(i==1 && j==4 && k==7){
                        startAnimation(imageView1);
                        startAnimation(imageView4);
                        startAnimation(imageView7);
                    }else if(i==2 && j==5 && k==8){
                        startAnimation(imageView2);
                        startAnimation(imageView5);
                        startAnimation(imageView8);
                    }else if(i==0 && j==4 && k==8){

                        startAnimation(imageView0);
                        startAnimation(imageView4);
                        startAnimation(imageView8);
                    }else if(i==2 && j==4 && k==6){
                        startAnimation(imageView2);
                        startAnimation(imageView4);
                        startAnimation(imageView6);
                    }

                    String win= Arrays.toString(winingPosition);
                   Log.i("winingPosition ",win);

                    if(gameState[winingPosition[0]]==0){
                        winner="Zero";
                        editor.putInt("SCORE_ZERO",++scoreZero);
                        gameIsActive=false;
                    }
                    else{
                        editor.putInt("SCORE_CROSS",++scoreCross);
                        gameIsActive=false;

                    }
                    editor.commit();
                    mscoreZeroBoard.setText("0's Score :"+Integer.toString(scoreZero));
                    mscoreCrossBoard.setText("X's Score :"+Integer.toString(scoreCross));

                    TextView winnertext= (TextView)findViewById(R.id.winnerMessage);
                    winnertext.setText(winner + " is winner.");
                    LinearLayout layout= (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);


                }else{
                    boolean isOver=true;
                    for(int counterState:gameState){
                        if(counterState==2){
                            isOver=false;
                        }
                    }
                    if(isOver){
                        TextView winnertext= (TextView)findViewById(R.id.winnerMessage);
                        winnertext.setText("Its a draw.");
                        LinearLayout layout= (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view){


            imageView0.clearAnimation();
            imageView1.clearAnimation();
            imageView2.clearAnimation();
            imageView3.clearAnimation();
            imageView4.clearAnimation();
            imageView5.clearAnimation();
            imageView6.clearAnimation();
            imageView7.clearAnimation();
            imageView8.clearAnimation();


        gameIsActive=true;
        LinearLayout layout= (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer=0;
        for(int i=0;i< gameState.length; i++){
            gameState[i]=2;
        }

        GridLayout grid= (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0;i<grid.getChildCount();i++){
            ((ImageView)grid.getChildAt(i)).setImageResource(0);
        }
    }

    void startAnimation(View myView){

        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        myView.startAnimation(alphaAnimation);

    }

    public void clearScore(View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Reset score");
        builder1.setMessage("Are you sure you want to reset score");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor1= sharedpreferences.edit();
                        editor1.clear();
                        editor1.commit();
                        scoreZero=sharedpreferences.getInt("SCORE_ZERO",0);
                        scoreCross=sharedpreferences.getInt("SCORE_CROSS",0);
                        mscoreZeroBoard.setText("0's Score : "+Integer.toString(scoreZero));
                        mscoreCrossBoard.setText("X's Score :"+Integer.toString(scoreCross));
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();

        Log.i("MSG","inside clearscore method");

        //playAgain(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView0= (ImageView) findViewById(imageView);
        imageView1= (ImageView) findViewById(R.id.imageView2);
        imageView2= (ImageView) findViewById(R.id.imageView3);
        imageView3= (ImageView) findViewById(R.id.imageView4);
        imageView4= (ImageView) findViewById(R.id.imageView5);
        imageView5= (ImageView) findViewById(R.id.imageView6);
        imageView6= (ImageView) findViewById(R.id.imageView7);
        imageView7= (ImageView) findViewById(R.id.imageView8);
        imageView8= (ImageView) findViewById(R.id.imageView9);

        mscoreZeroBoard= (TextView) findViewById(R.id.yellowScoreBoadr);
        mscoreCrossBoard= (TextView) findViewById(R.id.redScoreBoard);
        mscoreZeroBoard.setTypeface(null, Typeface.BOLD);
        mscoreCrossBoard.setTypeface(null, Typeface.BOLD);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        scoreZero=sharedpreferences.getInt("SCORE_ZERO",0);
        scoreCross=sharedpreferences.getInt("SCORE_CROSS",0);
        Log.i("scoreZero",Integer.toString(scoreZero));
        Log.i("scoreCross", Integer.toString(scoreCross));

        mscoreZeroBoard.setText("0's Score :"+Integer.toString(scoreZero));
        mscoreCrossBoard.setText("X's Score :"+Integer.toString(scoreCross));
    }


}
