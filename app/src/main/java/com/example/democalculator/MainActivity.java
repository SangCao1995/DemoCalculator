package com.example.democalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button number0Button, number1Button, number2Button, number3Button, number4Button, number5Button,
        number6Button, number7Button, number8Button, number9Button,
        additionOperationButton, subtractionOperationButton, multiplyOperationButton, divideOperationButton,
        decimalPointButton, sqrtButton, sqrButton, onePerXButton, minusSignButton,
        deleteButton, clearButton,
        equalButton;

    TextView mathOperationsTextView, resultTextView;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;

    SharedPreferences sharedPreferences;
    private final String TEN_RECENT_CALCULATION_PREFERENCES = "ten_recent_calculation";
    ArrayList<String> recentCalculation;

    TextToSpeech textToSpeech;

    private String contentOldOperations = "";
    private double firstNumber = 0;
    private double result = 0;
    private int secondNumberIndex = 0;
    private boolean isOperationPressed = false;
    private String currentOperation = "";
    private boolean isMinusSignPressed = false;
    private boolean isdecimalPointPressed = false;

    private final String ERROR = "hello, what's your name?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number0Button = findViewById(R.id.number0Button);
        number1Button = findViewById(R.id.number1Button);
        number2Button = findViewById(R.id.number2Button);
        number3Button = findViewById(R.id.number3Button);
        number4Button = findViewById(R.id.number4Button);
        number5Button = findViewById(R.id.number5Button);
        number6Button = findViewById(R.id.number6Button);
        number7Button = findViewById(R.id.number7Button);
        number8Button = findViewById(R.id.number8Button);
        number9Button = findViewById(R.id.number9Button);

        additionOperationButton = findViewById(R.id.additionOperationButton);
        subtractionOperationButton = findViewById(R.id.subtractionOperationButton);
        multiplyOperationButton = findViewById(R.id.multiplyOperationButton);
        divideOperationButton = findViewById(R.id.divideOperationButton);

        decimalPointButton = findViewById(R.id.decimalPointButton);
        sqrtButton = findViewById(R.id.sqrtButton);
        sqrButton = findViewById(R.id.sqrButton);
        onePerXButton = findViewById(R.id.onePerXButton);
        minusSignButton = findViewById(R.id.minusSignButton);

        deleteButton = findViewById(R.id.deleteButton);
        clearButton = findViewById(R.id.clearButton);

        equalButton = findViewById(R.id.equalButton);

        mathOperationsTextView = findViewById(R.id.mathOperationsTextView);
        resultTextView = findViewById(R.id.resultTextView);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        recentCalculation = new ArrayList<>();

        setNavigationViewListener();

        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);

                    if (result == textToSpeech.LANG_MISSING_DATA || result == textToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(MainActivity.this, "ko co cac tieng khac", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "hahaha", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void displayFromButtonToScreen(View view) {

        switch (view.getId()) {

            case R.id.number0Button:
                mathOperationsTextView.append("0");
                break;

            case R.id.number1Button:
                mathOperationsTextView.append("1");
                break;

            case R.id.number2Button:
                mathOperationsTextView.append("2");
                break;

            case R.id.number3Button:
                mathOperationsTextView.append("3");
                break;

            case R.id.number4Button:
                mathOperationsTextView.append("4");
                break;

            case R.id.number5Button:
                mathOperationsTextView.append("5");
                break;

            case R.id.number6Button:
                mathOperationsTextView.append("6");
                break;

            case R.id.number7Button:
                mathOperationsTextView.append("7");
                break;

            case R.id.number8Button:
                mathOperationsTextView.append("8");
                break;

            case R.id.number9Button:
                mathOperationsTextView.append("9");
                break;

            case R.id.additionOperationButton:
                contentOldOperations = mathOperationsTextView.getText().toString();

                try {
                    if (!contentOldOperations.isEmpty()) {
                        firstNumber = Double.parseDouble(contentOldOperations);
                        secondNumberIndex = contentOldOperations.length() + 1;
                        mathOperationsTextView.append("+");
                        isOperationPressed = true;
                        currentOperation = "+";
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, ERROR, Toast.LENGTH_SHORT).show();
                    textToSpeech.speak(ERROR, TextToSpeech.QUEUE_FLUSH, null);
                }

                break;

            case R.id.subtractionOperationButton:
                contentOldOperations = mathOperationsTextView.getText().toString();

                try {
                    if (!contentOldOperations.isEmpty()) {
                        firstNumber = Double.parseDouble(contentOldOperations);
                        secondNumberIndex = contentOldOperations.length() + 1;
                        mathOperationsTextView.append("-");
                        isOperationPressed = true;
                        currentOperation = "-";
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, ERROR, Toast.LENGTH_SHORT).show();
                    textToSpeech.speak(ERROR, TextToSpeech.QUEUE_FLUSH, null);
                }

                break;

            case R.id.multiplyOperationButton:
                contentOldOperations = mathOperationsTextView.getText().toString();

                try {
                    if (!contentOldOperations.isEmpty()) {
                        firstNumber = Double.parseDouble(contentOldOperations);
                        secondNumberIndex = contentOldOperations.length() + 1;
                        mathOperationsTextView.append("*");
                        isOperationPressed = true;
                        currentOperation = "*";
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, ERROR, Toast.LENGTH_SHORT).show();
                    textToSpeech.speak(ERROR, TextToSpeech.QUEUE_FLUSH, null);
                }

                break;

            case R.id.divideOperationButton:
                contentOldOperations = mathOperationsTextView.getText().toString();

                try {
                    if (!contentOldOperations.isEmpty()) {
                        firstNumber = Double.parseDouble(contentOldOperations);
                        secondNumberIndex = contentOldOperations.length() + 1;
                        mathOperationsTextView.append("/");
                        isOperationPressed = true;
                        currentOperation = "/";
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, ERROR, Toast.LENGTH_SHORT).show();
                    textToSpeech.speak(ERROR, TextToSpeech.QUEUE_FLUSH, null);
                }

                break;

            case R.id.equalButton:
                if (isOperationPressed) {
                    String contentNewOperations = mathOperationsTextView.getText().toString();
                    double secondNumber = Double.parseDouble(contentNewOperations.substring(secondNumberIndex, contentNewOperations.length()));
                    String resultScreen = "";

                    if (currentOperation.equals("+")) {
                        result = firstNumber + secondNumber;
                    }
                    else if (currentOperation.equals("-")) {
                        result = firstNumber - secondNumber;
                    }
                    else if (currentOperation.equals("*")) {
                        result = firstNumber * secondNumber;
                    }
                    else if (currentOperation.equals("/")){
                        result = firstNumber / secondNumber;
                    }
                    else if (currentOperation.equals("^")) {
                        result = Math.pow(firstNumber, secondNumber);
                    }

                    resultTextView.setText(resultScreen.valueOf(result));

                    recentCalculation.add(String.valueOf(result));

                    //Collections.sort();
//                    for (String s: recentCalculation) {
//                        Log.d("haha", s);
//                    }
                }
                break;

            case R.id.deleteButton:
                contentOldOperations = mathOperationsTextView.getText().toString();

                if (!contentOldOperations.isEmpty()) {
                    String deletion = contentOldOperations.substring(0, contentOldOperations.length() - 1);

                    if (contentOldOperations.length() == 2 && contentOldOperations.substring(0, 1).equals("-")) {
                        mathOperationsTextView.setText("");
                        isMinusSignPressed = false;
                    }
                    else {
                        mathOperationsTextView.setText(deletion);
                    }
                }

                break;

            case R.id.clearButton:
                mathOperationsTextView.setText("");
                resultTextView.setText("0");

                if (isMinusSignPressed == true) {
                    isMinusSignPressed = false;
                }
                if (isdecimalPointPressed == true) {
                    isdecimalPointPressed = false;
                }
                break;

            case R.id.decimalPointButton:
                if (isdecimalPointPressed == true) {
                    break;
                }
                mathOperationsTextView.append(".");
                isdecimalPointPressed = true;
                break;

            case R.id.sqrtButton:
                contentOldOperations = mathOperationsTextView.getText().toString();

                try {
                    if (!contentOldOperations.isEmpty()) {
                        double x = Double.parseDouble(contentOldOperations);
                        result = Math.sqrt(x);
                        String resultScreen = "";
                        resultTextView.setText(resultScreen.valueOf(result));
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, ERROR, Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.sqrButton:
                contentOldOperations = mathOperationsTextView.getText().toString();

                try {
                    if (!contentOldOperations.isEmpty()) {
                        firstNumber = Double.parseDouble(contentOldOperations);
                        mathOperationsTextView.append("^");
                        secondNumberIndex = contentOldOperations.length() + 1;
                        isOperationPressed = true;
                        currentOperation = "^";
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, ERROR, Toast.LENGTH_SHORT).show();
                    textToSpeech.speak(ERROR, TextToSpeech.QUEUE_FLUSH, null);
                }

                break;

            case R.id.onePerXButton:
                contentOldOperations = mathOperationsTextView.getText().toString();

                try {
                    if (!contentOldOperations.isEmpty()) {
                        Double x = Double.parseDouble(contentOldOperations);
                        result = 1 / x;
                        String resultScreen = "";
                        resultTextView.setText(resultScreen.valueOf(result));
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, ERROR, Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.minusSignButton:
                contentOldOperations = mathOperationsTextView.getText().toString();

                if (!contentOldOperations.isEmpty()) {


                    if (isMinusSignPressed == false) {
                        String firstNumberScreen = "-" + contentOldOperations;
                        mathOperationsTextView.setText(firstNumberScreen);
                        isMinusSignPressed = true;
                    }
                    else if (isMinusSignPressed == true){
                        String firstNumberScreen = contentOldOperations.substring(1, contentOldOperations.length());
                        mathOperationsTextView.setText(firstNumberScreen);
                        isMinusSignPressed = false;
                    }
                    isOperationPressed = true;
                }
                break;
        }

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_ten_recent_calculation:
                //recentCalculation.add(String.valueOf(result));
                //TreeSet<String> sortList = new TreeSet<>(recentCalculation);

//                NavigableSet<String> reverseList = recentCalculation.descendingSet();
//                for (String s: reverseList) {
//                    Log.d("haha", s);
//                }
                //Collections.sort(recentCalculation);
                Collections.reverse(recentCalculation);
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : recentCalculation) {
                    stringBuilder.append(s);
                    stringBuilder.append(",");
                }
                Log.d("hihi", stringBuilder + "");

                sharedPreferences = getSharedPreferences(TEN_RECENT_CALCULATION_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("result", stringBuilder + "");
                editor.apply();

                String wordsString = sharedPreferences.getString("result", "");
                String[] wordSplitString = wordsString.split(",");
                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < wordSplitString.length; i++ ){
                    items.add(wordSplitString[i]);
                }
                for (String str : items) {
                    Log.d("hihi", str);
                }

                Intent intent = new Intent(MainActivity.this, RecentCalculationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("recentCalculationList", items);
                intent.putExtras(bundle);
                startActivity(intent);

                //editor.putFloat("result", (float) result);
                //editor.putStringSet("result", recentCalculation);
                //editor.apply();
                //Toast.makeText(MainActivity.this, "Haha", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListener() {
        navigationView.setNavigationItemSelectedListener(this);
    }
}
