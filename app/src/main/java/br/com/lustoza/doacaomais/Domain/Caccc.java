package br.com.lustoza.doacaomais.Domain;

import java.util.ArrayList;
import java.util.Collection;

import br.com.lustoza.doacaomais.Domain.ObjectValue.Endereco;
import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;
import br.com.lustoza.doacaomais.Domain.ObjectValue.TpDoacao;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Caccc extends MasterDomain {

    public static String TAG = "Caccc";
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int CacccId;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Nome;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Description;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Email;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String EmailPagSeguro = null;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String EmailPayPal = null;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String UrlImagemPin = null;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String UrlImagem;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Telefone;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Celular;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private boolean Autorizado;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private int TipoDoacao;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private TpDoacao tpDoacao;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Endereco Endereco;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Collection<Conteudo> Conteudos;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Collection<Bazar> Bazares;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Collection<Campanha> Campanhas;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Collection<Noticia> Noticias;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Collection<ContaBancaria> ContasBancarias;

    public Caccc() {
        Endereco = new Endereco();
        Conteudos = new ArrayList<>();
        Bazares = new ArrayList<>();
        Campanhas = new ArrayList<>();
        Noticias = new ArrayList<>();
        ContasBancarias = new ArrayList<>();
    }

    public TpDoacao getEnumTipoDoacao() {

        switch (TipoDoacao) {
            case 0:
                tpDoacao = TpDoacao.Nenhum;
                break;
            case 1:
                tpDoacao = TpDoacao.CupomFiscal;
                break;
            case 2:
                tpDoacao = TpDoacao.PagSeguro;
                break;
            case 3:
                tpDoacao = TpDoacao.PayPal;
                break;
            case 4:
                tpDoacao = TpDoacao.PagSeguro_PayPal;
                break;
            case 5:
                tpDoacao = TpDoacao.ContaBancaria;
                break;
        }
        return tpDoacao;
    }

    public void setEnumTipoDoacao(int tipoDoacao) {
        TipoDoacao = tipoDoacao;

        switch (TipoDoacao) {
            case 0:
                tpDoacao = TpDoacao.Nenhum;
                break;
            case 1:
                tpDoacao = TpDoacao.CupomFiscal;
                break;
            case 2:
                tpDoacao = TpDoacao.PagSeguro;
                break;
            case 3:
                tpDoacao = TpDoacao.PayPal;
                break;
            case 4:
                tpDoacao = TpDoacao.PagSeguro_PayPal;
                break;
            case 5:
                tpDoacao = TpDoacao.ContaBancaria;
                break;
        }
    }

}
