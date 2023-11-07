package com.koreaIT.BAM.dto;

public class Article {
	public int id;
	public String regDate;
	public int memberId;
	public String title;
	public String body;
	
	public Article(int id, String regDate, int memberId, String title, String body) {
		this.id = id;
		this.regDate = regDate;
		this.memberId = memberId;
		this.title = title;
		this.body = body;
	}
}
