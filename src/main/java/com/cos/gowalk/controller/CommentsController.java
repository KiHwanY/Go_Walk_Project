package com.cos.gowalk.controller;

import com.cos.gowalk.model.Comments;
import com.cos.gowalk.model.UserSessionDto;
import com.cos.gowalk.service.CommentsService;
import com.cos.gowalk.util.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author   : 윤기환
 * @Class    : CommentsController.java
 * @Desc     : 댓글 관리 컨트롤러
 * */
@Controller
@RequestMapping("/user")
public class CommentsController {

    private static final Logger logger = LoggerFactory.getLogger(CommentsController.class);

    @Autowired
    private CommentsService commentsService;

    /**
     * @part        : 댓글 리스트 화면 호출
     * @Author      : 윤기환
     * @Type        : ModelAndView
     * */
    @GetMapping("/comment/commentsList")
    public ModelAndView commentsList(@LoginUser UserSessionDto user,
                                     Comments comments){

            ModelAndView mv = new ModelAndView();


            List<Comments> commentList = commentsService.findByCommentsList(comments,user.getIdx());

            mv.addObject("user", user);
            mv.addObject("commentList", commentList);
            mv.setViewName("/comments/commentList");

        return mv;
    }

    /**
     * @part        : 댓글 등록
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping("/comment/insert")
    @ResponseBody
    public String insertComments(Comments comments) {
        boolean insertCheck = commentsService.insertComments(comments);
        if (insertCheck) {
            return "success";
        } else {
            return "null";
        }
    }

    /**
     * @part        : 댓글 수정
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping("/comment/edit")
    @ResponseBody
    public String editComments(Comments comments) {
        boolean editCheck = commentsService.editComments(comments);
        if (editCheck) {
            return "success";
        } else {
            return "null";
        }
    }

    /**
     * @part        : 댓글 삭제
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping("/comment/delete")
    @ResponseBody
    public String deleteComment(Comments comments) {
        boolean deleteCheck = commentsService.deleteComments(comments);
        if (deleteCheck) {
            return "success";
        } else {
            return "null";
        }
    }

}
