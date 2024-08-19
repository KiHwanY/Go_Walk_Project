package com.cos.gowalk.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cos.gowalk.model.Board;
import com.cos.gowalk.model.Member;
import com.cos.gowalk.model.UserSessionDto;
import com.cos.gowalk.service.BoardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BoardService boardService;

    @InjectMocks
    private BoardController boardController;

    private MockHttpSession session;

    /**
     * @part        : 테스트 유저 세션 설정
     * @Author      : 윤기환
     * */
    @Before
    public void setup() {

        session = new MockHttpSession();
        Member member = new Member();
        member.setMemNum(9L);
        member.setMemId("naver_53czVp52V2V-VMabRFjXkbylsPQW94LquMw0P48qBhg");
        member.setMemPass("$2a$10$9DC4vopH8fr7OAAQG/GXQeCV8PmYTWX2nZQG8xHKMa9s2fU/6LeXO");
        member.setMemNickName("소셜임");
        member.setAdminCk("ROLE_USER");
        member.setLoginType("OAuth");

        UserSessionDto userSessionDto = new UserSessionDto(member);


        session.setAttribute("user",userSessionDto);
    }
    /**
     * @part        : 리뷰 게시판 화면 호출 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "naver_53czVp52V2V-VMabRFjXkbylsPQW94LquMw0P48qBhg", roles = {"USER"})
    public void testListPage() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("num", "1");
        paramMap.add("searchType", "title");
        paramMap.add("keyword", "부산");

        MvcResult result = mockMvc.perform(get("/user/review/listPage")
                        .session(session)
                        .params(paramMap))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @BeforeEach
    public void insertSetup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }
    /**
     * @part        : 리뷰 게시글 성공 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "naver_53czVp52V2V-VMabRFjXkbylsPQW94LquMw0P48qBhg", roles = {"USER"})
    public void testInsertReviewSuccess() throws Exception {
        // Given
        Board board = new Board();
        board.setMemNum(9L);
        board.setLNum(10L);
        board.setTitle("Test Title");
        board.setContent("Test Content");

        when(boardService.insertReview(board)).thenReturn(true);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/user/review/insert")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 폼 데이터로 요청
                        .param("memNum", "9")
                        .param("lNum", "10")
                        .param("title", "Test Title")
                        .param("content", "Test Content"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"))
                .andDo(print());
    }
    /**
     * @part        : 리뷰 게시글 실패 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "naver_53czVp52V2V-VMabRFjXkbylsPQW94LquMw0P48qBhg", roles = {"USER"})
    public void testInsertReviewFailure() throws Exception {
        // Given
        Board board = new Board();
        board.setMemNum(1L);
        board.setLNum(10L);
        board.setTitle("null test");
        board.setContent("Test Content");

        when(boardService.insertReview(board)).thenReturn(false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/user/review/insert")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 폼 데이터로 요청
                        .param("memNum", "1")
                        .param("lNum", "10")
                        .param("title", "null Title")
                        .param("content", "Test Content"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("null"))
                .andDo(print());
    }

    /**
     * @part        : 리뷰 게시글 수정 성공 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "naver_53czVp52V2V-VMabRFjXkbylsPQW94LquMw0P48qBhg", roles = {"USER"})
    public void testEditReviewSuccess() throws Exception {
        // Given
        Board board = new Board();
        board.setRNum(1L);
        board.setLNum(10L);
        board.setTitle("Edited Title");
        board.setContent("Edited Content");

        when(boardService.editReview(board)).thenReturn(true);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/user/review/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("rNum" , "1")
                        .param("lNum", "10")
                        .param("title", "Edited Title")
                        .param("content", "Edited Content"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"))
                .andDo(print());
    }

    /**
     * @part        : 리뷰 게시글 수정 실패 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "naver_53czVp52V2V-VMabRFjXkbylsPQW94LquMw0P48qBhg", roles = {"USER"})
    public void testEditReviewFailure() throws Exception {
        // Given
        Board board = new Board();
        board.setRNum(1L);
        board.setReviewYn("N");

        // Mock 설정: editReview 메서드가 false를 반환하도록 설정
        when(boardService.editReview(board)).thenReturn(false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/user/review/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("rNum" , "1")
                        .param("lNum", "10")
                        .param("title", "Edited Title")
                        .param("content", "Edited Content"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("null"))
                .andDo(print());
    }

    /**
     * @part        : 리뷰 게시글 삭제 성공 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "naver_53czVp52V2V-VMabRFjXkbylsPQW94LquMw0P48qBhg", roles = {"USER"})
    public void testDeleteReviewSuccess() throws Exception {
        // Given
        Board board = new Board();
        board.setRNum(1L);
        board.setReviewYn("N");

        when(boardService.deleteReview(board)).thenReturn(true);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/user/review/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("rNum", "1")
                        .param("reviewYn", "N"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"))
                .andDo(print());
    }

    /**
     * @part        : 리뷰 게시글 삭제 실패 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "naver_53czVp52V2V-VMabRFjXkbylsPQW94LquMw0P48qBhg", roles = {"USER"})
    public void testDeleteReviewFailure() throws Exception {
        // Given
        Board board = new Board();
        board.setRNum(1L);
        board.setReviewYn("N");

        when(boardService.deleteReview(board)).thenReturn(false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/user/review/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("rNum", "1")
                        .param("reviewYn", "N"))
                .andExpect(MockMvcResultMatchers.status().isOk( ))
                .andExpect(MockMvcResultMatchers.content().string("null"))
                .andDo(print());
    }



}
