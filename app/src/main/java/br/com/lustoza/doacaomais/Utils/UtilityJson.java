package br.com.lustoza.doacaomais.Utils;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class UtilityJson<T> {

    public String ParseObjToJson(T obj, Class<?> clazz) {
        String json = null;

        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
            json = gsonBuilder.create().toJson(obj, TypeToken.get(clazz).getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public T ParseJsonToObj(String json, Type clazz) {
        T obj = null;

        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
            obj = gsonBuilder.create().fromJson(json, TypeToken.get(clazz).getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public List<T> ParseJsonArrayToList(String jsonArray, Class<?> clazz) {
        List<T> list = null;
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
            gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
            list = gsonBuilder.create().fromJson(jsonArray, typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
