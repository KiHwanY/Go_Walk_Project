package com.cos.gowalk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author   : 윤기환
 * @Class    : PagingInfoVO.java
 * @Desc     : 페이징 로직 파일
 * */
public class PagingInfoVO extends BaseVO {
    private static final Logger logger = LoggerFactory.getLogger(PagingInfoVO.class);

    private int pageNum;
    private long pageTotalCount;
    private int pageRowCount = 6;
    private int lastPageNum = 0;
    private int pageDown = 0;
    private int pageUp = 0;
    private boolean hasPrevious = false;
    private boolean hasNext = false;

    public PagingInfoVO() {
        this.pageNum = 1;
        this.pageTotalCount = 0;
        this.lastPageNum = 0;
    }


    //  @Autowired
    public PagingInfoVO(int pageNum,
                        long pageTotalCount,
                        int pageRowCount) {

        this.pageNum = pageNum;
        this.pageTotalCount = pageTotalCount;
        this.pageRowCount = pageRowCount;
        this.pageDown = pageNum - 1;
        this.pageUp = pageNum + 1;
        if (pageTotalCount > 0) {
            if (pageTotalCount % pageRowCount > 0) {
                this.lastPageNum = Math.toIntExact((pageTotalCount / pageRowCount + 1));
            } else {
                this.lastPageNum = Math.toIntExact(pageTotalCount / pageRowCount);
            }
            hasPrevious = pageNum != 1;
            hasNext = pageNum != lastPageNum;

        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(long pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public int getPageRowCount() {
        return pageRowCount;
    }

    public int getLastPageNum() {
        return lastPageNum;
    }

    public void setLastPageNum(int lastPageNum) {
        this.lastPageNum = lastPageNum;
    }

    public int getPageDown() {
        return pageDown;
    }

    public void setPageDown(int pageDown) {
        this.pageDown = pageDown;
    }

    public int getPageUp() {
        return pageUp;
    }

    public void setPageUp(int pageUp) {
        this.pageUp = pageUp;
    }

    @Override
    public String toString() {
        return "PagingInfoVO{" +
                "pageNum=" + pageNum +
                ", pageTotalCount=" + pageTotalCount +
                ", pageRowCount=" + pageRowCount +
                ", lastPageNum=" + lastPageNum +
                ", pageDown=" + pageDown +
                ", pageUp=" + pageUp +
                ", hasPrevious=" + hasPrevious +
                ", hasNext=" + hasNext +
                '}';
    }
}

