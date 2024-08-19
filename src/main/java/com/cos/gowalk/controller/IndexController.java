package com.cos.gowalk.controller;

import com.cos.gowalk.model.Local;
import com.cos.gowalk.model.Member;
import com.cos.gowalk.model.UserSessionDto;
import com.cos.gowalk.service.LocalService;
import com.cos.gowalk.service.UserService;
import com.cos.gowalk.util.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
/**
 * @Author   : 윤기환
 * @Class    : IndexController.java
 * @Desc     : 회원가입 및 로그인 후 처리 컨트롤러
 * */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private LocalService localService;

    /**
     * @part        : 메인 화면 호출
     * @Author      : 윤기환
     * @Type        : String
     * */
    @GetMapping({"", "/"})
    public String index(Model model,
                        @LoginUser UserSessionDto user) {
        logger.info("Controller Session userId = {}", user.getUsername());
        logger.info("Controller Session getIdx = {}", user.getIdx());
        logger.info("Controller Session getRole = {}", user.getRole());
        logger.info("Controller Session getNickname = {}", user.getNickname());

        Random random = new Random();
        boolean hasAdminOrManagerRole = hasAdminOrManagerRole(user);
        List<Local> localList = localService.localList();

        model.addAttribute("user", user);
        model.addAttribute("role", hasAdminOrManagerRole);
        model.addAttribute("randomNumber", random.nextInt(4) + 1);
        model.addAttribute("localList", localList);

        if (!hasAdminOrManagerRole) {
            logger.info("유저가 로그인 했습니다.");
        }

        return "index";
    }

    /**
     * @part        : 아이디 중복 체크
     * @Author      : 윤기환
     * @Type        : int
     * */
    @GetMapping("/basic/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam("mem_id") String memid) {
        int cnt = userService.idCheck(memid);
        return cnt;
    }

    /**
     * @part        : 회원 가입
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping("/join")
    @ResponseBody
    public String join(Member member) {
        boolean saveCheck = userService.save(member);
        if (saveCheck) {
            return "success";
        } else {
            return "null";
        }
    }
    /**
     * @part        : 로그인 화면 호출
     * @Author      : 윤기환
     * @Type        : String
     * */
    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model
                          ) {
        logger.info("loginForm called");
        logger.info("(Controller)Error: " + error);
        logger.info("(Controller)Exception: " + exception);

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "loginForm";
    }

    /**
     * @part        : 회원 가입 화면 호출
     * @Author      : 윤기환
     * @Type        : String
     * */
    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    /**
     * @part        : 모달창 호출
     * @Author      : 윤기환
     * @Type        : Local
     * */
    @ResponseBody
    @PostMapping("/user/modal/{num}")
    public Local Modal(@PathVariable("num") Integer lNum) {
        Local local = localService.selectLocalList(lNum);
        return local;
    }

    /**
     * @part        : 필수 조건 메서드 정의
     * @Author      : 윤기환
     * @Type        : boolean
     * */
    private boolean hasAdminOrManagerRole(UserSessionDto user) {
        return "ROLE_ADMIN".equals(user.getRole()) || "ROLE_MANAGER".equals(user.getRole());
    }

}
