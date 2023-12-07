package br.com.lustoza.doacaomais.Domain;

import java.util.Date;

import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;

public class Noticia extends MasterDomain {

    public static String TAG = "Noticia";
    
    
    private int NoticiaId;

    public int getNoticiaId() {
        return NoticiaId;
    }

    public void setNoticiaId(int noticiaId) {
        NoticiaId = noticiaId;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getSubTitulo() {
        return SubTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        SubTitulo = subTitulo;
    }

    public String getConteudo() {
        return Conteudo;
    }

    public void setConteudo(String conteudo) {
        Conteudo = conteudo;
    }

    public String getUrlImagem() {
        return UrlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        UrlImagem = urlImagem;
    }

    public Date getDataPublicacao() {
        return DataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        DataPublicacao = dataPublicacao;
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

    public String getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        DataCadastro = dataCadastro;
    }

    public String getIdentificadorUnico() {
        return IdentificadorUnico;
    }

    public void setIdentificadorUnico(String identificadorUnico) {
        IdentificadorUnico = identificadorUnico;
    }

    private String Titulo;
    
    
    private String SubTitulo;
    
    
    private String Conteudo;
    
    
    private String UrlImagem;
    
    
    private Date DataPublicacao = null;
    
    
    private int CacccId;
    
    
    private String Caccc = null;
    
    
    private String DataCadastro;
    
    
    private String IdentificadorUnico;

}