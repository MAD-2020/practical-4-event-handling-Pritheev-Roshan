package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    private Button ButtonLeft;
    private Button ButtonMiddle;
    private Button ButtonRight;
    private int score = 0;
    private TextView scoreText;

    private static String TAG = "Whack A Mole!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonLeft = findViewById(R.id.leftButton);
        ButtonMiddle = findViewById(R.id.middleButton);
        ButtonRight = findViewById(R.id.rightButton);
        scoreText = findViewById(R.id.scoreText);
        Log.v(TAG, "Finished Pre-Initialisation!");


    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");

        ButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Button Right Clicked!");
                doCheck(ButtonLeft);
            }
        });

        ButtonMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Button Middle Clicked!");
                doCheck(ButtonMiddle);
            }
        });

        ButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Button Left Clicked!");
                doCheck(ButtonRight);
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if (checkButton.getText() == "*"){
            score++;
            Log.v(TAG, "Hit, score added!");
            scoreText.setText("" + score);
            setNewMole();
        }
        else{
            score--;
            Log.v(TAG, "Missed, point deducted!");
            scoreText.setText("" + score);
            setNewMole();
        }

        if (score % 10 == 0 && score != 0){
            nextLevelQuery();
        }
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/

        Log.v(TAG, "Advanced option given to user!");

        AlertDialog.Builder AdvancedBuilder = new AlertDialog.Builder(this);

        AdvancedBuilder.setTitle("Warning! Insane Whack-A-Mole incoming!");
        AdvancedBuilder.setMessage("Would you like to advance to advanced mode!");
        AdvancedBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "User Accepts!");
                nextLevel();
            }
        });

        AdvancedBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "User Decline!");
            }
        });

        AlertDialog alert = AdvancedBuilder.create();
        alert.show();
    }

    private void nextLevel(){
        /* Launch advanced page */

        Intent AdvancedMole = new Intent(MainActivity.this, Main2Activity.class);
        AdvancedMole.putExtra("Score", score);
        startActivity(AdvancedMole);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);

        if (randomLocation == 0){
            ButtonLeft.setText("*");
            ButtonMiddle.setText("O");
            ButtonRight.setText("O");
        }
        else if (randomLocation == 1){
            ButtonLeft.setText("O");
            ButtonMiddle.setText("*");
            ButtonRight.setText("O");
        }
        else{
            ButtonLeft.setText("O");
            ButtonMiddle.setText("O");
            ButtonRight.setText("*");
        }
    }
}