package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class SecondActivity extends AppCompatActivity {
    private CanvasDraw mySquareView;
    private Handler handler;
    private GestureDetectorCompat gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        gestureDetector = new GestureDetectorCompat(this, new MyGestureListener());
        handler = new Handler();
        handler.postDelayed(updateSquareRunnable, 400);
        mySquareView = findViewById(R.id.squareView);

//        findViewById(R.id.upp).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Выполните действия при нажатии кнопки
//                mySquareView.setDirection(Direction.Up);
//                mySquareView.drawNewSquare();
//            }
//        });
//
//        findViewById(R.id.down).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Выполните действия при нажатии кнопки
//                mySquareView.setDirection(Direction.Down);
//                mySquareView.drawNewSquare();
//            }
//        });
//
//        findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Выполните действия при нажатии кнопки
//                mySquareView.setDirection(Direction.Left);
//                mySquareView.drawNewSquare();
//            }
//        });
//
//        findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Выполните действия при нажатии кнопки
//                mySquareView.setDirection(Direction.Right);
//                mySquareView.drawNewSquare();
//            }
//        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            // Вызывается при начале касания
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            // Вызывается при свайпе
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                // Свайп влево или вправо
                if (velocityX > 0) {
                    // Свайп вправо
                    mySquareView.setDirection(Direction.Right);
                    mySquareView.drawNewSquare();
                } else {
                    // Свайп влево
                    mySquareView.setDirection(Direction.Left);
                    mySquareView.drawNewSquare();
                }
            } else {
                // Свайп вверх или вниз
                if (velocityY > 0) {
                    // Свайп вниз
                    mySquareView.setDirection(Direction.Down);
                    mySquareView.drawNewSquare();
                } else {
                    // Свайп вверх
                    mySquareView.setDirection(Direction.Up);
                    mySquareView.drawNewSquare();
                }
            }
            return true;
        }
    }

    private Runnable updateSquareRunnable = new Runnable() {
        @Override
        public void run() {
            mySquareView.fps();
            mySquareView.drawNewSquare();
            handler.postDelayed(this, 400);
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateSquareRunnable);
    }

}