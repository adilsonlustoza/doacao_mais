package br.com.lustoza.doacaomais.Domain.ObjectValue;

import android.net.Uri;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ubuntu on 12/13/16.
 */
public abstract class MasterUser extends MasterDomain {

    
    
    private String Id;
    
    
    private String Name;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public Uri getUrlImage() {
        return UrlImage;
    }

    public void setUrlImage(Uri urlImage) {
        UrlImage = urlImage;
    }

    private String Email;
    
    
    private String LastName;
    
    
    private String NickName;
    
    
    private Uri UrlImage;

    public String getCompositeName() {
        return Name + " " + LastName;
    }

}
