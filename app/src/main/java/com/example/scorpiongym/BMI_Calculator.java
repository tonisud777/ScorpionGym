package com.example.scorpiongym;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scorpiongym.ui.slideshow.SlideshowViewModel;

public class BMI_Calculator extends Fragment {
    EditText weight, height;
    TextView resulttext;
    String calculation, BMIresult;
    Button btn2;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.activity_calculadora, container, false);

        weight = root.findViewById(R.id.weight);
        height = root.findViewById(R.id.height);
        resulttext = root.findViewById(R.id.result);
        btn2 = root.findViewById(R.id.calculate_button);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String S1 = weight.getText().toString();
                String S2 = height.getText().toString();



                float weightValue = Float.parseFloat(S1);
                float heightValue = Float.parseFloat(S2) / 100;



                float bmi = weightValue / (heightValue * heightValue);



                if(bmi < 16){
                    BMIresult = "Severamente bajo de peso";
                }else if(bmi < 18.5){
                    BMIresult = "Bajo de peso";
                }else if(bmi >= 18.5 && bmi <= 24.9){
                    BMIresult = "Peso Ideal";
                }else if (bmi >= 25 && bmi <= 29.9){
                    BMIresult = "Sobrepeso";
                }else{
                    BMIresult = "Obeso";
                }


                calculation = String.format("Tu IMC es: " + "%.2f" + " " + BMIresult, bmi);
                /*
                calculation = "Result: " + bmi  +  BMIresult;
                */


                resulttext.setText(calculation);
            }
        });
        return root;
    }


}