package com.example.calculadora2_juan;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.cert.Extension;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int[]IDs_DIGITOS = {
            R.id.btn_1, R.id.btn_2 ,R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6,
            R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_0
    };
    private static final int[] IDS_OTROS ={
            R.id.btn_igual, R.id.btn_suma, R.id.btn_resta, R.id.btn_multiplicar, R.id.btn_dividir,
            R.id.btn_punto, R.id.btn_borrar

    };
    private TextView myText5View1 ;
    private TextView myTextView2;
    private  double m1 = 0;
    private char op1 = '+';
    private boolean resultadoMostrar = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);

        myText5View1 = findViewById(R.id.mensaje);
        myTextView2 = findViewById(R.id.resultado);
        myText5View1.setText("");
        myTextView2.setText("");

        for (int id: IDs_DIGITOS) findViewById(id).setOnClickListener(this);
        for (int id: IDS_OTROS) findViewById(id).setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        for (int idDigitos : IDs_DIGITOS){
            if (id == idDigitos){
                if (resultadoMostrar)reiniciar();
                myText5View1.append(((TextView) view).getText());
                return;
            }
        }
        if (id == R.id.btn_punto){
            if (resultadoMostrar)reiniciar();
            String actual = myText5View1.getText().toString();
            if (actual.contains(".")) return;
            myText5View1.append(actual.isEmpty()? "0":".");
        }
        else  if(id == R.id.btn_borrar){
            reiniciar();
        }
        else if (id == R.id.btn_suma){
            calcula('+');
        }
        else if (id == R.id.btn_resta){
            calcula('-');
        }
        else if (id == R.id.btn_multiplicar){
            calcula('x');
        }
        else if (id == R.id.btn_dividir){
            calcula('/');
        }
        else if (id == R.id.btn_igual){
            calcula('=');
        }


    }
    private void reiniciar() {
        myText5View1.setText("");
        myTextView2.setText("");
        m1 = 0;
        op1 = '+';
        resultadoMostrar = false;
    }
    public void calcula(char op){

    }



}