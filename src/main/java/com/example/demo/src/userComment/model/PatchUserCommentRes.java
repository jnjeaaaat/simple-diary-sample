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
public class PatchUserCommentRes {
    private String comment; // 댓글 내용
    private String updatedAt; // 댓글 수정 시간
}
