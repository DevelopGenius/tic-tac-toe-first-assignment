package com.example.firstapptictactoe;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private List<ImageView> cellButtons;
    State currPlayer;
    final int arrSize = 3;
    boolean isActive;

    enum State {
        EMPTY,
        X,
        O,
    }

    ImageView whoPlays;
    ImageView winningLine;
    Button resetButton;
    State stateArray[][] = new State[arrSize][arrSize];

    List<WinningPosition> winningPositions = new ArrayList<>(Arrays.asList(
            new WinningPosition(new int[][]{{0, 0}, {0, 1}, {0, 2}}, R.drawable.top_row),
            new WinningPosition(new int[][]{{1, 0}, {1, 1}, {1, 2}}, R.drawable.center_row),
            new WinningPosition(new int[][]{{2, 0}, {2, 1}, {2, 2}}, R.drawable.bottom_row),
            new WinningPosition(new int[][]{{0, 0}, {1, 0}, {2, 0}}, R.drawable.left_col),
            new WinningPosition(new int[][]{{0, 1}, {1, 1}, {2, 1}}, R.drawable.center_col),
            new WinningPosition(new int[][]{{0, 2}, {1, 2}, {2, 2}}, R.drawable.right_col),
            new WinningPosition(new int[][]{{0, 0}, {1, 1}, {2, 2}}, R.drawable.left_to_right_diagonal),
            new WinningPosition(new int[][]{{0, 2}, {1, 1}, {2, 0}}, R.drawable.right_to_left_diagonal)
    ));

    private State getCellStateFromIndex(int index) {
        return stateArray[index / 3][index % 3];
    }

    private void setCellState(int index, State state) {
        stateArray[index / 3][index % 3] = state;
    }

    private void endGame(){
        this.resetButton.setVisibility(View.VISIBLE);
        isActive = false;
    }
    public void boardClick(View cellView) {
        int index = Integer.parseInt((String) cellView.getContentDescription());

        if (isActive && this.getCellStateFromIndex(index) == State.EMPTY) {
            setCellState(index, currPlayer);
            ImageView cellImgView = (ImageView) cellView;
            cellImgView.setImageResource((currPlayer == State.X) ? R.drawable.x : R.drawable.o);

            if (isWinner()) {
                whoPlays.setImageResource((currPlayer == State.X) ? R.drawable.xwin : R.drawable.owin);
                endGame();
            } else {
                if (isStalemate()) {
                    whoPlays.setImageResource(R.drawable.nowin);
                    endGame();
                } else {
                    currPlayer = (currPlayer == State.X) ? State.O : State.X;
                    whoPlays.setImageResource((currPlayer == State.X) ? R.drawable.xplay : R.drawable.oplay);
                }
            }
        }
    }

    boolean isStalemate() {
        boolean isStalemateFlag = true;
        for (int i = 0; i < arrSize; i++) {
            for (int j = 0; j < arrSize; j++) {
                if (stateArray[i][j] == State.EMPTY) {
                    isStalemateFlag = false;
                }
            }
        }
        return isStalemateFlag;
    }

    boolean isWinner() {
        for (WinningPosition winPosition : winningPositions) {
            if (stateArray[winPosition.winningCoordinates[0][0]][winPosition.winningCoordinates[0][1]] != State.EMPTY &&
                    stateArray[winPosition.winningCoordinates[0][0]][winPosition.winningCoordinates[0][1]]
                            == stateArray[winPosition.winningCoordinates[1][0]][winPosition.winningCoordinates[1][1]] &&
                    stateArray[winPosition.winningCoordinates[1][0]][winPosition.winningCoordinates[1][1]]
                            == stateArray[winPosition.winningCoordinates[2][0]][winPosition.winningCoordinates[2][1]]
            ) {
                winningLine.setImageResource(winPosition.imageId);
                return true;
            }
        }

        return false;
    }

    public void reset() {
        for (int i = 0; i < arrSize; i++) {
            for (int j = 0; j < arrSize; j++) {
                stateArray[i][j] = State.EMPTY;
            }
        }

        currPlayer = State.X;
        isActive = true;
        whoPlays.setImageResource(R.drawable.xplay);
        this.cellButtons.forEach(cellButton -> cellButton.setImageResource(R.drawable.empty));
        this.winningLine.setImageResource(R.drawable.empty);
        this.resetButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.cellButtons = Stream.of(R.id.cellBtn1, R.id.cellBtn2, R.id.cellBtn3, R.id.cellBtn4, R.id.cellBtn5, R.id.cellBtn6, R.id.cellBtn7, R.id.cellBtn8, R.id.cellBtn9)
                .map(id -> (ImageView) findViewById(id))
                .collect(Collectors.toList());

        whoPlays = findViewById(R.id.whoPlays);
        winningLine = findViewById(R.id.winningLine);
        resetButton = findViewById(R.id.reset);

        resetButton.setOnClickListener((view) -> reset());

        this.cellButtons.forEach((cellButton) -> cellButton.setOnClickListener(this::boardClick));

        reset();
    }
}