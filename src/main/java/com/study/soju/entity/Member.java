package com.study.soju.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Map;

@Getter // getter 어노테이션
@Setter // setter 어노테이션
@NoArgsConstructor // 파라미터가 없는 기본 생성자 어노테이션
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 어노테이션
@Builder // 빌더 어노테이션 - 빌더를 통해 해당 객체의 필드 값을 재생성 한다.
@ToString // 객체를 불러올때 주소값이 아닌 String 타입으로 변경해 주는 어노테이션
@Entity(name = "Member") // Entity 어노테이션 - 괄호안에는 테이블명과 똑같이 작성한다.
public class Member {
    @Id // 기본키 어노테이션 - 기본키 설정 (PRIMARY KEY)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT - MySQL에서 시퀀스 역할을 담당한다.
    // @Column() // 컬럼 어노테이션 - 기본키 제외 나머지 컬럼 설정 - 기본키랑 같이 쓰이면 기본키의 설정값들을 잡아줄 수 있다.
    private Long idx; // MySQL에서 AUTO_INCREMENT를 사용하면 null값이 들어가야 자동으로 숫자가 올라간다.
                      // 하지만 long 즉, 원시타입으로 작성하면 null값을 허용하지 않기 때문에 오류가 난다.
                      // 그래서 Long 즉, 참조타입으로 작성해야 null값을 허용해서 값이 제대로 들어가게 된다.

    // length = 길이, unique = (기본값)false:유니크 해제 / true:유니크 설정, nullable = (기본값)true:눌값 허용 / false:눌값 불가
    @Column(length = 50, unique = true, nullable = false)
    private String emailId;

    @Column(length = 255)
    private String pwd;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 20, unique = true, nullable = false)
    private String nickname;

    @Column(length = 10, nullable = false)
    private String birthday;

    @Column(length = 1, nullable = false)
    private String gender;

    @Column(length = 15, unique = true, nullable = false)
    private String phoneNumber;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 10, nullable = false)
    private String studyType;

    @Column(length = 10, nullable = false)
    private String platform;

    @Column(length = 100, nullable = false)
    private String roleName; // Spring Security 권한 설정

    @Column(length = 100)
    private String profileImage;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DTO 구역

    // 이메일 인증 Response DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    @ToString
    public static class rpCheckEmailId {
        private String emailId;
        private String msg;

        // Entity를 DTO로 변환 (생성자 방식)
        public rpCheckEmailId(String emailId, String msg) { // 파라미터로 서비스에서 넘어온 체크 값과 메시지를 받아온다.
            // 조회된 유저가 있을 경우 - emailId : 0 / msg : 중복 가입 에러 메세지
            // 이메일 전송 성공할 경우 - emailId : 이메일 아이디 / 이메일 인증 번호
            // 이메일 전송 실패할 경우 - emailId : -1 / msg : 전송 실패 에러 메세지
            this.emailId = emailId; // emailId는 전송 및 에러 체크 값으로 사용한다.
            this.msg = msg; // msg는 알람 메세지로 사용한다.
        }
    }

    // 자사 회원가입 Request DTO
    @Getter // getter 어노테이션
    @Setter // setter 어노테이션
    @NoArgsConstructor // 파라미터가 없는 기본 생성자 어노테이션
    @AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 어노테이션
    @Builder // 빌더 사용 어노테이션
    @ToString // 객체를 불러올때 주솟값이 아닌 String 타입으로 변경해주는 어노테이션
    public static class rqJoinMember {
        private String emailId;
        private String pwd;
        private String name;
        private String nickname;
        private String birthday;
        private String gender;
        private String phoneNumber;
        private String address;
        private String studyType;

        // DTO를 Entity로 변환 (빌더 방식)
        public Member toEntity(PasswordEncoder passwordEncoder) { // 5. 파라미터로 서비스에서 넘어온 비밀번호 암호화 메소드를 받아온다.
            // 6. 비밀번호 암호화
            String enPassword = passwordEncoder.encode(pwd);
            // 7. 변환된 Entity를 반환한다.
            return Member.builder()
                    .idx(null)
                    .emailId(emailId)
                    .pwd(enPassword) // 암호화된 비밀번호 저장
                    .name(name)
                    .nickname(nickname)
                    .birthday(birthday)
                    .gender(gender)
                    .phoneNumber(phoneNumber)
                    .address(address)
                    .studyType(studyType)
                    .platform("soju") // 가입 플랫폼 설정
                    .roleName("USER") // Spring Security 권한에 USER로 설정
                    .profileImage("noImage.jpeg") // 가입할때는 아무 사진도 지정되어 있지 않다.
                    .build();
        }
    }

    // 자사 회원가입 Response DTO
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class rpJoinMember {
        private String nickname;
        private String roleName;

        // Entity를 DTO로 변환 (생성자 방식)
        public rpJoinMember(Member member) {
            this.nickname = member.getNickname();
            this.roleName = member.getRoleName();
        }
    }

    // 닉네임 Response DTO
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class rpNickname {
        private String nickname;

        // Entity를 DTO로 변환 (생성자 방식)
        public rpNickname(Member member) {
            this.nickname = member.getNickname();
        }
    }

    // 닉네임 및 프로필 사진 Response DTO
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class rpNickImage {
        private String nickname;
        private String profileImage;

        // Entity를 DTO로 변환 (생성자 방식)
        public rpNickImage(Member member) {
            this.nickname = member.getNickname();
            this.profileImage = member.getProfileImage();
        }
    }

    // MetaProfile Response DTO
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class rpMetaProfile {
        private String profileImage;
        private String nickname;
        private String studyType;

        // Entity를 DTO로 변환 (생성자 방식)
        public rpMetaProfile(Member member) {
            this.profileImage = member.getProfileImage();
            this.nickname = member.getNickname();
            this.studyType = member.getStudyType();
        }
    }
}
