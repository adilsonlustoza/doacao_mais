package br.com.lustoza.doacaomais.Domain.ObjectValue;

import java.io.Serializable;

public enum TpDoacao implements Serializable {

    Nenhum(0),
    CupomFiscal(1),
    PagSeguro(2),
    PayPal(3),
    PagSeguro_PayPal(4),
    ContaBancaria(5);

    public int valor;

    TpDoacao(int valor) {
        this.valor = valor;
    }

}
