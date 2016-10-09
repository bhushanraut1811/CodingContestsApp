package apps.techmines.codingcontestapp.ui.component.presenter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apps.techmines.codingcontestapp.service.apiservice.ApiClient;
import apps.techmines.codingcontestapp.service.apiservice.IContestsAPI;
import apps.techmines.codingcontestapp.service.apiservice.model.Challenge;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ContestHomePresenter handles non ui logic
 */
public class ContestsHomePresenter {


    private IContestHomeView mView;

    public ContestsHomePresenter(IContestHomeView view) {
        this.mView = view;
    }

    public void fetchContestData(final HashMap<String, ArrayList<Challenge>> challengesMap, final ArrayList<String> platformList) {

        IContestsAPI apiService = ApiClient.getClient().
                create(IContestsAPI.class);

        Call<List<Challenge>> call = apiService.getAllChallenges();
        call.enqueue(new Callback<List<Challenge>>() {
            @Override
            public void onResponse(Call<List<Challenge>> call,
                                   Response<List<Challenge>> response) {
                ArrayList<Challenge> challengeList = (ArrayList<Challenge>) response.body();
                for (Challenge challenge : challengeList) {
                    if (!challengesMap.containsKey(challenge.getPlatformName())) {
                        platformList.add(challenge.getPlatformName());
                        challengesMap.put(challenge.getPlatformName(), new ArrayList<Challenge>());
                    }
                    challengesMap.get(challenge.getPlatformName()).add(challenge);
                }
                mView.doViewPagerSetup();
            }

            @Override
            public void onFailure(Call<List<Challenge>> call, Throwable t) {
                //if failure do parametrizes for proper checks
                mView.doViewPagerSetup();
            }
        });
    }
}
