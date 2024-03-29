package com.example.task21p;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Spinner conversationType;
    private Spinner sourceUnitSpinner;
    private Spinner targetUnitSpinner;
    private EditText sourceValueEditText;
    private EditText resultEditText;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        conversationType = findViewById(R.id.conversationType);
        sourceUnitSpinner = findViewById(R.id.sourceUnitSpinner);
        targetUnitSpinner = findViewById(R.id.targetUnitSpinner);
        sourceValueEditText = findViewById(R.id.sourceValueEditText);
        resultEditText = findViewById(R.id.resultEditText);
        convertButton = findViewById(R.id.convertButton);

        // Set up unit spinners
        String[] unitCategories = {"Length", "Weight", "Temperature"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unitCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversationType.setAdapter(adapter);

        // Set up conversation type spinner listener
        conversationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                updateUnitSpinners(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        // Set up conversion logic
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performConversion();
            }
        });
    }

    private void updateUnitSpinners(int position) {
        ArrayAdapter<CharSequence> unitArrayAdapter;
        switch (position) {
            case 0: // Length
                unitArrayAdapter = ArrayAdapter.createFromResource(
                        this,
                        R.array.length_units,
                        android.R.layout.simple_spinner_item
                );
                break;
            case 1: // Weight
                unitArrayAdapter = ArrayAdapter.createFromResource(
                        this,
                        R.array.weight_units,
                        android.R.layout.simple_spinner_item
                );
                break;
            case 2: // Temperature
                unitArrayAdapter = ArrayAdapter.createFromResource(
                        this,
                        R.array.temperature_units,
                        android.R.layout.simple_spinner_item
                );
                break;
            default:
                unitArrayAdapter = ArrayAdapter.createFromResource(
                        this,
                        R.array.length_units,
                        android.R.layout.simple_spinner_item
                );
                break;
        }

        unitArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sourceUnitSpinner.setAdapter(unitArrayAdapter);
        targetUnitSpinner.setAdapter(unitArrayAdapter);
    }

    private void performConversion() {
        // Get user input
        String sourceValueString = sourceValueEditText.getText().toString();
        double sourceValue = Double.parseDouble(sourceValueString);

        // Check if the input is valid
        if (Double.isNaN(sourceValue)) {
            resultEditText.setText(R.string.invalid_input);
            return;
        }

        // Get selected units
        String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
        String targetUnit = targetUnitSpinner.getSelectedItem().toString();

        // Perform conversion
        double convertedValue = UnitConverter.convert(sourceUnit, targetUnit, sourceValue);

        // Construct the result string with the target unit
        String resultString = convertedValue + " " + targetUnit;

        // Update the UI with the result
        resultEditText.setText(resultString);
    }
}

class UnitConverter {
    static double convert(String sourceUnit, String targetUnit, double value) {

        switch (sourceUnit) {
            case "inch":
                switch (targetUnit) {
                    case "inch":
                        return value;
                    case "cm":
                        return value * 2.54;
                    case "foot":
                        return value / 12.0;
                    case "yard":
                        return value / 36.0;
                    case "mile":
                        return value / 63360.0;
                    case "km":
                        return value / 39370.1;
                    default:
                        return value;
                }
            case "foot":
                switch (targetUnit) {
                    case "inch":
                        return value * 12.0;
                    case "cm":
                        return value * 30.48;
                    case "foot":
                        return value;
                    case "yard":
                        return value / 3.0;
                    case "mile":
                        return value / 5280.0;
                    case "km":
                        return value / 3280.84;
                    default:
                        return value;
                }
            case "yard":
                switch (targetUnit) {
                    case "inch":
                        return value * 36.0;
                    case "cm":
                        return value * 91.44;
                    case "foot":
                        return value * 3.0;
                    case "yard":
                        return value;
                    case "mile":
                        return value / 1760.0;
                    case "km":
                        return value / 1093.61;
                    default:
                        return value;
                }
            case "mile":
                switch (targetUnit) {
                    case "inch":
                        return value * 63360.0;
                    case "cm":
                        return value * 160934.0;
                    case "foot":
                        return value * 5280.0;
                    case "yard":
                        return value * 1760.0;
                    case "mile":
                        return value;
                    case "km":
                        return value * 1.60934;
                    default:
                        return value;
                }
            case "cm":
                switch (targetUnit) {
                    case "inch":
                        return value / 2.54;
                    case "foot":
                        return value / 30.48;
                    case "yard":
                        return value / 91.44;
                    case "mile":
                        return value / 160934.0;
                    case "cm":
                        return value;
                    case "km":
                        return value / 100000.0;
                    default:
                        return value;
                }
            case "km":
                switch (targetUnit) {
                    case "inch":
                        return value * 39370.1;
                    case "foot":
                        return value * 3280.84;
                    case "yard":
                        return value * 1093.61;
                    case "mile":
                        return value * 0.621371;
                    case "cm":
                        return value * 100000.0;
                    case "km":
                        return value;
                    default:
                        return value;
                }
            case "pound":
                switch (targetUnit) {
                    case "pound":
                        return value;
                    case "kg":
                        return value * 0.453592;
                    case "g":
                        return value * 453.592;
                    case "ounce":
                        return value * 16.0;
                    case "ton":
                        return value * 0.0005;
                    default:
                        return value;
                }
            case "ounce":
                switch (targetUnit) {
                    case "pound":
                        return value / 16.0;
                    case "kg":
                        return value * 0.0283495;
                    case "g":
                        return value * 28.3495;
                    case "ounce":
                        return value;
                    case "ton":
                        return value * 0.00003125;
                    default:
                        return value;
                }
            case "ton":
                switch (targetUnit) {
                    case "pound":
                        return value * 2000.0;
                    case "kg":
                        return value * 907.185;
                    case "g":
                        return value * 907185.0;
                    case "ounce":
                        return value * 32000.0;
                    case "ton":
                        return value;
                    default:
                        return value;
                }
            case "g":
                switch (targetUnit) {
                    case "pound":
                        return value / 453.592;
                    case "kg":
                        return value / 1000.0;
                    case "g":
                        return value;
                    case "ounce":
                        return value / 28.3495;
                    case "ton":
                        return value / 907185.0;
                    default:
                        return value;
                }
            case "kg":
                switch (targetUnit) {
                    case "pound":
                        return value * 2.20462;
                    case "kg":
                        return value;
                    case "g":
                        return value * 1000.0;
                    case "ounce":
                        return value * 35.274;
                    case "ton":
                        return value / 907.185;
                    default:
                        return value;
                }
            case "Celsius":
                switch (targetUnit) {
                    case "Celsius":
                        return value;
                    case "Fahrenheit":
                        return (value * 1.8) + 32;
                    case "Kelvin":
                        return value + 273.15;
                    default:
                        return value;
                }
            case "Fahrenheit":
                switch (targetUnit) {
                    case "Celsius":
                        return (value - 32) / 1.8;
                    case "Fahrenheit":
                        return value;
                    case "Kelvin":
                        return (value - 32) / 1.8 + 273.15;
                    default:
                        return value;
                }
            case "Kelvin":
                switch (targetUnit) {
                    case "Celsius":
                        return value - 273.15;
                    case "Fahrenheit":
                        return (value - 273.15) * 1.8 + 32;
                    case "Kelvin":
                        return value;
                    default:
                        return value;
                }
            default:
                return value;
        }
    }
}

