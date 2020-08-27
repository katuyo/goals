insert into `t_account`(`code`, `name`, `type`, `inventory`, `comment`)
values ('ACT00001', 'Account 001', 0, 5000.00000001, 'This is the 1st system account for issuance');
insert into `t_account`(`code`, `name`, `type`, `inventory`, `comment`)
values ('ACT00002', 'Account 002', 0, 0.0, 'This is the 2nd system account for archive payment');
insert into `t_account`(`code`, `name`, `type`, `inventory`, `comment`)
values ('ACT00003', 'Account 003', 1, 0.0, 'This is the 1st normal account');
insert into `t_account`(`code`, `name`, `type`, `inventory`, `comment`)
values ('ACT00004', 'Account 004', 1, 0.0, 'This is the 2nd normal account');

insert into `t_issuance`(`code`, `name`, `type`, `amount`, `account_code`, `comment`)
values ('ISC00001', 'Issuance 001', 0, 5000.00000001, 'ACT00001', 'This is the first goals issuance');