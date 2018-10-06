package com.org.magiclibary.magiclibary;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

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
        this.eventsToolBar();
        Fresco.initialize(this);
        this.getCards();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cards_menu, menu);
        return true;
    }

    private void eventsToolBar() {
        Toolbar toolbar = findViewById(R.id.action_bar);
        toolbar.setTitle(R.string.cardlist);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListaCards.this);
                View view = getLayoutInflater().inflate(R.layout.activity_dialog_filter_cards, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    private void getCards() {
        MagicGatheringService magicGatheringService = new MagicGatheringService();
        IMagicService deckCall = magicGatheringService.connect();
        final ProgressBar progressBar = findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);

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
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Deck> call, Throwable t) {
                Log.e("Error", "Error x");
            }
        });
    }
}
