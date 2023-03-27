package com.example.demo.src.friend;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.friend.model.DeleteFriendReq;
import com.example.demo.src.friend.model.GetFriendRes;
import com.example.demo.src.friend.model.PostFriendReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/friends")
public class FriendController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final FriendProvider friendProvider;
    @Autowired
    private final FriendService friendService;
    @Autowired
    private final JwtService jwtService;

    public FriendController(FriendProvider friendProvider, FriendService friendService, JwtService jwtService) {
        this.friendProvider = friendProvider;
        this.friendService = friendService;
        this.jwtService = jwtService;
    }

    // 친구 요청
    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> requestFriend(@RequestBody PostFriendReq postFriendReq) {
        // 본인한테 요청했을 떄
        if (postFriendReq.getGiveUserId() == postFriendReq.getTakeUserId()) {
            return new BaseResponse<>(CANNOT_REQUEST_YOURSELF);
        }
        try {
            int userIdByJWT = jwtService.getUserId();
            if (postFriendReq.getGiveUserId() != userIdByJWT) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            friendService.requestFriend(postFriendReq);

            return new BaseResponse<>(SUCCESS_REQUEST_FRIEND);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 친구 삭제
    @ResponseBody
    @DeleteMapping("")
    public BaseResponse<String> deleteFriend(@RequestBody DeleteFriendReq deleteFriendReq) {
        try {
            int userIdByJWT = jwtService.getUserId();
            if (deleteFriendReq.getUserId() != userIdByJWT) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            friendService.deleteFriend(deleteFriendReq);

            return new BaseResponse<>(SUCCESS_DELETE_FRIEND);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 친구 요청 수락
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> acceptFriend(@RequestBody PostFriendReq postFriendReq) {
        try {
            int userIdByJWT = jwtService.getUserId();
            if (postFriendReq.getTakeUserId() != userIdByJWT) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            friendService.acceptFriend(postFriendReq);

            return new BaseResponse<>(SUCCESS_ACCEPT_FRIEND);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<List<GetFriendRes>> getMyFriends(@PathVariable("userId") int userId) {
        try {
            int userIdByJWT = jwtService.getUserId();
            if (userId != userIdByJWT) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetFriendRes> getFriendRes = friendProvider.getMyFriends(userId);
            return new BaseResponse<>(SUCCESS_FIND_MY_FRIENDS, getFriendRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
