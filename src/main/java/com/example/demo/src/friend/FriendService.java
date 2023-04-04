package com.example.demo.src.friend;

import com.example.demo.config.BaseException;
import com.example.demo.src.block.BlockProvider;
import com.example.demo.src.friend.model.DeleteFriendReq;
import com.example.demo.src.friend.model.PostFriendReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class FriendService {

    private final FriendDao friendDao;
    private final FriendProvider friendProvider;
    private final BlockProvider blockProvider;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    @Autowired
    public FriendService(FriendDao friendDao, FriendProvider friendProvider, BlockProvider blockProvider, JwtService jwtService) {
        this.friendDao = friendDao;
        this.friendProvider = friendProvider;
        this.blockProvider = blockProvider;
        this.jwtService = jwtService;
    }

    // 친구 요청
    public void requestFriend(PostFriendReq postFriendReq) throws BaseException {
        // 이미 친구 사이 인지
        if (friendProvider.isFriends(postFriendReq.getGiveUserId(), postFriendReq.getTakeUserId()) == 1) {
            throw new BaseException(ALREADY_FRIENDS);
        }
        // 이미 친구 요청 한 상태인지
        if (friendProvider.isExistRequestFriend(postFriendReq.getGiveUserId(), postFriendReq.getTakeUserId()) == 1) {
            throw new BaseException(ALREADY_REQUEST_FRIEND);
        }
        // 차단한 유저일 때
        if (blockProvider.isBlocked(postFriendReq.getGiveUserId(), postFriendReq.getTakeUserId()) == 1) {
            throw new BaseException(BLOCK_THE_USER);
        }
        // 상대방한테 차단당한 상태일 때
        if (blockProvider.isBlocked(postFriendReq.getTakeUserId(), postFriendReq.getGiveUserId()) == 1) {
            throw new BaseException(YOU_ARE_BLOCKED);
        }
        try {
            int result = friendDao.requestFriend(postFriendReq.getGiveUserId(), postFriendReq.getTakeUserId());
            if (result == 0) {
                throw new BaseException(DATABASE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 친구 삭제
    public void deleteFriend(DeleteFriendReq deleteFriendReq) throws BaseException {
        // 친구사이도, 친구요청한 사이도 아닐 때
        if ((friendProvider.isExistRequestFriend(deleteFriendReq.getUserId(), deleteFriendReq.getAntiUserId()) == 0) &&
                friendProvider.isFriends(deleteFriendReq.getUserId(), deleteFriendReq.getAntiUserId()) == 0) {
            throw new BaseException(ALREADY_NOT_FRIENDS);
        }
        try {
            int result = friendDao.deleteFriend(deleteFriendReq.getUserId(), deleteFriendReq.getAntiUserId());
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
        if (friendProvider.isFriends(postFriendReq.getGiveUserId(), postFriendReq.getTakeUserId()) == 1) {
            throw new BaseException(ALREADY_FRIENDS);
        }
        // 친구 요청한 상태가 아니면
        if (friendProvider.isExistRequestFriend(postFriendReq.getGiveUserId(), postFriendReq.getTakeUserId()) != 1) {
            throw new BaseException(NOT_REQUEST_FRIEND);
        }
        try {
            int result = friendDao.acceptFriend(postFriendReq.getGiveUserId(), postFriendReq.getTakeUserId());
            if (result == 0) {
                throw new BaseException(DATABASE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
