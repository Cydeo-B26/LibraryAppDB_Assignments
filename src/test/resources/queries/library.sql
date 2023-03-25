
-- US 01
select count(id) from users;

select count(distinct id) from users;

select * from users;

-- US 02
select count(*) from book_borrow
where is_returned=0;


select book_id,name from book_borrow bb
    inner join books b on bb.book_id = b.id
where book_id=847;


-- US 03
select name from book_categories;

-- US 04
select name,author,isbn,description,year from books
where name ='Head First Java';


-- US 05
select bc.name,count(*) from book_borrow bb
        inner join books b on bb.book_id = b.id
        inner join book_categories bc on b.book_category_id = bc.id
group by bc.name
order by 2 desc;


-- US 06
select id,name,author from books
where ISBN = '09112021' and author='Robert C.Martin' order by id desc;

-- US 07
select full_name,b.name,bb.borrowed_date from users u
    inner join book_borrow bb on u.id = bb.user_id
    inner join books b on bb.book_id = b.id
where full_name='Test Student 10'
order by 3 desc;
