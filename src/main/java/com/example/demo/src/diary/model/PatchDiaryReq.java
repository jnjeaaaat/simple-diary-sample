package com.example.demo.src.diary.model;

import com.example.demo.src.diaryImg.model.PatchDiaryImgReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchDiaryReq {
    private PatchDiaryImgReq diaryImg;
    private String title; // 글 제목
    private String contents; // 글 내용
    private String emotion; // 그날의 기분 default == happy [happy, angry, depressed, heart]
    private Integer consumption; // 그날의 지출 default == 0
    private Integer importation; // 그날의 수입 default == 0
    private Boolean isOpen; // 누구나 볼수있는지 default == true
    private Boolean isDeleted; // 상태, 지웠는지
    private String diaryDate; // 등록할 날짜 선택 default == current_date
}
