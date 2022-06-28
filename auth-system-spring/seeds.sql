create table users(
	id int primary key,
	firstName varchar(30) not null,
	lastName varchar(30) not null,
	username varchar(40) not null,
	email varchar(40) not null,
	password text not null
);

create table categories(
	id int primary key,
	user_id int not null,
	title varchar(30) not null,
	description text,
	foreign key (user_id) references users(id)
);

create table transactions(
	id int primary key,
	category_id int not null,
	user_id int not null,
	amount numeric(10, 2),
	note varchar(50) not null,
	transaction_date date not null default CURRENT_DATE,
	foreign key (user_id) references users(id),
	foreign key (category_id) references categories(id)
);

create sequence user_seq increment 1 start 1;
create sequence category_seq increment 1 start 1;
create sequence transaction_seq increment 1 start 1;