package com.example.demo.src.friend;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.friend.model.GetFriendRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class FriendProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FriendDao friendDao;
    private final JwtService jwtService;

    @Autowired
    public FriendProvider(FriendDao friendDao, JwtService jwtService) {
        this.friendDao = friendDao;
        this.jwtService = jwtService;
    }

    public List<GetFriendRes> getMyFriends(int userId) throws BaseException {
        try {
            List<GetFriendRes> getFriendRes = friendDao.getMyFriends(userId);
            return getFriendRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
