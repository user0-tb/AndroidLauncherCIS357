package com.example.androidlauncher_cis357;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;


        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

            float diffY = event2.getY() - event1.getY();
            float diffX = event2.getX() - event1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                }
            }
            return true;
        }

    private void onSwipeLeft() {
        Log.i("LeftSwipe","Swiped Left");
    }

    private void onSwipeRight() {
        Log.i("LeftSwipe","Swiped Right");
    }

    private void onSwipeTop() {

        Log.i("LeftSwipe","Swiped Up");
    }

    private void onSwipeBottom() {
        Log.i("LeftSwipe","Swiped Down");
    }
}