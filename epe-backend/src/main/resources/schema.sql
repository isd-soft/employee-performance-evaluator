create table assessment_statuses
(
    id     varchar(255) not null
        constraint assessment_statuses_pkey
            primary key,
    status varchar(255) not null
);

create table jobs
(
    id        varchar(255) not null
        constraint jobs_pkey
            primary key,
    job_title varchar(255) not null
);

create table roles
(
    id   serial
        constraint roles_pkey
            primary key,
    role varchar(255) not null
);

create table team
(
    id             varchar(255) not null
        constraint team_pkey
            primary key,
    name           varchar(255) not null,
    team_leader_id varchar(255)
);

create table users
(
    id                varchar(255) not null
        constraint users_pkey
            primary key,
    bio               text,
    birth_date        date         not null,
    buddy_id          varchar(255),
    email             varchar(255)
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    employment_date   date         not null,
    firstname         varchar(255) not null,
    image_bytes       bytea,
    lastname          varchar(255) not null,
    password          varchar(255) not null,
    phone_number      varchar(255) not null,
    registration_date timestamp,
    job_id            varchar(255)
        constraint fk4f60m7trc06r46cn56dgdnd23
            references jobs,
    role_id           integer
        constraint fkp56c1712k691lhsyewcssf40f
            references roles,
    team_id           varchar(255)
        constraint fkhn2tnbh9fqjqeuv8ehw5ple7a
            references team
);

create table assessments
(
    id                       varchar(255) not null
        constraint assessments_pkey
            primary key,
    creation_date            timestamp,
    description              text,
    end_date                 timestamp,
    evaluated_user_full_name varchar(255),
    evaluator_full_name      varchar(255),
    is_template              boolean,
    overall_score            numeric(5, 2),
    start_date               timestamp,
    status                   varchar(255),
    title                    varchar(255),
    job_id                   varchar(255)
        constraint fkr8b50hjegr3rxc859me2v2aqn
            references jobs,
    user_id                  varchar(255)
        constraint fkn765we78ik7ya6n5j5p5bgo7x
            references users
);

create table assessments_information
(
    id                       varchar(255) not null
        constraint assessments_information_pkey
            primary key,
    assessment_id            varchar(255),
    assessment_title         varchar(255),
    evaluated_user_full_name varchar(255),
    evaluated_user_id        varchar(255),
    performed_time           timestamp,
    reason                   varchar(255),
    status                   varchar(255),
    performed_by_user_id     varchar(255)
        constraint fk6r78rgswycckec3v8abiwah9b
            references users,
    performed_on_user_id     varchar(255)
        constraint fk614gk1ub7cp7m1geiga1qlupi
            references users
);

create table department_goal
(
    id            varchar(255) not null
        constraint department_goal_pkey
            primary key,
    goal_a_part   varchar(255) not null,
    goal_m_part   varchar(255) not null,
    goal_r_part   varchar(255) not null,
    goal_s_part   varchar(255) not null,
    goal_t_part   varchar(255) not null,
    assessment_id varchar(255)
        constraint fk7ho7jrjpd526dyq2konxyg7p9
            references assessments
);

create table evaluation_group
(
    id            varchar(255) not null
        constraint evaluation_group_pkey
            primary key,
    overall_score numeric(5, 2),
    title         varchar(255) not null,
    assessment_id varchar(255)
        constraint fkeu235pqn1p5nebh883brrqftx
            references assessments
);

create table evaluation_field
(
    id                  varchar(255) not null
        constraint evaluation_field_pkey
            primary key,
    comment             varchar(255),
    first_score         integer,
    second_score        integer,
    title               varchar(255) not null,
    evaluation_group_id varchar(255)
        constraint fk9sx0604isnjq3ex3eeqywmyf5
            references evaluation_group
);

create table feedbacks
(
    id               varchar(255) not null
        constraint feedbacks_pkey
            primary key,
    author_full_name varchar(255) not null,
    author_id        varchar(255) not null,
    context          varchar(255) not null,
    assessment_id    varchar(255)
        constraint fknxydxmunw8cyam3jm1sxiwfqv
            references assessments
);

create table personal_goals
(
    id            varchar(255) not null
        constraint personal_goals_pkey
            primary key,
    goal_a_part   varchar(255) not null,
    goal_m_part   varchar(255) not null,
    goal_r_part   varchar(255) not null,
    goal_s_part   varchar(255) not null,
    goal_t_part   varchar(255) not null,
    assessment_id varchar(255)
        constraint fkspqkqpkmy095pm3232tosx5og
            references assessments
);

alter table team
    add constraint fk4sibnb6rjpyth97lemggqt2ul
        foreign key (team_leader_id) references users;