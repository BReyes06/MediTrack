drop database if exists meditrack_test;
create database meditrack_test;
use meditrack_test;

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
    middle_name varchar(255),
    last_name varchar(255) not null,
    location varchar(255) not null,
    phone varchar(255) not null
);

create table prescription (
	prescription_id int primary key auto_increment,
    pill_count int not null,
    hourly_interval int not null,
    start_time datetime not null,
    product_ndc varchar(255) not null,
    app_user_id int not null,
    doctor_id int,
    pharmacy_id int,
    constraint fk_prescription_app_user_id
		foreign key (app_user_id)
        references app_user(app_user_id),
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
    constraint fk_tracker_prescription_id
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

delimiter //

create procedure set_known_good_state()
begin
	
    set sql_safe_updates = 0;
    
	delete from app_user_role;
    alter table app_user_role auto_increment = 1;
    
	delete from tracker;
	alter table tracker auto_increment = 1;
    
    delete from prescription;
    alter table prescription auto_increment = 1;
	
    delete from pharmacy;
    alter table pharmacy auto_increment = 1;
    
    delete from doctor;
    alter table doctor auto_increment = 1;
    
	delete from app_user;
    alter table app_user auto_increment = 1;
    
    delete from app_role;
    alter table app_role auto_increment = 1;
    
	set sql_safe_updates = 1;
    
    -- passwords are set to "P@ssw0rd!"
  
	insert into app_user (first_name, middle_name, last_name, email, phone, username, password_hash, enabled)
		values
			('Marvis','', 'Chan', 'mchan@email.com', '1-111-111-1111', 'mchan', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
			('Bernanrdo','Reyes', 'Badilla', 'breyes@email.com', '1-222-222-2222', 'breyes', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
			('Joe','Irina', 'Esin', 'instructor@email.com', '1-000-000-0000', 'instructor', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);


	insert into app_role (`name`) 
		values
			('USER'),
			('ADMIN');
          
    insert into app_user_role
		values
			(1, 1),
			(2, 2),
            (3, 1);
	
	insert into pharmacy(pharmacy_id,`name`, email, phone, address)
		values
			(1, 'Rite Aid', 'pharmacy@riteaid.com', '1-800-riteaid', '12 Rite St'),
			(2, 'WalGreens', 'pharmaWG@walgreens.com', '1-800-walgreen', '35 Greens St'),
			(3, 'MetroPharma', 'metroph@metropharma.com', '1-800-metroph', '2009 Card Road');
				
	insert into doctor(doctor_id, first_name, middle_name, last_name, location, phone)
		values
			(1, 'Phillip', 'C','McGraw', 'Paramount Los Angeles, CA', '1-800-DrPhil'),
			(2, 'Otto', 'Gunther', 'Octavius', 'Daily Bugle NYC, NY', '1-800-DocOct'),
			(3, 'Gregory', '', 'House', 'Princeton-Plainsboro Teaching Hospital Princeton, NJ', '1-800-Laurie');
            
     	insert into prescription(prescription_id, pill_count, hourly_interval, start_time, product_ndc, app_user_id, doctor_id, pharmacy_id)
		values
			(1, 30, 4, '2023-05-31 8:00:00','68788-7602-3', 1, 3, 1),
			(2, 10, 24, '2023-05-31 8:00:00', '67877-511-38', 1, 2, 2),         
            (3, 10, 24, '2023-05-31 8:00:00', '67877-511-38', 1, null, null); 
       
    insert into tracker(tracker_id, administration_time, prescription_id)
		values
			(1, '2023-05-31 10:00:00', 1),
			(2, '2023-05-31 10:00:00', 2),
			(3, '2023-05-31 14:00:00', 1),
			(4, '2023-05-31 18:00:00', 1);   
 
end //

delimiter ;

call set_known_good_state();
call set_known_good_state();
call set_known_good_state();
