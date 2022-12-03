package com.example.firstapptictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    State currPlayer;
    final int arrSize = 3;

    enum State {
        EMPTY,
        X,
        O,
    }

    State stateArray[][] = new State[arrSize][arrSize];

    int[][][] winPositions = {{{0,0}, {0,1}, {0,2}}, {{1,0}, {1,1}, {1,2}}, {{2,0}, {2,1}, {2,2}},
                             {{0,0}, {1,0}, {2,0}}, {{0,1}, {1,1}, {2,1}}, {{0,2}, {1,2}, {2,2}},
                             {{0,0}, {1,1}, {2,2}}, {{0,2}, {1,1}, {2,0}}};

    public void onClick () {

    }

    boolean isWinner () {
        for(int[][] winPosition : winPositions) {
            if (stateArray[winPosition[0][0]][winPosition[0][1]] != State.EMPTY &&
                    stateArray[winPosition[0][0]][winPosition[0][1]] == stateArray[winPosition[1][0]][winPosition[1][1]] &&
                    stateArray[winPosition[1][0]][winPosition[1][1]] == stateArray[winPosition[2][0]][winPosition[2][1]]
            ) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i = 0; i < arrSize; i++) {
            for(int j = 0; j < arrSize; j++) {
                stateArray[i][j] = State.EMPTY;
            }
        }
    }
}