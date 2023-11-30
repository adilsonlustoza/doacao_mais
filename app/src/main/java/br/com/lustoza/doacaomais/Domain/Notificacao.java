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
    
    
    private Caccc caccc;
    
    
    private int NotificacaoId;
    
    
    private boolean Ativa;

    public Caccc getCaccc() {
        return caccc;
    }

    public void setCaccc(Caccc caccc) {
        this.caccc = caccc;
    }

    public int getNotificacaoId() {
        return NotificacaoId;
    }

    public void setNotificacaoId(int notificacaoId) {
        NotificacaoId = notificacaoId;
    }

    public boolean isAtiva() {
        return Ativa;
    }

    public void setAtiva(boolean ativa) {
        Ativa = ativa;
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

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getConteudo() {
        return Conteudo;
    }

    public void setConteudo(String conteudo) {
        Conteudo = conteudo;
    }

    public Collection<Dispositivo> getDispositivos() {
        return Dispositivos;
    }

    public void setDispositivos(Collection<Dispositivo> dispositivos) {
        Dispositivos = dispositivos;
    }

    private Date DataInicial;
    
    
    private Date DataFinal;
    
    
    private String Titulo;
    
    
    private String Descricao;
    
    
    private String Conteudo;
    
    
    private Collection<Dispositivo> Dispositivos;

    public Notificacao() {
        super();
        Dispositivos = new ArrayList<>();
    }

}


