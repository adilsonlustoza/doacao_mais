package br.com.lustoza.doacaomais.Domain.ObjectValue;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ubuntu on 2/12/17.
 */
public class Endereco implements Serializable {

    
    
    private String Cep;
    
    
    private String Logradouro;
    
    
    private String Numero;
    
    
    private double Longitude;
    
    
    private double Latitude;
    
    
    private String Bairro;
    
    
    private String Cidade;
    
    
    private String Estado;
    
    
    private String Pais;
    
    
    private String PaisSigla;

    public String getCep() {
        return Cep;
    }

    public void setCep(String cep) {
        Cep = cep;
    }

    public String getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(String logradouro) {
        Logradouro = logradouro;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    public String getPaisSigla() {
        return PaisSigla;
    }

    public void setPaisSigla(String paisSigla) {
        PaisSigla = paisSigla;
    }

    public Endereco() {
    }

    public Endereco(String logradouro, String bairro, String cidade, String estado, String cep) {
        this.setLogradouro(logradouro);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setEstado(estado);
        this.setCep(cep);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", this.getLogradouro(), this.getBairro(), this.getCidade(), this.getEstado());
    }

}
