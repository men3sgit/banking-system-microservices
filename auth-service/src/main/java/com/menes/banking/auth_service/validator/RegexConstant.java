package com.menes.banking.auth_service.validator;

public final class RegexConstant {

    private RegexConstant() {
    }

    public static final String ALPHABET = "^[a-zA-Z]+$";

    public static final String COMMON_TEXT_WITHOUT_SPACE = "^[A-Za-z0-9_-]+$";

    public static final String UUID = "^[-a-zA-Z0-9]{32,36}$";

    public static final String ACCOUNT_NUMBER = "^[a-zA-Z0-9]{5,15}$";

    public static final String GL_ACCOUNT_NUMBER = "^[0-9_-]{5,15}$";

    public static final String COMMON_TEXT = "^[A-Za-z0-9\\s_-]*$";

    public static final String VERSION = "^(\\d)(\\.\\d){2}$";
}