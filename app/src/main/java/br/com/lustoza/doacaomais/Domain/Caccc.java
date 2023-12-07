package br.com.lustoza.doacaomais.Domain;

import java.util.ArrayList;
import java.util.Collection;

import br.com.lustoza.doacaomais.Domain.ObjectValue.Endereco;
import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;
import br.com.lustoza.doacaomais.Domain.ObjectValue.TpDoacao;

public class Caccc extends MasterDomain {

    public static String TAG = "Caccc";
    

    private int CacccId;
    
    
    private String Nome;
    
    
    private String Description;
    
    
    private String Email;
    
    
    private String EmailPagSeguro = null;
    
    
    private String EmailPayPal = null;
    
    
    private String UrlImagemPin = null;
    
    
    private String UrlImagem;
    
    
    private String Telefone;
    
    
    private String Celular;
    
    
    private boolean Autorizado;
    
    
    private int TipoDoacao;
    
    
    private TpDoacao tpDoacao;
    
    
    private Endereco Endereco;
    
    
    private Collection<Conteudo> Conteudos;
    
    
    private Collection<Bazar> Bazares;
    
    
    private Collection<Campanha> Campanhas;
    
    
    private Collection<Noticia> Noticias;


    public int getCacccId() {
        return CacccId;
    }

    public void setCacccId(int cacccId) {
        CacccId = cacccId;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEmailPagSeguro() {
        return EmailPagSeguro;
    }

    public void setEmailPagSeguro(String emailPagSeguro) {
        EmailPagSeguro = emailPagSeguro;
    }

    public String getEmailPayPal() {
        return EmailPayPal;
    }

    public void setEmailPayPal(String emailPayPal) {
        EmailPayPal = emailPayPal;
    }

    public String getUrlImagemPin() {
        return UrlImagemPin;
    }

    public void setUrlImagemPin(String urlImagemPin) {
        UrlImagemPin = urlImagemPin;
    }

    public String getUrlImagem() {
        return UrlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        UrlImagem = urlImagem;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public boolean isAutorizado() {
        return Autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        Autorizado = autorizado;
    }

    public int getTipoDoacao() {
        return TipoDoacao;
    }

    public void setTipoDoacao(int tipoDoacao) {
        TipoDoacao = tipoDoacao;
    }

    public TpDoacao getTpDoacao() {
        return tpDoacao;
    }

    public void setTpDoacao(TpDoacao tpDoacao) {
        this.tpDoacao = tpDoacao;
    }

    public br.com.lustoza.doacaomais.Domain.ObjectValue.Endereco getEndereco() {
        return Endereco;
    }

    public void setEndereco(br.com.lustoza.doacaomais.Domain.ObjectValue.Endereco endereco) {
        Endereco = endereco;
    }

    public Collection<Conteudo> getConteudos() {
        return Conteudos;
    }

    public void setConteudos(Collection<Conteudo> conteudos) {
        Conteudos = conteudos;
    }

    public Collection<Bazar> getBazares() {
        return Bazares;
    }

    public void setBazares(Collection<Bazar> bazares) {
        Bazares = bazares;
    }

    public Collection<Campanha> getCampanhas() {
        return Campanhas;
    }

    public void setCampanhas(Collection<Campanha> campanhas) {
        Campanhas = campanhas;
    }

    public Collection<Noticia> getNoticias() {
        return Noticias;
    }

    public void setNoticias(Collection<Noticia> noticias) {
        Noticias = noticias;
    }

    public Collection<ContaBancaria> getContasBancarias() {
        return ContasBancarias;
    }

    public void setContasBancarias(Collection<ContaBancaria> contasBancarias) {
        ContasBancarias = contasBancarias;
    }

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
