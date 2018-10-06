package Services.MagicGathering;

import java.util.Map;

import Models.Deck;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface IMagicService {
    public static final String BASE_URL = "https://api.magicthegathering.io/v1/";

    @GET("cards")
    Call<Deck> getCards(@QueryMap Map<String,String> filter);
}
