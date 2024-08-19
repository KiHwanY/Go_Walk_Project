package com.cos.gowalk.controller;

import com.cos.gowalk.model.Notice;
import com.cos.gowalk.model.UserSessionDto;
import com.cos.gowalk.service.NoticeService;
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

import java.util.Objects;

/**
 * @Author   : 윤기환
 * @Class    : NoticeController.java
 * @Desc     : 공지사항 게시판 컨트롤러
 * */
@Controller
public class NoticeController {
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeService noticeService;

    /**
     * @part        : 공지사항 화면 호출
     * @Author      : 윤기환
     * @Type        : ModelAndView
     * */
    @GetMapping("/user/notice/listPage")
    public ModelAndView listPage(@LoginUser UserSessionDto user, @RequestParam("num") Integer num,
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

            ResponseResultList noticeList = noticeService.findByReviewList(vo);

            boolean roleType = "ROLE_ADMIN".equals(user.getRole());

            mv.addObject("list", noticeList.getBody());
            mv.addObject("page", noticeList.getMeta().get("pagingInfo"));
            mv.addObject("select", num == vo.getPageNum());

            mv.addObject("title","title".equals(searchType));
            mv.addObject("content","content".equals(searchType));
            mv.addObject("mem_nickname","mem_nickname".equals(searchType));

            mv.addObject("keyword",keyword);
            mv.addObject("user", user);
            mv.addObject("roleType", roleType);

            mv.setViewName("/notice/noticeList");

        return mv;
    }

    /**
     * @part        : 공지사항 등록 화면 호출
     * @Author      : 윤기환
     * @Type        : ModelAndView
     * */
    @GetMapping("/admin/notice/write")
    public ModelAndView write(@LoginUser UserSessionDto user) {

            ModelAndView mv = new ModelAndView();
            mv.addObject("notice", new Notice());
            mv.addObject("user", user);
            mv.setViewName("/notice/insert");
        return mv;
    }

    /**
     * @part        : 공지사항 상세 화면 호출
     * @Author      : 윤기환
     * @Type        : ModelAndView
     * */
    @GetMapping("/user/notice/detail/{noNum}")
    public ModelAndView detail(@PathVariable("noNum") long noNum,
                               @LoginUser UserSessionDto user,
                               HttpServletRequest request,
                               HttpServletResponse response) {

            ModelAndView mv = new ModelAndView();


            noticeService.readCount(noNum, request, response);
            Notice notice = noticeService.detail(noNum);

            mv.addObject("user", user);
        if (Objects.isNull(notice)) {
                mv.setViewName("/notice/error");
                return mv;
            }


        mv.addObject("notice", notice);
            mv.addObject("authority", user.getIdx() == notice.getMemNum() );

            mv.setViewName("/notice/detail");
        return mv;
    }

    /**
     * @part        : 공지사항 수정 화면 호출
     * @Author      : 윤기환
     * @Type        : ModelAndView
     * */
    @GetMapping("/admin/notice/{noNum}")
    public ModelAndView editPage(@LoginUser UserSessionDto user,
                                 @PathVariable("noNum") long noNum) {

            ModelAndView mv = new ModelAndView();
            Notice notice = noticeService.detail(noNum);

            mv.addObject("user", user);
        if (Objects.isNull(notice)) {
                mv.setViewName("/notice/error");
                return mv;
            }

            mv.addObject("notice", notice);
            mv.setViewName("/notice/edit");
        return mv;
    }

    /**
     * @part        : 공지사항 등록
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping("/admin/notice/insert")
    @ResponseBody
    public String insertNotice(Notice notice) {
        boolean insertCheck = noticeService.insertNotice(notice);
        if (insertCheck) {
            return "success";
        } else {
            return "null";
        }
    }

    /**
     * @part        : 공지사항 수정
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping("/admin/notice/edit")
    @ResponseBody
    public String editNotice(Notice notice) {
        boolean editCheck = noticeService.editNotice(notice);
        if (editCheck) {
            return "success";
        } else {
            return "null";
        }
    }

    /**
     * @part        : 공지사항 삭제
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping("/admin/notice/delete")
    @ResponseBody
    public String deleteNotice(Notice notice) {
        boolean deleteCheck = noticeService.deleteNotice(notice);
        if (deleteCheck) {
            return "success";
        } else {
            return "null";
        }
    }
}
