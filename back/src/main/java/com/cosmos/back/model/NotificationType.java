package com.cosmos.back.model;

public enum NotificationType {
    APPLY(" 스터디에 스터디 신청이 도착했습니다.", "/study/"), ACCEPT(" 스터디 신청이 승인되었습니다.", "/study/"),
    REJECT(" 스터디 신청이 거절 혹은 스터디에서 강퇴되었습니다.", "/study/"), REPLY("에 댓글이 달렸습니다.", "/"),
    CHANGE_AUTHORITY(" 스터디의 권한이 변경되었습니다.", "/study/"), MESSAGE(" 회원에게 메세지가 도착했습니다.", "/mail/with/");

    private String content;
    private String url;

    NotificationType(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public String makeContent(String title) {
        return "'" + title + "'" + content;
    }

    public String makeUrl(Long id) {
        return url + id;
    }
}
