https://codevang.tistory.com/204?category=844273
abc 1234
-- ����� ������ ������ ���̺�
create table ORG_USER (
    USER_ID varchar(20) primary key,
    USER_PW varchar(20) not null,
    JOIN_DATE date default sysdate
);

-- ����� ������ ������ ���̺�
create table ORG_USER_INFO (
    USER_ID varchar(20) primary key,
    NAME varchar(20),
    PHONE varchar(15),
    EMAIL varchar(30),   
    gender varchar(10)
);

-- ���̺� �� ��� ������ �˻�
select * from ORG_USER;

-- ���̺� �� Ư�� �������� ��ü �� �˻�
select * from ORG_USER where user_id = 'codevang2';

-- ���̺� �� Ư�� �÷��� �˻�
select USER_ID from ORG_USER;

-- ���̺� �� Ư�� �÷��� Ư�� �������� �˻�
select USER_ID from ORG_USER where user_pw = '12345';

-- Ư�� ���ǿ��� ���ԵǴ� ���ڿ��� �ִ��� ã�� ���� ��
select * from ORG_USER where user_id like '%2'; -- 2�� ������ ��
select * from ORG_USER where user_id like 'c%'; -- c�� �����ϴ� ��
select * from ORG_USER where user_id like '%devan%'; -- devan�� ���ԵǴ� ��

-- ������ ���� (insert)
insert into ORG_USER (USER_ID, USER_PW) values ('codevang1', '12345');
insert into ORG_USER (USER_ID, USER_PW) values ('codevang2', '12345');
commit;

-- ORG_USER�� ��� �� ��ȸ
select * from ORG_USER

-- ������ ���� (update)
-- where ���� ������ user_id�� ��� ���� �ٲ����
update ORG_USER set USER_ID = 'codevang12345' where user_id = 'codevang1';
commit;
select * from ORG_USER;

-- ������ ����  (delete)
-- where ���� ������ ��� �����Ͱ� ������
delete ORG_USER where USER_ID = 'codevang12345';
commit;
select * from ORG_USER;

CREATE VIEW VIEW_USER_INFO AS

-- �������̺�(a)�� ���ν�ų ���̺�(b)���� ������ �÷� ����
select a.user_id, a.user_pw, b.phone, b.email, b.address from org_user a
    left outer join org_user_info b  -- ���ν�ų ���̺�
        on a.user_id = b.user_id;     -- ���� ����

select a.user_id, a.user_pw, b.phone, b.email, b.address from org_user a left outer join org_user_info b on a.user_id = b.user_id;
    -- ���ε� �ϼ������� �ٽ� ��ȸ ������ �ο��� �� ���� �� ���ǹ� ���
    -- where b.phone is not null;
    

create table board(
	num number not null
);