INSERT INTO `financial_service`.`role`
(`id`,
`name`)
VALUES
(1,
'ROLE_EMPLOYEE');

INSERT INTO `financial_service`.`role`
(`id`,
`name`)
VALUES
(2,
'ROLE_CUSTOMER');



INSERT INTO `financial_service`.`user`
(`dtype`,
`id`,
`first_name`,
`last_name`,
`password`,
`user_name`
)
VALUES
('Employee',
1,
'Efirstname',
'Elastname',
'password',
'employee');

INSERT INTO `financial_service`.`user`
(`dtype`,
`id`,
`first_name`,
`last_name`,
`password`,
`user_name`,
`addr_line1`,
`addr_line2`,
`cash`,
`balance_available`,
`city`,
`state`,
`zip`)
VALUES
('Customer',
2,
'Cfirstname',
'Clastname',
'password',
'customer',
'Caddr_line1',
'Caddr_line2',
100,
100,
'Pitts',
'PA',
'15213');



INSERT INTO `financial_service`.`users_roles`
(`user_id`,
`role_id`
)
VALUES
(1,1);

INSERT INTO `financial_service`.`users_roles`
(`user_id`,
`role_id`
)
VALUES
(2,2);



INSERT INTO `financial_service`.`fund`
(`id`,
 `name`,
 `symbol`)
VALUES
  (1,
   'Vanguard Total Stock Market Index Fund',
   'VTSMX');

INSERT INTO `financial_service`.`fund`
(`id`,
 `name`,
 `symbol`)
VALUES
  (2,
   'Fidelity Contrafund',
   'FCNTX');

INSERT INTO `financial_service`.`fund`
(`id`,
 `name`,
 `symbol`)
VALUES
  (3,
   'T. Rowe Price Growth Stock Fund',
   'PRGFX');

INSERT INTO `financial_service`.`fund`
(`id`,
 `name`,
 `symbol`)
VALUES
  (4,
   'Fidelity OTC Portfolio',
   'FOCPX');

INSERT INTO `financial_service`.`fund`
(`id`,
 `name`,
 `symbol`)
VALUES
  (5,
   'Reynolds Blue Chip Growth',
   'RBCGX');

INSERT INTO `financial_service`.`fund`
(`id`,
 `name`,
 `symbol`)
VALUES
  (6,
   'Yacktman',
   'YACKX');




INSERT INTO `financial_service`.`position`
(`shares_available`,
 `shares`,
 `customer_id`,
 `fund_id`)
VALUES
  (100,
   100,
   2,
   1);

INSERT INTO `financial_service`.`position`
(`shares_available`,
 `shares`,
 `customer_id`,
 `fund_id`)
VALUES
  (200,
   200,
   2,
   2);

INSERT INTO `financial_service`.`position`
(`shares_available`,
  `shares`,
 `customer_id`,
 `fund_id`)
VALUES
  (300,
   300,
   2,
   3);

INSERT INTO `financial_service`.`position`
(`shares_available`,
 `shares`,
 `customer_id`,
 `fund_id`)
VALUES
  (400,
   400,
   2,
   4);




INSERT INTO `financial_service`.`fund_price_history`
(`date`,
 `price`,
 `fund_id`)
VALUES
  ('2018-01-01',
   1.1,
   1);

INSERT INTO `financial_service`.`fund_price_history`
(`date`,
 `price`,
 `fund_id`)
VALUES
  ('2018-01-02',
   2.2,
   1);

INSERT INTO `financial_service`.`transaction`
(`id`,
 `amount`,
 `execute_date`,
 `shares`,
 `transaction_type`,
 `customer_id`,
 `fund_id`)
VALUES
 (1,
  100,
  '2018-01-01',
  20,
  0,
  2,
  1);

INSERT INTO `financial_service`.`transaction`
(`id`,
 `amount`,
 `execute_date`,
 `shares`,
 `transaction_type`,
 `customer_id`,
 `fund_id`)
VALUES
 (2,
  100,
  '2018-01-01',
  20,
  1,
  2,
  1);
