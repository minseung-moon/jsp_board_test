https://codevang.tistory.com/204?category=844273
abc 1234
-- 사용자 계정을 저장할 테이블
create table ORG_USER (
    USER_ID varchar(20) primary key,
    USER_PW varchar(20) not null,
    JOIN_DATE date default sysdate
);

-- 사용자 정보를 저장할 테이블
create table ORG_USER_INFO (
    USER_ID varchar(20) primary key,
    NAME varchar(20),
    PHONE varchar(15),
    EMAIL varchar(30),   
    gender varchar(10)
);

-- 테이블 내 모든 데이터 검색
select * from ORG_USER;

-- 테이블 내 특정 조건으로 전체 행 검색
select * from ORG_USER where user_id = 'codevang2';

-- 테이블 내 특정 컬럼만 검색
select USER_ID from ORG_USER;

-- 테이블 내 특정 컬럼을 특정 조건으로 검색
select USER_ID from ORG_USER where user_pw = '12345';

-- 특정 조건에서 포함되는 문자열이 있는지 찾고 싶을 때
select * from ORG_USER where user_id like '%2'; -- 2로 끝나는 값
select * from ORG_USER where user_id like 'c%'; -- c로 시작하는 값
select * from ORG_USER where user_id like '%devan%'; -- devan이 포함되는 값

-- 데이터 삽입 (insert)
insert into ORG_USER (USER_ID, USER_PW) values ('codevang1', '12345');
insert into ORG_USER (USER_ID, USER_PW) values ('codevang2', '12345');
commit;

-- ORG_USER의 모든 값 조회
select * from ORG_USER

-- 데이터 수정 (update)
-- where 조건 없으면 user_id의 모든 값을 바꿔버림
update ORG_USER set USER_ID = 'codevang12345' where user_id = 'codevang1';
commit;
select * from ORG_USER;

-- 데이터 삭제  (delete)
-- where 조건 없으면 모든 데이터가 삭제됨
delete ORG_USER where USER_ID = 'codevang12345';
commit;
select * from ORG_USER;

CREATE VIEW VIEW_USER_INFO AS

-- 기준테이블(a)와 조인시킬 테이블(b)에서 가져올 컬럼 지정
select a.user_id, a.user_pw, b.phone, b.email, b.address from org_user a
    left outer join org_user_info b  -- 조인시킬 테이블
        on a.user_id = b.user_id;     -- 조인 조건

select a.user_id, a.user_pw, b.phone, b.email, b.address from org_user a left outer join org_user_info b on a.user_id = b.user_id;
    -- 조인된 완성본에서 다시 조회 조건을 부여할 때 조인 후 조건문 사용
    -- where b.phone is not null;
    

create table board(
	num number not null
);