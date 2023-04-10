package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    // users
    SUCCESS_SIGN_IN(true, 1001, "회원가입에 성공하였습니다."),
    SUCCESS_LOG_IN(true,1002,"로그인에 성공하였습니다."),
    FIND_ALL_USER(true,1003,"전체 유저를 조회하였습니다."),
    FIND_NICKNAME_USER(true,1004,"해당 닉네임의 유저를 조회하였습니다."),
    FIND_NUMBER_USER(true,1005,"해당 번호의 유저를 조회하였습니다."),
    MODIFY_USER_INFORM(true,1006,"유저 정보를 수정하였습니다."),
    YOU_ARE_BLOCKED(true, 1007, "상대방에게 차단되었습니다."),
    SUCCESS_MODIFY_STATUS_USER(true, 1008, "유저의 현재상태를 변경하였습니다."),

    // diaries
    SUCCESS_CREATE_NEW_DIARY(true, 1010, "새 일기를 작성하였습니다."),
    FIND_ALL_DIARIES(true, 1011, "모든 일기를 조회하였습니다."),
    FIND_USER_DIARIES(true, 1012, "유저의 일기를 조회하였습니다."),
    FIND_ONE_DIARY(true, 1013, "일기를 조회하였습니다."),
    MODIFY_DIARY(true, 1014, "일기를 수정하였습니다."),
    FIND_USER_EMOTION_DIARIES(true, 1015, "해당 감정의 일기들을 조회하였습니다."),
    SUCCESS_DELETE_DIARY(true, 1016, "일기를 삭제하였습니다."),

    // friends
    SUCCESS_REQUEST_FRIEND(true, 1030,"친구요청 하였습니다."),
    SUCCESS_DELETE_FRIEND(true, 1031,"친구를 삭제하였습니다."),
    SUCCESS_ACCEPT_FRIEND(true, 1032, "친구요청을 수락 하였습니다."),
    SUCCESS_FIND_MY_FRIENDS(true, 1033,"친구목록을 받아왔습니다."),

    // blocks
    BLOCK_THE_USER(true, 1100, "유저를 차단 하였습니다."),
    UNBLOCK_THE_USER(true, 1101, "차단을 해제하였습니다."),
    SUCCESS_GET_ALL_BLOCK_USER(true, 1102, "차단한 유저들을 조회하였습니다."),

    // comments
    SUCCESS_WRITE_COMMENT(true, 1150, "방명록을 남겼습니다."),
    SUCCESS_MODIFY_COMMENT(true, 1151, "방명록을 수정하였습니다."),
    SUCCESS_PRESS_HEART(true, 1152, "방명록에 하트를 눌렀습니다."),
    SUCCESS_ANTI_PRESS_HEART(true, 1153, "방명록에 하트를 해제하였습니다."),
    SUCCESS_GET_COMMENT(true, 1154, "방명록을 조회하였습니다."),
    SUCCESS_GET_COMMENTS(true, 1155, "유저의 방명록을 조회하였습니다."),
    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    POST_NOT_STRING_TYPE(false, 2004, "문자열 타입을 입력해주세요."),
    POST_NOT_DATE_TYPE(false,2005,"날짜 형식을 확인해주세요"),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USER_EMPTY_PASSWORD(false, 2019, "비밀번호를 입력해주세요."),
    POST_USER_EMPTY_CHECK_PASSWORD(false, 2020, "비밀번호 확인 을 입력해주세요."),
    POST_USER_INCORRECT_PASSWORD(false, 2021, "비밀번호가 맞지 않습니다."),
    POST_USERS_EMPTY_NICKNAME(false, 2022, "닉네임을 입력해주세요."),
    POST_USERS_EMPTY_BIRTH(false, 2023, "생일을 입력해주세요."),

    // [POST] /diary
    POST_DIARY_EMPTY_USER_ID(false, 2050,"유저 번호를 입력해주세요"),
    POST_DIARY_IMG_NUM_OVER(false, 2051,"사진을 9장까지만 업로드해주세요."),
    POST_DIARY_EMPTY_TITLE(false, 2052,"일기 제목을 입력해주세요."),
    POST_DIARY_EMPTY_CONTENTS(false, 2053,"일기 내용을 입력해주세요."),
    POST_DIARY_EMPTY_EMOTION(false, 2054,"일기 기분을 설정해주세요."),
    POST_DIARY_EMPTY_CONSUMPTION(false, 2055,"지출액을 입력해주세요."),
    POST_DIARY_EMPTY_IMPORTATION(false, 2056,"수입액을 입력해주세요."),
    POST_DIARY_EMPTY_IS_OPEN(false, 2057,"일기 오픈 여부를 설정해주세요."),
    POST_DIARY_EMPTY_DIARY_DATE(false, 2058,"일기 작성 날짜를 설정해주세요."),

    //  [POST] /friend
    CANNOT_REQUEST_YOURSELF(false, 2100,"본인은 친구요청 할 수 없습니다."),
    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    NON_EXIST_OR_DELETED_USER(false,3015,"삭제된 유저입니다."),
    INACTIVE_USER(false, 3016, "탈퇴한 유저입니다."),

    // /diary
    DELETED_DIARY(false, 3020, "삭제된 일기입니다."),
    INACTIVE_USER_DIARY(false, 3021, "탈퇴한 유저의 일기입니다."),
    SECRET_DIARY(false, 3022, "비밀 일기 입니다."),
    ALREADY_DELETED_DIARY(false, 3023, "이미 삭제된 일기입니다."),

    // /friend
    ALREADY_REQUEST_FRIEND(false, 3050, "이미 친구요청 하였습니다."),
    ALREADY_FRIENDS(false, 3051, "이미 친구 사이 입니다."),
    ALREADY_NOT_FRIENDS(false, 3052, "친구 사이가 아닙니다."),
    NOT_REQUEST_FRIEND(false, 3053, "친구 요청 한 적이 없습니다."),

    // /block
    ALREADY_BLOCKED_USER(false, 3100, "이미 차단한 유저입니다."),
    NOT_BLOCKED_USER(false, 3101, "차단한 유저가 아닙니다."),

    // /comments
    NO_TODAY_COMMENTS(false, 3150,"오늘의 방명록이 없습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USER_INFORM(false,4014,"유저정보 수정 실패"),
    MODIFY_FAIL_DIARY(false,4015,"일기 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
