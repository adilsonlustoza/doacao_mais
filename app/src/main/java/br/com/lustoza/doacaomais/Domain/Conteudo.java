package br.com.lustoza.doacaomais.Domain;

import java.util.Date;

import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Conteudo extends MasterDomain {

    public static String TAG = "Conteudo";

    public int getConteudoId() {
        return ConteudoId;
    }

    public void setConteudoId(int conteudoId) {
        ConteudoId = conteudoId;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getSubtitulo() {
        return Subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        Subtitulo = subtitulo;
    }

    public String getColuna() {
        return Coluna;
    }

    public void setColuna(String coluna) {
        Coluna = coluna;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
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

    private int ConteudoId;
    
    
    private String Titulo;
    
    
    private String Subtitulo;
    
    
    private String Coluna;
    
    
    private String Url = null;
    
    
    private int CacccId;
    
    
    private String Caccc = null;
    
    
    private Date DataCadastro;
    
    
    private String IdentificadorUnico;
}