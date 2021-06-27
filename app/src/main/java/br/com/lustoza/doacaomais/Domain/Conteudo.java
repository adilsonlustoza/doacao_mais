package br.com.lustoza.doacaomais.Domain;

import java.util.Date;

import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Conteudo extends MasterDomain {

    public static String TAG = "Conteudo";
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int ConteudoId;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Titulo;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Subtitulo;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Coluna;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Url = null;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int CacccId;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Caccc = null;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Date DataCadastro;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String IdentificadorUnico;
}