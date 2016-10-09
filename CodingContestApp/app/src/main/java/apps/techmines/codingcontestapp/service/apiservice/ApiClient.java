package apps.techmines.codingcontestapp.service.apiservice;

import apps.techmines.codingcontestapp.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides Api Retrofit Client object
 */
public class ApiClient {

    private static Retrofit retrofit = null;

    /**
     * provides Retrofit object
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
