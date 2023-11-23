package com.example.apagar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractCollection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button bt;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Query query;
    ArrayList<Pessoa> pessoaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = findViewById(R.id.button);

        /* inicialização das informações do firebase */
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.e("firebaseDatabase: ", FirebaseDatabase.getInstance().toString());

        //para salvar os dados offline e sincronizar ao conectar
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
        Log.e("databaseReference: ", firebaseDatabase.getReference().toString());
        /*     */


        //exemplo: informação que será adicionada ao firebase
        Pessoa p = new Pessoa();
        p.setId("2");
        p.setNome("maria444 joão da silva");

        //exemplo: informação que será adicionada ao firebase
        Pessoa p2 = new Pessoa();
        p2.setId("22");
        p2.setNome("maria2123123 joão da silva");

        //exemplo: retornar usuário logado. Atenção: nesta tela específica, não há autenticação/usuário logado.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //exemplo: adicionar informação ao firebase, utilizando id do usuário logado.
        databaseReference.child("Usuário").
                child(user.getUid()).
                child(p.getNome()).
                setValue(p);

        //exemplo: adicionar informação ao firebase, utilizando id do usuário logado.
        databaseReference.child("Usuário").
                child(user.getUid()).
                child(p2.getNome()).
                setValue(p2);

        //exemplo: remoção de informação do firebase
        databaseReference.child("Usuário").
                child(p2.getNome()).
                removeValue();

        //montando a query para listar dados
        query = databaseReference.child("Usuário");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //código para listar dados - executar a query
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //percorrer variável "snapshot", que contém o retorno
                        //dos dados da execução da query
                        for(DataSnapshot objSnapShot:snapshot.getChildren()){
                            Pessoa p = objSnapShot.getValue(Pessoa.class);
                            pessoaList.add(p);
                            //arraylist de objetos a partir do retorno do banco.
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }
}


