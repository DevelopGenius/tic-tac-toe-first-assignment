package com.example.firstapptictactoe;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

    State stateArray[][] = new State[arrSize][arrSize];


    int[][][] winPositions = {
            {{0, 0}, {0, 1}, {0, 2}},
            {{1, 0}, {1, 1}, {1, 2}},
            {{2, 0}, {2, 1}, {2, 2}},
            {{0, 0}, {1, 0}, {2, 0}},
            {{0, 1}, {1, 1}, {2, 1}},
            {{0, 2}, {1, 2}, {2, 2}},
            {{0, 0}, {1, 1}, {2, 2}},
            {{0, 2}, {1, 1}, {2, 0}}};

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

    public void boardClick(View view) {
        int index = Integer.parseInt((String) view.getContentDescription());

        if (isActive && this.getCellStateFromIndex(index) == State.EMPTY) {
            setCellState(index, currPlayer);
            ImageView imgView = (ImageView) view;
            if (currPlayer == State.X) {
                imgView.setImageResource(R.drawable.x);
            } else {
                imgView.setImageResource(R.drawable.o);
            }

            if (isWinner()) {

                isActive = false;
            } else {
                if (isStalemate()) {
                    isActive = false;
                } else {
                    currPlayer = (currPlayer == State.X) ? State.O : State.X;
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
                ImageView imageView = findViewById(R.id.winningLine);
                imageView.setImageResource(winPosition.imageId);
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < arrSize; i++) {
            for (int j = 0; j < arrSize; j++) {
                stateArray[i][j] = State.EMPTY;
            }
        }

//        winningPositions.add()

        currPlayer = State.X;
        isActive = true;
        this.cellButtons = Stream.of(R.id.cellBtn1, R.id.cellBtn2, R.id.cellBtn3, R.id.cellBtn4, R.id.cellBtn5, R.id.cellBtn6, R.id.cellBtn7, R.id.cellBtn8, R.id.cellBtn9)
                .map(id -> (ImageView) findViewById(id))
                .collect(Collectors.toList());

        this.cellButtons.forEach((cellButton) -> cellButton.setOnClickListener(this::boardClick));
    }
}