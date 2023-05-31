drop database if exists meditrack;
create database meditrack;
use meditrack;

create table app_user (
	app_user_id int primary key auto_increment,
    first_name varchar(255) not null,
    middle_name varchar(255),
    last_name varchar(255) not null,
    email varchar(255) not null,
    phone varchar(255) not null,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default 1
);

create table pharmacy (
	pharmacy_id int primary key auto_increment,
    `name` varchar(255) not null,
    email varchar(255),
    phone varchar(255) not null, 
    address varchar(255) not null
);

create table doctor (
	doctor_id int primary key auto_increment,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    location varchar(255) not null,
    phone varchar(255) not null
);

create table prescription (
	prescription_id int primary key auto_increment,
    pill_count int not null,
    frequency int not null,
    `date` date not null,
    start_time datetime not null,
    product_ndc varchar(255) not null,
    user_id int not null,
    doctor_id int,
    pharmacy_id int,
    constraint fk_presciption_user_id
		foreign key (user_id)
        references `user`(user_id),
	constraint fk_prescription_doctor_id
		foreign key (doctor_id)
        references doctor(doctor_id),
	constraint fk_prescription_pharmacy_id
		foreign key (pharmacy_id)
        references pharmacy(pharmacy_id)
);

create table tracker (
	tracker_id int primary key auto_increment,
    administration_time datetime not null,
    prescription_id int not null,
    constraint fk_tracker_presciption_id
		foreign key (prescription_id)
		references prescription(prescription_id)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);