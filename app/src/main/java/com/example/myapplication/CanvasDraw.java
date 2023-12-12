package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CanvasDraw  extends View {
        private Bitmap imageBitmap;
        ArrayList<XY> snake = new ArrayList<>();
        ArrayList<XY> squares = new ArrayList<>();
        XY food = new XY(7,5);
        public Direction currentDir = Direction.Zero;


        public void onInit() {
            Collections.addAll(snake, new XY(5,5), new XY(5,6),new XY(5,7));
        }
        public CanvasDraw(Context context, AttributeSet attrs) {
            super(context, attrs);
            onInit();
        }

        public void setDirection(Direction direction) {
            currentDir = direction;
        }

        private void generateRandomSquares() {
            for (int row = 0; row < 19; row++) {
                for (int col = 0; col < 9; col++) {
                    squares.add(new XY(row, col));
                }
            }
        }
    public void fps() {
        if (currentDir == Direction.Left) {
            snake.add(0, new XY(snake.get(0).getRow(), snake.get(0).getCol()-1));
        }
        if (currentDir == Direction.Right) {
            snake.add(0, new XY(snake.get(0).getRow(), snake.get(0).getCol()+1));
        }
        if (currentDir == Direction.Up) {
            snake.add(0, new XY(snake.get(0).getRow()-1, snake.get(0).getCol()));
        }
        if (currentDir == Direction.Down) {
            snake.add(0, new XY(snake.get(0).getRow()+1, snake.get(0).getCol()));
        }
        if (food.getCol() ==  snake.get(0).getCol() && food.getRow() == snake.get(0).getRow()) {
            generateRandomSquares();
            squares.removeAll(snake);
            Random random = new Random();
            int randomIndex = random.nextInt(squares.size());
            food = squares.get(randomIndex);
            squares.clear();
        } else if (currentDir != Direction.Zero) {
            snake.remove(snake.size() - 1);
        }

    }
        public void drawNewSquare() {
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apple);
            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 100, 100, false);
            // Отрисовка квадратов
            for (int row = 0; row < 19; row++) {
                for (int col = 0; col < 9; col++) {
                    paint.setColor(Color.GRAY);
                    for (XY value: snake) {
                        if (row == value.getRow() && col == value.getCol()) {
                            paint.setColor(Color.GREEN);
                        }
                    }
                    int left = col * (100 + 10);
                    int top = row * (100 + 10);
                    int right = left + 100;
                    int bottom = top + 100;
                    canvas.drawRect(left, top, right, bottom, paint);
                    if (row == food.getRow() && col == food.getCol()) {
                        canvas.drawBitmap(imageBitmap, left, top, paint);
                    }
                }
            }
        }
    }

    class XY {
        private int row;
        private int col;
        public XY(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

    }