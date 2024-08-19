package com.cos.gowalk.controller;

import com.cos.gowalk.model.Board;
import com.cos.gowalk.model.Local;
import com.cos.gowalk.model.UserSessionDto;
import com.cos.gowalk.service.BoardService;
import com.cos.gowalk.service.LocalService;
import com.cos.gowalk.util.LoginUser;
import com.cos.gowalk.util.PagingInfoVO;
import com.cos.gowalk.util.ResponseResultList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
* @Author   : 윤기환
* @Class    : BoardController.java
* @Desc     : 리뷰 게시판 컨트롤러
* */
@Controller
@RequestMapping("/user")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private LocalService localService;


    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

    /**
     * @part        : 리뷰 게시판 화면 호출
     * @Author      : 윤기환
     * @Type        : ModelAndView
     * */
    @GetMapping("/review/listPage")
    public ModelAndView listPage(@LoginUser UserSessionDto user,@RequestParam("num") Integer num,
                                 @RequestParam(value = "searchType", required = false, defaultValue = "title") String searchType,
                                 @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                 @RequestParam(value = "area", required = false, defaultValue = "0") Integer area,
                                 @RequestParam(value = "sort", required = false, defaultValue = "add") String sort){

            ModelAndView mv = new ModelAndView();

            PagingInfoVO vo = new PagingInfoVO();
            vo.setPageNum(num);
            vo.setArea(area);
            vo.setSort(sort);
            vo.setStype(searchType);
            vo.setSdata(keyword);

            ResponseResultList reviewList = boardService.findByReviewList(vo);

            Random random = new Random();

            boolean loginType = "OAuth".equals(user.getLoginType());

            logger.info("reviewList.getMeta().get(\"pagingInfo\") : {}", reviewList.getMeta().get("pagingInfo"));

            mv.addObject("list", reviewList.getBody());
            mv.addObject("page", reviewList.getMeta().get("pagingInfo"));
            mv.addObject("local", localService.localList());
            mv.addObject("select", num == vo.getPageNum());

            mv.addObject("randomNumber", random.nextInt(6) + 1);

            mv.addObject("title","title".equals(searchType));
            mv.addObject("content","content".equals(searchType));
            mv.addObject("mem_nickname","mem_nickname".equals(searchType));

            mv.addObject("keyword",keyword);
            mv.addObject("user", user);
            mv.addObject("loginType", loginType);

            mv.setViewName("/board/boardList");

        return mv;
    }

    /**
     * @part        : 리뷰 게시판 등록 화면 호출
     * @Author      : 윤기환
     * @Type        : ModelAndView
     * */
    @GetMapping("/review/write")
    public ModelAndView write(@LoginUser UserSessionDto user ,
                              @RequestParam(value = "lNum", required = false)  Long lNum) {
        ModelAndView mv = new ModelAndView();

        mv.addObject("board", new Board());

        if(lNum != null){
            mv.addObject("lNum", lNum);
        }

            mv.addObject("user", user);
            mv.setViewName("/board/insert");
        return mv;
    }

    /**
     * @part        : 리뷰 상세 화면 호출
     * @Author      : 윤기환
     * @Type        : ModelAndView
     * */
    @GetMapping("/review/detail/{rNum}")
    public ModelAndView detail(@PathVariable("rNum") long rNum,
                               @LoginUser UserSessionDto user,
                               HttpServletRequest request,
                               HttpServletResponse response) {

            ModelAndView mv = new ModelAndView();


            boardService.readCount(rNum, request, response);
            Board board = boardService.detail(rNum);

            mv.addObject("user", user);

            if (Objects.isNull(board)) {
                mv.setViewName("/board/error");
                return mv;
            }

            mv.addObject("review", board);
            mv.addObject("authority", user.getIdx() == board.getMemNum() );

            mv.setViewName("/board/detail");
        return mv;
    }

    /**
     * @part        : 리뷰 수정 화면 호출
     * @Author      : 윤기환
     * @Type        : ModelAndView
     * */
    @GetMapping("/review/editPage/{rNum}")
    public ModelAndView editPage(@LoginUser UserSessionDto user,
                                 @PathVariable("rNum") long rNum) {

            ModelAndView mv = new ModelAndView();
            Board board = boardService.detail(rNum);

            mv.addObject("user", user);

            if (Objects.isNull(board)) {
                mv.setViewName("/board/error");
                return mv;
            }

            mv.addObject("board", board);
            mv.setViewName("/board/edit");
        return mv;
    }

    /**
     * @part        : 지역 전체 리스트 호출
     * @Author      : 윤기환
     * @Type        : List<Local>
     * */
    @GetMapping("/local")
    @ResponseBody
    private List<Local> local() {
        return localService.localList();
    }

    /**
     * @part        : 리뷰 글 등록
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping("/review/insert")
    @ResponseBody
    public String insertReview(Board board) {
        boolean insertCheck = boardService.insertReview(board);
        if (insertCheck) {
            return "success";
        } else {
            return "null";
        }
    }

    /**
     * @part        : 리뷰 글 수정
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping("/review/edit")
    @ResponseBody
    public String editReview(Board board) {
        boolean updateCheck = boardService.editReview(board);
        if (updateCheck) {
            return "success";
        } else {
            return "null";
        }
    }

    /**
     * @part        : 리뷰 글 삭제
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping("/review/delete")
    @ResponseBody
    public String deleteReview(Board board) {
        boolean deleteCheck = boardService.deleteReview(board);
        if (deleteCheck) {
            return "success";
        } else {
            return "null";
        }
    }
}
