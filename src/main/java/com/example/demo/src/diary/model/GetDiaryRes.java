package com.example.demo.src.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDiaryRes {
    private int postId; // 글 id
    private int userId; // 글 작성 유저 //TODO: 글 작성한 유저의 프로필 사진, 닉네임까지 나오게 추가
    private String title; // 글 제목
    private String contents; // 글 내용 //TODO: 나중에 사진까지 추가해서 나오게 수정
    private String feel; // 그날의 기분 default == happy [happy, angry, depressed, heart]
    private int consumption; // 그날의 지출 default == 0
    private int importation; // 그날의 수입 default == 0
    private Boolean isOpen; // 누구나 볼수있는지 default == true
    private Boolean isDeleted; // 상태, 지웠는지
    private Date diaryDate; // 등록할 날짜 선택 default == current_date
    private LocalDateTime createdAt; // 글 작성 시간
    private LocalDateTime updatedAt; // 글 수정 시간
}
