package com.example.demo.src.userComment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserCommentRes {
    private String comment; // 댓글 내용
    private LocalDateTime createdAt; // 댓글 단 시간
}
