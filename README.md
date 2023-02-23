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

## 20/23
### 1.스터디원 구하기 글 작성
### 2.스터디원 구하기 작성된 목록들 보여주기
