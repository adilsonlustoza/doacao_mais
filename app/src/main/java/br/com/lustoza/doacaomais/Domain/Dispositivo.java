package br.com.lustoza.doacaomais.Domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Dispositivo {

    public static String TAG = "Noticia";
    
    
    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRelease() {
        return Release;
    }

    public void setRelease(String release) {
        Release = release;
    }

    public String getIncremental() {
        return Incremental;
    }

    public void setIncremental(String incremental) {
        Incremental = incremental;
    }

    public String getRadioVersion() {
        return RadioVersion;
    }

    public void setRadioVersion(String radioVersion) {
        RadioVersion = radioVersion;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }

    public String getBoard() {
        return Board;
    }

    public void setBoard(String board) {
        Board = board;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getBootLoader() {
        return BootLoader;
    }

    public void setBootLoader(String bootLoader) {
        BootLoader = bootLoader;
    }

    public String getDisplay() {
        return Display;
    }

    public void setDisplay(String display) {
        Display = display;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getFingerPrint() {
        return FingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        FingerPrint = fingerPrint;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getManufacture() {
        return Manufacture;
    }

    public void setManufacture(String manufacture) {
        Manufacture = manufacture;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getVersaoSDK() {
        return VersaoSDK;
    }

    public void setVersaoSDK(String versaoSDK) {
        VersaoSDK = versaoSDK;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    private String Release;
    
    
    private String Incremental;
    
    
    private String RadioVersion;
    
    
    private String Serial;
    
    
    private String Board;
    
    
    private String Brand;
    
    
    private String BootLoader;
    
    
    private String Display;
    
    
    private String Device;
    
    
    private String DeviceId;
    
    
    private String FingerPrint;
    
    
    private String Host;
    
    
    private String Model;
    
    
    private String Manufacture;
    
    
    private String Product;
    
    
    private String User;
    
    
    private String VersaoSDK;
    
    
    private String Code;

    //     public Collection<Notificacao> Notificacoes;
}
