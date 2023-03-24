create table wallet
(
    id      binary(16)                                                 not null
        primary key,
    address varchar(42)                                                not null,
    balance decimal(60, 30) default 100.000000000000000000000000000000 null,
    constraint UK_3y9jbtvtnd4rm8xk3iu4wi6up
        unique (address)
);

create table transaction
(
    id               binary(16)                                                 not null
        primary key,
    amount           decimal(60, 30) default 100.000000000000000000000000000000 not null,
    receiver_address varchar(42)                                                not null,
    sender_address   varchar(42)                                                not null,
    constraint FK1oq5kcf05v0mp9uia61g8yijt
        foreign key (receiver_address) references wallet (address),
    constraint FK1ualqrluwatt44guvtgayguji
        foreign key (sender_address) references wallet (address)
);

