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
public class PatchUserCommentReq {
    private int userCommentId; // 댓글 식별 번호
    private int userId; // 댓글 쓴 유저
    private int takeUserId; // 댓글 달린 유저
    private String comment; // 댓글 내용
    private Boolean heart; // 계정 주인의 좋아요
    private Boolean isDeleted; // 삭제 유무
    private LocalDateTime createdAt; // 댓글 단 시간
    private LocalDateTime updatedAt; // 댓글 수정 시간
}
