package com.example.bwargo.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button a1, a2, a3, b1, b2, b3, c1, c2, c3,clear;
    Button[] bArray;
    ArrayList<String> sArray = new ArrayList<String>();
    boolean turn = true;
    //X = true, O = false
    int turn_count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a1 = (Button) findViewById(R.id.A1);
        a2 = (Button) findViewById(R.id.A2);
        a3 = (Button) findViewById(R.id.A3);
        b1 = (Button) findViewById(R.id.B1);
        b2 = (Button) findViewById(R.id.B2);
        b3 = (Button) findViewById(R.id.B3);
        c1 = (Button) findViewById(R.id.C1);
        c2 = (Button) findViewById(R.id.C2);
        c3 = (Button) findViewById(R.id.C3);
        clear = (Button) findViewById(R.id.clear);

        bArray = new Button[]{a1, a2, a3, b1, b2, b3, c1, c2, c3};

        for(Button b : bArray){
            b.setOnClickListener(this);
        }

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turn = true;
                turn_count = 0;
                toggleAllButtons(true);
            }
        });
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turn = true;
                turn_count = 0;
                toggleAllButtons(true);
                toast("Board Cleared, New Game Started");
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        turn_count = savedInstanceState.getInt("turn_count");
        turn = savedInstanceState.getBoolean("turn");
        sArray = savedInstanceState.getStringArrayList("sArray");
        if (sArray != null){
            if(sArray.size() == bArray.length) {
                for (int i = 0; i < sArray.size(); i++) {
                    if(sArray.get(i).length() > 0) {
                        bArray[i].setText(sArray.get(i));
                        bArray[i].setBackgroundColor(Color.LTGRAY);
                        bArray[i].setClickable(false);
                    }
                }
            }
        }
        checkForWinner();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        buttonClicked(b);
        //toast(b.getId() + " clicked");
    }

    public void buttonClicked(Button b){

        if(turn){
            b.setText("X");
        }else{
            b.setText("O");
        }
        b.setBackgroundColor(Color.LTGRAY);
        b.setClickable(false);
        turn = !turn;
        turn_count++;
        checkForWinner();
    }

    private void checkForWinner() {
        boolean winner = false;

        //horizontal
        if(a1.getText() == a2.getText() && a2.getText() == a3.getText() && !a1.isClickable()){
           winner = true;
        }
        else if(b1.getText() == b2.getText() && b2.getText() == b3.getText() && !b1.isClickable()){
            winner = true;
        }
        else if(c1.getText() == c2.getText() && c2.getText() == c3.getText() && !c1.isClickable()){
            winner = true;
        }

        //vertical
        else if(a1.getText() == b1.getText() && b1.getText() == c1.getText() && !a1.isClickable()){
            winner = true;
        }
        else if(a2.getText() == b2.getText() && b2.getText() == c2.getText() && !b2.isClickable()){
            winner = true;
        }
        else if(a3.getText() == b3.getText() && b3.getText() == c3.getText() && !c3.isClickable()){
            winner = true;
        }
        //diagonal
        else if(a1.getText() == b2.getText() && b2.getText() == c3.getText() && !a1.isClickable()){
            winner = true;
        }
        else if(a3.getText() == b2.getText() && b2.getText() == c1.getText() && !b2.isClickable()){
            winner = true;
        }

        if(winner){
            if(!turn){
                toast("X Wins");
            }else{
                toast("O Wins");
            }
            toggleAllButtons(false);
        }else if(turn_count == 9){
            toast("It's a Draw!");
        }
    }

    private void toggleAllButtons(boolean clickable) {
        for(Button b : bArray){
            b.setClickable(clickable);
            if(clickable){
                b.setBackgroundColor(Color.parseColor("#f7f7f7"));
                b.setText("");
            }else{
                b.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    private void toast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        savedInstanceState.putInt("turn_count", turn_count);
        savedInstanceState.putBoolean("turn", turn);
        ArrayList<String> sArray = new ArrayList<>();
        for(Button b : bArray){
            sArray.add(b.getText().toString());
        }

        savedInstanceState.putStringArrayList("sArray",sArray);
        super.onSaveInstanceState(savedInstanceState);
    }
}
