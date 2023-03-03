package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSpecificUserRes {
    private int userId;
    private String profileImgUrl;
    private String email;
    private String nickName;
    private String birth;
    private String status;
    private String createdAt;
    private Boolean birthOpen;
    private int view;
}
