package com.example.currencyexchange.util;

import java.util.regex.Pattern;

public class Validation {
    private static final int CODE_LENGTH = 3;

    public static boolean currencyCodeValidation(String code) {
        // Регулярное выражение для проверки, что строка состоит из CODE_LENGTH английских букв
        String regex = "^[a-zA-Z]{" + CODE_LENGTH + "}$";
        
        return code != null && Pattern.matches(regex, code);
    }
    
    public static boolean exchangeRateCodesValidation(String codes) {
    	String regex = "^[a-zA-Z]{" + CODE_LENGTH * 2 + "}$";
    	
    	return codes != null && Pattern.matches(regex, codes);
    }
}
