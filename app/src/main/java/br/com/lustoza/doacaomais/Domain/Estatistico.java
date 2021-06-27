package br.com.lustoza.doacaomais.Domain;

import java.util.Date;

import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Estatistico extends MasterDomain {

    public static String TAG = "Estatistico";
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int EstatisticoId;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Float Valor;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Nome;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Descricao;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Date DataCadastro;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String IdentificadorUnico;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Integer Tipo;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Fonte;

}