package com.example.demo.src.diaryImg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDiaryImgRes {
    private int diaryImgId;
    private int diaryId;
    private List<String> diaryImgUrl;
    private LocalDateTime createdAt;
}
