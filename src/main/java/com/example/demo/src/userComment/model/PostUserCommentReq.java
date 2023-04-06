package com.example.demo.src.userComment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserCommentReq {
    private int userId; // 댓글 쓴 유저
    private int takeUserId; // 댓글 달린 유저
    private String comment; // 댓글 내용
}
