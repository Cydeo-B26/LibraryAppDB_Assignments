select count(id) from users;

select count(distinct id) from users;

select * from users;


select book_id,name from book_borrow bb
    inner join books b on bb.book_id = b.id
where book_id=847;

select count(*) from book_borrow
where is_returned=0;


