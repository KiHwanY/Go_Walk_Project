package com.cos.gowalk.service;

import com.cos.gowalk.model.Notice;
import com.cos.gowalk.repository.NoticeRepository;
import com.cos.gowalk.util.PagingInfoVO;
import com.cos.gowalk.util.ResponseResultList;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Author   : 윤기환
 * @Class    : NoticeService.java
 * @Desc     : 공지사항 로직 처리 서비스
 * */
@Service
public class NoticeService {
    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

    @Autowired
    private NoticeRepository noticeRepository;

    public ResponseResultList findByReviewList(PagingInfoVO vo) {

        HashMap<String, Object> data = new HashMap<String, Object>();

        int currentPageNum = (Integer) vo.getPageNum();
        int pageRowCount = (Integer)  vo.getPageRowCount();
        int offset = (currentPageNum-1) * pageRowCount;

        data.put("offset",offset);
        data.put("pageRowCount",pageRowCount);
        data.put("searchType", vo.getStype());
        data.put("keyword", vo.getSdata());
        data.put("area", vo.getArea());
        data.put("sort", vo.getSort());

        int total = noticeRepository.total(data);
        List<Notice> list = noticeRepository.findByReviewList(data);
        PagingInfoVO pagingInfoVO = new PagingInfoVO(vo.getPageNum(), total, vo.getPageRowCount());
        ResponseResultList resultList = new ResponseResultList();

        LocalDateTime updateDt = null;

        for (Notice notice : list) {

            updateDt = notice.getUpdateDt();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String updateDtResult = updateDt.format(formatter);

            notice.setUpdateDtResult(updateDtResult);

        }



        resultList.setBody(list);
        resultList.setPagingInfo(pagingInfoVO);

        return resultList;


    }

    public boolean insertNotice(Notice notice) {
        try {
            int Check = noticeRepository.insertNotice(notice);
            return Check > 0;
        } catch (Exception e) {
            // 로그를 기록하거나 적절한 예외 처리를 수행
            e.printStackTrace();
            return false;
        }
    }

    public void readCount(long noNum,
                      HttpServletRequest request,
                      HttpServletResponse response) {

        Cookie[] cookies  = request.getCookies();
        logger.info("request get = {}" , noNum);
        boolean visited = false;
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getValue().contains("_" + noNum + "_")) {
                    visited = true;
                    break;
                } else {
                    cookie.setValue(cookie.getValue() + "_" + noNum + "_");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);
                    visited = true;
                    noticeRepository.readCount(noNum);
                    break;
                }
            }
        }

        if (!visited) {
            Cookie newCookie = new Cookie("visit_cookie", "_" + noNum + "_");
            newCookie.setMaxAge(60 * 60);
            response.addCookie(newCookie);
            noticeRepository.readCount(noNum);
        }
    }
    public Notice detail(long noNum) {
        Notice notice  = noticeRepository.detail(noNum);

        if (Objects.isNull(notice)) {
            return null;
        }

        LocalDateTime updateDt = notice.getUpdateDt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String updateDtResult = updateDt.format(formatter);

        notice.setUpdateDtResult(updateDtResult);

        return notice;
    }

    public boolean editNotice(Notice notice) {
        try {
            int Check = noticeRepository.editNotice(notice);
            return Check > 0;
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteNotice(Notice notice) {
        try {
            int Check = noticeRepository.deleteNotice(notice);
            return Check > 0;
        } catch (Exception e) {
            // 로그를 기록하거나 적절한 예외 처리를 수행
            e.printStackTrace();
            return false;
        }

    }
}
