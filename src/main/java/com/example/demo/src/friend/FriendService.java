package com.example.demo.src.friend;

import com.example.demo.config.BaseException;
import com.example.demo.src.friend.model.DeleteFriendReq;
import com.example.demo.src.friend.model.PostFriendReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class FriendService {
    @Autowired
    private final FriendDao friendDao;
    private final FriendProvider friendProvider;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    public FriendService(FriendDao friendDao, FriendProvider friendProvider, JwtService jwtService) {
        this.friendDao = friendDao;
        this.jwtService = jwtService;
        this.friendProvider = friendProvider;
    }

    // 친구 요청
    public void requestFriend(PostFriendReq postFriendReq) throws BaseException {
        // 이미 친구 사이 인지
        if (friendDao.isFriends(postFriendReq) == 1) {
            throw new BaseException(ALREADY_FRIENDS);
        }
        // 이미 친구 요청 한 상태인지
        if (friendDao.isExistRequestFriend(postFriendReq) == 1) {
            throw new BaseException(ALREADY_REQUEST_FRIEND);
        }
        try {
            int result = friendDao.requestFriend(postFriendReq);
            if (result == 0) {
                throw new BaseException(DATABASE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 친구 삭제
    public void deleteFriend(DeleteFriendReq deleteFriendReq) throws BaseException {
        try {
            int result = friendDao.deleteFriend(deleteFriendReq);
            if (result == 0) {
                throw new BaseException(DATABASE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 친구 요청 수락
    public void acceptFriend(PostFriendReq postFriendReq) throws BaseException{
        // 이미 친구 사이 인지
        if (friendDao.isFriends(postFriendReq) == 1) {
            throw new BaseException(ALREADY_FRIENDS);
        }
        try {
            int result = friendDao.acceptFriend(postFriendReq);
            if (result == 0) {
                throw new BaseException(DATABASE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
