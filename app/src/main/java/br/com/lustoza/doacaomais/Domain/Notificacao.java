package br.com.lustoza.doacaomais.Domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Adilson on 04/12/2017.
 */

public class Notificacao extends MasterDomain {

    public static String TAG = "Notificacao";
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Caccc caccc;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int NotificacaoId;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private boolean Ativa;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Date DataInicial;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Date DataFinal;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Titulo;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Descricao;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Conteudo;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Collection<Dispositivo> Dispositivos;

    public Notificacao() {
        super();
        Dispositivos = new ArrayList<>();
    }

}


