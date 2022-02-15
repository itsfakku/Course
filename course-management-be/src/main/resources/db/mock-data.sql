-- role data
insert into role (id, role)
values (1, 'admin'),
       (2, 'staff'),
       (3, 'teacher'),
       (4, 'student');
-- notification data
insert into notification (id, created_date, noti_content, status, url)
values (1, '2021-09-21', 'notification', 1, 'url'),
       (2, '2021-09-21', 'notification', 1, 'url'),
       (3, '2021-09-21', 'notification', 0, 'url'),
       (4, '2021-09-21', 'notification', 1, 'url'),
       (5, '2021-09-21', 'notification', 1, 'url'),
       (6, '2021-09-21', 'notification', 1, 'url'),
       (7, '2021-09-21', 'notification', 1, 'url'),
       (8, '2021-09-21', 'notification', 1, 'url'),
       (9, '2021-09-21', 'notification', 1, 'url'),
       (10, '2021-09-21', 'notification', 0, 'url');

-- conversation data
insert into conversation (id, started_date, title)
values (1, '2021-09-21', 'chao'),
       (2, '2021-09-21', 'hello'),
       (3, '2021-09-21', 'dear');

-- user data
insert into user (id, address, avatar, dob, email, first_name, last_name, password, phone, status, username, role,
                  created_date)
values (1, 'Ha Noi', 'ava.jpg', '1998-01-20', 'a@gmail.com', 'Duong', 'Van Dat',
        '$2a$12$QYZCmM8w0rvPwNXvRBnV5ed.ClqIrBHKWxKizs4k/IWNFB7yIiW86', 113, 1, 'stu1', 4, '2021-09-21'),
       (2, 'Ha Nam', 'ava.jpg', '1997-01-20', 'b@gmail.com', 'Hoang', 'Viet Ha',
        '$2a$12$QYZCmM8w0rvPwNXvRBnV5ed.ClqIrBHKWxKizs4k/IWNFB7yIiW86', 113, 1, 'stu2', 4, '2021-09-21'),
       (3, 'Ha Noi', 'ava.jpg', '1998-01-20', 'c@gmail.com', 'Ngo', 'Nhung',
        '$2a$12$QYZCmM8w0rvPwNXvRBnV5ed.ClqIrBHKWxKizs4k/IWNFB7yIiW86', 113, 1, 'stu3', 4, '2021-09-21'),
       (4, 'Dien Bien', 'ava.jpg', '2000-01-20', 'd@gmail.com', 'Nguyen', 'Linh', 'teacher', 113, 1, 'teacher1', 3,
        '2021-09-21'),
       (5, 'Thanh Hoa', 'ava.jpg', '1990-01-20', 'e@gmail.com', 'Cu', 'Dong', 'teacher', 113, 1, 'teacher2', 3,
        '2021-09-21'),
       (6, 'Nghe An', 'ava.jpg', '1996-01-20', 'f@gmail.com', 'Hoang', 'Hieu', 'teacher', 113, 1, 'teacher3', 3,
        '2021-09-21'),
       (7, 'Ha Noi', 'ava.jpg', '1998-01-20', 'g@gmail.com', 'Thu', 'Nga', 'staff', 113, 1, 'staff1', 2, '2021-09-21'),
       (8, 'Ha Noi', 'ava.jpg', '1998-01-20', 'h@gmail.com', 'Hoang', 'Viet', 'staff', 113, 1, 'staff2', 2,
        '2021-09-21'),
       (9, 'Vinh Phuc', 'ava.jpg', '1998-01-20', 'i@gmail.com', 'Tran', 'Hieu', 'staff', 113, 1, 'staff3', 2,
        '2021-09-21'),
       (10, 'Ha Noi', 'ava.jpg', '2001-01-20', 'j@gmail.com', 'Van', 'Son',
        '$2a$12$9UjiB4pFYYmU1Z3PYl30G.zgOoi6B/tdbwmyPXcOn65OtX6cO8EJ.', 113, 1, 'admin', 1, '2021-09-21'),
       (11, 'Ha Noi', 'ava.jpg', '1996-01-20', 'k@gmail.com', 'Thu', 'Huyen', 'admin', 113, 1, 'admin2', 1,
        '2021-09-21'),
       (12, 'Ha Noi', 'ava.jpg', '1995-01-20', 'm@gmail.com', 'Phan', 'Dinh', 'admin', 113, 1, 'admin3', 1,
        '2021-09-21');

-- user_notification data
insert into user_notification (user_id, notification_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 4),
       (2, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (1, 9),
       (12, 10);

-- participant data
insert into participant (user_id, conversation_id)
values (1, 1),
       (1, 2),
       (3, 1),
       (4, 2),
       (5, 3),
       (6, 3);

-- message data
insert into message (id, content, date, sender_id, conversation_id)
values (1, 'a', '2021-09-21', 1, 1),
       (2, 'b', '2021-09-21', 1, 2),
       (3, 'c', '2021-09-21', 3, 1),
       (4, 'd', '2021-09-21', 4, 2),
       (5, 'e', '2021-09-21', 5, 3),
       (6, 'f', '2021-09-21', 6, 3);

-- topic data
insert into topic (id, description, name, created_date)
values (1, 'cong nghe', 'technology', '2021-09-21'),
       (2, 'kinh te', 'economic', '2021-09-21');

-- user-topic data
insert into user_topic (user_id, topic_id)
values (4, 1),
       (4, 2),
       (5, 1),
       (5, 2),
       (6, 1);

-- category data
insert into category (id, description, name)
values (1, 'cntt', 'IT'),
       (2, 'thiet ke', 'designer'),
       (3, 'kinh doanh', 'business'),
       (4, 'ke toan', 'accountant');

-- course data
insert into course (id, created_date, description, enroll_code, name, start_date, end_date, thumbnail, category_id,
                    topic_id, teacher_id)
values (1, '2021-11-20', 'a', 30, 'abcxyz', '2021-11-20', '2021-11-29', 'thumnail.jpg', 1, 1, 4),
       (2, '2021-11-20', 'a', 30, 'abcxyz', '2021-11-20', '2021-11-29', 'thumnail.jpg', 2, 1, 4),
       (3, '2021-11-20', 'a', 30, 'abcxyz', '2021-11-20', '2021-11-29', 'thumnail.jpg', 3, 2, 4),
       (4, '2021-11-20', 'a', 30, 'abcxyz', '2021-11-20', '2021-11-29', 'thumnail.jpg', 4, 2, 4),
       (5, '2021-11-20', 'a', 30, 'abcxyz', '2021-11-20', '2021-11-29', 'thumnail.jpg', 3, 2, 4);


-- assignment data
insert into assignment (id, assignment_name, due_date, feedback_content, file_format, file_quantity, course,
                        created_date, modified_date)
values (1, 'Assignment 1', '2021-11-28', 'feedback', 'pdf', '1', 1, '2021-11-20', '2021-11-20'),
       (2, 'Assignment 2', '2021-11-28', 'feedback', 'pdf', '1', 1, '2021-11-20', '2021-11-20'),
       (3, 'Assignment 1', '2021-11-28', 'feedback', 'pdf', '1', 2, '2021-11-20', '2021-11-20'),
       (4, 'Assignment 2', '2021-11-28', 'feedback', 'pdf', '1', 2, '2021-11-20', '2021-11-20'),
       (5, 'Assignment 1', '2021-11-28', 'feedback', 'pdf', '1', 3, '2021-11-20', '2021-11-20'),
       (6, 'Assignment 2', '2021-11-28', 'feedback', 'pdf', '1', 3, '2021-11-20', '2021-11-20'),
       (7, 'Assignment 1', '2021-11-28', 'feedback', 'pdf', '1', 4, '2021-11-20', '2021-11-20'),
       (8, 'Assignment 2', '2021-11-28', 'feedback', 'pdf', '1', 4, '2021-11-20', '2021-11-20');

-- assignment submission data
insert into asm_submission (file_url, submit_date, submit_status, user_id, assignment_id, grade, grade_date,
                            grade_status)
values ('url', '2021-11-20', 1, 1, 1, 40, '2021-11-22', 1),
       ('url', '2021-11-20', 1, 2, 2, 40, '2021-11-22', 1),
       ('url', '2021-11-20', 1, 1, 3, 40, '2021-11-22', 1),
       ('url', '2021-11-20', 1, 2, 4, 40, '2021-11-22', 1),
       ('url', '2021-11-20', 1, 3, 5, 40, '2021-11-22', 1),
       ('url', '2021-11-20', 1, 3, 6, 40, '2021-11-22', 1);

-- assignment comment data
insert into asm_comment (id, comment_content, comment_date, user_id, asm_id)
values (1, 'cmt', '2021-11-20', 1, 1),
       (2, 'cmt', '2021-11-20', 1, 3),
       (3, 'cmt', '2021-11-20', 2, 2);

-- course comment data
insert into course_comment (id, comment_content, comment_date, parent_id, user_id, course_id)
values (1, 'cmt', '2021-11-20', 1, 1, 1),
       (2, 'cmt', '2021-11-20', 2, 1, 2),
       (3, 'cmt', '2021-11-20', 3, 2, 1);

