package br.com.lustoza.doacaomais.Domain;

import br.com.lustoza.doacaomais.Domain.ObjectValue.Endereco;
import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;

public class Bazar extends MasterDomain {

    public static String TAG = "Bazar";
    private String Nome;
    private String Description;
    private String Informacao;

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

    public String getInformacao() {
        return Informacao;
    }

    public void setInformacao(String informacao) {
        Informacao = informacao;
    }

    public br.com.lustoza.doacaomais.Domain.ObjectValue.Endereco getEndereco() {
        return Endereco;
    }

    public void setEndereco(br.com.lustoza.doacaomais.Domain.ObjectValue.Endereco endereco) {
        Endereco = endereco;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getUrlImagem() {
        return UrlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        UrlImagem = urlImagem;
    }

    public float getCacccId() {
        return CacccId;
    }

    public void setCacccId(float cacccId) {
        CacccId = cacccId;
    }

    public String getCaccc() {
        return Caccc;
    }

    public void setCaccc(String caccc) {
        Caccc = caccc;
    }

    private Endereco Endereco;
    private String Email = null;
    private String Telefone = null;
    private String UrlImagem;
    private float CacccId;
    private String Caccc = null;
}