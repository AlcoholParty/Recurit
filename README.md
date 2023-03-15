# Recurit
소주...소중한 친구들 구하기...ㅎㅎ
## 소중한 친구들 구하기에서 스터디쪽 작업 완료
### 데이터베이스 RecuritStudy
	create table RecuritStudy(
		idx INT PRIMARY KEY, #게시글 번호
		title VARCHAR(100) NOT NULL, #제목
		writeDate DATE NOT NULL, #작성일자
		writer VARCHAR(20) NOT NULL, #작성자
		studyType VARCHAR(20) NOT NULL, #분야
		personnel INT NOT NULL, #모집인원
		recruitingPersonnel INT NOT NULL, #모집된 인원
		recruiting INT NOT NULL, #모집 상태
		image VARCHAR(50) NOT NULL, #모집 사진
		studyIntro VARCHAR(500) NOT NULL, #스터디원 소개글
		studyLike INT NOT NULL, #찜 숫자
		studyLikeCheck INT NOT NULL #찜 확인
	);

### 추가된 데이터베이스
### RecruitMentor
	CREATE TABLE RecruitMentor(
		idx INT PRIMARY KEY, #게시글 번호
		title VARCHAR(100) NOT NULL, #제목
		writeDate DATE NOT NULL, #작성일자
		writer VARCHAR(20) NOT NULL, #작성자
		studyType VARCHAR(20) NOT NULL, #분야
		studyIntro VARCHAR(500) NOT NULL, #멘토 소개글
		recruiting INT NOT NULL, #모집 상태
		studyLike INT NOT NULL, #찜 숫자
		studyLikeCheck INT NOT NULL #찜 확인
	);

### RecruitMentee
	CREATE TABLE RecruitMentee(
		idx INT PRIMARY KEY, #게시글 번호
		title VARCHAR(100), #제목
		writeDate DATE, #작성일자
		writer VARCHAR(20), #작성자
		studyType VARCHAR(20), #분야
		image VARCHAR(50), #대표 사진
		studyIntro VARCHAR(500), #본인 소개글
		recruiting INT, #모집 상태
		studyLike INT NOT NULL, #찜 숫자
		studyLikeCheck VARCHAR(2) NOT NULL #찜 확인
	);

## 02/23
### 1.스터디원 구하기 글 작성
### 2.스터디원 구하기 작성된 목록들 보여주기

## 02/24
### 1. 멘토구하기 글 작성
### 2. 멘토구하기 작성된 목록들 보여주기
### 3. 멘티구하기 글 작성
### 4. 멘티구하기 작성된 목록들 보여주기

## 02/25
### 1. 스터디원 구하기 글 수정작업
### 2. 스터디원 구하기 글 삭제작업
### 3. 스터디원 구하기 글에서 좋아요 버튼 누르기 (다시 수정해야함 -> db를 추가해서 어떤 유저가 클릭헀는지도 확인 해야함)

## 02/27
### 1. 스터디원 구하기 찜기능 작업
### 1-1. 스터디원 구하기 찜하기
### 1-2. 스터디원 구하기 찜 취소하기
### 추가된 데이터베이스
### RecruitStudyLike
	CREATE TABLE RecruitStudyLike(
		recruitStudyLikeIdx INT PRIMARY KEY, #좋아요 번호
		recruitStudyIdx INT NOT NULL, #스터디 게시글 번호
		nickname VARCHAR(50) NOT NULL #유저 닉네임
	);


### 2. 스터디원 구하기 댓글 기능 작업
### 2-1. 스터디원 구하기 댓글 추가
### 2-2. 스터디원 구하기 댓글 삭제
### 2-3. 스터디원 구하기 댓글 수정
### 추가된 데이터베이스
### RecruitStudyComment
	CREATE TABLE RecruitStudyComment(
		commentIdx INT PRIMARY KEY, #댓글 번호
		recruitStudyIdx INT NOT NULL, #스터디 게시글 번호
		writeDate VARCHAR(10) NOT NULL, #댓글 작성시간
		writer VARCHAR(50) NOT NULL, #댓글 작성유저 닉네임
		comment VARCHAR(500) NOT NULL, # 댓글 내용
		deleteCheck INT NOT NULL, # 댓글 삭제 확인
	);
### 테스트 돌려보다 발견한 오류
### 1. 게시글을 삭제해도 댓글 테이블은 그대로 남아있어서 게시글을 다시 생성하면 기존 댓글들이 남아있는 현상
### 해결방안1 : 게시글 삭제시 댓글에 게시글 idx로 검색후 조건에 맞는 모든 댓글 삭제
### 해결방안2 : 게시글 삭제시 댓글도 자동으로 삭제되는 포린키 사용

## 03/11
### 1.스터디원 구하기 페이징 처리
### 페이징 처리를 통해서 많은 값들을 처리할 때 눈에 보기 쉽게 정렬
### 이후 리스트를 가져올 때 최근 게시물부터 보여주기 위해서 역순으로 정렬한 뒤 리스트를 사용

## 03/15
### 1. 멘토, 멘티 구하기 글 수정
### 2. 멘토, 멘티 구하기 글 삭제
### 3. 멘토, 멘티 좋아요 기능
### 추가된 데이터베이스 
### RecruitMentorLike
	CREATE TABLE RecruitMentorLike(
		idx BIGINT PRIMARY KEY,
		likeIdx BIGINT NOT NULL,
		memberIdx BIGINT NOT NULL
	);
### RecruitMenteeLike
	CREATE TABLE RecruitMenteeLike(
		idx BIGINT PRIMARY KEY,
		likeIdx BIGINT NOT NULL,
		memberIdx BIGINT NOT NULL
	);
### 4. 멘토, 멘티 찜 기능
### 추가된 데이터베이스
### RecruitMentorComment
	CREATE TABLE RecruitMentorComment(
		idx BIGINT PRIMARY KEY,
		commentIdx BIGINT NOT NULL,
		writeDate VARCHAR NOT NULL,
		writer VARCHAR NOT NULL,
		comment VARCHAR NOT NULL,
		deleteCheck INT NOT NULL
	);

### RecruitMenteeComment
	CREATE TABLE RecruitMentorComment(
		idx BIGINT PRIMARY KEY,
		commentIdx BIGINT NOT NULL,
		writeDate VARCHAR NOT NULL,
		writer VARCHAR NOT NULL,
		comment VARCHAR NOT NULL,
		deleteCheck INT NOT NULL
	);

### 수정된 데이터베이스
### RecruitStudyLike
	CREATE TABLE RecruitStudyLike(
		idx BIGINT PRIMARY KEY, #좋아요 번호
		likeIdx BIGINT NOT NULL, #스터디 게시글 번호
		memberIdx BIGINT NOT NULL #유저 닉네임
	);
	
### RecruitStudyComment
	CREATE TABLE RecruitStudyComment(
		idx BIGINT PRIMARY KEY, #댓글 번호
		commentIdx BIGINT NOT NULL, #스터디 게시글 번호
		writeDate VARCHAR(10) NOT NULL, #댓글 작성시간
		writer VARCHAR(50) NOT NULL, #댓글 작성유저 닉네임
		comment VARCHAR(500) NOT NULL, # 댓글 내용
		deleteCheck INT NOT NULL, # 댓글 삭제 확인
	);
