package br.com.lustoza.doacaomais.Domain;

import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterUser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ubuntu on 12/13/16.
 */
public class FacebookUser extends MasterUser {

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String ImageUrl;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Link;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Locale;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int Age_Range;

}
