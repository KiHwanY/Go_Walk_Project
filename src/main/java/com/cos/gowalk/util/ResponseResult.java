package com.cos.gowalk.util;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.HashMap;

/**
 * @Author   : 윤기환
 * @Class    : ResponseResult.java
 * @Desc     : 페이징 로직 파일
 * */
@JsonRootName(value="responseResult")
public class ResponseResult {

    private String msg;
    private HashMap<String, Object> meta;
    private Long prev;
    private Long next;

    public Long getPrev() {
        return prev;
    }
    public void setPrev(Long prev) {
        this.prev = prev;
    }
    public Long getNext() {
        return next;
    }
    public void setNext(Long next) {
        this.next = next;
    }

    public void addMetaItem(String key,
                            Object value){

        if (meta == null){
            meta = new HashMap<String, Object>();
        }
        meta.put(key, value);
    }

    public HashMap<String, Object> getMeta() {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        if (meta != null){
            for( String key : meta.keySet() ){
                returnMap.put(key, meta.get(key));
            }
        }
        return returnMap;
    }

    public void setMeta(HashMap<String, Object> meta) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        if (meta != null){
            for( String key : meta.keySet() ){
                returnMap.put(key, meta.get(key));
            }
        }
        this.meta = returnMap;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}