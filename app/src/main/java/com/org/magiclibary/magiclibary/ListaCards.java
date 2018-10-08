package com.org.magiclibary.magiclibary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Adapters.ListCardsAdapter;
import Interfaces.OnGetItemAdapter;
import Models.Deck;
import Services.MagicGathering.MagicGatheringService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaCards extends AppCompatActivity implements OnGetItemAdapter<Models.Card> {
    Map<String, String> filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cards);
        this.eventsToolBar();
        Fresco.initialize(this);
        this.filter = new HashMap<>();
        this.getCards();

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filter.clear();
                getCards();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
                final Button btn = view.findViewById(R.id.button);
                final EditText name = view.findViewById(R.id.editText2);
                final EditText pagesize = view.findViewById(R.id.pagesize);
                final CheckBox black = view.findViewById(R.id.search_color_black);
                final CheckBox white = view.findViewById(R.id.search_color_white);
                final CheckBox red = view.findViewById(R.id.search_color_red);
                final CheckBox blue = view.findViewById(R.id.search_color_blue);

                black.setChecked(filter.containsKey("black"));
                white.setChecked(filter.containsKey("white"));
                red.setChecked(filter.containsKey("red"));
                blue.setChecked(filter.containsKey("blue"));
                pagesize.setText(filter.get("pageSize"));

                final List<CheckBox> colors = new LinkedList<CheckBox>();
                colors.add(black);
                colors.add(white);
                colors.add(red);
                colors.add(blue);

                if (!filter.isEmpty()) {
                    name.setText(!filter.get("name").isEmpty() ? filter.get("name") : "");
                }

                builder.setView(view);
                final AlertDialog alertDialog = builder.create();

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filterColor = "";

                        for (CheckBox color :
                                colors) {
                            if (!color.isChecked()) continue;

                            switch (color.getId()) {
                                case R.id.search_color_black:
                                    filterColor += "|black";
                                    filter.put("black", "true");
                                    break;
                                case R.id.search_color_white:
                                    filterColor += "|white";
                                    filter.put("white", "true");
                                    break;
                                case R.id.search_color_red:
                                    filterColor += "|red";
                                    filter.put("red", "true");
                                    break;
                                case R.id.search_color_blue:
                                    filterColor += "|blue";
                                    filter.put("blue", "true");
                                    break;
                            }
                        }

                        if (name.getText().toString() != "") {
                            filter.put("name", name.getText().toString());
                        }
                        if (!filterColor.isEmpty()) {
                            filter.put("colors", filterColor);
                        }
                        if (pagesize.getText().toString() != "") {
                            filter.put("pageSize", pagesize.getText().toString());
                        }
                        final ProgressBar progressBar = findViewById(R.id.progressbar);
                        progressBar.setElevation(100);
                        progressBar.setVisibility(View.VISIBLE);
                        getCards();
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
                return true;
            }
        });
    }

    private void getCards() {
        MagicGatheringService magicGatheringService = new MagicGatheringService();
        final ProgressBar progressBar = findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setElevation(999);

        Callback<Deck> deckCallback = new Callback<Deck>() {
            @Override
            public void onResponse(Call<Deck> call, Response<Deck> response) {
                if (response.isSuccessful()) {
                    Deck deck = response.body();
                    Gson gson = new Gson();
                    TextView notFoundCards = findViewById(R.id.notfound);

                    if (deck.cards.isEmpty()) {
                        notFoundCards.setVisibility(View.VISIBLE);
                    } else {
                        notFoundCards.setVisibility(View.GONE);
                    }

                    RecyclerView recyclerView = findViewById(R.id.recly);
                    ListCardsAdapter listCardsAdapter = new ListCardsAdapter(ListaCards.this);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ListaCards.this, LinearLayoutManager.VERTICAL, false);
                    listCardsAdapter.setDeck(deck);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(listCardsAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Deck> call, Throwable t) {
                Toast.makeText(ListaCards.this, getString(R.string.failedtoconnect), Toast.LENGTH_LONG).show();
            }
        };
        magicGatheringService.getCards(filter, deckCallback);
    }

    @Override
    public void getItem(Models.Card item) {
            Intent it = new Intent(this, DetalheCard.class);
            it.putExtra("name", item.name);
            it.putExtra("description", item.text);
            it.putExtra("url", item.imageUrl);
            startActivity(it);
    }
}
