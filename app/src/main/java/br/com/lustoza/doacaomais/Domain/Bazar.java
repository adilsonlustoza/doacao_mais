package br.com.lustoza.doacaomais.Domain;

import br.com.lustoza.doacaomais.Domain.ObjectValue.Endereco;
import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Bazar extends MasterDomain {

    public static String TAG = "Bazar";
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Nome;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Description;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Informacao;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Endereco Endereco;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Email = null;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Telefone = null;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String UrlImagem;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private float CacccId;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Caccc = null;
}