package com.example.demo.src.user.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(email, password, nickname, profileImage)를 받는 생성자를 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 해당 클래스의 파라미터가 없는 생성자를 생성, 접근제한자를 PROTECTED로 설정.
public class PostUserReq {
    private String email; // 회원가입 아이디, 중복불가능
    private String password; // 회원가입 비밀번호
    private String checkPassword; // 회원가입 비밀번호 확인
    private String nickName; // 회원가입 닉네임, 중복가능
    private String birth; // 회원 생일
}
