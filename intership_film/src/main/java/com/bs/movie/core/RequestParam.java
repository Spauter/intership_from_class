package com.bs.movie.core;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Company 源辰信息
 * 参数对象
 * @author navy
 * @date 2024/3/4
 * @Email haijunzhou@hnit.edu.cn
 */
public class RequestParam {
    private Map<String, String> parameter = new Hashtable<>();
    private Map<String, List<Part>> partMap = new Hashtable<>();

    public RequestParam() {

    }

    public RequestParam(Map<String, String> parameter, Map<String, List<Part>> partMap) {
        this.parameter = parameter;
        this.partMap = partMap;
    }

    public RequestParam(Map<String, String> parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return "RequestParam{" +
                "parameter=" + parameter +
                ", partList=" + partMap +
                '}';
    }

    public Map<String, String> getParameterMap() {
        return parameter;
    }

    public String getParameter(String name) {
        return parameter.get(name);
    }

    public void setParameter(Map<String, String> parameter) {
        this.parameter = parameter;
    }

    public Map<String, List<Part>> getPartMap() {
        return partMap;
    }

    public Part getPart(String name) {
        List<Part> parts = partMap.get(name);
        if (parts != null && !parts.isEmpty()) {
            return parts.get(0);
        }
        return null;
    }

    public List<Part> getParts(String name) {
        return partMap.get(name);
    }

    public void setPartList(Map<String, List<Part>> partMap) {
        this.partMap = partMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestParam that = (RequestParam) o;
        return Objects.equals(parameter, that.parameter) && Objects.equals(partMap, that.partMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter, partMap);
    }
}
