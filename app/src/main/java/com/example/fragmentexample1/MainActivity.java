package com.example.fragmentexample1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private boolean isFragmentDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";   //to handle rotation of the screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.open_button);

        //If returning from a configuration change,get
        //the fragment state and set the button text

        if (savedInstanceState != null) {
            isFragmentDisplayed =
                    savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                // If the fragment is displayed, change button to "close".
                mButton.setText(R.string.close);
            }
        }

        //set the click listener for the button
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed){
                    displayFragment();
                }else{
                    closeFragment();
                }
            }
        });
    }


    //displayFragment -- when the user clicks the btn to open a fragment
    public void displayFragment(){
        SimpleFragment simpleFragment = SimpleFragment.newInstance();
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.fragment_container,simpleFragment).addToBackStack(null).commit();
        //update the button text
        mButton.setText(R.string.close);
        //set boolean flag to indicate fragment is open
        isFragmentDisplayed = true;
    }

    //closeFragment -- when the user clicks the btn to close a fragment
    public void closeFragment(){
        //get the fragment manager
        FragmentManager fragmentManager =getSupportFragmentManager();
        //check to see if the fragment is already shown
        SimpleFragment simpleFragment = (SimpleFragment)fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null){
            //create and commit the transaction to remove the fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }

        //update the button text
        mButton.setText(R.string.open);
        //set boolean flag to indicate fragment is closed
        isFragmentDisplayed = false;
    }
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }
}
