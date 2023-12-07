package br.com.lustoza.doacaomais.Domain;

import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterUser;

/**
 * Created by ubuntu on 12/13/16.
 */
public class FacebookUser extends MasterUser {

    
    private String ImageUrl;
    
    private String Link;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getLocale() {
        return Locale;
    }

    public void setLocale(String locale) {
        Locale = locale;
    }

    private String Locale;


}
