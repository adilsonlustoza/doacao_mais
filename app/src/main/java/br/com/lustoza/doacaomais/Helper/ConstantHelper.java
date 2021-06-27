package br.com.lustoza.doacaomais.Helper;

import br.com.lustoza.doacaomais.Utils.UtilityMethods;

/**
 * Created by ubuntu on 12/3/16.
 */
public class ConstantHelper {

    public static final String API_KEY_GOOOGLE = UtilityMethods.GetProperties("apiKeyGooogle");
    public static final String servidorApi = String.format("%s/%s", UtilityMethods.GetProperties("dominio"), UtilityMethods.GetProperties("caminho"));
    public static final String _urlPagSeguro = UtilityMethods.GetProperties("urlPagSeguro");
    public static final String _urlPayPal = UtilityMethods.GetProperties("urlPayPal");
    public static final String _urlPolitica = UtilityMethods.GetProperties("urlPolitica");
    public static final String urlWebApiListAllCampanhas = servidorApi.concat(UtilityMethods.GetProperties("WebApiListAllCampanha"));
    public static final String fileListAllCampanhas = "fileListAllBoletim.json";
    public static final String urlWebApiListarConteudoContasPorCaccc = servidorApi.concat(UtilityMethods.GetProperties("urlWebApiListarConteudoContasPorCaccc"));
    public static final String fileListarConteudoContasPorCaccc = "fileListarConteudoContasPorCaccc.json";
    public static final String urlWebApiSalvarDispositivo = servidorApi.concat(UtilityMethods.GetProperties("urlWebApiSalvarDispositivo"));
    public static final String CEP_MASK = "#####-###";
    public static final String urlMapsApi = UtilityMethods.GetProperties("urlMapsApi");
    public static final String urlAuthorizationLogin = servidorApi.concat(UtilityMethods.GetProperties("urlAuthorizationLogin"));
    public static final String urlWebApiNotificacaoPorDeviceId = servidorApi.concat(UtilityMethods.GetProperties("urlWebApiNotificacaoPorDeviceId"));
    public static final String fileNotiticacaoRecebida = "fileNotiticacaoRecebida.json";
    public static boolean isRegistradoServicos = false;
    public static boolean isLogado;
    public static boolean isLogadoRedesSociais;
    public static boolean foiTrocadaImagemPerfil;
    public static int login = 1;
    public static boolean isStartedAplication = true;
    public static String pref_atualizar = "pref_atualizar";
    public static String AppName = "Doação Mais";
    public static int SplashScreenTime = 4000;
    public static int OneSecond = 1000;
    public static int OneMinute = 1000 * 60;
    public static String ToolbarSubTitleNoticias = "Notícias";
    public static String ToolbarSubTitleVisaoDeRua = "Visão de rua";
    public static String ToolbarSubTitleBazares = "Bazares";
    public static String ToolbarSubTitleCaccc = "Entidades";
    public static String ToolbarSubTitleSuper = "Painel de bordo";
    public static String ToolbarSubTitlePreferencia = "Preferências";
    public static String ToolbarSubTitlePagSeguro = "Integração PagSeguro";
    public static String ToolbarSubTitleLogin = "Identificação básica";
    public static String ToolbarSubTitleBemVindo = "Acessar aplicativo";
    public static String ToolbarSubTitlePerfil = "Perfil";
    public static String ToolbarSubTitleAjuda = "Ajuda";
    public static String TabSobre = "Sobre";
    public static String TabCampanha = "Campanhas";
    public static String TabDonation = "Doação";
    public static String IntentStartAllServices = "doacao.mais.start.services";
    public static String objBundle = "objBundle";
    public static String objCaccc = "objCaccc";
    public static String objBazar = "objBazar";
    public static String objNoticia = "objNoticia";
    public static String objActivity = "objActivity";
    public static String urlWebApiListAllNotificacoes = servidorApi.concat(UtilityMethods.GetProperties("urlWebApiListAllNotificacoes"));
    public static String urlWebApiListAllCaccc = servidorApi.concat(UtilityMethods.GetProperties("urlWebApiListAllCaccc"));
    public static String fileListAllCaccc = "fileListAllCaccc.json";
    public static String urlWebApiListarEstatisticoPorTipo = servidorApi.concat(UtilityMethods.GetProperties("urlWebApiListarEstatisticoPorTipo"));
    public static String fileListarEstatisticoPorTipo = "fileListarEstatisticoPorTipo.json";
    public static String urlWebApiListAllCacccBazar = servidorApi.concat((UtilityMethods.GetProperties("urlWebApiListAllCacccBazar")));
    public static String fileListAllCacccBazar = "fileListAllCacccBazar.json";
    public static String urlWebApiListAllBazar = servidorApi.concat(UtilityMethods.GetProperties("urlWebApiListAllBazar"));
    public static String fileListAllBazar = "fileListAllBazar.json";
    public static String urlWebApiListAllNoticia = servidorApi.concat(UtilityMethods.GetProperties("urlWebApiListAllNoticia"));
    public static String fileListAllNoticia = "fileListAllNoticia.json";
    public static String urlWebApiConteudoContasPorCaccc = servidorApi.concat(UtilityMethods.GetProperties("urlWebApiConteudoContasPorCaccc"));
}
