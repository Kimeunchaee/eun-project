-- 관리자 test 값 
insert into admin(admin_no, email, password, nickname) 
values(1, 'ogong@test.com', password('1234'), '오늘의공부');

-- 회원 test 값
insert into member(member_no, name, nickname, email, password, tel, photo, created_dt, status) 
values(1, '김초보', '초보초보쌩초보', 'naver@test.com', password('n1'), '010-1111-1111',
'naver.gif', '2020-01-01', 1);

insert into member(member_no, name, nickname, email, password, tel, photo, created_dt, status) 
values(2, '박미술', '미술부장', 'gmail@test.com', password('g1'), '010-2222-2222',
'gmail.gif', '2021-03-15', 1);

insert into member(member_no, name, nickname, email, password, tel, photo, created_dt, status) 
values(3, '이코딩', '코딩부장', 'kakao@test.com', password('k1'), '010-3333-3333',
'kakao.gif','2021-10-16', 1);

insert into member(member_no, name, nickname, email, password, tel, photo, status) 
values(4, '우음악', '음악대장', 'daum@test.com', password('d1'), '010-4444-4444', 
'daum.gif', 1);

insert into member(member_no, name, nickname, email, password, tel, photo, status) 
values(5, '조고수', '고수고수왕고수', 'hanmail@test.com', password('h1'), '010-5555-5555',
'hanmail.gif', 1);

insert into member(member_no, name, nickname, email, password, tel, photo, status) 
values(6, '송중수', '중수중수중중수', 'todaystudymail@gmail.com', password('1111'), '010-6666-6666',
'gmail.jpg', 1);

-------------------------------------------------------------------------------------------------
insert into member(member_no, name, nickname, email, password, tel, photo, created_dt, status) 
values(7, '김사장', '김사장카페', 'cafe1@test.com', password('c1'), '010-1111-0000',
'cafe1.gif', '2020-02-02', 2);

insert into member(member_no, name, nickname, email, password, tel, photo, created_dt, status) 
values(8, '송사장', '송사장카페', 'cafe2@test.com', password('c2'), '010-2222-0000',
'cafe2.gif', '2021-04-15', 2);

insert into member(member_no, name, nickname, email, password, tel, photo, created_dt, status) 
values(9, '우사장', '우사장카페', 'cafe3@test.com', password('c3'), '010-3333-0000',
'cafe3.gif','2021-09-16', 2);

insert into member(member_no, name, nickname, email, password, tel, photo, status) 
values(10, '조사장', '조사장카페', 'cafe4@test.com', password('c4'), '010-4444-0000', 
'cafe4.gif', 2);

-- 개인회원 test 값
insert into per_member(per_member_no, member_no) values(1,1);
insert into per_member(per_member_no, member_no) values(2,2);
insert into per_member(per_member_no, member_no) values(3,3);
insert into per_member(per_member_no, member_no) values(4,4);
insert into per_member(per_member_no, member_no) values(5,5);
insert into per_member(per_member_no, member_no) values(6,6);

-- 사장회원 test 값
insert into ceo_member(ceo_member_no, member_no, bossname, license_no) 
values(1, 7, '김사장', '123-45-12345');

insert into ceo_member(ceo_member_no, member_no, bossname, license_no) 
values(2, 8, '송사장', '321-45-98745');

insert into ceo_member(ceo_member_no, member_no, bossname, license_no) 
values(3, 9, '우사장', '546-85-14545');

insert into ceo_member(ceo_member_no, member_no, bossname, license_no) 
values(4, 10, '조사장', '333-28-15464');









