package com.example.demo.src.userComment;

import com.example.demo.config.BaseException;
import com.example.demo.src.userComment.model.GetUserCommentRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserCommentProvider {
    private final UserCommentDao userCommentDao;
    private final JwtService jwtService;

    @Autowired
    public UserCommentProvider(UserCommentDao userCommentDao, JwtService jwtService) {
        this.userCommentDao = userCommentDao;
        this.jwtService = jwtService;
    }

    /**
     * 방명록 조회
     * @param userCommentId
     * @return GetUserCommentRes
     * @throws BaseException
     */
    public GetUserCommentRes getCommentById(int userCommentId) throws BaseException {
        try {
            GetUserCommentRes getUserCommentRes = userCommentDao.getCommentById(userCommentId);
            return getUserCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetUserCommentRes> getComments(int takeUserId) throws BaseException {
        try {
            List<GetUserCommentRes> getUserCommentRes = userCommentDao.getComments(takeUserId);
            return getUserCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**
     * 방명록 작성 유저 id
     * @param userCommentId
     * @return int userId
     * @throws BaseException
     */
    public int getUserIdFromComment(int userCommentId) throws BaseException {
        try {
            int userIdFromComment = userCommentDao.getUserIdFromComment(userCommentId);
            return userIdFromComment;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 방명록 받은 유저 id
     * @param userCommentId
     * @return int takeUserId
     * @throws BaseException
     */
    public int getTakeUserIdComment(int userCommentId) throws BaseException {
        try {
            int takeUserIdComment = userCommentDao.getTakeUserIdComment(userCommentId);
            return takeUserIdComment;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
