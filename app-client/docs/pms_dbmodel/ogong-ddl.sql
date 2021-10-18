-- 스터디카페룸사진
DROP TABLE IF EXISTS studycafe_room_photo RESTRICT;

-- 스터디카페룸
DROP TABLE IF EXISTS studycafe_room RESTRICT;

-- 리뷰
DROP TABLE IF EXISTS studycafe_review RESTRICT;

-- 스터디카페예약상태
DROP TABLE IF EXISTS studycafe_reservation_status RESTRICT;

-- 스터디카페예약
DROP TABLE IF EXISTS studycafe_reservation RESTRICT;

-- 스터디카페사진
DROP TABLE IF EXISTS studycafe_photo RESTRICT;

-- 결제유형
DROP TABLE IF EXISTS studycafe_payment_type RESTRICT;

-- 결제
DROP TABLE IF EXISTS studycafe_payment RESTRICT;

-- 카페운영상태
DROP TABLE IF EXISTS studycafe_operating_status RESTRICT;

-- 카페휴무일
DROP TABLE IF EXISTS studycafe_holiday RESTRICT;

-- 스터디카페
DROP TABLE IF EXISTS studycafe RESTRICT;

-- 투두진행상태
DROP TABLE IF EXISTS study_todolsit_progress RESTRICT;

-- 투두리스트
DROP TABLE IF EXISTS study_todolist RESTRICT;

-- 스터디분야
DROP TABLE IF EXISTS study_subject RESTRICT;

-- 한스터디여러지역
DROP TABLE IF EXISTS study_multiple_location RESTRICT;

-- 스터디지역
DROP TABLE IF EXISTS study_location RESTRICT;

-- 스터디구성원
DROP TABLE IF EXISTS study_guilder RESTRICT;

-- 스터디대면상태
DROP TABLE IF EXISTS study_face_status RESTRICT;

-- 캘린더중요도
DROP TABLE IF EXISTS study_calender_importance RESTRICT;

-- 캘린더
DROP TABLE IF EXISTS study_calender RESTRICT;

-- 스터디게시판첨부파일
DROP TABLE IF EXISTS study_board_file RESTRICT;

-- 댓글
DROP TABLE IF EXISTS study_board_comment RESTRICT;

-- 스터디게시판
DROP TABLE IF EXISTS study_board RESTRICT;

-- 스터디
DROP TABLE IF EXISTS study RESTRICT;

-- 개인
DROP TABLE IF EXISTS per_member RESTRICT;

-- 결제현황
DROP TABLE IF EXISTS studycafe_payment_status RESTRICT;

-- 첨부파일
DROP TABLE IF EXISTS notice_file RESTRICT;

-- 공지사항
DROP TABLE IF EXISTS notice RESTRICT;

-- 사장
DROP TABLE IF EXISTS ceo_member RESTRICT;

-- 스터디북마크
DROP TABLE IF EXISTS study_bookmark RESTRICT;

-- 좋아요
DROP TABLE IF EXISTS study_board_like RESTRICT;

-- 답변
DROP TABLE IF EXISTS ask_board_reply RESTRICT;

-- 문의게시판
DROP TABLE IF EXISTS ask_board RESTRICT;

-- 관리자
DROP TABLE IF EXISTS admin RESTRICT;

-- 회원
DROP TABLE IF EXISTS member RESTRICT;

-- 스터디카페룸사진
CREATE TABLE studycafe_room_photo (
  photo_no     INTEGER      NOT NULL COMMENT '사진번호', -- 사진번호
  studyroom_no INTEGER      NOT NULL COMMENT '스터디룸번호', -- 스터디룸번호
  name         VARCHAR(255) NOT NULL COMMENT '사진명' -- 사진명
)
COMMENT '스터디카페룸사진';

-- 스터디카페룸사진
ALTER TABLE studycafe_room_photo
  ADD CONSTRAINT PK_studycafe_room_photo -- 스터디카페룸사진 기본키
    PRIMARY KEY (
      photo_no -- 사진번호
    );

ALTER TABLE studycafe_room_photo
  MODIFY COLUMN photo_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '사진번호';

-- 스터디카페룸
CREATE TABLE studycafe_room (
  studyroom_no  INTEGER     NOT NULL COMMENT '스터디룸번호', -- 스터디룸번호
  name          VARCHAR(50) NOT NULL COMMENT '스터디룸명', -- 스터디룸명
  cafe_no       INTEGER     NOT NULL COMMENT '스터디카페번호', -- 스터디카페번호
  Introduction  TEXT        NOT NULL COMMENT '소개글', -- 소개글
  people        INTEGER     NOT NULL DEFAULT 1 COMMENT '인원제한수', -- 인원제한수
  hourly_amount INTEGER     NOT NULL COMMENT '시간당금액' -- 시간당금액
)
COMMENT '스터디카페룸';

-- 스터디카페룸
ALTER TABLE studycafe_room
  ADD CONSTRAINT PK_studycafe_room -- 스터디카페룸 기본키
    PRIMARY KEY (
      studyroom_no -- 스터디룸번호
    );

-- 스터디카페룸 유니크 인덱스
CREATE UNIQUE INDEX UIX_studycafe_room
  ON studycafe_room ( -- 스터디카페룸
    cafe_no ASC, -- 스터디카페번호
    name ASC     -- 스터디룸명
  );

ALTER TABLE studycafe_room
  MODIFY COLUMN studyroom_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '스터디룸번호';

-- 리뷰
CREATE TABLE studycafe_review (
  review_no        INTEGER      NOT NULL COMMENT '리뷰번호', -- 리뷰번호
  studycafe_rsv_no INTEGER      NOT NULL COMMENT '스터디카페예약번호', -- 스터디카페예약번호
  content          VARCHAR(255) NOT NULL COMMENT '내용', -- 내용
  grade            INTEGER      NOT NULL COMMENT '평점', -- 평점
  create_dt        DATE         NOT NULL DEFAULT curdate() COMMENT '등록일' -- 등록일
)
COMMENT '리뷰';

-- 리뷰
ALTER TABLE studycafe_review
  ADD CONSTRAINT PK_studycafe_review -- 리뷰 기본키
    PRIMARY KEY (
      review_no -- 리뷰번호
    );

ALTER TABLE studycafe_review
  MODIFY COLUMN review_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '리뷰번호';

-- 스터디카페예약상태
CREATE TABLE studycafe_reservation_status (
  rsv_status_no INTEGER     NOT NULL COMMENT '예약상태번호', -- 예약상태번호
  rsv_name      VARCHAR(50) NOT NULL COMMENT '예약상태명' -- 예약상태명
)
COMMENT '스터디카페예약상태';

-- 스터디카페예약상태
ALTER TABLE studycafe_reservation_status
  ADD CONSTRAINT PK_studycafe_reservation_status -- 스터디카페예약상태 기본키
    PRIMARY KEY (
      rsv_status_no -- 예약상태번호
    );

-- 스터디카페예약상태 유니크 인덱스
CREATE UNIQUE INDEX UIX_studycafe_reservation_status
  ON studycafe_reservation_status ( -- 스터디카페예약상태
    rsv_name ASC -- 예약상태명
  );

-- 스터디카페예약
CREATE TABLE studycafe_reservation (
  studycafe_rsv_no INTEGER  NOT NULL COMMENT '스터디카페예약번호', -- 스터디카페예약번호
  studyroom_no     INTEGER  NOT NULL COMMENT '스터디룸번호', -- 스터디룸번호
  per_member_no    INTEGER  NOT NULL COMMENT '개인번호', -- 개인번호
  rsv_dt           DATETIME NOT NULL DEFAULT now() COMMENT '예약일', -- 예약일
  using_dt         DATE     NOT NULL COMMENT '이용날짜', -- 이용날짜
  start_time       TIME     NOT NULL COMMENT '시작시간', -- 시작시간
  using_time       TIME     NOT NULL COMMENT '이용시간', -- 이용시간
  people           INTEGER  NOT NULL DEFAULT 1 COMMENT '사용인원수', -- 사용인원수
  total_price      INTEGER  NOT NULL COMMENT '총금액', -- 총금액
  rsv_status_no    INTEGER  NOT NULL COMMENT '예약상태번호', -- 예약상태번호
  review           INTEGER  NOT NULL COMMENT '리뷰작성여부' -- 리뷰작성여부
)
COMMENT '스터디카페예약';

-- 스터디카페예약
ALTER TABLE studycafe_reservation
  ADD CONSTRAINT PK_studycafe_reservation -- 스터디카페예약 기본키
    PRIMARY KEY (
      studycafe_rsv_no -- 스터디카페예약번호
    );

ALTER TABLE studycafe_reservation
  MODIFY COLUMN studycafe_rsv_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '스터디카페예약번호';

-- 스터디카페사진
CREATE TABLE studycafe_photo (
  photo_no INTEGER      NOT NULL COMMENT '사진번호', -- 사진번호
  name     VARCHAR(255) NOT NULL COMMENT '사진명', -- 사진명
  cafe_no  INTEGER      NOT NULL COMMENT '스터디카페번호' -- 스터디카페번호
)
COMMENT '스터디카페사진';

-- 스터디카페사진
ALTER TABLE studycafe_photo
  ADD CONSTRAINT PK_studycafe_photo -- 스터디카페사진 기본키
    PRIMARY KEY (
      photo_no -- 사진번호
    );

ALTER TABLE studycafe_photo
  MODIFY COLUMN photo_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '사진번호';

-- 결제유형
CREATE TABLE studycafe_payment_type (
  payment_type_no INTEGER     NOT NULL COMMENT '결제유형번호', -- 결제유형번호
  name            VARCHAR(50) NOT NULL COMMENT '결제유형명' -- 결제유형명
)
COMMENT '결제유형';

-- 결제유형
ALTER TABLE studycafe_payment_type
  ADD CONSTRAINT PK_studycafe_payment_type -- 결제유형 기본키
    PRIMARY KEY (
      payment_type_no -- 결제유형번호
    );

-- 결제유형 유니크 인덱스
CREATE UNIQUE INDEX UIX_studycafe_payment_type
  ON studycafe_payment_type ( -- 결제유형
    name ASC -- 결제유형명
  );

-- 결제
CREATE TABLE studycafe_payment (
  payment_no        INTEGER  NOT NULL COMMENT '결제번호', -- 결제번호
  studycafe_rsv_no  INTEGER  NULL     COMMENT '스터디카페예약번호', -- 스터디카페예약번호
  payment_type_no   INTEGER  NOT NULL COMMENT '결제유형번호', -- 결제유형번호
  payment_status_no INTEGER  NOT NULL COMMENT '결제현황', -- 결제현황
  payment_dt        DATETIME NOT NULL DEFAULT now() COMMENT '결제일' -- 결제일
)
COMMENT '결제';

-- 결제
ALTER TABLE studycafe_payment
  ADD CONSTRAINT PK_studycafe_payment -- 결제 기본키
    PRIMARY KEY (
      payment_no -- 결제번호
    );

ALTER TABLE studycafe_payment
  MODIFY COLUMN payment_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '결제번호';

-- 카페운영상태
CREATE TABLE studycafe_operating_status (
  operating_status_no INTEGER     NOT NULL COMMENT '운영상태번호', -- 운영상태번호
  name                VARCHAR(50) NOT NULL COMMENT '운영상태명' -- 운영상태명
)
COMMENT '카페운영상태';

-- 카페운영상태
ALTER TABLE studycafe_operating_status
  ADD CONSTRAINT PK_studycafe_operating_status -- 카페운영상태 기본키
    PRIMARY KEY (
      operating_status_no -- 운영상태번호
    );

-- 카페운영상태 유니크 인덱스
CREATE UNIQUE INDEX UIX_studycafe_operating_status
  ON studycafe_operating_status ( -- 카페운영상태
    name ASC -- 운영상태명
  );

-- 카페휴무일
CREATE TABLE studycafe_holiday (
  holiday_no INTEGER NOT NULL COMMENT '휴무일번호', -- 휴무일번호
  cafe_no    INTEGER NOT NULL COMMENT '스터디카페번호', -- 스터디카페번호
  date       DATE    NOT NULL COMMENT '날짜' -- 날짜
)
COMMENT '카페휴무일';

-- 카페휴무일
ALTER TABLE studycafe_holiday
  ADD CONSTRAINT PK_studycafe_holiday -- 카페휴무일 기본키
    PRIMARY KEY (
      holiday_no -- 휴무일번호
    );

ALTER TABLE studycafe_holiday
  MODIFY COLUMN holiday_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '휴무일번호';

-- 스터디카페
CREATE TABLE studycafe (
  cafe_no             INTEGER      NOT NULL COMMENT '스터디카페번호', -- 스터디카페번호
  name                VARCHAR(50)  NOT NULL COMMENT '점포명', -- 점포명
  info                TEXT         NOT NULL COMMENT '소개글', -- 소개글
  location            VARCHAR(255) NOT NULL COMMENT '주소', -- 주소
  phone               VARCHAR(30)  NOT NULL COMMENT '전화번호', -- 전화번호
  open_time           TIME         NOT NULL COMMENT '오픈시간', -- 오픈시간
  close_time          TIME         NOT NULL COMMENT '마감시간', -- 마감시간
  bookable            INTEGER      NULL     COMMENT '예약가능인원', -- 예약가능인원
  view_cnt            INTEGER      NOT NULL DEFAULT 0 COMMENT '조회수', -- 조회수
  operating_status_no INTEGER      NOT NULL COMMENT '운영상태번호', -- 운영상태번호
  ceo_member_no       INTEGER      NOT NULL COMMENT '사장번호' -- 사장번호
)
COMMENT '스터디카페';

-- 스터디카페
ALTER TABLE studycafe
  ADD CONSTRAINT PK_studycafe -- 스터디카페 기본키
    PRIMARY KEY (
      cafe_no -- 스터디카페번호
    );

-- 스터디카페 인덱스
CREATE INDEX IX_studycafe
  ON studycafe( -- 스터디카페
    name ASC -- 점포명
  );

ALTER TABLE studycafe
  MODIFY COLUMN cafe_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '스터디카페번호';

-- 투두진행상태
CREATE TABLE study_todolsit_progress (
  progress_no INTEGER     NOT NULL COMMENT '진행상태번호', -- 진행상태번호
  name        VARCHAR(50) NOT NULL COMMENT '진행상태명' -- 진행상태명
)
COMMENT '투두진행상태';

-- 투두진행상태
ALTER TABLE study_todolsit_progress
  ADD CONSTRAINT PK_study_todolsit_progress -- 투두진행상태 기본키
    PRIMARY KEY (
      progress_no -- 진행상태번호
    );

-- 투두진행상태 유니크 인덱스
CREATE UNIQUE INDEX UIX_study_todolsit_progress
  ON study_todolsit_progress ( -- 투두진행상태
    name ASC -- 진행상태명
  );

-- 투두리스트
CREATE TABLE study_todolist (
  todolist_no   INTEGER      NOT NULL COMMENT '투두리스트번호', -- 투두리스트번호
  study_no      INTEGER      NOT NULL COMMENT '스터디번호', -- 스터디번호
  per_member_no INTEGER      NOT NULL COMMENT '개인번호', -- 개인번호
  title         VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
  content       VARCHAR(255) NOT NULL COMMENT '내용', -- 내용
  create_dt     DATE         NOT NULL DEFAULT curdate() COMMENT '등록일', -- 등록일
  note          VARCHAR(255) NULL     COMMENT '비고', -- 비고
  progress_no   INTEGER      NOT NULL COMMENT '진행상태번호' -- 진행상태번호
)
COMMENT '투두리스트';

-- 투두리스트
ALTER TABLE study_todolist
  ADD CONSTRAINT PK_study_todolist -- 투두리스트 기본키
    PRIMARY KEY (
      todolist_no -- 투두리스트번호
    );

-- 투두리스트 인덱스
CREATE INDEX IX_study_todolist
  ON study_todolist( -- 투두리스트
    title ASC -- 제목
  );

ALTER TABLE study_todolist
  MODIFY COLUMN todolist_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '투두리스트번호';

-- 스터디분야
CREATE TABLE study_subject (
  subject_no INTEGER     NOT NULL COMMENT '스터디카테고리번호', -- 스터디카테고리번호
  name       VARCHAR(50) NOT NULL COMMENT '카테고리명' -- 카테고리명
)
COMMENT '스터디분야';

-- 스터디분야
ALTER TABLE study_subject
  ADD CONSTRAINT PK_study_subject -- 스터디분야 기본키
    PRIMARY KEY (
      subject_no -- 스터디카테고리번호
    );

-- 스터디분야 유니크 인덱스
CREATE UNIQUE INDEX UIX_study_subject
  ON study_subject ( -- 스터디분야
    name ASC -- 카테고리명
  );

-- 한스터디여러지역
CREATE TABLE study_multiple_location (
  study_no    INTEGER NOT NULL COMMENT '스터디번호', -- 스터디번호
  location_no INTEGER NOT NULL COMMENT '지역번호' -- 지역번호
)
COMMENT '한스터디여러지역';

-- 한스터디여러지역
ALTER TABLE study_multiple_location
  ADD CONSTRAINT PK_study_multiple_location -- 한스터디여러지역 기본키
    PRIMARY KEY (
      study_no,    -- 스터디번호
      location_no  -- 지역번호
    );

-- 스터디지역
CREATE TABLE study_location (
  location_no INTEGER     NOT NULL COMMENT '지역번호', -- 지역번호
  name        VARCHAR(50) NOT NULL COMMENT '지역명' -- 지역명
)
COMMENT '스터디지역';

-- 스터디지역
ALTER TABLE study_location
  ADD CONSTRAINT PK_study_location -- 스터디지역 기본키
    PRIMARY KEY (
      location_no -- 지역번호
    );

ALTER TABLE study_location
  MODIFY COLUMN location_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '지역번호';

-- 스터디구성원
CREATE TABLE study_guilder (
  study_no      INTEGER NOT NULL COMMENT '스터디번호', -- 스터디번호
  per_member_no INTEGER NOT NULL COMMENT '개인번호', -- 개인번호
  created_dt    DATE    NOT NULL DEFAULT curdate() COMMENT '스터디가입일', -- 스터디가입일
  status        INTEGER NULL     DEFAULT 1 COMMENT '승인여부' -- 승인여부
)
COMMENT '스터디구성원';

-- 스터디구성원
ALTER TABLE study_guilder
  ADD CONSTRAINT PK_study_guilder -- 스터디구성원 기본키
    PRIMARY KEY (
      study_no,      -- 스터디번호
      per_member_no  -- 개인번호
    );

-- 스터디대면상태
CREATE TABLE study_face_status (
  face_no INTEGER     NOT NULL COMMENT '대면상태번호', -- 대면상태번호
  name    VARCHAR(50) NOT NULL COMMENT '대면상태명' -- 대면상태명
)
COMMENT '스터디대면상태';

-- 스터디대면상태
ALTER TABLE study_face_status
  ADD CONSTRAINT PK_study_face_status -- 스터디대면상태 기본키
    PRIMARY KEY (
      face_no -- 대면상태번호
    );

-- 스터디대면상태 유니크 인덱스
CREATE UNIQUE INDEX UIX_study_face_status
  ON study_face_status ( -- 스터디대면상태
    name ASC -- 대면상태명
  );

-- 캘린더중요도
CREATE TABLE study_calender_importance (
  importance_no INTEGER NOT NULL COMMENT '중요도상태번호', -- 중요도상태번호
  importance    INTEGER NOT NULL COMMENT '중요도' -- 중요도
)
COMMENT '캘린더중요도';

-- 캘린더중요도
ALTER TABLE study_calender_importance
  ADD CONSTRAINT PK_study_calender_importance -- 캘린더중요도 기본키
    PRIMARY KEY (
      importance_no -- 중요도상태번호
    );

-- 캘린더중요도 유니크 인덱스
CREATE UNIQUE INDEX UIX_study_calender_importance
  ON study_calender_importance ( -- 캘린더중요도
    importance ASC -- 중요도
  );

ALTER TABLE study_calender_importance
  MODIFY COLUMN importance_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '중요도상태번호';

-- 캘린더
CREATE TABLE study_calender (
  calender_no   VARCHAR(50)  NOT NULL COMMENT '캘린더번호', -- 캘린더번호
  study_no      INTEGER      NOT NULL COMMENT '스터디번호', -- 스터디번호
  per_member_no INTEGER      NOT NULL COMMENT '개인번호', -- 개인번호
  calender_dt   DATE         NOT NULL COMMENT '날짜', -- 날짜
  day_week      VARCHAR(3)   NOT NULL COMMENT '요일', -- 요일
  content       VARCHAR(255) NOT NULL COMMENT '내용', -- 내용
  end_dt        DATE         NOT NULL COMMENT '종료일', -- 종료일
  importance_no INTEGER      NOT NULL COMMENT '중요도상태번호' -- 중요도상태번호
)
COMMENT '캘린더';

-- 캘린더
ALTER TABLE study_calender
  ADD CONSTRAINT PK_study_calender -- 캘린더 기본키
    PRIMARY KEY (
      calender_no -- 캘린더번호
    );

ALTER TABLE study_calender
  MODIFY COLUMN calender_no VARCHAR(50) NOT NULL AUTO_INCREMENT COMMENT '캘린더번호';

-- 스터디게시판첨부파일
CREATE TABLE study_board_file (
  file_no     INTEGER      NOT NULL COMMENT '첨부파일번호', -- 첨부파일번호
  name        VARCHAR(255) NOT NULL COMMENT '첨부파일명', -- 첨부파일명
  st_board_no INTEGER      NOT NULL COMMENT '스터디자유게시판번호' -- 스터디자유게시판번호
)
COMMENT '스터디게시판첨부파일';

-- 스터디게시판첨부파일
ALTER TABLE study_board_file
  ADD CONSTRAINT PK_study_board_file -- 스터디게시판첨부파일 기본키
    PRIMARY KEY (
      file_no -- 첨부파일번호
    );

-- 댓글
CREATE TABLE study_board_comment (
  comment_no    INTEGER      NOT NULL COMMENT '댓글번호', -- 댓글번호
  study_no      INTEGER      NOT NULL COMMENT '스터디번호', -- 스터디번호
  st_board_no   INTEGER      NOT NULL COMMENT '스터디자유게시판번호', -- 스터디자유게시판번호
  content       VARCHAR(255) NOT NULL COMMENT '내용', -- 내용
  per_member_no INTEGER      NOT NULL COMMENT '개인번호', -- 개인번호
  created_dt    DATETIME     NOT NULL DEFAULT now() COMMENT '작성일' -- 작성일
)
COMMENT '댓글';

-- 댓글
ALTER TABLE study_board_comment
  ADD CONSTRAINT PK_study_board_comment -- 댓글 기본키
    PRIMARY KEY (
      comment_no -- 댓글번호
    );

ALTER TABLE study_board_comment
  MODIFY COLUMN comment_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '댓글번호';

-- 스터디게시판
CREATE TABLE study_board (
  st_board_no   INTEGER      NOT NULL COMMENT '스터디자유게시판번호', -- 스터디자유게시판번호
  study_no      INTEGER      NOT NULL COMMENT '스터디번호', -- 스터디번호
  per_member_no INTEGER      NOT NULL COMMENT '개인번호', -- 개인번호
  title         VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
  content       VARCHAR(255) NOT NULL COMMENT '내용', -- 내용
  view_ct       INTEGER      NOT NULL DEFAULT 0 COMMENT '조회수', -- 조회수
  created_dt    DATETIME     NOT NULL DEFAULT now()
   COMMENT '작성일' -- 작성일
)
COMMENT '스터디게시판';

-- 스터디게시판
ALTER TABLE study_board
  ADD CONSTRAINT PK_study_board -- 스터디게시판 기본키
    PRIMARY KEY (
      st_board_no -- 스터디자유게시판번호
    );

-- 스터디게시판 인덱스
CREATE INDEX IX_study_board
  ON study_board( -- 스터디게시판
    title ASC -- 제목
  );

ALTER TABLE study_board
  MODIFY COLUMN st_board_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '스터디자유게시판번호';

-- 스터디
CREATE TABLE study (
  study_no      INTEGER     NOT NULL COMMENT '스터디번호', -- 스터디번호
  name          VARCHAR(50) NOT NULL COMMENT '스터디명', -- 스터디명
  subject_no    INTEGER     NOT NULL COMMENT '스터디카테고리번호', -- 스터디카테고리번호
  no_people     INTEGER     NOT NULL COMMENT '인원수', -- 인원수
  face_no       INTEGER     NOT NULL COMMENT '대면상태번호', -- 대면상태번호
  introduction  TEXT        NOT NULL COMMENT '소개글', -- 소개글
  created_dt    DATE        NOT NULL DEFAULT curdate() COMMENT '스터디등록일', -- 스터디등록일
  per_member_no INTEGER     NOT NULL COMMENT '개인번호', -- 개인번호
  score         INTEGER     NOT NULL DEFAULT 0 COMMENT '스터디활동점수' -- 스터디활동점수
)
COMMENT '스터디';

-- 스터디
ALTER TABLE study
  ADD CONSTRAINT PK_study -- 스터디 기본키
    PRIMARY KEY (
      study_no -- 스터디번호
    );

-- 스터디 인덱스
CREATE INDEX IX_study
  ON study( -- 스터디
    name ASC -- 스터디명
  );

ALTER TABLE study
  MODIFY COLUMN study_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '스터디번호';

-- 개인
CREATE TABLE per_member (
  per_member_no INTEGER NOT NULL COMMENT '개인번호', -- 개인번호
  member_no     INTEGER NOT NULL COMMENT '회원번호' -- 회원번호
)
COMMENT '개인';

-- 개인
ALTER TABLE per_member
  ADD CONSTRAINT PK_per_member -- 개인 기본키
    PRIMARY KEY (
      per_member_no -- 개인번호
    );

-- 개인 유니크 인덱스
CREATE UNIQUE INDEX UIX_per_member
  ON per_member ( -- 개인
    member_no ASC -- 회원번호
  );

ALTER TABLE per_member
  MODIFY COLUMN per_member_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '개인번호';

-- 결제현황
CREATE TABLE studycafe_payment_status (
  payment_status_no INTEGER     NOT NULL COMMENT '결제현황', -- 결제현황
  name              VARCHAR(50) NOT NULL COMMENT '현황' -- 현황
)
COMMENT '결제현황';

-- 결제현황
ALTER TABLE studycafe_payment_status
  ADD CONSTRAINT PK_studycafe_payment_status -- 결제현황 기본키
    PRIMARY KEY (
      payment_status_no -- 결제현황
    );

-- 결제현황 유니크 인덱스
CREATE UNIQUE INDEX UIX_studycafe_payment_status
  ON studycafe_payment_status ( -- 결제현황
    name ASC -- 현황
  );

-- 첨부파일
CREATE TABLE notice_file (
  notice_file_no INTEGER      NOT NULL COMMENT '첨부파일번호', -- 첨부파일번호
  notice_no      INTEGER      NOT NULL COMMENT '공지사항번호', -- 공지사항번호
  filepath       VARCHAR(255) NOT NULL COMMENT '첨부파일명' -- 첨부파일명
)
COMMENT '첨부파일';

-- 첨부파일
ALTER TABLE notice_file
  ADD CONSTRAINT PK_notice_file -- 첨부파일 기본키
    PRIMARY KEY (
      notice_file_no -- 첨부파일번호
    );

ALTER TABLE notice_file
  MODIFY COLUMN notice_file_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '첨부파일번호';

-- 공지사항
CREATE TABLE notice (
  notice_no INTEGER      NOT NULL COMMENT '공지사항번호', -- 공지사항번호
  title     VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
  content   VARCHAR(255) NOT NULL COMMENT '내용', -- 내용
  create_dt DATETIME     NOT NULL DEFAULT now() COMMENT '등록일' -- 등록일
)
COMMENT '공지사항';

-- 공지사항
ALTER TABLE notice
  ADD CONSTRAINT PK_notice -- 공지사항 기본키
    PRIMARY KEY (
      notice_no -- 공지사항번호
    );

-- 공지사항 인덱스
CREATE INDEX IX_notice
  ON notice( -- 공지사항
    title ASC -- 제목
  );

ALTER TABLE notice
  MODIFY COLUMN notice_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '공지사항번호';

-- 사장
CREATE TABLE ceo_member (
  ceo_member_no INTEGER     NOT NULL COMMENT '사장번호', -- 사장번호
  member_no     INTEGER     NOT NULL COMMENT '회원번호', -- 회원번호
  bossname      VARCHAR(50) NOT NULL COMMENT '대표자명', -- 대표자명
  license_no    VARCHAR(13) NOT NULL COMMENT '사업자등록번호' -- 사업자등록번호
)
COMMENT '사장';

-- 사장
ALTER TABLE ceo_member
  ADD CONSTRAINT PK_ceo_member -- 사장 기본키
    PRIMARY KEY (
      ceo_member_no -- 사장번호
    );

-- 사장 유니크 인덱스
CREATE UNIQUE INDEX UIX_ceo_member
  ON ceo_member ( -- 사장
    member_no ASC -- 회원번호
  );

ALTER TABLE ceo_member
  MODIFY COLUMN ceo_member_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '사장번호';

-- 스터디북마크
CREATE TABLE study_bookmark (
  per_member_no INTEGER NOT NULL COMMENT '개인번호', -- 개인번호
  study_no      INTEGER NOT NULL COMMENT '스터디번호' -- 스터디번호
)
COMMENT '스터디북마크';

-- 스터디북마크
ALTER TABLE study_bookmark
  ADD CONSTRAINT PK_study_bookmark -- 스터디북마크 기본키
    PRIMARY KEY (
      per_member_no, -- 개인번호
      study_no       -- 스터디번호
    );

-- 좋아요
CREATE TABLE study_board_like (
  study_no      INTEGER NOT NULL COMMENT '스터디번호', -- 스터디번호
  per_member_no INTEGER NOT NULL COMMENT '개인번호', -- 개인번호
  st_board_no   INTEGER NOT NULL COMMENT '스터디자유게시판번호', -- 스터디자유게시판번호
  create_dt     DATE    NOT NULL DEFAULT curdate()
   COMMENT '등록일' -- 등록일
)
COMMENT '좋아요';

-- 좋아요
ALTER TABLE study_board_like
  ADD CONSTRAINT PK_study_board_like -- 좋아요 기본키
    PRIMARY KEY (
      study_no,      -- 스터디번호
      per_member_no, -- 개인번호
      st_board_no    -- 스터디자유게시판번호
    );

-- 답변
CREATE TABLE ask_board_reply (
  reply_no     INTEGER     NOT NULL COMMENT '답변번호', -- 답변번호
  admin_no     VARCHAR(50) NOT NULL COMMENT '관리자번호', -- 관리자번호
  ask_board_no INTEGER     NOT NULL COMMENT '문의게시판번호' -- 문의게시판번호
)
COMMENT '답변';

-- 답변
ALTER TABLE ask_board_reply
  ADD CONSTRAINT PK_ask_board_reply -- 답변 기본키
    PRIMARY KEY (
      reply_no -- 답변번호
    );

ALTER TABLE ask_board_reply
  MODIFY COLUMN reply_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '답변번호';

-- 문의게시판
CREATE TABLE ask_board (
  ask_board_no  INTEGER      NOT NULL COMMENT '문의게시판번호', -- 문의게시판번호
  member_no     INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
  title         VARCHAR(255) NOT NULL COMMENT '제목', -- 제목
  content       VARCHAR(255) NOT NULL COMMENT '내용', -- 내용
  view_cnt      INTEGER      NOT NULL DEFAULT 0 COMMENT '조회수', -- 조회수
  create_dt     DATETIME     NOT NULL DEFAULT now() COMMENT '등록일', -- 등록일
  use_secret    INTEGER      NOT NULL COMMENT '비밀글', -- 비밀글
  reply_title   VARCHAR(255) NULL     COMMENT '답변제목', -- 답변제목
  reply_content VARCHAR(255) NULL     COMMENT '답변내용', -- 답변내용
  reply_dt      DATETIME     NULL     DEFAULT now() COMMENT '답변일' -- 답변일
)
COMMENT '문의게시판';

-- 문의게시판
ALTER TABLE ask_board
  ADD CONSTRAINT PK_ask_board -- 문의게시판 기본키
    PRIMARY KEY (
      ask_board_no -- 문의게시판번호
    );

-- 문의게시판 인덱스
CREATE INDEX IX_ask_board
  ON ask_board( -- 문의게시판
    title ASC -- 제목
  );

ALTER TABLE ask_board
  MODIFY COLUMN ask_board_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '문의게시판번호';

-- 관리자
CREATE TABLE admin (
  admin_no VARCHAR(50)  NOT NULL COMMENT '관리자번호', -- 관리자번호
  email    VARCHAR(40)  NOT NULL COMMENT '이메일', -- 이메일
  password VARCHAR(100) NOT NULL COMMENT '비밀번호', -- 비밀번호
  nickname VARCHAR(50)  NOT NULL COMMENT '닉네임' -- 닉네임
)
COMMENT '관리자';

-- 관리자
ALTER TABLE admin
  ADD CONSTRAINT PK_admin -- 관리자 기본키
    PRIMARY KEY (
      admin_no -- 관리자번호
    );

-- 관리자 유니크 인덱스
CREATE UNIQUE INDEX UIX_admin
  ON admin ( -- 관리자
    email ASC -- 이메일
  );

ALTER TABLE admin
  MODIFY COLUMN admin_no VARCHAR(50) NOT NULL AUTO_INCREMENT COMMENT '관리자번호';

-- 회원
CREATE TABLE member (
  member_no  INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
  name       VARCHAR(50)  NOT NULL COMMENT '이름', -- 이름
  nickname   VARCHAR(50)  NOT NULL COMMENT '닉네임', -- 닉네임
  email      VARCHAR(40)  NOT NULL COMMENT '이메일', -- 이메일
  password   VARCHAR(100) NOT NULL COMMENT '비밀번호', -- 비밀번호
  tel        VARCHAR(30)  NOT NULL COMMENT '전화', -- 전화
  photo      VARCHAR(255) NULL     COMMENT '사진', -- 사진
  created_dt DATE         NOT NULL DEFAULT curdate() COMMENT '가입일', -- 가입일
  status     INTEGER      NOT NULL COMMENT '상태', -- 상태
  active     INTEGER      NULL     DEFAULT 1 COMMENT '탈퇴' -- 탈퇴
)
COMMENT '회원';

-- 회원
ALTER TABLE member
  ADD CONSTRAINT PK_member -- 회원 기본키
    PRIMARY KEY (
      member_no -- 회원번호
    );

-- 회원 유니크 인덱스
CREATE UNIQUE INDEX UIX_member
  ON member ( -- 회원
    email ASC,    -- 이메일
    nickname ASC  -- 닉네임
  );

ALTER TABLE member
  MODIFY COLUMN member_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원번호';

-- 스터디카페룸사진
ALTER TABLE studycafe_room_photo
  ADD CONSTRAINT FK_studycafe_room_TO_studycafe_room_photo -- 스터디카페룸 -> 스터디카페룸사진
    FOREIGN KEY (
      studyroom_no -- 스터디룸번호
    )
    REFERENCES studycafe_room ( -- 스터디카페룸
      studyroom_no -- 스터디룸번호
    );

-- 스터디카페룸
ALTER TABLE studycafe_room
  ADD CONSTRAINT FK_studycafe_TO_studycafe_room -- 스터디카페 -> 스터디카페룸
    FOREIGN KEY (
      cafe_no -- 스터디카페번호
    )
    REFERENCES studycafe ( -- 스터디카페
      cafe_no -- 스터디카페번호
    );

-- 리뷰
ALTER TABLE studycafe_review
  ADD CONSTRAINT FK_studycafe_reservation_TO_studycafe_review -- 스터디카페예약 -> 리뷰
    FOREIGN KEY (
      studycafe_rsv_no -- 스터디카페예약번호
    )
    REFERENCES studycafe_reservation ( -- 스터디카페예약
      studycafe_rsv_no -- 스터디카페예약번호
    );

-- 스터디카페예약
ALTER TABLE studycafe_reservation
  ADD CONSTRAINT FK_studycafe_reservation_status_TO_studycafe_reservation -- 스터디카페예약상태 -> 스터디카페예약
    FOREIGN KEY (
      rsv_status_no -- 예약상태번호
    )
    REFERENCES studycafe_reservation_status ( -- 스터디카페예약상태
      rsv_status_no -- 예약상태번호
    );

-- 스터디카페예약
ALTER TABLE studycafe_reservation
  ADD CONSTRAINT FK_studycafe_room_TO_studycafe_reservation -- 스터디카페룸 -> 스터디카페예약
    FOREIGN KEY (
      studyroom_no -- 스터디룸번호
    )
    REFERENCES studycafe_room ( -- 스터디카페룸
      studyroom_no -- 스터디룸번호
    );

-- 스터디카페예약
ALTER TABLE studycafe_reservation
  ADD CONSTRAINT FK_per_member_TO_studycafe_reservation -- 개인 -> 스터디카페예약
    FOREIGN KEY (
      per_member_no -- 개인번호
    )
    REFERENCES per_member ( -- 개인
      per_member_no -- 개인번호
    );

-- 스터디카페사진
ALTER TABLE studycafe_photo
  ADD CONSTRAINT FK_studycafe_TO_studycafe_photo -- 스터디카페 -> 스터디카페사진
    FOREIGN KEY (
      cafe_no -- 스터디카페번호
    )
    REFERENCES studycafe ( -- 스터디카페
      cafe_no -- 스터디카페번호
    );

-- 결제
ALTER TABLE studycafe_payment
  ADD CONSTRAINT FK_studycafe_payment_type_TO_studycafe_payment -- 결제유형 -> 결제
    FOREIGN KEY (
      payment_type_no -- 결제유형번호
    )
    REFERENCES studycafe_payment_type ( -- 결제유형
      payment_type_no -- 결제유형번호
    );

-- 결제
ALTER TABLE studycafe_payment
  ADD CONSTRAINT FK_studycafe_payment_status_TO_studycafe_payment -- 결제현황 -> 결제
    FOREIGN KEY (
      payment_status_no -- 결제현황
    )
    REFERENCES studycafe_payment_status ( -- 결제현황
      payment_status_no -- 결제현황
    );

-- 결제
ALTER TABLE studycafe_payment
  ADD CONSTRAINT FK_studycafe_reservation_TO_studycafe_payment -- 스터디카페예약 -> 결제
    FOREIGN KEY (
      studycafe_rsv_no -- 스터디카페예약번호
    )
    REFERENCES studycafe_reservation ( -- 스터디카페예약
      studycafe_rsv_no -- 스터디카페예약번호
    );

-- 카페휴무일
ALTER TABLE studycafe_holiday
  ADD CONSTRAINT FK_studycafe_TO_studycafe_holiday -- 스터디카페 -> 카페휴무일
    FOREIGN KEY (
      cafe_no -- 스터디카페번호
    )
    REFERENCES studycafe ( -- 스터디카페
      cafe_no -- 스터디카페번호
    );

-- 스터디카페
ALTER TABLE studycafe
  ADD CONSTRAINT FK_studycafe_operating_status_TO_studycafe -- 카페운영상태 -> 스터디카페
    FOREIGN KEY (
      operating_status_no -- 운영상태번호
    )
    REFERENCES studycafe_operating_status ( -- 카페운영상태
      operating_status_no -- 운영상태번호
    );

-- 스터디카페
ALTER TABLE studycafe
  ADD CONSTRAINT FK_ceo_member_TO_studycafe -- 사장 -> 스터디카페
    FOREIGN KEY (
      ceo_member_no -- 사장번호
    )
    REFERENCES ceo_member ( -- 사장
      ceo_member_no -- 사장번호
    );

-- 투두리스트
ALTER TABLE study_todolist
  ADD CONSTRAINT FK_study_todolsit_progress_TO_study_todolist -- 투두진행상태 -> 투두리스트
    FOREIGN KEY (
      progress_no -- 진행상태번호
    )
    REFERENCES study_todolsit_progress ( -- 투두진행상태
      progress_no -- 진행상태번호
    );

-- 투두리스트
ALTER TABLE study_todolist
  ADD CONSTRAINT FK_study_guilder_TO_study_todolist -- 스터디구성원 -> 투두리스트
    FOREIGN KEY (
      study_no,      -- 스터디번호
      per_member_no  -- 개인번호
    )
    REFERENCES study_guilder ( -- 스터디구성원
      study_no,      -- 스터디번호
      per_member_no  -- 개인번호
    );

-- 한스터디여러지역
ALTER TABLE study_multiple_location
  ADD CONSTRAINT FK_study_location_TO_study_multiple_location -- 스터디지역 -> 한스터디여러지역
    FOREIGN KEY (
      location_no -- 지역번호
    )
    REFERENCES study_location ( -- 스터디지역
      location_no -- 지역번호
    );

-- 한스터디여러지역
ALTER TABLE study_multiple_location
  ADD CONSTRAINT FK_study_TO_study_multiple_location -- 스터디 -> 한스터디여러지역
    FOREIGN KEY (
      study_no -- 스터디번호
    )
    REFERENCES study ( -- 스터디
      study_no -- 스터디번호
    );

-- 스터디구성원
ALTER TABLE study_guilder
  ADD CONSTRAINT FK_TABLE4_TO_TABLE10 -- 스터디 -> 스터디멤버
    FOREIGN KEY (
    )
    REFERENCES study ( -- 스터디
      study_no -- 스터디번호
    );

-- 스터디구성원
ALTER TABLE study_guilder
  ADD CONSTRAINT FK_per_member_TO_study_guilder -- 개인 -> 스터디구성원
    FOREIGN KEY (
      per_member_no -- 개인번호
    )
    REFERENCES per_member ( -- 개인
      per_member_no -- 개인번호
    );

-- 스터디구성원
ALTER TABLE study_guilder
  ADD CONSTRAINT FK_study_TO_study_guilder -- 스터디 -> 스터디구성원
    FOREIGN KEY (
      study_no -- 스터디번호
    )
    REFERENCES study ( -- 스터디
      study_no -- 스터디번호
    );

-- 캘린더
ALTER TABLE study_calender
  ADD CONSTRAINT FK_study_calender_importance_TO_study_calender -- 캘린더중요도 -> 캘린더
    FOREIGN KEY (
      importance_no -- 중요도상태번호
    )
    REFERENCES study_calender_importance ( -- 캘린더중요도
      importance_no -- 중요도상태번호
    );

-- 캘린더
ALTER TABLE study_calender
  ADD CONSTRAINT FK_study_guilder_TO_study_calender -- 스터디구성원 -> 캘린더
    FOREIGN KEY (
      study_no,      -- 스터디번호
      per_member_no  -- 개인번호
    )
    REFERENCES study_guilder ( -- 스터디구성원
      study_no,      -- 스터디번호
      per_member_no  -- 개인번호
    );

-- 스터디게시판첨부파일
ALTER TABLE study_board_file
  ADD CONSTRAINT FK_study_board_TO_study_board_file -- 스터디게시판 -> 스터디게시판첨부파일
    FOREIGN KEY (
      st_board_no -- 스터디자유게시판번호
    )
    REFERENCES study_board ( -- 스터디게시판
      st_board_no -- 스터디자유게시판번호
    );

-- 댓글
ALTER TABLE study_board_comment
  ADD CONSTRAINT FK_study_board_TO_study_board_comment -- 스터디게시판 -> 댓글
    FOREIGN KEY (
      st_board_no -- 스터디자유게시판번호
    )
    REFERENCES study_board ( -- 스터디게시판
      st_board_no -- 스터디자유게시판번호
    );

-- 댓글
ALTER TABLE study_board_comment
  ADD CONSTRAINT FK_study_guilder_TO_study_board_comment -- 스터디구성원 -> 댓글
    FOREIGN KEY (
      study_no,      -- 스터디번호
      per_member_no  -- 개인번호
    )
    REFERENCES study_guilder ( -- 스터디구성원
      study_no,      -- 스터디번호
      per_member_no  -- 개인번호
    );

-- 스터디게시판
ALTER TABLE study_board
  ADD CONSTRAINT FK_study_guilder_TO_study_board -- 스터디구성원 -> 스터디게시판
    FOREIGN KEY (
      study_no,      -- 스터디번호
      per_member_no  -- 개인번호
    )
    REFERENCES study_guilder ( -- 스터디구성원
      study_no,      -- 스터디번호
      per_member_no  -- 개인번호
    );

-- 스터디
ALTER TABLE study
  ADD CONSTRAINT FK_study_subject_TO_study -- 스터디분야 -> 스터디
    FOREIGN KEY (
      subject_no -- 스터디카테고리번호
    )
    REFERENCES study_subject ( -- 스터디분야
      subject_no -- 스터디카테고리번호
    );

-- 스터디
ALTER TABLE study
  ADD CONSTRAINT FK_study_face_status_TO_study -- 스터디대면상태 -> 스터디
    FOREIGN KEY (
      face_no -- 대면상태번호
    )
    REFERENCES study_face_status ( -- 스터디대면상태
      face_no -- 대면상태번호
    );

-- 스터디
ALTER TABLE study
  ADD CONSTRAINT FK_per_member_TO_study -- 개인 -> 스터디
    FOREIGN KEY (
      per_member_no -- 개인번호
    )
    REFERENCES per_member ( -- 개인
      per_member_no -- 개인번호
    );

-- 개인
ALTER TABLE per_member
  ADD CONSTRAINT FK_member_TO_per_member -- 회원 -> 개인
    FOREIGN KEY (
      member_no -- 회원번호
    )
    REFERENCES member ( -- 회원
      member_no -- 회원번호
    );

-- 첨부파일
ALTER TABLE notice_file
  ADD CONSTRAINT FK_notice_TO_notice_file -- 공지사항 -> 첨부파일
    FOREIGN KEY (
      notice_no -- 공지사항번호
    )
    REFERENCES notice ( -- 공지사항
      notice_no -- 공지사항번호
    );

-- 사장
ALTER TABLE ceo_member
  ADD CONSTRAINT FK_member_TO_ceo_member -- 회원 -> 사장
    FOREIGN KEY (
      member_no -- 회원번호
    )
    REFERENCES member ( -- 회원
      member_no -- 회원번호
    );

-- 스터디북마크
ALTER TABLE study_bookmark
  ADD CONSTRAINT FK_study_TO_study_bookmark -- 스터디 -> 스터디북마크
    FOREIGN KEY (
      study_no -- 스터디번호
    )
    REFERENCES study ( -- 스터디
      study_no -- 스터디번호
    );

-- 스터디북마크
ALTER TABLE study_bookmark
  ADD CONSTRAINT FK_per_member_TO_study_bookmark -- 개인 -> 스터디북마크
    FOREIGN KEY (
      per_member_no -- 개인번호
    )
    REFERENCES per_member ( -- 개인
      per_member_no -- 개인번호
    );

-- 좋아요
ALTER TABLE study_board_like
  ADD CONSTRAINT FK_study_guilder_TO_study_board_like -- 스터디구성원 -> 좋아요
    FOREIGN KEY (
      study_no,      -- 스터디번호
      per_member_no  -- 개인번호
    )
    REFERENCES study_guilder ( -- 스터디구성원
      study_no,      -- 스터디번호
      per_member_no  -- 개인번호
    );

-- 좋아요
ALTER TABLE study_board_like
  ADD CONSTRAINT FK_study_board_TO_study_board_like -- 스터디게시판 -> 좋아요
    FOREIGN KEY (
      st_board_no -- 스터디자유게시판번호
    )
    REFERENCES study_board ( -- 스터디게시판
      st_board_no -- 스터디자유게시판번호
    );

-- 답변
ALTER TABLE ask_board_reply
  ADD CONSTRAINT FK_ask_board_TO_ask_board_reply -- 문의게시판 -> 답변
    FOREIGN KEY (
      ask_board_no -- 문의게시판번호
    )
    REFERENCES ask_board ( -- 문의게시판
      ask_board_no -- 문의게시판번호
    );

-- 답변
ALTER TABLE ask_board_reply
  ADD CONSTRAINT FK_admin_TO_ask_board_reply -- 관리자 -> 답변
    FOREIGN KEY (
      admin_no -- 관리자번호
    )
    REFERENCES admin ( -- 관리자
      admin_no -- 관리자번호
    );

-- 문의게시판
ALTER TABLE ask_board
  ADD CONSTRAINT FK_member_TO_ask_board -- 회원 -> 문의게시판
    FOREIGN KEY (
      member_no -- 회원번호
    )
    REFERENCES member ( -- 회원
      member_no -- 회원번호
    );