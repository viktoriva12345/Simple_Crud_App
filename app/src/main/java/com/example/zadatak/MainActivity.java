package com.example.zadatak;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editIme, editPrezime, editGodinaUpisa, editBrojPoena;
    Button btnUnesi, btnPrikazi, btnObrisi;
    TextView textViewPolaznici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getWritableDatabase();

        editIme = findViewById(R.id.editTextIme);
        editPrezime = findViewById(R.id.editTextPrezime);
        editGodinaUpisa = findViewById(R.id.editTextGodinaUpisa);
        editBrojPoena = findViewById(R.id.editTextBrojPoena);
        btnUnesi = findViewById(R.id.buttonUnesi);
        btnPrikazi = findViewById(R.id.buttonPrikazi);
        btnObrisi = findViewById(R.id.buttonObrisi);
        textViewPolaznici = findViewById(R.id.textViewPolaznici);

        unesiPolaznika();
        prikaziPolaznike();
        obrisiPolaznike();



    }
    public void unesiPolaznika() {
        btnUnesi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String ime = editIme.getText().toString();
                String prezime = editPrezime.getText().toString();
                int godina_upisa = Integer.parseInt(editGodinaUpisa.getText().toString());
                int broj_poena = Integer.parseInt(editBrojPoena.getText().toString());

                boolean isInserted = myDb.insertData(ime, prezime, godina_upisa, broj_poena);
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Polaznik uspjesno dodat", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Podaci nije uspjesno dodat", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void prikaziPolaznike() {
        btnPrikazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getTopPolaznici();
                if (res.getCount() == 0) {
                    textViewPolaznici.setText("Nema polaznika");
                    return;
                }

                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("ID: ").append(res.getString(0)).append("\n");
                    buffer.append("Ime: ").append(res.getString(1)).append("\n");
                    buffer.append("Prezime: ").append(res.getString(2)).append("\n");
                    buffer.append("Godina upisa: ").append(res.getString(3)).append("\n");
                    buffer.append("Broj poena: ").append(res.getString(4)).append("\n\n");
                }

                textViewPolaznici.setText(buffer.toString());
            }
        });
    }


    public void obrisiPolaznike() {
        btnObrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteAll();
                textViewPolaznici.setText("");
                Toast.makeText(MainActivity.this, "Svi polaznici su obrisani", Toast.LENGTH_LONG).show();
            }
        });
    }



}