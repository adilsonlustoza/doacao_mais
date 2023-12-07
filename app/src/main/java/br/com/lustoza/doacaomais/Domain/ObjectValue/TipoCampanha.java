package br.com.lustoza.doacaomais.Domain.ObjectValue;

/**
 * Created by Adilson on 31/12/2017.
 */

public enum TipoCampanha {

    Campanha(1),
    Evento(2),
    Projeto(3),
    Noticia(4),
    Depoimento(5),
    Parceiro(6),
    Voluntario(7);
    private final int valor;

    TipoCampanha(int valorOpcao) {
        valor = valorOpcao;
    }
}
