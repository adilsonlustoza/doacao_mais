package br.com.lustoza.doacaomais.Domain.ObjectValue;

import android.net.Uri;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ubuntu on 12/13/16.
 */
public abstract class MasterUser extends MasterDomain {

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Id;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Name;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Email;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String LastName;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String NickName;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Uri UrlImage;

    public String getCompositeName() {
        return Name + " " + LastName;
    }

}
