package com.hemkar.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    //0 is for yellow.
    int activePlayer=0;
    Boolean gameIsActive=true;
    int[] gameState={2,2,2,2,2,2,2,2,2};
    int[][] winingPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;


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
                   gameIsActive=false;
                   if(gameState[winingPosition[0]]==0){
                        winner="Zero";

                   }
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
