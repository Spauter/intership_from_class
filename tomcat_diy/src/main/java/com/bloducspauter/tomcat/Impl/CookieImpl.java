package com.bloducspauter.tomcat.Impl;


import com.bloducspauter.tomcat.Cookie;

public class CookieImpl implements Cookie {

    private String domain;
    private Integer maxAge;
    private String path;
    private String name;
    private String value;

    @Override
    public String toString() {
        String ret = String.format("Set-Cookie: %s=%s", name, value);
        if (maxAge != null){
            ret += String.format("; Max-Age=%s",maxAge);
        }
        if (path != null){
            ret += String.format("; Path=%s",path);
        }
        if (domain != null){
            ret += String.format("; Damain=%s",domain);
        }
        ret +="\n";
        return ret;
    }

    public CookieImpl(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void setDomain(String pattern) {
        this.domain = pattern;
    }

    @Override
    public void setMaxAge(int expiry) {
        this.maxAge = expiry;
    }

    @Override
    public void setPath(String uri) {
        this.path = uri;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void add(Cookie cookie) {
    }
}
