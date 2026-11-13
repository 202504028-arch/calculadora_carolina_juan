package com.example.calculadora2_juan;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // IDs de los botones numéricos
    private static final int[] IDS_DIGITOS = {
            R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6,
            R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_0
    };

    // IDs del resto de botones
    private static final int[] IDS_OTROS = {
            R.id.btn_igual, R.id.btn_suma, R.id.btn_resta, R.id.btn_multiplicar,
            R.id.btn_dividir, R.id.btn_punto, R.id.btn_borrar
    };

    private TextView myText5View1;   // número que se está escribiendo
    private TextView myTextView2;    // resultado acumulado

    private double m1 = 0;           // acumulado
    private char op1 = '+';          // operación pendiente
    private boolean resultadoMostrar = false;  // true justo después de pulsar "="

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);

        myText5View1 = findViewById(R.id.mensaje);
        myTextView2 = findViewById(R.id.resultado);
        myText5View1.setText("");
        myTextView2.setText("");

        for (int id : IDS_DIGITOS) findViewById(id).setOnClickListener(this);
        for (int id : IDS_OTROS) findViewById(id).setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        // Dígitos
        for (int idDigito : IDS_DIGITOS) {
            if (id == idDigito) {
                if (resultadoMostrar) reiniciar();   // empieza un cálculo nuevo
                myText5View1.append(((TextView) view).getText());
                return;
            }
        }

        if (id == R.id.btn_punto) {
            if (resultadoMostrar) reiniciar();
            String actual = myText5View1.getText().toString();
            if (actual.contains(".")) return;        // evita dos puntos
            myText5View1.append(actual.isEmpty() ? "0." : ".");
        } else if (id == R.id.btn_borrar) {
            reiniciar();
        } else if (id == R.id.btn_suma) {
            calcula('+');
        } else if (id == R.id.btn_resta) {
            calcula('-');
        } else if (id == R.id.btn_multiplicar) {
            calcula('x');
        } else if (id == R.id.btn_dividir) {
            calcula('/');
        } else if (id == R.id.btn_igual) {
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

    public void calcula(char op) {
        String cadena = myText5View1.getText().toString();

        // Sin número escrito: solo se cambia la operación, o hay error
        if (cadena.isEmpty()) {
            if (op != '=' && myTextView2.getText().length() > 0) {
                op1 = op;
                resultadoMostrar = false;
                myTextView2.setText(formatear(m1) + op1);
            } else {
                mostrarError(getString(R.string.error_numero));
            }
            return;
        }

        try {
            double m2 = Double.parseDouble(cadena);
            double result = m1;

            // Se hace la operación PENDIENTE (op1), no la que se acaba de pulsar
            switch (op1) {
                case '+': result = m1 + m2; break;
                case '-': result = m1 - m2; break;
                case 'x': result = m1 * m2; break;
                case '/':
                    if (m2 == 0) {
                        mostrarError(getString(R.string.error_division_cero));
                        reiniciar();
                        return;
                    }
                    result = m1 / m2;
                    break;
                default: break;
            }

            m1 = result;
            op1 = op;
            myText5View1.setText("");

            if (op == '=') {
                myTextView2.setText(formatear(m1));
                resultadoMostrar = true;
            } else {
                myTextView2.setText(formatear(m1) + op1);
                resultadoMostrar = false;
            }
        } catch (NumberFormatException nfe) {
            mostrarError(getString(R.string.error_numero));
        }
    }

    // 5.0 se muestra como "5", y 2.5 se queda como "2.5"
    private String formatear(double valor) {
        if (valor == Math.rint(valor) && Math.abs(valor) < 1e15) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }

    private void mostrarError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}