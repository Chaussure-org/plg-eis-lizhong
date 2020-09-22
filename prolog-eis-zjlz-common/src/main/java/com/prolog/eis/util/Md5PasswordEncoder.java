package com.prolog.eis.util;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wujiahui
 * @since 2020/2/28 11:24
 */
public class Md5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return PrologMd5Util.md5(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        return encodedPassword.equals(PrologMd5Util.md5(charSequence.toString()));
    }
}
