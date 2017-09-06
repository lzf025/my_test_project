package com.li.food.bean;

import com.google.gson.JsonElement;
import java.lang.reflect.Field;

/**
 * 数据返回基类bean
 */

public class BaseBean {


    protected JsonElement result;
    private String status;
    private String msg;

    public JsonElement getResult() {
        return result;
    }

    public void setResult(JsonElement result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        String[] types1 = {"int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte"};
        String[] types2 = {"Integer", "java.lang.String", "java.lang.Boolean", "java.lang.Character", "java.lang.Float", "java.lang.Double", "java.lang.Long", "java.lang.Short", "java.lang.Byte"};
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        Field[] fields = this.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String name = fields[i].getName();
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            // 字段值
            for (int m = 0; m < types1.length; m++) {
                if (fields[i].getType().getName()
                        .equalsIgnoreCase(types1[m]) || fields[i].getType().getName().equalsIgnoreCase(types2[m])) {
                    try {
                        sb.append(name + ":");
                        sb.append(fields[i].get(this) + ";\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sb.toString();
    }
}

