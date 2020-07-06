# create schema if not exists `domain` default charset utf8mb4 collate utf8mb4_unicode_ci;
# create user `domain`@'%' identified by 'domain';
# grant all on domain.* to domain@'%';
# use `domain`;

create table if not exists `t_domain_module` (
    `id`         bigint unsigned  not null auto_increment comment 'id for index',
    `code`       varchar(16)      not null default '' comment '域 模块 编码',
    `name`       varchar(64)      not null default '' comment '模块 名称',
    `type`       int unsigned     not null default 0 comment '模块 类型',
    `status`     int unsigned     not null default 0 comment '模块 状态',
    `sort`       int unsigned     not null default 0 comment '模块 排序位 默认为0',
    `image_url`  varchar(128)     not null default '' comment '模块 图示 url',
    `comment`    varchar(1024)    not null default '' comment '备注，说明等',
    `deleted`    tinyint unsigned not null default 0 comment '软删除标示:0否1是',
    `created_at` datetime         not null default current_timestamp comment '创建时间',
    `updated_at` datetime         not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`id`) using btree
) engine = InnoDB comment '品牌表';

create unique index `unq_domain_module_code` on `t_domain_module`(`code`);
create index `idx_domain_module_name` on `t_domain_module`(`name`(20));