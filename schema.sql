create table if not exists addresses
(
    id           integer primary key auto_increment,
    street       varchar(50) not null,
    house_number varchar(6)  not null,
    city         varchar(20) not null,
    zip_code     varchar(6)  not null
);

create table if not exists cinemas
(
    id         integer primary key auto_increment,
    name       varchar(20) not null,
    address_id integer     not null,
    foreign key (address_id) references addresses (id) on delete cascade on update cascade
);

create table if not exists cinema_rooms
(
    id           integer primary key auto_increment,
    name         varchar(20) not null,
    rows         integer     not null,
    place_number integer     not null,
    cinema_id    integer     not null,
    foreign key (cinema_id) references cinemas (id) on delete cascade on update cascade
);

create table if not exists seats
(
  id integer primary key auto_increment,
  row integer not null,
  place integer not null,
  seat_type varchar(8),
  cinema_room_id integer not null,
  foreign key (cinema_room_id) references cinema_rooms(id) on delete cascade on update cascade
);

