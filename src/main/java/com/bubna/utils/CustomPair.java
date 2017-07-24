package com.bubna.utils;

public class CustomPair<T1, T2> {

    private T1 v1;
    private T2 v2;

    public CustomPair(T1 v1, T2 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public T1 getV1() {
        return v1;
    }

    public T2 getV2() {
        return v2;
    }

    public void setV1(T1 v1) {
        this.v1 = v1;
    }

    public void setV2(T2 v2) {
        this.v2 = v2;
    }

    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (v1==null?0:v1.hashCode());
        hashCode = 31 * hashCode + (v2==null?0:v2.hashCode());
        return hashCode;
    }

    public boolean equals(Object object) {
        if (!object.getClass().equals(this.getClass())) return false;
        CustomPair customPair = (CustomPair) object;
        return v1.equals(customPair.v1) && v2.equals(customPair.v2);
    }
}
