package br.com.lustoza.doacaomais.Domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;

public class Campanha extends MasterDomain {

    public static String TAG = "Campanha";
    
    
    private int CampanhaId;
    
    
    private String Nome;
    
    
    private String Descricao;

    public int getCampanhaId() {
        return CampanhaId;
    }

    public void setCampanhaId(int campanhaId) {
        CampanhaId = campanhaId;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getLinkWeb() {
        return LinkWeb;
    }

    public void setLinkWeb(String linkWeb) {
        LinkWeb = linkWeb;
    }

    public Date getDataInicial() {
        return DataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        DataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return DataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        DataFinal = dataFinal;
    }

    public String getUrlImagem() {
        return UrlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        UrlImagem = urlImagem;
    }

    public boolean isAtiva() {
        return Ativa;
    }

    public void setAtiva(boolean ativa) {
        Ativa = ativa;
    }

    public int getTipoCampanha() {
        return TipoCampanha;
    }

    public void setTipoCampanha(int tipoCampanha) {
        TipoCampanha = tipoCampanha;
    }

    public int getCacccId() {
        return CacccId;
    }

    public void setCacccId(int cacccId) {
        CacccId = cacccId;
    }

    public String getCaccc() {
        return Caccc;
    }

    public void setCaccc(String caccc) {
        Caccc = caccc;
    }

    public List<Notificacao> getNotificacoes() {
        return Notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        Notificacoes = notificacoes;
    }

    public Date getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        DataCadastro = dataCadastro;
    }

    public String getIdentificadorUnico() {
        return IdentificadorUnico;
    }

    public void setIdentificadorUnico(String identificadorUnico) {
        IdentificadorUnico = identificadorUnico;
    }

    private String LinkWeb = null;
    
    
    private Date DataInicial = null;
    
    
    private Date DataFinal = null;
    
    
    private String UrlImagem;
    
    
    private boolean Ativa;
    
    
    private int TipoCampanha;
    
    
    private int CacccId;
    
    
    private String Caccc = null;
    
    
    private List<Notificacao> Notificacoes;
    
    
    private Date DataCadastro;
    
    
    private String IdentificadorUnico;

    public Campanha() {
        Notificacoes = new ArrayList<>();
    }

}