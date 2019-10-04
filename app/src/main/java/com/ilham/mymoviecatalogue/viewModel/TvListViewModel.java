package com.ilham.mymoviecatalogue.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.android.volley.Header;
import com.ilham.mymoviecatalogue.items.Tv;
import com.ilham.mymoviecatalogue.repo.TvRepo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ilham.mymoviecatalogue.repo.TvRepo.API_KEY;

public class TvListViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Tv>> listTv = new MutableLiveData<ArrayList<Tv>>();
    private MutableLiveData<Tv> tvData;
    private TvRepo tvModel;

    public TvListViewModel() {
        tvModel = new TvRepo();
    }

    public void init() {
        if (this.tvData != null) {
            return;
        }
        tvData = tvModel.getTv();
    }

    public MutableLiveData<Tv> getTv() {
        return this.tvData;
    }

    public void searchMovies(final String text, final String type, String language) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Tv> listItems = new ArrayList<>();
        final String url = "https://api.themoviedb.org/3/search/" + type + "?api_key=" + API_KEY + "&language=" + language + "en-US&query=" + text;
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvs = list.getJSONObject(i);
                        Tv tv = new Tv(tvs);
                        listItems.add(tv);
                    }
                    listTv.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());

            }
        });
    }

    public MutableLiveData<ArrayList<Tv>> getMovies() {
        return listTv;
    }
}

