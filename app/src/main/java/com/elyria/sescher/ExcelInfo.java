package com.elyria.sescher;

/**
 * Created by jungletian on 2017/10/28.
 */
public class ExcelInfo {
    private int index;
    private String ip;
    private String community;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    @Override
    public String toString() {
        return "ExcelInfo{" + "index=" + index + ", ip='" + ip + '\'' + ", community='" + community + '\'' + '}';
    }
}
