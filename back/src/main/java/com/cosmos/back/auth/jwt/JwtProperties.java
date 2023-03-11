package com.cosmos.back.auth.jwt;

public interface JwtProperties {

    String SECRET = "COSMOSe104COSMOScoursemoreusCOSMOSe104COSMOScoursemoreusCOSMOSe104COSMOScoursemoreus"; //우리 서버만 알고 있는 비밀값 (512bits,64자 이상)
    int AccessToken_TIME =  20000000;//20000000; // (1/1000초)
    int RefreshToken_TIME = 30000000;//* 12 * 24;
    String HEADER_STRING = "accessToken";
}
