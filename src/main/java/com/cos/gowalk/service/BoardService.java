package com.cos.gowalk.service;

import com.cos.gowalk.model.Board;
import com.cos.gowalk.repository.BoardRepository;
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
 * @Class    : BoardService.java
 * @Desc     : 리뷰 게시판 로직 처리 서비스
 * */
@Service
public class BoardService {
    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);

    @Autowired
    private BoardRepository boardRepository;

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

        int total = boardRepository.total(data);
        List<Board> list = boardRepository.findByReviewList(data);
        PagingInfoVO pagingInfoVO = new PagingInfoVO(vo.getPageNum(), total, vo.getPageRowCount());
        ResponseResultList resultList = new ResponseResultList();
        logger.info("service = {}",pagingInfoVO.getLastPageNum());
        resultList.setBody(list);
        resultList.setPagingInfo(pagingInfoVO);

        return resultList;


    }


    public boolean insertReview(Board board) {
        try {
            int Check = boardRepository.insertReview(board);
            return Check > 0;
        } catch (Exception e) {
            // 로그를 기록하거나 적절한 예외 처리를 수행
            e.printStackTrace();
            return false;
        }

    }

    public void readCount(long rNum,
                      HttpServletRequest request,
                      HttpServletResponse response) {

        Cookie[] cookies  = request.getCookies();

        boolean visited = false;
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getValue().contains("_" + rNum + "_")) {
                    visited = true;
                    break;
                } else {
                    cookie.setValue(cookie.getValue() + "_" + rNum + "_");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);
                    visited = true;
                    boardRepository.readCount(rNum);
                    break;
                }
            }
        }

        if (!visited) {
            Cookie newCookie = new Cookie("visit_cookie", "_" + rNum + "_");
            newCookie.setMaxAge(60 * 60);
            response.addCookie(newCookie);
            boardRepository.readCount(rNum);
        }


    }

    public Board detail(long lNum) {
        Board board = boardRepository.detail(lNum);

        if (Objects.isNull(board)) {
            return null;
        }

        LocalDateTime updateDt = board.getUpdateDt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String updateDtResult = updateDt.format(formatter);

        board.setUpdateDtResult(updateDtResult);

        return board;
    }

    public boolean editReview(Board board) {
        try {
            int Check = boardRepository.editReview(board);
            return Check > 0;
        } catch (Exception e) {
            // 로그를 기록하거나 적절한 예외 처리를 수행
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteReview(Board board) {
        try {
            int Check = boardRepository.deleteReview(board);
            return Check > 0;
        } catch (Exception e) {
            // 로그를 기록하거나 적절한 예외 처리를 수행
            e.printStackTrace();
            return false;
        }

    }
}
