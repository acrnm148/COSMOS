package com.cosmos.back.auth;

import com.cosmos.back.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * /login api 매핑 시 여기 username, password를 가져와서 인증을 수행함.
 * 커스텀
 */
@Data
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities  = new ArrayList<>();
//        user.getRole().forEach(r -> {
//            authorities.add(() -> r);
//        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    } //중복이 없어야 함

    @Override
    public boolean isAccountNonExpired() {
        return true; //계정 만료 안됨
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } //계정 안잠김

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } //비밀번호 만료 안됨

    @Override
    public boolean isEnabled() {
        return true;
    } //계정 활성화됨
}
