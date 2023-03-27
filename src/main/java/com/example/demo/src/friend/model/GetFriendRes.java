package com.example.demo.src.friend.model;

import com.example.demo.src.user.model.GetSpecificUserRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetFriendRes {
    private int friendId;
    private int userId;
    private String profileImgUrl;
    private String nickName;
}
