package cis436mn.project3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static cis436mn.project3.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    int seconds = 5;

    EditText timeIn, messageIn;
    Button setButton, startButton;
    TextView textView; //for debugging
    Spinner spinner;

    public void btnStart_Service_onClick(View view){}
    //public void btnStop_Service_onClick(View view){} //in case we need to have a service stop button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeIn = (EditText) findViewById(R.id.timeIn);              //linking variables to xml counterparts
        setButton = (Button) findViewById(R.id.setButton);
        messageIn = (EditText) findViewById(R.id.messageIn);
        startButton = (Button) findViewById(R.id.startButton);
        textView = (TextView) findViewById(R.id.textView);


        setButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String text = timeIn.getText().toString();
                spinner = (Spinner) findViewById(R.id.highestNotifSpinner);

                if(!text.equalsIgnoreCase("")){
                    seconds = Integer.valueOf(text);

                    if(seconds%5 == 0  && seconds >= 5 && seconds <= 120 ) {  //condition for allowing time count down value

                        populateSpinner(seconds);

                        startButton.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View view){
                                String text = timeIn.getText().toString();

                                if(!text.equalsIgnoreCase("")){

                                    Intent serviceTent = new Intent(getBaseContext(), MyService.class);
                                    serviceTent.putExtra("time", seconds);
                                    serviceTent.putExtra("getMessageText", messageIn.getText().toString());
                                    serviceTent.putExtra("highSpin", spinner.getSelectedItemPosition());
                                    startService(serviceTent);

                                }
                            }
                        });

                        Toast.makeText(getApplicationContext(), "Count down set! Check Highest Notification Dropdown", Toast.LENGTH_LONG).show();
                    }

                    else{
                        timeIn.setText(""); // Resets text field
                        Toast.makeText(getApplicationContext(), "Invalid Entry! Try again!", Toast.LENGTH_LONG).show();
                    }

                }

                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter value for time to count down!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        });
    }

    public void populateSpinner(int seconds)    //function prints out dynamic spinner data
    {
        ArrayList<String> highNotif = new ArrayList<String>(); //Array list for Highest Notifications
        Spinner spinner = (Spinner) findViewById(R.id.highestNotifSpinner); //Setting the Highest Notification spinner

        String[] spinValues = {"1","5","10","20","30","60","90"}; //default array values
        int length = 6; //Default value will be to add all highest values to notification

        spinner.setEnabled(true);


        if(seconds > 90)
        {
            length = 7;
        }
        else if (seconds > 60 && seconds <= 90)
        {
            length = 6;
        }
        else if (seconds > 30 && seconds <= 60)
        {
            length = 5;
        }
        else if (seconds > 20 && seconds <= 30)
        {
            length = 4;
        }
        else if (seconds > 10 && seconds <= 20)
        {
            length = 3;
        }
        else if (seconds > 5 && seconds <= 10)
        {
            length = 2;
        }
        else
        {
            length = 1;
        }

        for (int i = 0; i < length; i++) //Populate the spinner based on how many notfications it needs
        {
            highNotif.add(" " + spinValues[i] + " Second(s)"); //add it to the spinner
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, highNotif);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }


}

