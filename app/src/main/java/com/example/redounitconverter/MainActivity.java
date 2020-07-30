package com.example.redounitconverter;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.service.autofill.CharSequenceTransformation;
import android.service.autofill.TextValueSanitizer;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.example.redounitconverter.R.array.lengthUnits;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner chooseCategory;
    private Spinner chooseUnit;
    private Spinner convertingTo;
    private EditText inputVal;
    private Button clickToConvert;
    private TextView convertedVal;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code to create drop down menu to choose what category of unit you want to covert
        chooseCategory = findViewById(R.id.chooseUnit);
        ArrayAdapter<CharSequence> unitType = ArrayAdapter.createFromResource(this, R.array.baseUnits, android.R.layout.simple_spinner_item);
        unitType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseCategory.setAdapter(unitType);
        chooseCategory.setOnItemSelectedListener(this);

        //Getting the number selected
        inputVal = findViewById(R.id.inputVal);

        //Creating the button to click after value is entered
        clickToConvert = findViewById(R.id.clickToConvert);

        clickToConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the string of the unit that is being converted from
                String convertFrom = chooseUnit.getSelectedItem().toString();

                //Get the string of the unit that is being converted to
                String convertTo = convertingTo.getSelectedItem().toString();

                //Call the function to do the conversion, gets passed the unit category, the original unit, and the desired unit
                calculateConversion(category, convertFrom, convertTo);
            }
        });
    }

    public void calculateConversion(String unit, String convertFrom, String convertTo) {
        //Get the number entered as a string, convert to a double and declare auxiliary variables
        String valueEntered = inputVal.getText().toString();
        double result = Double.parseDouble(valueEntered);

        if (unit.equals("Length"))
        {
            convertLength(result, convertFrom, convertTo);

        }

        if (unit.equals("Mass"))
        {
            convertMass(result, convertFrom, convertTo);
        }

        if (unit.equals("Temperature"))
        {
            convertTemp(result, convertFrom, convertTo);
        }

        if (unit.equals("Time"))
        {
            convertTime(result, convertFrom, convertTo);
        }
    }

    public void convertLength(double result, String convertFrom, String convertTo)
    {
        //Create two arrays that store the unit and conversion factor to loop through later on
        String[] lengthUnits = new String[]{"Inches", "Feet", "Yards", "Miles", "Millimeter", "Centimeters", "Meters", "Kilometers"};
        double[] lengthValues = new double[]{0.0254, .3048, .9144, 1609.344, .001, .01, 1, 1000};
        int userConvertFrom = 0, userConvertTo = 0;
        DecimalFormat setDecimal = new DecimalFormat("#.####");
        setDecimal.setRoundingMode(RoundingMode.CEILING);
        TextView displayResult = findViewById(R.id.convertedVal);

        //Loop through units array until the unit is found, keep track of the index of the unit, and then multiply the given value by the calculated conversion value
        for (int i = 0; i < lengthUnits.length; i++) {
            if (lengthUnits[i].equals(convertFrom)) {
                userConvertFrom = i;
            }
            if (lengthUnits[i].equals(convertTo)) {
                userConvertTo = i;
            }
        }
        //Calculate result and display the output
        result = result * lengthValues[userConvertFrom] / lengthValues[userConvertTo];
        if(result > 1000000 || result < 0.001)
        {
            displayResult.setText(String.valueOf(String.format("%.3E", result)));
        }
        else
        {
            displayResult.setText(String.valueOf(setDecimal.format(result)));
        }
    }

    public void convertMass(double result, String convertFrom, String convertTo)
    {
        //Create two arrays that store the unit and conversion factor to loop through later on
        String[] massUnits = new String[]{"Ounces", "Pounds", "Milligrams", "Grams", "Kilograms"};
        double[] massValues = new double[]{0.0625, 1, 2.2046e-6, 0.00220462, 2.20462};
        int userConvertFrom = 0, userConvertTo = 0;
        DecimalFormat setDecimal = new DecimalFormat("#.####");
        setDecimal.setRoundingMode(RoundingMode.CEILING);
        TextView displayResult = findViewById(R.id.convertedVal);

        //Loop through units array until the unit is found, keep track of the index of the unit, and then multiply the given value by the calculated conversion value
        for (int i = 0; i < massUnits.length; i++) {
            if (massUnits[i].equals(convertFrom)) {
                userConvertFrom = i;
            }
            if (massUnits[i].equals(convertTo)) {
                userConvertTo = i;
            }
        }
        result = result * massValues[userConvertFrom] / massValues[userConvertTo];
        if(result > 1000000 || result < 0.001)
        {
            displayResult.setText(String.valueOf(String.format("%.3E", result)));
        }
        else {
            displayResult.setText(String.valueOf(setDecimal.format(result)));
        }
    }

    public void convertTemp(double result, String convertFrom, String convertTo)
    {
        DecimalFormat setDecimal = new DecimalFormat("#.####");
        setDecimal.setRoundingMode(RoundingMode.CEILING);
        TextView displayResult = findViewById(R.id.convertedVal);

        // Converting from Kelvin
        if(convertFrom.equals("Kelvin"))
        {
            if (convertTo.equals("Fahrenheit"))
            {
                result = (result - 273.15) * 9 / 5 + 32;
            }

            if(convertTo.equals("Celsius"))
            {
                result = result - 273.15;
            }

            if(result > 1000000 || result < 0.001)
            {
                displayResult.setText(String.valueOf(String.format("%.3E", result)));
            }
            else
            {
                displayResult.setText(String.valueOf(setDecimal.format(result)));
            }
        }

        //Converting from Fahrenheit
        if(convertFrom.equals("Fahrenheit"))
        {
            if(convertTo.equals("Kelvin"))
            {
                result = ((result - 32) * 5/9) + 273.15;
            }
            if(convertTo.equals("Celsius")) {
                result = (result - 32) * 5/9;
            }

            if(result > 1000000 || result < 0.001)
            {
                displayResult.setText(String.valueOf(String.format("%.3E", result)));
            }
            else
            {
                displayResult.setText(String.valueOf(setDecimal.format(result)));
            }
        }

        //Converting from Celsius
        if(convertFrom.equals("Celsius"))
        {
            if(convertTo.equals("Kelvin"))
            {
                result = result + 273.15;
            }
            if (convertTo.equals("Fahrenheit"))
            {
                result = result * 9 / 5 + 32;
            }

            if(result > 1000000 || result < 0.001)
            {
                displayResult.setText(String.valueOf(String.format("%.3E", result)));
            }
            else
            {
                displayResult.setText(String.valueOf(setDecimal.format(result)));
            }
        }
    }

    public void convertTime(double result, String convertFrom, String convertTo)
    {
        DecimalFormat setDecimal = new DecimalFormat("#.####");
        setDecimal.setRoundingMode(RoundingMode.CEILING);
        TextView displayResult = findViewById(R.id.convertedVal);
        int userConvertFrom = 0, userConvertTo = 0;

        //Create two arrays that store the unit and conversion factor to loop through later on
        String[] timeUnits = new String[]{"Seconds", "Minutes", "Hours"};
        double[] timeValues = new double[]{60, 1, 0.0166667};

        //Loop through units array until the unit is found, keep track of the index of the unit, and then multiply the given value by the calculated conversion value
        for (int i = 0; i < timeUnits.length; i++) {
            if (timeUnits[i].equals(convertFrom)) {
                userConvertFrom = i;
            }
            if (timeUnits[i].equals(convertTo)) {
                userConvertTo = i;
            }
            result = result * timeValues[userConvertFrom] / timeValues[userConvertTo];
            if(result > 1000000 || result < 0.001)
            {
                displayResult.setText(String.valueOf(String.format("%.3E", result)));
            }
            else
            {
                displayResult.setText(String.valueOf(setDecimal.format(result)));
            }
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Getting the category selected by the user
        category = parent.getItemAtPosition(position).toString();
        displayCorrectUnits(category);
    }

    public void displayCorrectUnits(String chosenCategory) {
        //If statement to check what category is chosen and display the correct units
        if(chosenCategory.equals("Length")) {
            //Display the dropdown menu for the units being converting from
            chooseUnit = findViewById((R.id.convertingFromSpinner));
            ArrayAdapter<CharSequence> unitType = ArrayAdapter.createFromResource(this, lengthUnits, android.R.layout.simple_spinner_item);
            unitType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseUnit.setAdapter(unitType);

            //Display the dropdown menu for the units being converted to
            convertingTo = findViewById((R.id.convertingToSpinner));
            convertingTo.setAdapter(unitType);

        }

        if(chosenCategory.equals("Mass")) {
            chooseUnit = findViewById((R.id.convertingFromSpinner));
            ArrayAdapter<CharSequence> unitType = ArrayAdapter.createFromResource(this, R.array.massUnits, android.R.layout.simple_spinner_item);
            unitType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseUnit.setAdapter(unitType);

            convertingTo = findViewById((R.id.convertingToSpinner));
            convertingTo.setAdapter(unitType);
        }

        if(chosenCategory.equals("Temperature")) {
            chooseUnit = findViewById((R.id.convertingFromSpinner));
            ArrayAdapter<CharSequence> unitType = ArrayAdapter.createFromResource(this, R.array.temperatureUnits, android.R.layout.simple_spinner_item);
            unitType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseUnit.setAdapter(unitType);

            convertingTo = findViewById((R.id.convertingToSpinner));
            convertingTo.setAdapter(unitType);
        }

        if(chosenCategory.equals("Time")) {
            chooseUnit = findViewById((R.id.convertingFromSpinner));
            ArrayAdapter<CharSequence> unitType = ArrayAdapter.createFromResource(this, R.array.timeUnits, android.R.layout.simple_spinner_item);
            unitType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            chooseUnit.setAdapter(unitType);

            convertingTo = findViewById((R.id.convertingToSpinner));
            convertingTo.setAdapter(unitType);
        }
    }
}