CREATE  VIEW `fetch_view_user_book` AS select `userinfo`.`id` AS `id`,`userinfo`.`user_name` AS `user_name`,`userinfo`.`email` AS `email`,`book`.`author` AS `author`,`book`.`book_name` AS `book_name`,`book`.`description` AS `description`,`book`.`no_of_sets` AS `no_of_sets`,`studentbook`.`submission_date` AS `submission_date`,`studentbook`.`status` AS `status` from ((`lm_user` `userinfo` left join `lm_student_book` `studentbook` on((`userinfo`.`id` = `studentbook`.`user_id`))) left join `lm_book` `book` on((`studentbook`.`book_id` = `book`.`id`)))


-- get all user books

 CREATE VIEW fetch_view_user_book AS
SELECT 
    userInfo.id AS id,
    userInfo.user_name,
    userInfo.email, 
    book.id AS bookId,
    book.author,
    book.book_name,
    book.description,
    book.no_of_sets,
    studentBook.submission_date,
    studentBook.status
FROM lm_user AS userInfo
LEFT JOIN lm_student_book AS studentBook
    ON userInfo.id = studentBook.user_id
LEFT JOIN lm_book AS book
    ON studentBook.book_id = book.id;


--- bookDetails and all the users list who have accessed the book

CREATE VIEW fetch_book_with_users AS
SELECT 
    b.id AS bookId,
    b.book_name,
    b.author,
    b.description,
    JSON_ARRAYAGG(
        JSON_OBJECT(
            'userId', u.id,
            'userName', u.user_name,
            'phone', u.phone,
            'email', u.email,
            'rollNo', u.roll_no,
            'status', sb.status
        )
    ) AS userList
FROM 
    lm_book b
LEFT JOIN 
    lm_student_book sb ON b.id = sb.book_id
LEFT JOIN 
    lm_user u ON sb.user_id = u.id
GROUP BY 
    b.id, b.book_name, b.author, b.description;



    SELECT * FROM lm_book where 
case when searchKey  is null then 1
when searchKey != null and lower(trim(author)) like '%' || searchKey || '%' or
 searchKey != null and lower(trim(book_name)) like '%' || searchKey || '%'
then 1 end=1;

