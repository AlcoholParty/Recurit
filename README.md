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
