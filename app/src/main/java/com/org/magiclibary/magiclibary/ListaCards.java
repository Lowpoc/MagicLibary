package com.org.magiclibary.magiclibary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;

import Adapters.ListCardsAdapter;
import Models.Deck;
import Services.MagicGathering.IMagicService;
import Services.MagicGathering.MagicGatheringService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaCards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cards);
        Fresco.initialize(this);

        MagicGatheringService magicGatheringService = new MagicGatheringService();

        IMagicService deckCall = magicGatheringService.connect();

        deckCall.getCards().enqueue(new Callback<Deck>() {
            @Override
            public void onResponse(Call<Deck> call, Response<Deck> response) {
                if (response.isSuccessful()) {
                    Deck deck = response.body();

                    RecyclerView recyclerView = findViewById(R.id.recly);
                    ListCardsAdapter listCardsAdapter = new ListCardsAdapter();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ListaCards.this, LinearLayoutManager.VERTICAL, false);
                    listCardsAdapter.setDeck(deck);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(listCardsAdapter);
                }
            }

            @Override
            public void onFailure(Call<Deck> call, Throwable t) {
                Log.e("Error", "Error x");
            }
        });

    }
}
