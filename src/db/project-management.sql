create table accounts (
    id              int IDENTITY(1,1) not null,
    created_at      datetime not null default getdate(),
    updated_at      datetime not null default getdate(),
    deleted_at      datetime,
    password        varchar(50) not null,
    full_name       text not null,    
    email           varchar(100) unique not null,
    team_name       text not null,
    primary key(id)
)

create table user_infos (
    id              int IDENTITY(1,1) not null, 
    created_at      datetime not null default getdate(),
    updated_at      datetime not null default getdate(),
    deleted_at      datetime,
    account_id      int not null foreign key references accounts(id),
    full_name       text not null,
    gender          int,
    age             int,
    address         text, 
    email           varchar(100) unique not null,
    code            text,
    primary key(id)
)

create table projects (
    id              int IDENTITY(1,1) not null,
    created_at      datetime not null default getdate(),
    updated_at      datetime not null default getdate(),
    deleted_at      datetime,
    name            varchar(100) unique not null,
    code            text,
    start_date      datetime not null,
    end_date        datetime not null,
    fee             decimal(19,2),
    hours           decimal(19,2),
    primary key(id)
)

create table roles (
    id              int IDENTITY(1,1) not null, 
    created_at      datetime not null default getdate(),
    updated_at      datetime not null default getdate(),
    deleted_at      datetime,
    account_id      int not null foreign key references accounts(id),
    project_id      int not null foreign key references projects(id),
    name            varchar(50),
    primary key(id)
)

create table tasks (
    id              int IDENTITY(1,1) not null,
    created_at      datetime not null default getdate(),
    updated_at      datetime not null default getdate(),
    deleted_at      datetime,    
    account_id      int not null foreign key references accounts(id),
    name            text not null,
    billable        int,
    rate            decimal(19,2),
    primary key(id)
)

create table times (
    id                      int IDENTITY(1,1) not null,
    created_at              datetime not null default getdate(),
    updated_at              datetime not null default getdate(),
    deleted_at              datetime,  
    project_id              int not null foreign key references projects(id), 
    task_id                 int not null foreign key references tasks(id),
    account_id              int not null foreign key references accounts(id),
    hours                   decimal(19,2),
    date                    datetime,
    hours_with_timer        decimal(19,2), 
    primary key(id)
)

create table account_project (
    account_id      int foreign key references accounts(id),
    project_id      int foreign key references projects(id),
    primary key(account_id, project_id)
)

create table task_project (
    task_id         int foreign key references tasks(id),
    project_id      int foreign key references projects(id),
    primary key(task_id, project_id)
)

