package com.cos.gowalk.service;

import com.cos.gowalk.model.Comments;
import com.cos.gowalk.repository.CommentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author   : 윤기환
 * @Class    : CommentsService.java
 * @Desc     : 댓글 로직 처리 서비스
 * */
@Service
public class CommentsService {
    private static final Logger logger = LoggerFactory.getLogger(CommentsService.class);

    @Autowired
    private CommentsRepository commentsRepository;

    public List<Comments> findByCommentsList(Comments comments, Long idx) {
        List<Comments> commentsList = commentsRepository.commentsList(comments);

        boolean userCheck = false;
        LocalDateTime updateDt = null;
        logger.info("댓글 갯수는 ?  = {}" , commentsList.size());
        for (Comments commentsArr : commentsList) {
            userCheck = Boolean.parseBoolean(String.valueOf(commentsArr.getMemNum() == idx));
            commentsArr.setUserCheck(userCheck);

            updateDt = commentsArr.getUpdateDt();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String updateDtResult = updateDt.format(formatter);

            commentsArr.setUpdateDtResult(updateDtResult);

        }


        return commentsList;
    }

    public boolean insertComments(Comments comments) {
        try {
            int Check = commentsRepository.insertComments(comments);
            return Check > 0;
        } catch (Exception e) {
            // 로그를 기록하거나 적절한 예외 처리를 수행
            e.printStackTrace();
            return false;
        }

    }

    public boolean editComments(Comments comments) {
        try {
            int Check = commentsRepository.editComments(comments);
            return Check > 0;
        } catch (Exception e) {
            // 로그를 기록하거나 적절한 예외 처리를 수행
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteComments(Comments comments) {
        try {
            int Check = commentsRepository.deleteComments(comments);
            return Check > 0;
        } catch (Exception e) {
            // 로그를 기록하거나 적절한 예외 처리를 수행
            e.printStackTrace();
            return false;
        }
    }
}
