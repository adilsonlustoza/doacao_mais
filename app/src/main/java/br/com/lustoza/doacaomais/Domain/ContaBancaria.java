package br.com.lustoza.doacaomais.Domain;

import br.com.lustoza.doacaomais.Domain.ObjectValue.MasterDomain;

public class ContaBancaria extends MasterDomain {

    public static String TAG = "ContaBancaria";
    
    
    private int ContaBancariaId;
    
    
    private String NumeroBanco = null;
    
    
    private String NomeBanco;

    public int getContaBancariaId() {
        return ContaBancariaId;
    }

    public void setContaBancariaId(int contaBancariaId) {
        ContaBancariaId = contaBancariaId;
    }

    public String getNumeroBanco() {
        return NumeroBanco;
    }

    public void setNumeroBanco(String numeroBanco) {
        NumeroBanco = numeroBanco;
    }

    public String getNomeBanco() {
        return NomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        NomeBanco = nomeBanco;
    }

    public String getAgencia() {
        return Agencia;
    }

    public void setAgencia(String agencia) {
        Agencia = agencia;
    }

    public String getConta() {
        return Conta;
    }

    public void setConta(String conta) {
        Conta = conta;
    }

    public String getBeneficiario() {
        return Beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        Beneficiario = beneficiario;
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

    private String Agencia;
    
    
    private String Conta;
    
    
    private String Beneficiario;
    
    
    private String UrlImagem = null;
    
    
    private float CacccId;
    
    
    private String Caccc = null;
    
    
    private String DataCadastro;
    
    
    private String IdentificadorUnico;

}