package Services.MagicGathering;

import Models.Deck;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IMagicService {
    public static final String BASE_URL = "https://api.magicthegathering.io/v1/";

    @GET("cards")
    Call<Deck> getCards();
}
