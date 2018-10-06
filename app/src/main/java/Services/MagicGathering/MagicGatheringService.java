package Services.MagicGathering;

import java.util.Map;

import Models.Deck;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MagicGatheringService {

    private Retrofit retrofit;

    public MagicGatheringService() {
        this.retrofit = new Retrofit
                    .Builder()
                    .baseUrl(IMagicService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    public void getCards(Map<String,String> filters, Callback<Deck> deckCallback) {
        IMagicService magicService =  this.retrofit.create(IMagicService.class);
        magicService.getCards(filters).enqueue(deckCallback);
    }
}
