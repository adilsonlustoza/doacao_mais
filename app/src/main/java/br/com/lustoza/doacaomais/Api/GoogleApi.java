package br.com.lustoza.doacaomais.Api;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import br.com.lustoza.doacaomais.Domain.GoogleUser;
import br.com.lustoza.doacaomais.Helper.TrackHelper;

/**
 * Created by ubuntu on 1/14/17.
 */
public class GoogleApi {

    private static GoogleUser googleUser;

    public static GoogleUser GetProfileGoogle() {

        if (googleUser != null)
            return googleUser;
        return null;

    }

    public void SetProfileGoogle(GoogleSignInAccount googleSignInAccount) {
        try {
            if (googleSignInAccount != null) {

                googleUser = new GoogleUser();
                googleUser.setId(googleSignInAccount.getId());
                googleUser.setName(googleSignInAccount.getDisplayName());
                googleUser.setLastName(googleSignInAccount.getFamilyName());
                googleUser.setEmail(googleSignInAccount.getEmail());
                googleUser.setUrlImage(googleSignInAccount.getPhotoUrl());
                googleUser.setNickName(googleSignInAccount.getGivenName());

            } else
                googleUser = null;

        } catch (Exception e) {
            TrackHelper.WriteError(GoogleApi.class, "GoogleUser", e.getMessage());

        }

    }

}
