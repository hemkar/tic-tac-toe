package com.hemkar.connect3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
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
                    mscoreZeroBoard.setText("0's Score : "+Integer.toString(scoreZero));
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

    public void clearScore(View view){
        Log.i("MSG","inside clearscore method");
        SharedPreferences.Editor editor1= sharedpreferences.edit();
        editor1.clear();
        editor1.commit();
        scoreZero=sharedpreferences.getInt("SCORE_ZERO",0);
        scoreCross=sharedpreferences.getInt("SCORE_CROSS",0);
        mscoreZeroBoard.setText("0's Score : "+Integer.toString(scoreZero));
        mscoreCrossBoard.setText("X's Score :"+Integer.toString(scoreCross));
        //playAgain(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mscoreZeroBoard= (TextView) findViewById(R.id.yellowScoreBoadr);
        mscoreCrossBoard= (TextView) findViewById(R.id.redScoreBoard);
        mscoreZeroBoard.setTypeface(null, Typeface.BOLD);
        mscoreCrossBoard.setTypeface(null, Typeface.BOLD);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        scoreZero=sharedpreferences.getInt("SCORE_ZERO",0);
        scoreCross=sharedpreferences.getInt("SCORE_CROSS",0);
        Log.i("scoreZero",Integer.toString(scoreZero));
        Log.i("scoreCross", Integer.toString(scoreCross));

        mscoreZeroBoard.setText("0's Score : "+Integer.toString(scoreZero));
        mscoreCrossBoard.setText("X's Score :"+Integer.toString(scoreCross));
    }
}
