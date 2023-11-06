package com.koreaIT.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.BAM.dto.Article;
import com.koreaIT.BAM.dto.Member;
import com.koreaIT.BAM.util.Util;

public class App {
	
	private List<Article> articles;
	private List<Member> members;
	
	public App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}
	
	public void run() {
		System.out.println("== 프로그램 시작 ==");

		makeTestData();
		
		Scanner sc = new Scanner(System.in);
		
		int lastArticleId = 3;
		int lastMemberId = 0;

		while (true) {

			System.out.printf("명령어) ");

			String cmd = sc.nextLine().trim();

			if (cmd.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (cmd.equals("exit")) {
				break;
			}
			
			if (cmd.equals("member join")) {
				System.out.println("== member join ==");
				
				lastMemberId++;
				String regDate = Util.getDate();
				
				String loginId = null;
				String loginPw = null;
				String name = null;
				String loginPwChk = null;
				
				while(true) {
					System.out.printf("아이디 : ");
					loginId = sc.nextLine().trim();
					
					if (loginId.length() == 0) {
						System.out.println("아이디를 입력해주세요");
						continue;
					}
					
					if (isLoginIdDup(loginId)) {
						System.out.printf("%s은(는) 이미 사용중인 아이디입니다\n", loginId);
						continue;
					}
					
					System.out.printf("%s은(는) 사용가능한 아이디입니다\n", loginId);
					break;
				}
				while (true) {
					System.out.printf("비밀번호 : ");
					loginPw = sc.nextLine().trim();
					
					if (loginPw.length() == 0) {
						System.out.println("비밀번호를 입력해주세요");
						continue;
					}
					
					while (true) {
						System.out.printf("비밀번호 확인 : ");
						loginPwChk = sc.nextLine().trim();
						
						if (loginPwChk.length() == 0) {
							System.out.println("비밀번호 확인을 입력해주세요");
							continue;
						}
						break;
					}
					
					if (loginPw.equals(loginPwChk) == false) {
						System.out.println("비밀번호를 확인해주세요");
						continue;
					}
					break;
				}
				
				while (true) {
					System.out.printf("이름 : ");
					name = sc.nextLine().trim();
					
					if (name.length() == 0) {
						System.out.println("이름을 입력해주세요");
						continue;
					}
					break;
				}

				Member member = new Member(lastMemberId, regDate, loginId, loginPw, name);

				members.add(member);

				System.out.println(name + "회원님이 가입되었습니다");

			} else if (cmd.equals("article write")) {
				System.out.println("== article write ==");
				lastArticleId++;
				String regDate = Util.getDate();
				
				String title = null;
				String body = null;
				
				while(true) {
					System.out.printf("제목 : ");
					title = sc.nextLine().trim();
					
					if (title.length() == 0) {
						System.out.println("제목을 입력해주세요");
						continue;
					}
					break;
				}
				while(true) {
					System.out.printf("내용 : ");
					body = sc.nextLine().trim();
					
					if (body.length() == 0) {
						System.out.println("내용을 입력해주세요");
						continue;
					}
					break;
				}

				Article article = new Article(lastArticleId, regDate, title, body);

				articles.add(article);

				System.out.println(lastArticleId + "번글이 생성되었습니다");

			} else if (cmd.startsWith("article list")) {

				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다");
					continue;
				}
				
				String searchKeyword = cmd.substring("article list".length()).trim();
				
				List<Article> forPrintArticle = articles;
				
				if (searchKeyword.length() > 0) {
				
					System.out.println("검색어 : " + searchKeyword);
					
					forPrintArticle = new ArrayList<>();
					
					for (Article article : articles) {
						if (article.title.contains(searchKeyword)) {
							forPrintArticle.add(article);
						}
					}
					
					if (forPrintArticle.size() == 0) {
						System.out.println("검색결과가 없습니다");
						continue;
					}
				}
				
				System.out.println("== article list ==");
				System.out.println("번호	|		날짜		|	제목");
				for (int i = forPrintArticle.size() - 1; i >= 0; i--) {
					Article article = forPrintArticle.get(i);
					
					System.out.printf("%d	|	%s	|	%s\n", article.id, article.regDate, article.title);
				}

			} else if (cmd.startsWith("article detail ")) {

				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);

				Article foundArticle = null;
				
				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if (article.id == id) {
						foundArticle = article;
						break;
					}
				}
				
				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				
				System.out.printf("== %d번 게시물 상세보기 ==\n", foundArticle.id);
				System.out.printf("번호 : %d\n", foundArticle.id);
				System.out.printf("작성일 : %s\n", foundArticle.regDate);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.body);
				
			} else if (cmd.startsWith("article modify ")) {

				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);

				Article foundArticle = null;
				
				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if (article.id == id) {
						foundArticle = article;
						break;
					}
				}
				
				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				
				System.out.printf("수정할 제목 : ");
				String title = sc.nextLine();
				System.out.printf("수정할 내용 : ");
				String body = sc.nextLine();
				
				foundArticle.title = title;
				foundArticle.body = body;
				
				System.out.printf("%d번 게시물이 수정되었습니다\n", id);
				
			} else if (cmd.startsWith("article delete ")) {
				
				System.out.println("== article delete ==");
				
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);

				int foundIndex = -1;
				
				for (int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					if (article.id == id) {
						foundIndex = i;
						break;
					}
				}
				
				if (foundIndex == -1) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				
				articles.remove(foundIndex);
				
				System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
				
			} else {
				System.out.println("존재하지 않는 명령어 입니다");
			}

		}

		sc.close();

		System.out.println("== 프로그램 종료 ==");
	}
	
	private boolean isLoginIdDup(String loginId) {
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return true;
			}
		}
		return false;
	}

	private void makeTestData() {
		articles.add(new Article(1, Util.getDate(), "제목1", "내용1"));
		articles.add(new Article(2, Util.getDate(), "제목2", "내용2"));
		articles.add(new Article(3, Util.getDate(), "제목3", "내용3"));
		System.out.println("테스트용 게시물 데이터 3개를 생성했습니다");
	}
}
