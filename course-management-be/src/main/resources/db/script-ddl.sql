create table category
(
    id          bigint auto_increment
        primary key,
    description varchar(255) null,
    name        varchar(255) null
);

create table conversation
(
    id           bigint auto_increment
        primary key,
    started_date datetime(6)  null,
    title        varchar(255) null
);

create table notification
(
    id           bigint auto_increment
        primary key,
    created_date datetime(6)  null,
    noti_content varchar(255) not null,
    status       int          null,
    url          varchar(255) null
);

create table role
(
    id          bigint auto_increment
        primary key,
    description varchar(255) null,
    name        varchar(255) not null
);

create table topic
(
    id          bigint auto_increment
        primary key,
    description varchar(255) null,
    name        varchar(255) not null
);

create table course
(
    id           bigint auto_increment
        primary key,
    created_time datetime(6)  null,
    description  varchar(255) null,
    duration     int          null,
    enroll_code  varchar(255) not null,
    name         varchar(255) not null,
    schedule     datetime(6)  null,
    thumbnail    varchar(255) null,
    category_id  bigint       null,
    topic_id     bigint       null,
    constraint FKkyes7515s3ypoovxrput029bh
        foreign key (category_id) references category (id),
    constraint FKokaxyfpv8p583w8yspapfb2ar
        foreign key (topic_id) references topic (id)
);

create table assignment
(
    id               bigint auto_increment
        primary key,
    due_date         datetime(6)  not null,
    feedback_content varchar(255) null,
    file_format      varchar(255) null,
    file_quantity    varchar(255) null,
    grade            float        null,
    grade_date       datetime(6)  null,
    grade_status     int          null,
    submit_date      datetime(6)  null,
    course           bigint       null,
    constraint FKbftxots6q5bqktw7qwtf2x0nr
        foreign key (course) references course (id)
);

create table user
(
    id         bigint auto_increment
        primary key,
    address    varchar(255) null,
    avatar     varchar(255) null,
    dob        datetime(6)  null,
    email      varchar(255) not null,
    first_name varchar(255) null,
    last_name  varchar(255) null,
    password   varchar(60)  not null,
    phone      varchar(255) null,
    status     int          null,
    username   varchar(50)  not null,
    role       bigint       null,
    constraint FKl5alypubd40lwejc45vl35wjb
        foreign key (role) references role (id)
);

create table asm_comment
(
    id              bigint       not null,
    comment_content varchar(255) null,
    comment_date    datetime(6)  null,
    user_id         bigint auto_increment,
    asm_id          bigint       not null,
    primary key (user_id, asm_id),
    constraint FKkp8ey7e8dmlx4ut147kxsx6b6
        foreign key (asm_id) references assignment (id),
    constraint FKn945vkpul0rw5pb888yvi1xfm
        foreign key (user_id) references user(id)
);

create table asm_submission
(
    id            bigint       not null,
    file_url      varchar(255) null,
    submit_date   datetime(6)  null,
    submit_status int          null,
    user_id       bigint auto_increment,
    asm_id        bigint       not null,
    primary key (user_id, asm_id),
    constraint FKlllu6qh9hk7n5kxtmkbf6vtew
        foreign key (asm_id) references assignment (id),
    constraint FKod0g4csp7m89lnljsqup1b7ds
        foreign key (user_id) references user (id)
);

create table course_comment
(
    id              bigint       not null,
    comment_content varchar(255) null,
    comment_date    datetime(6)  null,
    parent_id       varchar(255) null,
    user_id         bigint auto_increment,
    course_id       bigint       not null,
    primary key (user_id, course_id),
    constraint FKi2x4qwjfqowv1gxbt2ybxo9il
        foreign key (course_id) references course (id),
    constraint FKj42et85edtuy1x9y60wk44ou8
        foreign key (user_id) references user (id)
);

create table message
(
    id              bigint       not null,
    content         varchar(255) null,
    date            datetime(6)  null,
    sender_id       bigint auto_increment,
    conversation_id bigint       not null,
    primary key (sender_id, conversation_id),
    constraint FK6yskk3hxw5sklwgi25y6d5u1l
        foreign key (conversation_id) references conversation (id),
    constraint FKcnj2qaf5yc36v2f90jw2ipl9b
        foreign key (sender_id) references user (id)
);

create table participant
(
    user_id         bigint not null,
    conversation_id bigint not null,
    primary key (user_id, conversation_id),
    constraint FKj2ywtc5meno2ouhf5pcq9rsbh
        foreign key (user_id) references user (id),
    constraint FKsiftd56p4vnlfthffmf07xhng
        foreign key (conversation_id) references conversation (id)
);

create table user_course
(
    id        bigint      not null,
    join_date datetime(6) null,
    user_id   bigint auto_increment,
    course_id bigint      not null,
    primary key (user_id, course_id),
    constraint FKka18m18kpimodvy8yg2icu083
        foreign key (course_id) references course (id),
    constraint FKpv8tt3252hb6kyej8p7e7pokl
        foreign key (user_id) references user (id)
);

create table user_notification
(
    user_id         bigint not null,
    notification_id bigint not null,
    primary key (user_id, notification_id),
    constraint FKi5naecliicmigrk01qx5me5sp
        foreign key (notification_id) references notification (id),
    constraint FKnbuq84cli119n9cdakdw0kv5v
        foreign key (user_id) references user (id)
);

create table user_topic
(
    user_id  bigint not null,
    topic_id bigint not null,
    primary key (user_id, topic_id),
    constraint FKdc8ayjtjsav1ahruawdrcsdr3
        foreign key (user_id) references user (id),
    constraint FKrbbt9w8w48w4pl7dv0lh3om0v
        foreign key (topic_id) references topic (id)
);

