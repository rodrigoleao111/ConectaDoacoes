/**
 * Aplicação utilizando o firebase para acesso das localizações
 */
package com.example.conectadoacoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conectadoacoes.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NearestPlace2 extends AppCompatActivity {

    private ArrayList<Location> collectionPlaces = new ArrayList<>();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_nearest_place);

        getSupportActionBar().hide();


        TextView result;
        result = findViewById(R.id.txViewResultado);

        // Receber informações da tela anterior
        Bundle userCoordinates = getIntent().getExtras();

        // Popular a lista
        collectionPlaces = PopulateArrayListFromDB();

        // Cálculo de local mais próximo
        float distanceNearestPlace = distanceCalculator(userCoordinates, collectionPlaces.get(0));
        float distanceBetweenPlaces;
        int position = 0;
        for(int i = 0; i < collectionPlaces.size(); i++){
            distanceBetweenPlaces = distanceCalculator(userCoordinates, collectionPlaces.get(i));
            if(distanceBetweenPlaces <= distanceNearestPlace){
                distanceNearestPlace = distanceBetweenPlaces;
                position = i;
            }
        }

        // Exibir result
        String show = "Este é o local mais próximo:\n\n" +
                "Nome: " + collectionPlaces.get(position).getEquipamento_publico_disponivel() +
                "\nEndereço: " + collectionPlaces.get(position).getEndereco() +
                "\nBairro: " + collectionPlaces.get(position).getBairro();
        result.setText(show);

    }

    /**
     * Método para calcular distância entre coordenadas
     *
     * Nesta versão estamos utilizando a fórmula de Haversine para cálculo de distância
     * Obs1.: O valor informada é a distância em linha reta entre os dois pontos
     * Obs2.: Para versões futuras, calcular utilizando as rotas fornecidas pelo Google Maps
     *
     * @author Rodrigo Leão
     * @param origin    bundle com coordenadas da origem
     * @param destiny   bundle com coordenadas do destino
     * @return Float    distância, em Km, entre pontos.
     */
    public Float distanceCalculator(Bundle origin, Location destiny){
        Double distance;

        float latOrigin = toRadians(origin.getDouble("latitude"));
        float longOrigin = toRadians(origin.getDouble("longitude"));
        float latDestiny = toRadians(destiny.getLatitude());
        float longDestiny = toRadians(destiny.getLongitude());

        float deltaLat = calculateDelta(latOrigin, latDestiny);
        float deltaLong = calculateDelta(longOrigin, longDestiny);

        // a = [sen²(Δlat/2) + cos(lat1)] x cos(lat2) x sen²(Δlong/2)
        distance = (Math.pow(Math.sin(deltaLat/2), 2) + Math.cos(latOrigin))*
                Math.cos(latDestiny)*Math.pow((Math.sin(deltaLong)/2),2);

        // c = 2 x cot(√a/√(1−a))
        distance = 2*Math.pow(Math.tan(Math.sqrt(distance)/Math.sqrt(1-distance)),-1);

        // d = R x c | R = 6,371 km (raio da terra)
        distance = 6.371*distance;

        return distance.floatValue();
    }

    /**
     * Método para converter de graus para radianos
     *
     * @author Rodrigo Leão
     * @param numDouble número em graus
     * @return Float    valor em radianos
     */
    public float toRadians(double numDouble){
        float pi = 3.14159f;
        return (float)numDouble*(pi/180.0f);
    }

    /**
     * Calcular delta: x1 - x2
     *
     * @author Rodrigo Leão
     * @param x1    valor 1
     * @param x2    valor 2
     * @return delta
     */
    public float calculateDelta(float x1, float x2){
        float delta = x1 - x2;
        if (delta < 0){
            return delta*(-1);
        } else {
            return delta;
        }
    }

    public ArrayList<Location> PopulateArrayListFromDB() {
        ArrayList<Location> locationArrayList = new ArrayList<>();


        DatabaseReference dbLocations = mDatabase.child("records");

        dbLocations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dn : snapshot.getChildren()){
                    Location location = dn.getValue(Location.class);
                    locationArrayList.add(location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "erro no db", Toast.LENGTH_LONG).show();
            }
        });

        return locationArrayList;
    }

}

