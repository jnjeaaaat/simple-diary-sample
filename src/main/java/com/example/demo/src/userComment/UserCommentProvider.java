package com.example.demo.src.userComment;

import com.example.demo.config.BaseException;
import com.example.demo.src.block.BlockProvider;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.userComment.model.GetUserCommentRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UserCommentProvider {
    private final UserCommentDao userCommentDao;
    private final UserProvider userProvider;
    private final BlockProvider blockProvider;
    private final JwtService jwtService;

    @Autowired
    public UserCommentProvider(UserCommentDao userCommentDao, UserProvider userProvider, BlockProvider blockProvider, JwtService jwtService) {
        this.userCommentDao = userCommentDao;
        this.userProvider = userProvider;
        this.blockProvider = blockProvider;
        this.jwtService = jwtService;
    }

    /**
     * 방명록 조회
     * @param userCommentId
     * @return GetUserCommentRes
     * @throws BaseException
     */
    public GetUserCommentRes getCommentById(int userCommentId) throws BaseException {
        // 차단 당한 상태
        if (blockProvider.isBlocked(getTakeUserIdComment(userCommentId), jwtService.getUserId()) == 1) {
            throw new BaseException(YOU_ARE_BLOCKED);
        }
        // takeUser 가 탈퇴한 유저
        if (!userProvider.isExistUserByUserId(getTakeUserIdComment(userCommentId))) {
            throw new BaseException(INACTIVE_USER);
        }
        // 삭제된 방명록
        if (checkIsDeletedComment(userCommentId)) {
            throw new BaseException(DELETED_COMMENT);
        }
        try {
            GetUserCommentRes getUserCommentRes = userCommentDao.getCommentById(userCommentId);
            return getUserCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 유저의 방명록 조회
     * @param takeUserId
     * @return List
     * @throws BaseException
     */
    public List<GetUserCommentRes> getComments(int takeUserId) throws BaseException {
        // 차단 당한 상태
        if (blockProvider.isBlocked(takeUserId, jwtService.getUserId()) == 1) {
            throw new BaseException(YOU_ARE_BLOCKED);
        }
        // takeUser 가 탈퇴한 유저
        if (!userProvider.isExistUserByUserId(takeUserId)) {
            throw new BaseException(INACTIVE_USER);
        }
        try {
            List<GetUserCommentRes> getUserCommentRes = userCommentDao.getComments(takeUserId);
            return getUserCommentRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 방명록 작성 유저 id
    public int getUserIdFromComment(int userCommentId) throws BaseException {
        try {
            int userIdFromComment = userCommentDao.getUserIdFromComment(userCommentId);
            return userIdFromComment;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 방명록 받은 유저 id
    public int getTakeUserIdComment(int userCommentId) throws BaseException {
        try {
            int takeUserIdComment = userCommentDao.getTakeUserIdComment(userCommentId);
            return takeUserIdComment;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 방명록 삭제상태 인지 확인
    public Boolean checkIsDeletedComment(int userCommentId) throws BaseException {
        try {
            Boolean isDeleted = userCommentDao.checkIsDeletedComment(userCommentId);
            return isDeleted;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
