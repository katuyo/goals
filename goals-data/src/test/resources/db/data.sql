insert into `t_account`(`code`, `name`, `type`, `inventory`, `comment`)
values ('ACT00001', 'Account 001', 0, 5000.00000001, 'This is the 1st system account for issuance'),
       ('ACT00002', 'Account 002', 0, 0.0, 'This is the 2nd system account for archive payment'),
       ('ACT00003', 'Account 003', 1, 0.0, 'This is the 1st normal account'),
       ('ACT00004', 'Account 004', 1, 0.0, 'This is the 2nd normal account');

insert into `t_issuance`(`code`, `name`, `type`, `amount`, `account_code`, `comment`)
values ('ISC00001', 'Issuance 001', 0, 5000.00000001, 'ACT00001', 'This is the first goals issuance'),
       ('ISC00002', 'Issuance 002', 1, 6000.00000001, 'ACT00002', 'This is the second goals issuance'),
       ('ISC00003', 'Issuance 003', 0, 7000.00000001, 'ACT00003', 'This is the third goals issuance'),
       ('ISC00004', 'Issuance 004', 1, 9000.00000001, 'ACT00004', 'This is the fourth goals issuance');

insert into `t_transfer`(`code`, `name`, `type`, `from_account_code`, `to_account_code`, `amount`, `matter`, `reference`, `reference_code`, `comment`)
values ('TSF00001', 'Transfer1', 1, 'ACT00001', 'ACT00002', 101.01, 1, 'Order', 'ORD912hjr43hnfda', 'comment1'),
       ('TSF00002', 'Transfer2', 2, 'ACT00001', 'ACT00002', 202.02, 2, 'Campaign', 'CPN3294ueh2jfda', 'comment2'),
       ('TSF00003', 'Transfer3', 3, 'ACT00001', 'ACT00002', 303.03, 3, 'Award', 'AWD432j2fh92', 'comment3'),
       ('TSF00004', 'Transfer4', 4, 'ACT00001', 'ACT00002', 404.04, 4, 'Default', 'DFT8329urh', 'comment4');