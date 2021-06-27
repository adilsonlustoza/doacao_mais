package br.com.lustoza.doacaomais.Domain.ObjectValue;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ubuntu on 2/12/17.
 */
public class Endereco implements Serializable {

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Cep;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Logradouro;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Numero;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private double Longitude;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private double Latitude;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Bairro;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Cidade;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Estado;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String Pais;
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String PaisSigla;

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
