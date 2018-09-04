create table account
(
   accountId integer not null,
   userName varchar(255) not null,
   balance double not null,
 balanceChangeLock boolean not null,  primary key(accountId)
);