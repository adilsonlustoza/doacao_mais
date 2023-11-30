package br.com.lustoza.doacaomais.Api;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

import br.com.lustoza.doacaomais.Domain.FacebookUser;
import br.com.lustoza.doacaomais.Helper.TrackHelper;

/**
 * Created by ubuntu on 12/11/16.
 */

public class FacebookApi {

    private static FacebookUser facebookUser;
    private GraphRequest graphRequest;

    public FacebookUser GetProfilePicture() {
        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large),first_name,last_name,age_range,link,locale");

        facebookUser = new FacebookUser();

        graphRequest = new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();

                                if (data.has("picture")) {
                                    facebookUser.setImageUrl(data.getJSONObject("picture").getJSONObject("data").getString("url"));
                                    facebookUser.setId(data.getString("id"));
                                    facebookUser.setEmail(data.getString("email"));
                                    facebookUser.setName(data.getString("first_name"));
                                    facebookUser.setLastName(data.getString("last_name"));
                                 //   facebookUser.setAge_Range(data.getJSONObject("age_range").getInt("min"));
                                    facebookUser.setLink(data.getString("link"));
                                    facebookUser.setLocale(data.getString("locale"));

                                }
                            } catch (Exception e) {
                                TrackHelper.WriteError(this, "GetProfilePicture", e.getMessage());
                            }
                        }
                    }
                });

        Thread t = new Thread(() -> graphRequest.executeAndWait());

        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return facebookUser;

    }

    public void setNullProfileFacebook() {
        facebookUser = null;
    }

}




