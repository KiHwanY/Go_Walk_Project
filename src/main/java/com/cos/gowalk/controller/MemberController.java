package com.cos.gowalk.controller;

import com.cos.gowalk.model.Member;
import com.cos.gowalk.model.UserSessionDto;
import com.cos.gowalk.service.UserService;
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
 * @Class    : MemberController.java
 * @Desc     : 유저 관리 컨트롤러
 * */
@Controller
@RequestMapping("/user")
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private UserService userService;

    /**
     * @part        : 회원 정보 화면 호출
     * @Author      : 윤기환
     * @Type        : ModelAndView
     * */
    @GetMapping("/myPage")
    public ModelAndView myPage(@LoginUser UserSessionDto user){

        ModelAndView mv = new ModelAndView();

            List<Member> findMember = userService.findByMember(user.getIdx());

            boolean loginType = "OAuth".equals(user.getLoginType());

            mv.addObject("member" , findMember);
            mv.addObject("user", user);
            mv.addObject("loginType", loginType);

            mv.setViewName("/member/myPage");


        return mv;
    }

    /**
     * @part        : 회원 정보 수정
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping("/updateMember")
    @ResponseBody
    public String updateMember(Member member) {
        boolean updateCheck = userService.updateMember(member);
        if (updateCheck) {
            return "success";
        } else {
            return "null";
        }
    }

}
