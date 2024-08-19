package com.cos.gowalk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author   : 윤기환
 * @Class    : ResponseResultList.java
 * @Desc     : 페이징 로직 호출 파일
 * */
public class ResponseResultList extends ResponseResult {
    private static final Logger logger = LoggerFactory.getLogger(ResponseResultList.class);
    private List body = null;

    public List getBody() {
        List resultList = new ArrayList();
        resultList.addAll(body);
        return resultList;
    }

    public void setBody(List body) {
        List resultList = new ArrayList();
        if (body != null){
            resultList.addAll(body);
        }
        this.body = resultList;
    }

    public void setPagingInfo(PagingInfoVO vo) {
        if (this.getMeta() == null) {
            this.setMeta(new HashMap<String, Object>());
        }
        this.addMetaItem("pagingInfo", vo);
    }
}