package sg.edu.np.WhackAMole;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {

    private int advancedScore;
    private TextView advancedScoreText;
    private Button checkButton;
    private Button currentButton;
    private CountDownTimer myCountDown;
    private CountDownTimer moleTimer;

    private static final int[] BUTTON_IDS = {
            /* HINT:
                Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
                You may use if you wish to change or remove to suit your codes.*/
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
    private static String TAG = "Whack A Mole 2.0!";
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

        myCountDown = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                final Toast toastMessage;
                String message = "Get Ready in " + millisUntilFinished/1000 + " seconds!";
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/1000);
                toastMessage = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                toastMessage.show();

                Timer toastCancel = new Timer();
                toastCancel.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        toastMessage.cancel();
                    }
                }, 1000);

            }

            @Override
            public void onFinish() {
                final Toast go;
                go = Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT);
                go.show();
                myCountDown.cancel();
                Log.v(TAG, "Ready CountDown Complete!");
            }
        };

        myCountDown.start();

    }

    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        moleTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long permilli) {
                setNewMole();
                Log.v(TAG, "New Mole Location!");
            }

            @Override
            public void onFinish() {
                moleTimer.start();
            }
        };

        moleTimer.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        advancedScoreText = (TextView) findViewById(R.id.advancedScoreText);
        Intent receivingEnd = getIntent();
        Bundle scoreReceiver = receivingEnd.getExtras();
        if (scoreReceiver != null){
            advancedScore = (int) scoreReceiver.get("Score");
            advancedScoreText.setText("" + advancedScore);
        }

        Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));

        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            checkButton = findViewById(id);

            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentButton = findViewById(view.getId());
                    doCheck(currentButton);
                    placeMoleTimer();
                    setNewMole();
                }
            });

        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        readyTimer();
        setNewMole();

    }
    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        if (checkButton.getText().toString() == "*"){
            advancedScore++;
            advancedScoreText.setText("" + advancedScore);
            Log.v(TAG, "Hit, score added!");

        }
        else{
            advancedScore--;
            advancedScoreText.setText("" + advancedScore);
            Log.v(TAG, "Missed, score deducted!");

        }
    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);

        for(int count = 0; count<BUTTON_IDS.length;count++){

            checkButton = (Button) findViewById(BUTTON_IDS[count]);
            if (randomLocation == count){
                checkButton.setText("*");
            }
            else {
                checkButton.setText("O");
            }
        }

        Log.v(TAG, "New Mole Location!");

    }
}

