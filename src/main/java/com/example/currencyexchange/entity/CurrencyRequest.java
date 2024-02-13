package com.example.currencyexchange.entity;

public class CurrencyRequest {
	// Поля формы - name, code, sign.
	private String name;
	private String code;
	private String sign;

	public CurrencyRequest() {
	}

	public CurrencyRequest(String name, String code, String sign) {
		this.name = name;
		this.code = code;
		this.sign = sign;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "CurrencyDto [name=" + name + ", code=" + code + ", sign=" + sign + "]";
	}

}
