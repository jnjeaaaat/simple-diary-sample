package com.example.demo.src.block.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetBlockRes {
    private int userId;
    private int blockUserId;
    private int profileImgUrl;
    private int nickName;
}
