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
		idx BIGINT PRIMARY KEY, #좋아요 번호
		likeIdx BIGINT NOT NULL, #멘토 게시글 번호
		memberIdx BIGINT NOT NULL #유저 번호
	);
### RecruitMenteeLike
	CREATE TABLE RecruitMenteeLike(
		idx BIGINT PRIMARY KEY, #좋아요 번호
		likeIdx BIGINT NOT NULL, #멘티 게시글 번호
		memberIdx BIGINT NOT NULL #유저 번호
	);
### 4. 멘토, 멘티 찜 기능
### 추가된 데이터베이스
### RecruitMentorComment
	CREATE TABLE RecruitMentorComment(
		idx BIGINT PRIMARY KEY, #댓글 번호
		commentIdx BIGINT NOT NULL, #멘토 게시글 번호
		writeDate VARCHAR NOT NULL, #댓글 작성시간
		writer VARCHAR NOT NULL, #댓글 작성유저 닉네임
		comment VARCHAR NOT NULL, #댓글 내용
		deleteCheck INT NOT NULL #댓글 삭제 확인
	);

### RecruitMenteeComment
	CREATE TABLE RecruitMentorComment(
		idx BIGINT PRIMARY KEY, #댓글 번호
		commentIdx BIGINT NOT NULL, #멘티 게시글 번호
		writeDate VARCHAR NOT NULL, #댓글 작성시간
		writer VARCHAR NOT NULL, #댓글 작성유저 닉네임
		comment VARCHAR NOT NULL, #댓글 내용
		deleteCheck INT NOT NULL #댓글 삭제 확인
	);

### 수정된 데이터베이스
### RecruitStudyLike
	CREATE TABLE RecruitStudyLike(
		idx BIGINT PRIMARY KEY, #좋아요 번호
		likeIdx BIGINT NOT NULL, #스터디 게시글 번호
		memberIdx BIGINT NOT NULL #유저 번호
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

## 03/21
### 스터디원 구하기 페이징 처리방식 변경
### 기존에 사용하던 Page 클래스를 이용하는 방식에서 직접 페이징 처리를 구현 하는 방식으로 변경했다.
### 이유는 기존에 사용하던 Page 클래스를 이용하는 방식은 다음페이지로 이동하는 버튼 구현을 할때
### 내가 원하는 방식대로 사용할 수 없어서 직접 구현하는 방식을 선택했다.

## 03/22
### 멘토구하기, 멘티구하기 페이징 처리하기

## 03/23
### 스터디원 구하기, 멘토구하기, 멘티구하기
### 스터디원 구하기, 댓글, 좋아요 알림뜨게하기
### 멘토 구하기, 댓글, 좋아요 알림뜨게하기
### 멘티 구하기, 댓글, 좋아요 

## 03/28
### 메인페이지 에서 작업
### 1. 분야별로 각자 리스트 다르게 보여주기(분야별 선택에 따라서 스터디원 구하기 리스트를 보여줌)
### 2. 가장 인기있는 스터디 리스트 탑 10위 보여주기(정렬방식 : 1차 좋아요, 2차 최신순 글)
### 3. 주목받는 멘토 리스트 탑 10위 보여주기(정렬방식 : 1차 좋아요, 2차 최신순 글)

## 04/04
### 스토어페이지 작업
#### 1. 스토어 병합작업
### 추가된 데이터베이스
### Store
	CREATE TABLE Store(
		storeIdx BIGINT PRIMARY KEY,
		goods VARCHAR(50),
		category VARCHAR(50),
		price INT NOT NULL,
		introduce VARCHAR(500),
		stock INT NOT NULL,
		goodsLike INT,
		itemName VARCHAR(50) NOT NULL
	);
### 결제페이지 작업
#### 1. 결제기능 병합작업
### 추가된 데이터베이스
### Pay
	CREATE TABLE Pay(
		impUid VARCHAR(200) PRIMARY KEY,
		merchantUid VARCHAR(200),
		PGname VARCHAR(50), 
		payMethod VARCHAR(300),
		itemName VARCHAR(50),
		price INT,
		buyerEmail VARCHAR(50),
		buyerName VARCHAR(20),
		buyerTel VARCHAR(20),
		buyerAddress VARCHAR(50),
		buyerPostNum VARCHAR(50),
		itemCount INT,
		isPaid INT
	);
### 메인페이지 작업
#### 1. 메인페이지 알림 리스트로 보여주기 작업
#### 2. 알림 확인 및 내용 삭제
### 마이페이지 작업
#### 1. 찜 목록 보여주기(좋아요를 한 모든 게시물 - 스터디원구하기, 멘토구하기, 멘토프로필)
#### 2. 구매내역 보여주기

## 04/05
### 알림기능 오류 해결
### 멘토신청, 멘티 신청 오류 

## 04/10
### 스토어 좋아요 기능 넣기
### 추가된 데이터베이스
### StoreLike
	CREATE TABLE StoreLike(
		idx BIGINT PRIMARY KEY,
		likeIdx BIGINT,
		memberIdx BIGINT
	);

## 04/12
### 스토어 댓글 기능 넣기
### 댓글은 구매한 사람만 작성할 수 있게 설정
### 댓글 수정 및 삭제기능 추가
### 추가된 데이터베이스
### StoreComment
	CREATE TABLE StoreComment(
		idx BIGINT PROMARY KEY,
		commentIdx BIGINT NOT NULL,
		writer VARCHAR(20) NOT NULL,
		comment VARCHAR(100) NOT NULL,
		deleteCheck INT NOT NULL
	);

## 04/13
### 현재진행중인 만남 기능 넣기
### 전체 주석정리
### 추가된 데이터베이스
### Meeting
	CREATE TABLE Meeting(
		idx BIGINT PRIMARY KEY,
		emailId VARCHAR(20) NOT NULL,
		recruitStudyIdx BIGINT,
		recruitStudyImage VARCHAR(50),
		recruitStudyTitle VARCHAR(100),
		recruitMentorIdx BIGINT,
		recruitMentorTitle VARCHAR(100),
		recruitMentorWriter VARCHAR(20),
		recruitMenteeIdx BIGINT,
		recruitMenteeImage VARCHAR(50),
		recruitMenteeTitle VARCHAR(100),
		recruitMenteeWriter VARCHAR(20)
	
	);

### store 테이블 변경
### 변경된 테이블
### Store
	CREATE TABLE Store(
		storeIdx BIGINT PRIMARY KEY AUTO_INCREMENT,
		goods VARCHAR(50) NOT NULL,
		category VARCHAR(50) NOT NULL,
		price INT NOT NULL,
		introduce VARCHAR(500),
		stock INT(10) NOT NULL,
		goodsLike INT NOT NULL,
		itemName VARCHAR(50) NOT NULL,
		image VARCHAR(50) NOT NULL
	);
