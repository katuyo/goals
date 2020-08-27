create table if not exists `t_account` (
    `id`         bigint unsigned  not null auto_increment primary key,
    `code`       varchar(16)      not null default '''',
    `name`       varchar(64)      not null default '''',
    `type`       int unsigned     not null default 0,
    `inventory`  double unsigned  not null default 0.0,
    `comment`    varchar(255)     not null default '''',
    `deleted`    tinyint unsigned not null default 0,
    `created_at` datetime         not null default current_timestamp,
    `updated_at` datetime         not null default current_timestamp on update current_timestamp,
    unique index `unq_account_code` (`code`)
);

create table if not exists `t_issuance` (
    `id`           bigint unsigned  not null auto_increment primary key,
    `code`         varchar(16)      not null default '''',
    `name`         varchar(64)      not null default '''',
    `type`         int unsigned     not null default 0,
    `amount`       double unsigned  not null default 0.0,
    `account_code` varchar(16)      not null default '''',
    `comment`      varchar(255)     not null default '''',
    `deleted`      tinyint unsigned not null default 0,
    `created_at`   datetime         not null default current_timestamp,
    `updated_at`   datetime         not null default current_timestamp on update current_timestamp,
    unique index `unq_issuance_code` (`code`)
);

create table if not exists `t_transfer`(
    `id`                bigint unsigned  not null auto_increment primary key,
    `code`              varchar(16)      not null default '''',
    `name`              varchar(64)      not null default '''',
    `type`              int unsigned     not null default 0,
    `from_account_code` varchar(16)      not null default '''',
    `to_account_code`   varchar(16)      not null default '''',
    `amount`            double unsigned  not null default 0.0,
    `matter`            int unsigned     not null default 0,
    `reference`         varchar(16)      not null default '''',
    `reference_code`    varchar(16)      not null default '''',
    `comment`           varchar(255)     not null default '''',
    `deleted`           tinyint unsigned not null default 0,
    `created_at`        datetime         not null default current_timestamp,
    `updated_at`        datetime         not null default current_timestamp on update current_timestamp,
    unique index `unq_transfer_code` (`code`)
);