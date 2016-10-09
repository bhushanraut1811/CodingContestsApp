package apps.techmines.codingcontestapp.service.apiservice;

import java.util.ArrayList;
import java.util.List;

import apps.techmines.codingcontestapp.service.apiservice.model.Challenge;
import apps.techmines.codingcontestapp.service.apiservice.model.Platform;
import apps.techmines.codingcontestapp.util.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by bhushan.raut on 9/24/2016.
 */
public interface IContestsAPI {

    /**
     * Provides all challenges //not used
     *
     * @param response
     */
    @GET("/allchallenges")
    public void getAllChallenges(Callback<Challenge> response);

    /**
     * Provides all challenges
     *
     * @return object of ResponseBody
     */
    @GET(Constants.All_CHALLENGES_URL)
    Call<List<Challenge>> getAllChallenges();

    /**
     * provides list of all coding contest platforms
     */
    @GET(Constants.All_PLATFORMS_URL)
    Call<List<Platform>> getAllPlatforms();

    //images
   /* @GET("/")
    Call<HashMap<String, ArrayList<Challenge>>> getAllPlatforms();*/
}
