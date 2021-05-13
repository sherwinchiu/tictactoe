package com.sherwin.basicfunctions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainGameActivity extends AppCompatActivity implements View.OnClickListener{
    private final char CIRCLE = 1;
    private final char CROSS = 2;
    private final char MAX_MOVES = 9;
    public int buttonIds[] = {
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9
    };
    private Button buttonList[] = new Button[9];
    private int moves[][] = {  {0, 0, 0},   // 0 is blank
                                {0, 0, 0},   // 1 is O circle
                                {0, 0, 0}}; // 2 is X cross
    private static int scores[] = {0, 0};
    private char currentMove = 1; // 1 for O, 2 for X
    private int victoryScore = 0;
    private TextView score1TextView;
    private TextView score2TextView;
    private int playerPieces[] = {
            R.id.imageView1,
            R.id.imageView2,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        Intent intent = getIntent(); // gets the intend sent to this activity
        String player1Name = intent.getStringExtra(MainActivity.PLAYER_1); // access property of MainActivity that has EXTRA_MESSAGE contained in it
        String player2Name = intent.getStringExtra(MainActivity.PLAYER_2);

        TextView player1TextView = (TextView) findViewById(R.id.player1TextView);
        TextView player2TextView = (TextView) findViewById(R.id.player2TextView);
        player1TextView.setText(player1Name);
        player2TextView.setText(player2Name);
        score1TextView = (TextView) findViewById(R.id.score1TextView);
        score2TextView = (TextView) findViewById(R.id.score2TextView);
        score1TextView.setText(String.valueOf(scores[0]));
        score2TextView.setText(String.valueOf(scores[1]));
        initializeButtons();
    }
    /**
     * Adds button views to list
     */
    private void initializeButtons() {
        for (int i = 0; i < buttonList.length; i++) {
            buttonList[i] = (Button) findViewById(buttonIds[i]);
            buttonList[i].setOnClickListener(this);
        }
    }
    /**
     * Changes the tile a user presses
     * @author Sherwin Chiu
     * @return Character representing a circle or a cross (O or X)
     */
    private char changeTile(){
        if (currentMove == CIRCLE){
            currentMove = CROSS;
            return CIRCLE;
        } else {
            currentMove = CIRCLE;
            return CROSS;
        }
    }
    /**
     * Initializes the 9 buttons used for the TicTacToe grid
     * @author Sherwin Chiu
     */

    /**
     * Resets the moves on the board
     * @author Sherwin Chiu
     */
    private void resetMoves(int player){
        for(int i = 0; i < moves.length; i++){
            for(int j = 0; j < moves.length; j++){
               moves[i][j] = 0;
               currentMove = CIRCLE;
            }
        }
        if(player > 0)
            scores[player-1]++;
        for(int i = 0; i < buttonList.length; i++){
            buttonList[i].setText("");
            buttonList[i].setForeground(null);
        }
    }
    /**
     * Checks if game has been won by a player, or if their is a draw
     * @author Sherwin Chiu
     */
    private void winGame(){
        for (int players = 1; players < 3; players++) {
            for(int i = 0; i < moves.length; i++){
                for(int j = 0; j < moves.length; j++) {
                // Check if won horizontal(---)
                    victoryScore += checkWon(players, i, j);
                }
                checkScore(victoryScore, players);
                victoryScore = 0;
                // Check if won vertical (|||)
                for(int j = 0; j < moves.length; j++){
                    victoryScore += checkWon(players, j, i);
                }
                checkScore(victoryScore, players);
                victoryScore = 0;
            }
            // Check if won diagonal (\)
            for(int i = 0; i < moves.length; i++){
                victoryScore += checkWon(players, i, i);
            }
            checkScore(victoryScore, players);
            victoryScore = 0;
            for(int i = 0; i < moves.length; i++){
                victoryScore += checkWon(players, i, 2-i);
            }
            checkScore(victoryScore, players);
            victoryScore = 0;
        }
        // Check if draw
        checkDraw();
    }

    /**
     * Checks who gets a 3-in-a-row
     * @param player Specifies the player to check who wins
     * @param i Specifies which index to put for rows
     * @param j Specifies which index to put for columns
     * @return Returns 1 or 0 based on if move exists in that position
     */
    private int checkWon(int player, int i, int j){
        if (player == moves[i][j])
            return 1;
        return 0;
    }

    /**
     * Checks the score for the 3-in-a-row and runs the method that adds score and resets
     * @param score Gets the score for a 3-in-a-row
     * @param player Gets the player who placed the 3-in-a-row
     */
    private void checkScore(int score, int player){
        if(score == 3)
            resetMoves(player);
    }

    /**
     * Checks if a draw happens in the game
     * @author Sherwin Chiu
     */
    private void checkDraw(){
        int counter = 0;
        for(int i = 0; i < buttonList.length; i++){
            if (buttonList[i].getText().length()>0){
                counter++;
            }
        }
        if (counter == MAX_MOVES)
            resetMoves(0);
    }
    @Override
    public void onClick(View view){
        int buttonNum = view.getId();
        for(int i = 0; i < buttonIds.length; i++){
            if (buttonNum == buttonIds[i] && !("1".equals(buttonList[i].getText()) || "2".equals(buttonList[i].getText()))){
                int move = changeTile();
                moves[(int) (i / 3)][i % 3] = move;
                buttonList[i].setText(String.valueOf(move));
                buttonList[i].setForeground(findViewById(playerPieces[move-1]).getForeground());
            }
        }
        winGame();
        score1TextView.setText(String.valueOf(scores[0]));
        score2TextView.setText(String.valueOf(scores[1]));
    }

}