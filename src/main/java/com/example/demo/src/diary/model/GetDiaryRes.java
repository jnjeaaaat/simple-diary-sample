package com.example.demo.src.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDiaryRes {
    private int diaryId; // 글 id
    private int numDiary; // 유저의 몇번째 일기인지
    private int userId; // 글 작성 유저
    private String profileImgUrl; // 글 작성 유저 프사
    private String nickName; // 글 작성 유저 닉네임
    private List<String> diaryImgs;
    private String title; // 글 제목
    private String contents; // 글 내용
    private String emotion; // 그날의 기분 default == happy [happy, angry, depressed, heart]
    private int consumption; // 그날의 지출 default == 0
    private int importation; // 그날의 수입 default == 0
    private Boolean isOpen; // 누구나 볼수있는지 default == true
    private Boolean isDeleted; // 상태, 지웠는지
    private String diaryDate; // 등록할 날짜 선택 default == current_date
    private int view; // 일기 조회수
    private LocalDateTime createdAt; // 글 작성 시간
    private LocalDateTime updatedAt; // 글 수정 시간
}
