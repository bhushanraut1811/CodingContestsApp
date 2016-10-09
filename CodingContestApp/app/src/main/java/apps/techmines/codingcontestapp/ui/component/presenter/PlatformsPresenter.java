package apps.techmines.codingcontestapp.ui.component.presenter;

import java.util.ArrayList;
import java.util.List;

import apps.techmines.codingcontestapp.service.apiservice.ApiClient;
import apps.techmines.codingcontestapp.service.apiservice.IContestsAPI;
import apps.techmines.codingcontestapp.service.apiservice.model.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * PlatformsPresenter Handle non UI logic
 */
public class PlatformsPresenter {
    private IPlatformsView mView;

    public PlatformsPresenter(IPlatformsView platformsView) {
        this.mView = platformsView;
    }

    public void fetchPlatforms(final ArrayList<Platform> platformList) {

        IContestsAPI apiService = ApiClient.getClient().
                create(IContestsAPI.class);
        Call<List<Platform>> call = apiService.getAllPlatforms();
        call.enqueue(new Callback<List<Platform>>() {
            @Override
            public void onResponse(Call<List<Platform>> call, Response<List<Platform>> response) {
                platformList.addAll(response.body());
                mView.populateListView();
            }

            @Override
            public void onFailure(Call<List<Platform>> call, Throwable t) {
                mView.populateListView();
            }
        });
    }
}
