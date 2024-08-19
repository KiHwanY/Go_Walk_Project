package com.cos.gowalk.controller;


import com.cos.gowalk.model.Member;
import com.cos.gowalk.model.Notice;
import com.cos.gowalk.model.UserSessionDto;
import com.cos.gowalk.service.NoticeService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private NoticeService noticeService;

    @InjectMocks
    private NoticeController noticeController;

    private MockHttpSession session;

    /**
     * @part        : 테스트 유저 세션 설정
     * @Author      : 윤기환
     * */
    @Before
    public void setup() {

        session = new MockHttpSession();
        Member member = new Member();
        member.setMemNum(1L);
        member.setMemId("kim");
        member.setMemPass("$2a$10$iyazGfbtvPqH8MLLy3dXDOWJ89.hE5E6ifzEaZqtNYIzfySlVhpBC");
        member.setMemNickName("사람인");
        member.setAdminCk("ROLE_ADMIN");

        UserSessionDto userSessionDto = new UserSessionDto(member);


        session.setAttribute("user",userSessionDto);
    }
    /**
     * @part        : 공지사항 게시판 화면 호출 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "kim", roles = {"ADMIN"})
    public void testListPage() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("num", "1");
        paramMap.add("searchType", "title");
        paramMap.add("keyword", "테스트");

        MvcResult result = mockMvc.perform(get("/user/notice/listPage")
                        .session(session)
                        .params(paramMap))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @BeforeEach
    public void insertSetup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(noticeController).build();
    }
    /**
     * @part        : 공지사항 게시글 등록 성공 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "kim", roles = {"ADMIN"})
    public void testInsertNocieSuccess() throws Exception {
        // Given
        Notice notice = new Notice();
        notice.setMemNum(1L);
        notice.setTitle("Test Title");
        notice.setContent("Test Content");

        when(noticeService.insertNotice(notice)).thenReturn(true);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/notice/insert")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 폼 데이터로 요청
                        .param("memNum", "1")
                        .param("title", "Test Title")
                        .param("content", "Test Content"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"))
                .andDo(print());
    }
    /**
     * @part        : 리뷰 게시글 등록 실패 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "kim", roles = {"ADMIN"})
    public void testInsertNoticeFailure() throws Exception {
        // Given
        Notice notice = new Notice();
        notice.setMemNum(1L);
        notice.setTitle("null Title");
        notice.setContent("Test Content");

        when(noticeService.insertNotice(notice)).thenReturn(false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/notice/insert")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 폼 데이터로 요청
                        .param("memNum", "1")
                        .param("title", "null Title")
                        .param("content", "Test Content"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("null"))
                .andDo(print());
    }

    /**
     * @part        : 공지사항 게시글 수정 성공 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "kim", roles = {"ADMIN"})
    public void testEditNoticeSuccess() throws Exception {
        // Given
        Notice notice = new Notice();
        notice.setNoNum(11L);
        notice.setMemNum(1L);
        notice.setTitle("성공 테스트 진행");
        notice.setContent("성공 테스트 진행합니다.");

        when(noticeService.editNotice(notice)).thenReturn(true);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/notice/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("noNum" , "11")
                        .param("memNum", "1")
                        .param("title", "성공 테스트 진행")
                        .param("content", "성공 테스트 진행합니다."))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"))
                .andDo(print());
    }

    /**
     * @part        : 공지사항 게시글 수정 실패 테스트
     * @Author      : 윤기환
     * */
        @Test
        @WithMockUser(username = "kim", roles = {"ADMIN"})
        public void testEditNoticeFailure() throws Exception {
        // Given
        Notice notice = new Notice();
        notice.setNoNum(12L);
        notice.setMemNum(1L);
        notice.setTitle("실패 테스트 진행");
        notice.setContent("실패 테스트 진행합니다.");

        when(noticeService.editNotice(notice)).thenReturn(false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/notice/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("noNum" , "12")
                        .param("memNum", "1")
                        .param("title", "실패 테스트 진행")
                        .param("content", "실패 테스트 진행합니다."))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("null"))
                .andDo(print());
    }

    /**
     * @part        : 공지사항 게시글 삭제 성공 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "kim", roles = {"ADMIN"})
    public void testDeleteNoticeSuccess() throws Exception {
        // Given
        Notice notice = new Notice();
        notice.setNoNum(11L);
        notice.setNoticeYn("N");

        when(noticeService.deleteNotice(notice)).thenReturn(true);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/notice/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("noNum", "11")
                        .param("noticeYn", "N"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"))
                .andDo(print());
    }

    /**
     * @part        : 공지사항 게시글 삭제 실패 테스트
     * @Author      : 윤기환
     * */
    @Test
    @WithMockUser(username = "kim", roles = {"ADMIN"})
    public void testDeleteNoticeFailure() throws Exception {
        // Given
        Notice notice = new Notice();
        notice.setNoNum(12L);
        notice.setNoticeYn("N");

        when(noticeService.deleteNotice(notice)).thenReturn(false);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/notice/delete")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("noNum", "12")
                        .param("noticeYn", "N"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("null"))
                .andDo(print());
    }



}
