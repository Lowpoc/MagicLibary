package Services.MagicGathering;

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

    public IMagicService connect() {
        return this.retrofit.create(IMagicService.class);
    }
}
