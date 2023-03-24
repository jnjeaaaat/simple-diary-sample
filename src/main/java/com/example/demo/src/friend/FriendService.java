package com.example.demo.src.friend;

import com.example.demo.config.BaseException;
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

    public void requestFriend(PostFriendReq postFriendReq) throws BaseException {
        try {
            int result = friendDao.requestFriend(postFriendReq);
            System.out.println(result);
            if (result == 0) {
                throw new BaseException(DATABASE_ERROR);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
