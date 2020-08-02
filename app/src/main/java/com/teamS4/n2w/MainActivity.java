package com.teamS4.n2w;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private Button button;
    private EditText et1, et2;
    private TextView ht1;
    private ConstraintLayout lay1;
    private Handler mainHandler = new Handler();
    private Handler mainHandler1 = new Handler();
    private final static String LOG_TAG =
            MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //required
        super.onCreate(savedInstanceState);


        //setup UI
        initializeUI();
        //initiate animation
        initializeAnimation();


    }

    //Onclick function for Button
    public void didTapButton(View view) {

        final Animation myAnim = AnimationUtils
                .loadAnimation(this, R.anim.bounce);
        button.startAnimation(myAnim);

        int str = 0;
        //this if-else is given because Integer.parseInt() can work on upto  Integer.MAX_VALUE nearly 11-digit number
        if (!et1.getText().toString().equals("") && et1.getText().toString().length() < 10) {
            str = Integer.parseInt(et1.getText().toString());
            //calling of Worker's Thread
            threadOne obj = new threadOne(str);
            obj.start();

        } else
            et2.setText("Exceed limit");

        hideKeyboard(view);


        //set the TextField empty after giving the number
        // et1.setText("");
    }

    // Hide keyboard
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //Overrided method for onClick interface
    @Override
    public void onClick(View view) {
        if (view == lay1)
            hideKeyboard(view);
    }

    private void initializeUI() {
        //set the layout
        setContentView(R.layout.activity_main);

        //initialize the views
        button = findViewById(R.id.button);
        et2 = findViewById(R.id.editText1);
        et1 = findViewById(R.id.editText);
        ht1 = findViewById(R.id.headText);
        lay1 = findViewById(R.id.layout1);

        lay1.setOnClickListener(MainActivity.this);
    }


    private void initializeAnimation() {
        //set position TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta
        final Animation animation = new TranslateAnimation(0, 0, 500, 0);
        // set Animation for 4 sec
        animation.setDuration(4000);
        //for button stops in the new position.
        animation.setFillAfter(true);
        button.startAnimation(animation);

        final Animation animation1 = new TranslateAnimation(0, 0, 500, 0);
        animation.setDuration(4000);
        animation.setFillAfter(true);
        ht1.startAnimation(animation1);


        Animation aniFade = AnimationUtils
                .loadAnimation(getApplicationContext(), R.anim.fadein);
        et1.startAnimation(aniFade);

        et2.startAnimation(aniFade);
    }

    //printing on editText function
    public void print(String text) {
        et2.setText(" " + text + " only");

        //NOT WORKING NEED HELP

//        Log.d(LOG_TAG, "print(String)" + text);
//        mainHandler.post(new Runnable() {
//            @Override
//            public void run() {
//              et2.setText(" " + text + " only");
//            }
//        });

    }


    public class threadOne extends Thread {

        MainActivity obj_m = new MainActivity();
        logic obj_l = new logic(obj_m);
        int n;

        public threadOne(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            Log.d(LOG_TAG, String.valueOf(n));
            final String w = obj_l.process(n);

//         et2.setText(String.valueOf(n));

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    et2.setText("" + w + " only");
                }
            });
        }
    }

}
