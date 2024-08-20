package com.cos.gowalk.controller;

import com.cos.gowalk.model.Board;
import com.cos.gowalk.model.Member;
import com.cos.gowalk.model.Notice;
import com.cos.gowalk.model.UserSessionDto;
import com.cos.gowalk.service.AdminService;
import com.cos.gowalk.service.BoardService;
import com.cos.gowalk.service.NoticeService;
import com.cos.gowalk.util.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;



    @GetMapping("/adminPage")
    public ModelAndView adminPage(@LoginUser UserSessionDto user){

        logger.info("유저 권한 값 체크 = {}" , user.getRole());

        ModelAndView mv  = new ModelAndView();

        if ("ROLE_ADMIN".equals(user.getRole())){

            List<Member> memberList = adminService.userList();
            List<Board> boardList = adminService.boardList();
            List<Notice> noticeList = adminService.noticeList();


            mv.addObject("member" ,memberList);
            mv.addObject("memCount" ,memberList.size() > 5);
            mv.addObject("board" ,boardList);
            mv.addObject("notice" ,noticeList);
            mv.addObject("user" , user);
            mv.setViewName("/admin/adminPage");

        }else{
            mv.setViewName("/admin/error");
        }

        return mv;
    }
}
