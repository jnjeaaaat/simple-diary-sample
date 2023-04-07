package com.example.demo.src.userComment;

import com.example.demo.config.BaseException;
import com.example.demo.src.userComment.model.PatchUserCommentReq;
import com.example.demo.src.userComment.model.PatchUserCommentRes;
import com.example.demo.src.userComment.model.PostUserCommentReq;
import com.example.demo.src.userComment.model.PostUserCommentRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserCommentService {

    private final UserCommentProvider userCommentProvider;
    private final UserCommentDao userCommentDao;
    private final JwtService jwtService;

    @Autowired
    public UserCommentService (UserCommentProvider userCommentProvider, UserCommentDao userCommentDao, JwtService jwtService) {
        this.userCommentProvider = userCommentProvider;
        this.userCommentDao = userCommentDao;
        this.jwtService = jwtService;
    }

    /**
     * 방명록 작성
     * @param postUserCommentReq
     * @return comment, createdAt
     * @throws BaseException
     */
    public PostUserCommentRes writeComment(PostUserCommentReq postUserCommentReq) throws BaseException {
        try {
            PostUserCommentRes postUserCommentRes = userCommentDao.writeComment(postUserCommentReq);
            return postUserCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 방명록 수정
     * @param userCommentId
     * @param patchUserCommentReq
     * @return comment, updatedAt
     * @throws BaseException
     */
    public PatchUserCommentRes modifyComment(int userCommentId, PatchUserCommentReq patchUserCommentReq) throws BaseException {
        try {
            PatchUserCommentRes patchUserCommentRes = userCommentDao.modifyComment(userCommentId, patchUserCommentReq);
            return patchUserCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 방명록 하트누르기
     * @param userCommentId
     * @return Boolean
     * @throws BaseException
     */
    public Boolean heartComment(int userCommentId) throws BaseException {
        try {
            Boolean isHearted = userCommentDao.heartComment(userCommentId);
            return isHearted;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
