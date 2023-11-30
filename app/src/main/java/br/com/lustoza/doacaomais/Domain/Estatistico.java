package br.com.lustoza.doacaomais.Domain;

import java.util.Date;

import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Estatistico extends MasterDomain {

    public static String TAG = "Estatistico";

    public int getEstatisticoId() {
        return EstatisticoId;
    }

    public void setEstatisticoId(int estatisticoId) {
        EstatisticoId = estatisticoId;
    }

    public Float getValor() {
        return Valor;
    }

    public void setValor(Float valor) {
        Valor = valor;
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

    public Integer getTipo() {
        return Tipo;
    }

    public void setTipo(Integer tipo) {
        Tipo = tipo;
    }

    public String getFonte() {
        return Fonte;
    }

    public void setFonte(String fonte) {
        Fonte = fonte;
    }

    private int EstatisticoId;
    
    
    private Float Valor;
    
    
    private String Nome;
    
    
    private String Descricao;
    
    
    private Date DataCadastro;
    
    
    private String IdentificadorUnico;
    
    
    private Integer Tipo;
    
    
    private String Fonte;

}