use Cinema

--maybe i am wrong about the "on delete" and the "on cascade", idk
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- --------------------------------------------------
--dropping
drop table Director
drop table Actor
drop table Movie
drop table Studio
drop table Item
drop table Shop
drop table Employee
drop table Ticket
drop table Client
drop table Cinema
drop table Hall
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- --------------------------------------------------

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- --------------------------------------------------
--creation
--Big Father, everything is linked to him in a way or another
create table Cinema
(
	cinema_id int not null primary key,
	cinema_name varchar (55) not null,
	cinema_adress varchar (255) not null,
	email varchar (255),
	phone_number char (12) not null,
);
--I use this because i want all mails to be unique (as it is in real life) but allow them to not have one if they so desire. 
--Function below just checks for unique values every time the email is not null
--Will be used later down
create unique nonclustered index email_unique
on Cinema(email)
where email is not null;

create table Employee
(
	employee_id int not null primary key,
	first_name varchar (60) not null,
	second_name varchar (30) not null,
	age int not null,
	employee_home_adress varchar (255) not null,
	phone_number char (12) not null,
	email varchar (255),
	salary int not null,
	cinema_id int not null,
	foreign key (cinema_id)
		references Cinema (cinema_id)
		on delete cascade
		on update no action
	--ONE TO MANY! a specific employee id (not cnp) is given to the person and is linked with the cinema id of that location. Person can go at a different cinema to work there too, if they so choose, but will have diff employee id
	--If the cinema closes down, it's id basically gets deleted. The employee is left jobless (Basically, since the cinema is gone, the database for that cinema is gone...so all the data regarding employees is gone too
	--If the cinema changes it's name, the employees still work there, the database for that cinema stays
);
create unique nonclustered index email_unique
on Employee(email)
where email is not null;

create table Client
(
	client_personal_id int not null primary key,--(CNP)
	first_name varchar (60) not null,
	second_name varchar (30) not null,
	phone_number char (12), --of the romanian format +40*********
	email varchar (255),
);
create unique nonclustered index email_unique
on Client(email)
where email is not null;

create table Ticket
(
	ticket_id int not null primary key,
	hall_number varchar (10) not null,
	hall_row int not null,
	seat_number int not null,
	price float not null,
	cinema_id int not null,
	--ONE TO MANY WITH CINEMA: the id of a single tickets belong to one and only one cinema. A ticket with the same details (row, seat) that is for the same movie MUST have another id for it to work
	foreign key (cinema_id)
		references Cinema (cinema_id)
		on delete cascade --same explanation as at Employee. Once the cinema dissapears, all the elements that were "made" in the cinema (employees that worked there, shops that existed there, tickets that were bought) have no more value
		on update cascade,
	client_id int not null
	--ONE TO MANY WITH CLIENT: A client can own multiple tickets, hence why the same unique key (the same cnp) can be linked with multiple different (unqiue) tickets. However, the exact same ticket cannot belong to 2 people simultaneously
	foreign key(client_id)
		references Client(client_personal_id)
		on delete no action--if something happens to the client, it does not really matter? The ticket still has significance, since the seat and row number that was used by the previous client can be given to someone eles. The ticket does not lose it;s importance
		on update no action--same explanation as with delete
	--MANY TO MANY BETWEEN CLIENT AND CINEMA ACHIEVED!!!
);

create table Shop
(
	shop_id int not null primary key,
	shop_name varchar (30),
	cinema_id int not null,
	--ONE TO MANY WITH CINEMA. A cinema can own multiple shops in it's property. the exact same shop cannot be located in 2 different cinemas across the world
	foreign key (cinema_id)
		references Cinema
		on delete cascade --all the things related to that cinema establishment get thrown away
		on update no action
);

create table Item
(
	--barcode int not null primary key,
	barcode int not null, 
	item_name varchar (255) not null,
	price float not null,
	expiration_date datetime not null,
	shop_id int not null,
	--ONE TO MANY WITH SHOP: this one to many explains it the best! The same shop has MANY bags of m&ms, each with a different barcode. However, the exact same m&ms bag cannot exist in 2 shops simultaneously (unless you believe in quantum physicis
	foreign key (shop_id) 
		references Shop (shop_id)
		on delete cascade
		on update no action
);

create table Studio
(
	studio_id int not null primary key,
	studio_name varchar(255) not null,
	email varchar (255)
);
create unique nonclustered index email_unique
on Studio(email)
where email is not null;

create table Movie
(
	movie_id int not null primary key,
	movie_name varchar (255) not null,
	duration int not null,
	starting_hour time not null,
	movie_type varchar (255) not null,
	cinema_id int not null,
	--ONE TO MANY WITH CINEMA. Multiple movies can be played at the same cinema. HOWEVER, this exact movie with this exact name can only "belong" to that cinema once
	foreign key (cinema_id)
		references Cinema (cinema_id)
		on delete no action
		on update no action,-- can be either cascade or no action, does not really matter
	--the exact movie can belong to this studio only once, while a studio can make a multitude of movies
	studio_id int not null,
	foreign key (studio_id)
		references Studio(studio_id)
		on delete cascade
		on update cascade
);

create table Actor
(
	actor_id int not null primary key,
	first_name varchar (60) not null,
	second_name varchar (30) not null,
	age int not null,
	phone_number char (12) not null,
	email varchar (255),
	studio_id int not null,
	--basically signifies the contract between the studio and the actor. That contract exist at the same time twice with the exact same actor, but the studio can have multiple actors with that contract
	foreign key (studio_id)
		references Studio (studio_id)
		on delete cascade
		on update cascade
);
create unique nonclustered index email_unique
on Actor(email)
where email is not null;

create table Director
(
	director_id int not null primary key,
	first_name varchar (60) not null,
	second_name varchar (30) not null,
	age int,
	phone_number char (12) not null,
	email varchar (255),
	studio_id int not null,
	--basically signifies the contract between the studio and the actor. That contract exist at the same time twice with the exact same actor, but the studio can have multiple actors with that contract
	foreign key (studio_id)
		references Studio (studio_id)
		on delete cascade
		on update cascade
);
create unique nonclustered index email_unique
on Director(email)
where email is not null;

create table Hall
(
	hall_id int not null,
	hall_name varchar (20) not null,
	hall_row_number int not null,
	hall_seats_per_row int not null,
	primary key(hall_id, hall_name)
);
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- --------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--population
Insert into Cinema
    select 634, 'Cinema Arta - Bucuresti', '883 River View Road ', 'cinemalibracluj@yahoo.com', '+40722080855'
union all
    select 586, 'Cinema City - Cluj', '4162 Oakwood Drive ', 'cinemalibrapiatraneamt@yahoo.com', '+40792324041'
union all
    select 283, 'Cinema Lumina - Bucuresti', '6077 Pine Road ', 'cinemaartabucuresti@yahoo.com', '+40773917813'
union all
    select 236, 'Cinema Arta - Cluj', '1722 Oakwood Drive ', 'cinemaluminacluj@gmail.com', '+40726676471'
union all
    select 598, 'Cinema City - Ramnicu Valcea', '8872 Oakwood Drive ', 'capitolbucuresti@gmail.com', '+40709806641'
union all
    select 991, 'Cinema Libra - Brasov', '8148 Elm Street ', 'cinemalibraramnicuvalcea@gmail.com', '+40710834986'
union all
    select 238, 'Capitol - Cluj', '3396 River View Road ', 'cinemaartabucuresti@cinemamail.com', '+40724387641'
union all
    select 764, 'Cinema City - Ramnicu Valcea', '982 Maple Avenue ', 'cinemacitybrasov@cinemamail.com', '+40752287049'
union all
    select 466, 'Cinema City - Bacau', '4256 Birchwood Avenue ', 'cinemaluminaramnicuvalcea@gmail.com', '+40754024361'
union all
    select 398, 'Capitol - Ramnicu Valcea', '5064 Sycamore Drive ', 'cinemacitybacau@gmail.com', '+40723869675';

Insert into Employee
    select 773, 'Liam', 'Lopez', 19, '8426 Birchwood Avenue ', '+40712665079', 'liamlopez@gmail.com',2000, 283
union all
    select 520, 'Liam', 'Moore', 42, '1101 Maple Avenue ', '+40770841924', 'liammoore@gmail.com',5000, 764
union all
    select 165, 'Mason', 'Martinez', 56, '1567 Birchwood Avenue ', '+40781025248', NULL, 3000,586
union all
    select 979, 'Lucas', 'Johnson', 39, '8430 Oakwood Drive ', '+40740994879', 'lucasjohnson@gmail.com', 7000,466
union all
    select 287, 'Ethan', 'Martin', 22, '1591 Birchwood Avenue ', '+40714308369', 'ethanmartin@yahoo.com', 5500,991
union all
    select 250, 'Ethan', 'Moore', 60, '8654 Maple Avenue ', '+40710426902', 'ethanmoore@cinemamail.com', 1200,466
union all
    select 567, 'Olivia', 'Gonzalez', 37, '4764 Pine Road ', '+40784203412', 'oliviagonzalez@gmail.com', 3200,236
union all
    select 981, 'Benjamin', 'Gonzalez', 52, '7103 Sycamore Drive ', '+40771480368', 'benjamingonzalez@gmail.com',3700, 466
union all
    select 418, 'Benjamin', 'Smith', 21, '1236 Elm Street ', '+40723770302', 'benjaminsmith@yahoo.com',8000, 634
union all
    select 781, 'Isabella', 'Johnson', 37, '1294 Maple Avenue ', '+40740308299', NULL, 7600,236;

Insert into Client
    select 319, 'Mia', 'Gonzalez',  '+40793010044', 'miagonzalez@yahoo.com'
union all
    select 201, 'Amelia', 'Hernandez',  '+40764150358', 'ameliahernandez@gmail.com'
union all
    select 618, 'William', 'Lopez',  '+40713798000', NULL
union all
    select 434, 'Olivia', 'Rodriguez',  '+40716526171', 'oliviarodriguez@gmail.com'
union all
    select 939, 'Charlotte', 'Martin',  '+40731485642', 'charlottemartin@gmail.com'
union all
    select 945, 'Mason', 'Hernandez',  '+40793973724', 'masonhernandez@cinemamail.com'
union all
    select 549, 'Mason', 'Brown',  '+40799415044', 'masonbrown@cinemamail.com'
union all
    select 792, 'Lucas', 'Williams',  '+40767103579', 'lucaswilliams@yahoo.com'
union all
    select 971, 'Charlotte', 'Miller', '+40779775158', 'charlottemiller@yahoo.com'
union all
    select 543, 'Liam', 'Garcia', '+40725714589', 'liamgarcia@yahoo.com';

Insert into Ticket
    select 5925, '7', 8, 13, 33.38, 586, 201
union all
    select 2178, '1', 3, 17, 31.74, 991, 434
union all
    select 6067, '6', 9, 12, 38.54, 236, 319
union all
    select 2662, '6', 2, 1, 43.97, 586, 543
union all
    select 2822, '4A', 6, 2, 96.8, 586, 971;

Insert into Shop
    select 41, 'Mega Image', 398 
union all
    select 83, 'Mall Shop', 634
union all
    select 76, 'Auchan', 634
union all
    select 23, 'Carrefour', 586
union all
    select 26, 'Mega Image', 991;

Insert into Item
    select 668, 'Chips', 42.04, '20540903', 76
union all
    select 429, 'Fanta', 19.08, '20350719', 41
union all
    select 861, 'Snickers', 5.66, '20270920', 26
union all
    select 893, 'Chips', 69.0, '20420728', 76
union all
    select 654, 'Fanta', 28.85, '20321115', 26
union all
    select 511, 'Water', 9.31, '20510217', 41
union all
    select 776, 'Fanta', 9.5, '20470726', 23
union all
    select 690, 'Chips', 8.48, '20600824', 76
union all
    select 214, 'Snickers', 34.22, '20420723', 41
union all
    select 956, 'Coke', 86.99, '20321127', 26;

Insert into Studio
    select 497, 'Marvel', 'marvel01@gmail.com'
union all
    select 654, 'Dc', 'dc12@yahoo.com'
union all
    select 207, 'Warner Brothers', 'warnerbrothers23@mail.com'
union all
    select 955, 'Dc', null
union all
    select 942, 'Marvel', 'marvel45@yahoo.com'
union all
	select 234, 'Warner Brothers', 'warnerbros22@gmail.com';

Insert into Movie
    select 737, 'The Matrix', 70, '13:50', 'Sci-Fi/Action',283, 942
union all
    select 563, 'The Matrix', 70, '14:50', 'Sci-Fi/Action',764, 654
union all
    select 294, 'Forrest Gump', 100, '13:10', 'Drama/Comedy',586, 955
union all
    select 934, 'Avatar', 90, '11:10', 'Sci-Fi/Action',398, 497
union all
    select 924, 'The Lord of the Rings', 160, '19:30', 'Fantasy/Adventure',764, 942
union all
    select 341, 'Pulp Fiction', 100, '14:40', 'Crime/Drama',598, 942
union all
    select 197, 'The Dark Knight', 60, '13:0', 'Action/Crime',398, 497
union all
    select 754, 'The Lord of the Rings', 160, '17:50', 'Fantasy/Adventure',283, 654
union all
    select 712, 'The Shawshank Redemption', 140, '10:10', 'Crime/Drama',991, 654
union all
    select 703, 'The Dark Knight', 110, '10:40', 'Action/Crime',238, 942;

Insert into Director
    select 93, 'Christopher', 'Nolan', 57, '+40711690584', 'guillermotoro@gmail.com', 942
union all
    select 33, 'Ridley', 'Scott', 63, '+40774784304', 'wesanderson01@cinemamail.com', 955
union all
    select 22, 'Wes', 'Anderson', 49, '+40797147469', 'gretagerwig01@yahoo.com', 955
union all
    select 39, 'Greta', 'Gerwig', 46, '+40706353958', 'wesanderson02@cinemamail.com', 207
union all
    select 26, 'Guillermo', 'Toro', 37, '+400777892748', 'christophernolan@yahoo.com', 497
union all
    select 80, 'Martin', 'Scorsese', 18, '+40728608158', 'wesanderson03@cinemamail.com', 942
union all
    select 68, 'Christopher', 'Nolan', 66, '+40777043664', 'gretagerwig@yahoo.com', 942
union all
    select 67, 'Wes', 'Anderson', 61, '+40755481596', 'quentintarantino@gmail.com', 497
union all
    select 47, 'Guillermo', 'Toro', 39, '+40737095769', 'quentintarantino@cinemamail.com', 942
union all
    select 57, 'Wes', 'Anderson', 18, '+40781155082', 'ridleyscott@gmail.com', 497;

Insert into Actor
    select 60, 'Brad', 'Pitt', 47, '+40745725534', 'merylstreep@yahoo.com', 942
union all
    select 37, 'Charlize', 'Theron', 21, '+40785278506', 'scarlettjohansson@gmail.com', 207
union all
    select 77, 'Julia', 'Roberts', 19, '+40700767048', 'charlizetheron@yahoo.com', 497
union all
    select 22, 'Julia', 'Roberts', 27, '+40738807936', 'bradpitt@yahoo.com', 497
union all
    select 83, 'Denzel', 'Washington', 58, '+40749251003', 'merylstreep@gmail.com', 942
union all
    select 95, 'Denzel', 'Washington', 51, '+40785267942', 'johnnydepp@cinemamail.com', 654
union all
    select 72, 'Denzel', 'Washington', 60, '+40747945829', 'leonardodicaprio01@cinemamail.com', 207
union all
    select 99, 'Julia', 'Roberts', 19, '+40797751769', 'leonardodicaprio02@cinemamail.com', 955
union all
    select 92, 'Scarlett', 'Johansson', 29, '+40760986136', 'bradpitt@gmail.com', 654
union all
    select 21, 'Leonardo', 'DiCaprio', 44, '+40701894112', NULL, 942;

--the one that violates refferential instructions (primary key not unique):
Insert into Actor
    select 60, 'Brad', 'Pitt', 47, '+40745725534', 'merylstreep@yahoo.com', 942
union all
    select 37, 'Charlize', 'Theron', 21, '+40785278506', 'scarlettjohansson@gmail.com', 207
union all
    select 77, 'Julia', 'Roberts', 19, '+40700767048', 'charlizetheron@yahoo.com', 497
union all
    select 22, 'Julia', 'Roberts', 27, '+40738807936', 'bradpitt@yahoo.com', 497
union all
    select 83, 'Denzel', 'Washington', 58, '+40749251003', 'merylstreep@gmail.com', 942
union all
    select 95, 'Denzel', 'Washington', 51, '+40785267942', 'johnnydepp@cinemamail.com', 654
union all
    select 72, 'Denzel', 'Washington', 60, '+40747945829', 'leonardodicaprio01@cinemamail.com', 207
union all
    select 60, 'Julia', 'Roberts', 19, '+40797751769', 'leonardodicaprio02@cinemamail.com', 955
union all
    select 92, 'Scarlett', 'Johansson', 29, '+40760986136', 'bradpitt@gmail.com', 654
union all
    select 21, 'Leonardo', 'DiCaprio', 44, '+40701894112', NULL, 942;

insert into Hall
	select 111, 'A', 20, 10
union all
	select 111, 'B', 15, 8
union all
	select 222, 'C', 18, 10;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--general deletors
delete from Actor;
delete from Director;
delete from Ticket;
delete from Movie;
delete from Client;
delete from Employee;
delete from Item;
delete from Shop;
delete from Cinema;
delete from Studio;
delete from Hall;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--general selectors
select * from Actor;
select * from Director;
select * from Ticket;
select * from Movie;
select * from Client;
select * from Employee;
select * from Item;
select * from Shop;
select * from Cinema;
select * from Hall;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--update data – for at least 3 tables;
--using "is null"
update Actor
set age = 18
where email is null;

--using "in"
update Studio
set studio_name = 'Disney'
where studio_name in ('DC','Marvel');

--using "between"
update Item
set expiration_date = '20251010'
where shop_id between 25 and 50;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--delete data – for at least 3 tables;
--using "like"
delete from Studio
where studio_name like ('%Marvel%');

--"using", "or" and value comparison operators
delete from Employee
where (email = NUll and cinema_id <=300) or cinema_id>=700;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--UNION - you get 2 sets of data and unite them in a single database; if you use UNION ALL, elements that are in both the databases you unite (repeats) will show both times
--QUERRY a. 1
--a. 2 queries with the union operation; use UNION [ALL] and OR;
--1/3 arithmetic expressions in the SELECT clause

select e1.first_name, e1.second_name, e1.salary * 0.8 as Salary, e1.email, e1.age
from Employee e1
where e1.email is null or e1.salary > 7000
union 
select e2.first_name, e2.second_name,e2.salary * 1.25, e2.email, e2.age
from Employee e2
where e2.email is not null and e2.age >=50;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--QUERRY a. 2
--a. 2 queries with the union operation; use UNION [ALL] and OR;
--1/3 conditions with AND, OR, NOT, and parentheses in the WHERE clause
select e1.first_name, e1.second_name, e1.cinema_id
from Employee e1
where substring(e1.first_name, 1,1) in ('A','E','I','O','U') or substring(e1.second_name, 1,1) in ('A','E','I','O','U')
union all
select e2.first_name, e2.second_name, e2.cinema_id
from Employee e2
where (substring(e2.second_name, 1,1) = 'E' and substring(e2.second_name, 1,1) = 'M') OR (substring(e2.second_name, 1,1) = 'L' and not( substring(e2.second_name, 1,1) = 'M'));
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query b. 1
--b. 2 queries with the intersection operation; use INTERSECT and IN;
--2/3 conditions with AND, OR, NOT, and parentheses in the WHERE clause
select *
from Employee e1, Cinema c1
where e1.salary >= 5000
intersect
select *
from Employee e2, Cinema c2
where ((e2.cinema_id in (select cinema_id from Cinema) and e2.cinema_id = c2.cinema_id and c2.cinema_name like '%Cluj%') or (e2.cinema_id in (select cinema_id from Cinema) and e2.cinema_id = c2.cinema_id and c2.cinema_name like '%Bacau%') and not (e2.cinema_id != c2.cinema_id));
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query b. 2
--b. 2 queries with the intersection operation; use INTERSECT and IN;
--2/3 arithmetic expressions in the SELECT clause
select i1.price,i1.price * 0.5 as updated_price,i1.item_name, i1.shop_id, s1.shop_id, s1.shop_name
from Item i1, Shop s1
where s1.shop_id = i1.shop_id and s1.shop_name in ('Mega Image', 'Carrefour')
intersect
select i2.price,i2.price * 0.5 as updated_price,i2.item_name, i2.shop_id, s2.shop_id, s2.shop_name
from Item i2, Shop s2
where s2.shop_id = i2.shop_id and s2.shop_id <30;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query c. 1
--. 2 queries with the difference operation; use EXCEPT and NOT IN;
select c1.first_name, c1.second_name, c1.client_personal_id
from Client c1
where c1.second_name not in ('Hernandez' , 'Miller')
except
select c2.first_name, c2.second_name, c2.client_personal_id
from Client c2
where c2.client_personal_id not in (select client_id from Ticket);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query c. 2
--c. 2 queries with the difference operation; use EXCEPT and NOT IN;
select a1.age, a1.first_name, a1.second_name, a1.phone_number
from Actor a1
where a1.second_name not in ('Dicaprio')
except
select a2.age, a2.first_name, a2.second_name, a2.phone_number
from Actor a2
where a2.age < 30;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query d. 1
--4 queries with INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN (one query per operator); one query will join at least 3 tables, while another one will join at least two many-to-many relationships;
--1/3 DISTINCT CLAUSES
--selects what each branded shop offers in each cinema
--this QUERY joins 3 tables
select distinct s.shop_name, i.item_name, c.cinema_name
from Shop s
inner join Item i
on i.shop_id = s.shop_id
inner join Cinema c
on s.cinema_id = c.cinema_id
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query d. 2
--4 queries with INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN (one query per operator); one query will join at least 3 tables, while another one will join at least two many-to-many relationships;
select c.first_name, c.second_name, t.hall_number
from Client c
left join Ticket t
on c.client_personal_id = t.client_id;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query d. 3
--4 queries with INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN (one query per operator); one query will join at least 3 tables, while another one will join at least two many-to-many relationships;
--3/3 arithmetic
Insert into Studio
    select 854, 'Paramount', 'paramount01@gmail.com'
select s.studio_id, s.studio_name, a.actor_id,a.first_name, a.second_name, a.age+5 as updated_age
from Actor a
right join Studio s
on a.studio_id = s.studio_id;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query d. 4
--4 queries with INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN (one query per operator); one query will join at least 3 tables, while another one will join at least two many-to-many relationships;
--shows all studios and what contracts they have with each director
--2/3 distinct
--1/2 order by
select distinct d.first_name, d.second_name, s.studio_id, s.studio_name
from Director d
full join Studio s
on d.studio_id = s.studio_id
order by d.first_name, d.second_name
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query e. 1
--2 queries with the IN operator and a subquery in the WHERE clause; in at least one case, the subquery must include a subquery in its own WHERE clause;
--where subquery in where subquery
--2/2 order by
--find all cinemas that have mega images with items cheaper than 20
select c.cinema_name, c.phone_number, c.phone_number
from Cinema c
where c.cinema_id in
	( 
		select s.cinema_id
		from  Shop s
		where s.shop_name like '%Mega Image%' and s.shop_id in
				(
					select i.shop_id
					from Item i
					where i.price <= 20
				)

	)
order by c.phone_number;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query e. 2
--2 queries with the IN operator and a subquery in the WHERE clause; in at least one case, the subquery must include a subquery in its own WHERE clause;
--find all studios (that do not have the same overarching studio (same name) that have contracts with young directors)
select distinct s.studio_name
from Studio s
where s.studio_id in 
	(
		select d.studio_id
		from Director d
		where d.age <40
	);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query f. 1
--2 queries with the EXISTS operator and a subquery in the WHERE clause;
--select all studios with no contract with an director or an actor (except marvel and dc)
--3/3 conditions with AND, OR, NOT, and parentheses in the WHERE clause
select s.studio_name, s.studio_id
from Studio s
where
(
not exists
	(
		select *
		from Director d
		where d.studio_id = s.studio_id
	)
or
not exists
	(
	select *
	from Actor a
	where a.studio_id = s.studio_id
	)
) 
and s.studio_name not in ('Marvel','Dc');
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query f. 2
--2 queries with the EXISTS operator and a subquery in the WHERE clause;
--select all cinemas that had a "hernandez" come to them
select c.cinema_name, c.cinema_adress
from Cinema c
where exists
(
	select * 
	from Ticket t
	where t.cinema_id =c.cinema_id and exists
	(
		select * 
		from Client c
		where c.client_personal_id = t.client_id and c.second_name = 'Hernandez'
	)
)
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query g. 2
--2 queries with a subquery in the FROM clause; 
--1/2 top
Select top 3 e.employee_id, e.first_name, e.second_name, e.salary
from
	(
		select avg(salary) as average_salary 
		from Employee
	) as Salary_of_worker, Employee as e
where Salary_of_worker.average_salary < e.salary
order by e.salary desc;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query g. 2
--2 queries with a subquery in the FROM clause; 
--top 2 cheapest items that have an expiration date eariler than 2040
--top 2/2
select top 2 Filtered_Items.item_name, Filtered_Items.expiration_date, Filtered_Items.price
from
	(
		select *
		from Item i
		where i.expiration_date < '20400101'
	)as Filtered_Items
where Filtered_Items.price < 30
order by Filtered_Items.price;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query h. 1
--4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause; 2 of the latter will also have a subquery in the HAVING clause; use the aggregation operators: COUNT, SUM, AVG, MIN, MAX;
--sum
--"having" 1/3
select m.movie_type, sum(m.duration) as total_duration
from Movie m
group by m.movie_type
having sum(m.duration) > 100;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query h. 2
--4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause; 2 of the latter will also have a subquery in the HAVING clause; use the aggregation operators: COUNT, SUM, AVG, MIN, MAX;
--finds how many of each item there are
--count
select i.item_name, count(i.item_name)
from Item i
group by i.item_name;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query h. 3
--4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause; 2 of the latter will also have a subquery in the HAVING clause; use the aggregation operators: COUNT, SUM, AVG, MIN, MAX;
--gives me all empployees (grouped_by_names) with a salary higher than the average salary
--"having" 2/3
--"subquerry with having" 1/2
select e.first_name, avg(e.salary) as average_salary
from Employee e
group by e.first_name
having avg(e.salary) > (select avg(salary) from Employee);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query h. 4
--4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause; 2 of the latter will also have a subquery in the HAVING clause; use the aggregation operators: COUNT, SUM, AVG, MIN, MAX;
--"having" 3/3
--"subquerry with having" 2/2
--gives me all cinemas that have an average salary higher than the average salary of all employees
select e.cinema_id, min(e.salary) as minimujm_salary, max(e.salary) as maximum_salary
from Employee e
group by e.cinema_id
having avg(e.salary) > (select avg(salary) from Employee);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query i. 1
--i. 4 queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator); rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT] IN.
--all nr 1
--select all movies that are longer than any other movies of the same category as it
--all 1/2
SELECT distinct m.movie_name, m.movie_type
FROM Movie m
WHERE m.duration >= all 
(
    SELECT m1.duration
    FROM Movie m1
    WHERE m1.movie_type = m.movie_type and m1.movie_id != m.movie_id
);

--rewriting with aggregator operators
--if the movie_type appears only once then this breaks and so do i
SELECT distinct m.movie_name, m.movie_type
FROM Movie m
WHERE m.duration >= 
(
    SELECT MAX(m1.duration)
    FROM Movie m1
    WHERE m1.movie_type = m.movie_type and m1.movie_id != m.movie_id
    GROUP BY m1.movie_type
) --bandaid solution :D
or m.duration = 
(
    SELECT MAX(m1.duration)
    FROM Movie m1
    WHERE m1.movie_type = m.movie_type
    GROUP BY m1.movie_type
) 
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query i. 1
--i. 4 queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator); rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT] IN.
--all nr 1
--select all movies that are longer than any other movies of the same category as it
--all 1/2
SELECT distinct m.movie_name, m.movie_type
FROM Movie m
WHERE m.duration >= all 
(
    SELECT m1.duration
    FROM Movie m1
    WHERE m1.movie_type = m.movie_type --and m1.movie_id != m.movie_id
);

--rewriting with aggregator operators
--if the movie_type appears only once then this breaks and so do i
SELECT distinct m.movie_name, m.movie_type
FROM Movie m
WHERE m.duration >= 
(
    SELECT MAX(m1.duration)
    FROM Movie m1
    WHERE m1.movie_type = m.movie_type and m1.movie_id != m.movie_id
    GROUP BY m1.movie_type
) --bandaid solution :D
or m.duration = 
(
    SELECT MAX(m1.duration)
    FROM Movie m1
    WHERE m1.movie_type = m.movie_type
    GROUP BY m1.movie_type
) 
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query i. 2
--i. 4 queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator); rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT]
--"all" 2/2
SELECT i.barcode, i.item_name, i.price
FROM Item i
WHERE i.price <= all 
(
    SELECT i1.price
    FROM Item i1
    WHERE i1.item_name = i.item_name
)
order by i.price;
--rewriting with aggregator operators
SELECT i.barcode, i.item_name, i.price
FROM Item i
WHERE i.price <=
(
    SELECT min(i1.price)
    FROM Item i1
    WHERE i1.item_name = i.item_name
    GROUP BY i1.item_name
) --bandaid solution :D
order by i.price;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query i. 3
--i. 4 queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator); rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT]
--"any" 1/2
--selects the details of the cinemas where at least one employee has an email
SELECT DISTINCT c.cinema_id, c.cinema_name
FROM Cinema c
WHERE c.cinema_id = ANY (
    SELECT e.cinema_id
    FROM Employee e
    WHERE e.email IS NOT NULL
);
--rewriting with "in"
SELECT DISTINCT c.cinema_id, c.cinema_name
FROM Cinema c
WHERE c.cinema_id in (
    SELECT e.cinema_id
    FROM Employee e
    WHERE e.email IS NOT NULL
);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Query i. 3
--i. 4 queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator); rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT]
--"any" 1/2
--find studios that has any movies greater in length than 120 minutes
SELECT DISTINCT s.studio_id, s.studio_name
FROM Studio s
WHERE s.studio_id = ANY (
    SELECT m.studio_id
    FROM Movie m
    WHERE m.duration > 120
);
--rewriting with "not in"
SELECT DISTINCT s.studio_id, s.studio_name
FROM Studio s
WHERE s.studio_id in (
    SELECT m.studio_id
    FROM Movie m
    WHERE m.duration > 120
);
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--a. modify the type of column

create or alter procedure change_person_age
as
begin
    alter table Employee
		alter column age varchar(10);
    -- PRINT 'alter table Employee from int to varchar';
end;

create or alter procedure undo_change_person_age
as
begin
	alter table Employee
		alter column age int;
end;


--b. add/remove a column

create or alter procedure remove_age
as
begin
	alter table Director
		drop column age;
end;

create or alter procedure undo_remove_age
as
begin
	alter table Director
		add age int;
end;

-- c.add/remove a default constraint

create or alter procedure add_default_constraint_salary
as
begin
	alter table Employee
		add constraint default_salary default 2000 for salary;
end;

create or alter procedure undo_add_default_constraint_salary
as
begin
	alter table Employee
		drop constraint default_salary;
end;

--d. add or remove a primary key

create or alter procedure remove_primary_key_item
as
begin
	alter table Item
		drop constraint barcode;
end

create or alter procedure undo_remove_primary_key_item
as
begin
	alter table Item
		add constraint barcode primary key (barcode);
end;

--select name
--from sys.key_constraints;

--e. add/remove a candidate key

create or alter procedure add_candidate_key_shop
as
begin
	alter table Shop
		add constraint shop_id unique (shop_id);
end;

create or alter procedure undo_add_candidate_key_shop
as
begin
	alter table Shop
		drop constraint shop_id;
end;

--f. add/remove foreing key

create or alter procedure undo_add_foreign_key_ticket
as
begin
	alter table Ticket
		drop constraint client_id;
end;

create or alter procedure add_foreign_key_ticket
as
begin
	alter table Ticket
		add constraint client_id foreign key (client_id) references Client(client_personal_id);
end;

--g. create.drop a table

create or alter procedure create_test_table
as
begin
	create table Test_Table
	(
		test_id int not null primary key,
		test_message varchar(50),
		test_name char (30)
	);
end;

create or alter procedure undo_create_test_table
as
begin
	drop table if exists Test_Table;
end;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

execute change_person_age;
execute undo_change_person_age;

execute remove_age;
execute undo_remove_age;

execute add_default_constraint_salary;
execute undo_add_default_constraint_salary;

execute remove_primary_key_item;
execute undo_remove_primary_key_item;

execute add_candidate_key_shop;
execute undo_add_candidate_key_shop;

execute add_foreign_key_ticket;
execute undo_add_foreign_key_ticket;

execute create_test_table;
execute undo_create_test_table;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

create table version_table(
	version int,
	primary key(version)
);

drop table version_table;
insert into version_table values(1);
select * from version_table;

create table procedures_table(
	from_version int,
	to_version int,
	name_procedure varchar(255),
	primary key(from_version, to_version)
);
drop table procedures_table;

insert into procedures_table values (1, 2, 'change_person_age');
insert into procedures_table values (2, 1, 'undo_change_person_age');
insert into procedures_table values (2, 3, 'remove_age');
insert into procedures_table values (3, 2, 'undo_remove_age');
insert into procedures_table values (3, 4, 'add_default_constraint_salary');
insert into procedures_table values (4, 3, 'undo_add_default_constraint_salary');
insert into procedures_table values (4, 5, 'remove_primary_key_item');
insert into procedures_table values (5, 4, 'undo_remove_primary_key_item');
insert into procedures_table values (5, 6, 'add_candidate_key_shop');
insert into procedures_table values (6, 5, 'undo_add_candidate_key_shop');
insert into procedures_table values (6, 7, 'add_foreign_key_ticket');
insert into procedures_table values (7, 6, 'undo_add_foreign_key_ticket');
insert into procedures_table values (7, 8, 'create_test_table');
insert into procedures_table values (8, 7, 'undo_create_test_table');
select * from procedures_table;


create or alter procedure go_to_version(@new_version int) 
as
	declare @current int
	declare @procedurename varchar(255)
	select @current=version from version_table
	--print cast (@current as varchar(50))
	if @new_version > (select max(to_version) from procedures_table)
		raiserror ('version must be betweeen 1 and 8', 10, 1)
	if @new_version < (select min(from_version) from procedures_table)
		raiserror ('version must be betweeen 1 and 8', 10, 1)
	while @current < @new_version begin
		select @procedurename=name_procedure from procedures_table where @current=from_version and @current+1=to_version
		print cast(@procedurename as varchar(50))
		exec (@procedurename)
		set @current=@current+1
		update version_table set version=@current
	end
	while @current > @new_version begin
		select @procedurename=name_procedure from procedures_table where @current=from_version and @current-1=to_version
		exec (@procedurename)
		set @current=@current-1
		update version_table set version=@current
	end; 
 
drop procedure go_to_version;

exec go_to_version 1;
exec go_to_version 2;
exec go_to_version 3;
exec go_to_version 4;
exec go_to_version 5;
exec go_to_version 6;
exec go_to_version 7;
exec go_to_version 8;
exec go_to_version 6;
exec go_to_version 5;
exec go_to_version 4;
exec go_to_version 3;
exec go_to_version 2;
exec go_to_version 1;
exec go_to_version 3;
exec go_to_version 5;
exec go_to_version 7;
exec go_to_version 6;
exec go_to_version 4;
exec go_to_version 2;


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--codul de pe site

if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestRunTables_Tables]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [TestRunTables] DROP CONSTRAINT FK_TestRunTables_Tables
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestTables_Tables]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [TestTables] DROP CONSTRAINT FK_TestTables_Tables
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestRunTables_TestRuns]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [TestRunTables] DROP CONSTRAINT FK_TestRunTables_TestRuns
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestRunViews_TestRuns]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [TestRunViews] DROP CONSTRAINT FK_TestRunViews_TestRuns
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestTables_Tests]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [TestTables] DROP CONSTRAINT FK_TestTables_Tests
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestViews_Tests]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [TestViews] DROP CONSTRAINT FK_TestViews_Tests
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestRunViews_Views]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [TestRunViews] DROP CONSTRAINT FK_TestRunViews_Views
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[FK_TestViews_Views]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [TestViews] DROP CONSTRAINT FK_TestViews_Views
GO


if exists (select * from dbo.sysobjects where id = object_id(N'[Tables]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [Tables]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[TestRunTables]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [TestRunTables]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[TestRunViews]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [TestRunViews]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[TestRuns]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [TestRuns]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[TestTables]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [TestTables]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[TestViews]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [TestViews]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[Tests]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [Tests]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[Views]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [Views]
GO


CREATE TABLE [Tables] (
	[TableID] [int] IDENTITY (1, 1) NOT NULL ,
	[Name] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [TestRunTables] (
	[TestRunID] [int] NOT NULL ,
	[TableID] [int] NOT NULL ,
	[StartAt] [datetime] NOT NULL ,
	[EndAt] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [TestRunViews] (
	[TestRunID] [int] NOT NULL ,
	[ViewID] [int] NOT NULL ,
	[StartAt] [datetime] NOT NULL ,
	[EndAt] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [TestRuns] (
	[TestRunID] [int] IDENTITY (1, 1) NOT NULL ,
	[Description] [nvarchar] (2000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
	[StartAt] [datetime] NULL ,
	[EndAt] [datetime] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [TestTables] (
	[TestID] [int] NOT NULL ,
	[TableID] [int] NOT NULL ,
	[NoOfRows] [int] NOT NULL ,
	[Position] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [TestViews] (
	[TestID] [int] NOT NULL ,
	[ViewID] [int] NOT NULL 
) ON [PRIMARY]

GO

CREATE TABLE [Tests] (
	[TestID] [int] IDENTITY (1, 1) NOT NULL ,
	[Name] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [Views] (
	[ViewID] [int] IDENTITY (1, 1) NOT NULL ,
	[Name] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 
) ON [PRIMARY]
GO


ALTER TABLE [Tables] WITH NOCHECK ADD 
	CONSTRAINT [PK_Tables] PRIMARY KEY  CLUSTERED 
	(
		[TableID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [TestRunTables] WITH NOCHECK ADD 
	CONSTRAINT [PK_TestRunTables] PRIMARY KEY  CLUSTERED 
	(
		[TestRunID],
		[TableID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [TestRunViews] WITH NOCHECK ADD 
	CONSTRAINT [PK_TestRunViews] PRIMARY KEY  CLUSTERED 
	(
		[TestRunID],
		[ViewID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [TestRuns] WITH NOCHECK ADD 
	CONSTRAINT [PK_TestRuns] PRIMARY KEY  CLUSTERED 
	(
		[TestRunID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [TestTables] WITH NOCHECK ADD 
	CONSTRAINT [PK_TestTables] PRIMARY KEY  CLUSTERED 
	(
		[TestID],
		[TableID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [TestViews] WITH NOCHECK ADD 
	CONSTRAINT [PK_TestViews] PRIMARY KEY  CLUSTERED 
	(
		[TestID],
		[ViewID]
	)  ON [PRIMARY]
GO

ALTER TABLE [Tests] WITH NOCHECK ADD 
	CONSTRAINT [PK_Tests] PRIMARY KEY  CLUSTERED 
	(
		[TestID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [Views] WITH NOCHECK ADD 
	CONSTRAINT [PK_Views] PRIMARY KEY  CLUSTERED 
	(
		[ViewID]
	)  ON [PRIMARY] 
GO

ALTER TABLE [TestRunTables] ADD 
	CONSTRAINT [FK_TestRunTables_Tables] FOREIGN KEY 
	(
		[TableID]
	) REFERENCES [Tables] (
		[TableID]
	) ON DELETE CASCADE  ON UPDATE CASCADE ,
	CONSTRAINT [FK_TestRunTables_TestRuns] FOREIGN KEY 
	(
		[TestRunID]
	) REFERENCES [TestRuns] (
		[TestRunID]
	) ON DELETE CASCADE  ON UPDATE CASCADE 
GO

ALTER TABLE [TestRunViews] ADD 
	CONSTRAINT [FK_TestRunViews_TestRuns] FOREIGN KEY 
	(
		[TestRunID]
	) REFERENCES [TestRuns] (
		[TestRunID]
	) ON DELETE CASCADE  ON UPDATE CASCADE ,
	CONSTRAINT [FK_TestRunViews_Views] FOREIGN KEY 
	(
		[ViewID]
	) REFERENCES [Views] (
		[ViewID]
	) ON DELETE CASCADE  ON UPDATE CASCADE 
Go

ALTER TABLE [TestTables] ADD 
	CONSTRAINT [FK_TestTables_Tables] FOREIGN KEY 
	(
		[TableID]
	) REFERENCES [Tables] (
		[TableID]
	) ON DELETE CASCADE  ON UPDATE CASCADE ,
	CONSTRAINT [FK_TestTables_Tests] FOREIGN KEY 
	(
		[TestID]
	) REFERENCES [Tests] (
		[TestID]
	) ON DELETE CASCADE  ON UPDATE CASCADE 
GO

ALTER TABLE [TestViews] ADD 
	CONSTRAINT [FK_TestViews_Tests] FOREIGN KEY 
	(
		[TestID]
	) REFERENCES [Tests] (
		[TestID]
	),
	CONSTRAINT [FK_TestViews_Views] FOREIGN KEY 
	(
		[ViewID]
	) REFERENCES [Views] (
		[ViewID]
	)
GO

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

create or alter procedure add_to_tables (@tableName varchar(50)) as
    if @tableName in (select Name from Tables) begin
        print 'Table already present in Tables'
        return
    end
    if @tableName not in (select TABLE_NAME from INFORMATION_SCHEMA.TABLES) begin
        print 'Table not present in the database'
        return
    end
    insert into Tables (Name) values (@tableName)
GO

create or alter procedure add_to_views (@viewName varchar(50)) as
    if @viewName in (select Name from Views) begin
        print 'View already present in Views'
        return
    end
    if @viewName not in (select TABLE_NAME from INFORMATION_SCHEMA.VIEWS) begin
        print 'View not present in the database'
        return
    end
    insert into Views (Name) values (@viewName)
GO

create or alter procedure add_to_tests (@testName varchar(50)) as
    if @testName in (select Name from Tests) begin
        print 'Test already present in Tests'
        return
    end
    insert into Tests (Name) values (@testName)
GO

create or alter procedure connect_table_to_test (@tableName varchar(50), @testName varchar(50), @rows int, @pos int) as
    if @tableName not in (select Name from Tables) begin
        print 'Table not present in Tables'
        return
    end
    if @testName not in (select Name from Tests) begin
        print 'Tests not present in Tests'
        return
    end
    if exists(
        select *
        from TestTables T1 join Tests T2 on T1.TestID = T2.TestID
        where T2.Name=@testName and Position=@pos
        ) begin
        print 'Position provided conflicts with previous positions'
        return
    end
    insert into TestTables (TestID, TableID, NoOfRows, Position) values (
        (select Tests.TestID from Tests where Name=@testName),
        (select Tables.TableID from Tables where Name=@tableName),
        @rows,
        @pos
    )
GO

create or alter procedure connect_view_to_test (@viewName varchar(50), @testName varchar(50)) as
    if @viewName not in (select Name from Views) begin
        print 'View not present in Views'
        return
    end
    if @testName not in (select Name from Tests) begin
        print 'Tests not present in Tests'
        return
    end
    insert into TestViews (TestID, ViewID) values (
        (select Tests.TestID from Tests where Name=@testName),
        (select Views.ViewID from Views where Name=@viewName)
    )
GO

create or alter procedure run_test (@testName varchar(50)) as
    if @testName not in (select Name from Tests) begin
        print 'test not in Tests'
        return
    end
    declare @command varchar(100)

    declare @testStartTime datetime2

    declare @startTime datetime2
    declare @endTime datetime2

    declare @table varchar(50)
    declare @rows int
    declare @pos int

    declare @view varchar(50)

    declare @testId int
    select @testId=TestID from Tests where Name=@testName

    declare @testRunId int
    set @testRunId = (select max(TestRunID)+1 from TestRuns)
    if @testRunId is null
        set @testRunId = 0

    declare tableCursor cursor scroll for
        select T1.Name, T2.NoOfRows, T2.Position
        from Tables T1 join TestTables T2 on T1.TableID = T2.TableID
        where T2.TestID = @testId
        order by T2.Position

    declare viewCursor cursor for
        select V.Name
        from Views V join TestViews TV on V.ViewID = TV.ViewID
        where TV.TestID = @testId

    set @testStartTime = sysdatetime()
    open tableCursor
    fetch last from tableCursor into @table, @rows, @pos
    while @@FETCH_STATUS = 0 begin
        exec ('delete from '+ @table)
        fetch prior from tableCursor into @table, @rows, @pos
    end
    close tableCursor

    open tableCursor

    SET IDENTITY_INSERT TestRuns ON
    insert into TestRuns (TestRunID, Description, StartAt)values (@testRunId, 'Tests results for: ' + @testName, @testStartTime)
    SET IDENTITY_INSERT TestRuns OFF

    fetch tableCursor into @table, @rows, @pos

    while @@FETCH_STATUS = 0 begin
        set @command = 'populateTable' + @table
        if @command not in (select ROUTINE_NAME from INFORMATION_SCHEMA.ROUTINES) begin
            print @command + 'does not exist'
            return
        end
        set @startTime = sysdatetime()
        exec @command 
        set @endTime = sysdatetime()
        insert into TestRunTables (TestRunID, TableId, StartAt, EndAt) values (@testRunId, (select TableID from Tables where Name=@table), @startTime, @endTime)
        fetch tableCursor into @table, @rows, @pos
    end
    close tableCursor
    deallocate tableCursor

    open viewCursor
    fetch viewCursor into @view
    while @@FETCH_STATUS = 0 begin
        set @command = 'select * from ' + @view
        set @startTime = sysdatetime()
        exec (@command)
        set @endTime = sysdatetime()
        insert into TestRunViews (TestRunID, ViewID, StartAt, EndAt) values (@testRunId, (select ViewID from Views where Name=@view), @startTime, @endTime)
        fetch viewCursor into @view
    end
    close viewCursor
    deallocate viewCursor

    update TestRuns
    set EndAt=sysdatetime()
    where TestRunID = @testRunId
GO

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Tables 

drop table Client;
drop table Cinema;
drop table Movie;

--table with single column primary key and no foreign keys
create table Cinema
(
	cinema_id int not null primary key,
	cinema_name varchar (55) not null,
	cinema_adress varchar (255) not null,
	email varchar (255),
	phone_number char (12) not null,
);

--table with a table with a single-column primary key and at least one foreign key;
create table Client
(
	client_personal_id int not null primary key,--(CNP)
	first_name varchar (60) not null,
	second_name varchar (30) not null,
	cinema_id int not null,
	foreign key (cinema_id)	
		references Cinema(cinema_id)
		on delete cascade
);

--table with multicolumn primary key
create table Movie
(
	movie_id int not null,
	movie_name varchar (255) not null,
	duration int not null,
	movie_type varchar (255) not null,
	primary key (movie_id, movie_name)
);


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Views

--a view with a SELECT statement operating on one table;
create or alter view MovieView as
	select * from Movie
go

--a view with a SELECT statement that operates on at least 2 different tables and contains at least one JOIN operator;
create or alter view ClientCinemaView as
select
    C.client_personal_id,
    C.first_name,
    C.second_name,
    CI.cinema_id,
    CI.cinema_name,
    CI.cinema_adress,
    CI.email,
    CI.phone_number
from
    Client C
join
    Cinema CI ON C.cinema_id = CI.cinema_id;

--a view with a SELECT statement that has a GROUP BY clause, operates on at least 2 different tables and contains at least one JOIN operator.
create or alter view ClientMovieView as
select
    C.cinema_id,
    M.movie_type,
    COUNT(*) AS NumberOfClients,
    AVG(M.duration) AS AverageMovieDuration
from
    Client C
join
    Movie M ON C.cinema_id = M.movie_id
group by
    C.cinema_id, M.movie_type;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Populating

create or alter procedure populateTableMovie (@numrecords int)
as
begin
    declare @counter int = 1;

    while @counter <= @numrecords
    begin
        insert into movie (movie_id, movie_name, duration, movie_type)
        values (
            @counter,
            'movie' + convert(varchar(10), @counter),
            rand() * 180, -- assuming duration is in minutes, generating a random value between 0 and 180 minutes
            case
                when rand() > 0.5 then 'action'
                else 'drama'
            end
        );

        set @counter = @counter + 1;
    end
end;

create or alter procedure populateTableCinema (@numrecords int) as
begin
    declare @counter int = 1;

    while @counter <= @numrecords
    begin
        insert into cinema (cinema_id, cinema_name, cinema_adress, email, phone_number)
        values (
            @counter,
            'cinema' + convert(varchar(10), @counter),
            'address' + convert(varchar(10), @counter),
            'cinema' + convert(varchar(10), @counter) + '@example.com',
            '123-456-789' -- modify as needed based on your requirements
        );

        set @counter = @counter + 1;
    end
end;

create or alter procedure populateTableClient (@numrecords int) as
begin
    declare @counter int = 1;

    while @counter <= @numrecords
    begin
        insert into client (client_personal_id, first_name, second_name, cinema_id)
        values (
            @counter,
            'firstname' + convert(varchar(10), @counter),
            'lastname' + convert(varchar(10), @counter),
            abs(checksum(newid())) % (select count(*) from cinema) + 1
        );

        set @counter = @counter + 1;
    end
end;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

exec add_to_tables 'Movie';
exec add_to_views 'MovieView';
exec add_to_tests 'Test1';
exec connect_table_to_test 'Movie', 'Test1', 1000, 1;
exec connect_view_to_test 'MovieView', 'Test1';
exec run_test 'Test1';

exec add_to_tests 'Test2';
exec add_to_tables 'Cinema';
exec add_to_tables 'Client';
exec add_to_views 'ClientCinemaView';
exec connect_table_to_test 'Cinema', 'Test2', 1000, 2;
exec connect_table_to_test 'Client', 'Test2', 1000, 3;
exec connect_view_to_test 'ClientCinemaView', 'Test2';
exec run_test 'Test2';

exec add_to_tables 'Client';
exec add_to_tables 'Movie';
exec add_to_views 'ClientMovieView';
exec add_to_tests 'Test3';
exec connect_table_to_test 'Client', 'Test3', 1000, 4;
exec connect_table_to_test 'Movie', 'Test3', 1000, 5;
exec connect_view_to_test 'ClientMovieView', 'Test3';
exec run_test 'Test3'

select * from TestRuns
select * from TestRunTables
select * from testRunViews

delete from Tables;
delete from TestViews;
delete from Tests;
delete from Views;
Delete from TestRuns;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


drop view if exists view0

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

drop table if exists Company;
drop table if exists Item;
drop table if exists Shop;

create table Item (
	item_id int primary key,
	price int unique, --nonclustered, unique, unique key
	number int --nonclustered unique on PRIMARY
)

create table Shop (
    shop_id int primary key, --clustered, unique, primary key
    size int --nonclustered locatred on PRIMARY
)

create table Company (
    company_id int primary key, --clustered, unique, primary key
    item_id int,
    shop_id int,
	foreign key (item_id) references Item (item_id),
	foreign key (shop_id) references Shop (shop_id)
)

delete from Item;
delete from Shop;
delete from Company;

select * from Item
select * from Shop
select * from Company

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

drop procedure if exists populate_tables

go
create or alter procedure populate_tables(@nrRows int) AS
	declare @counter int
	set @counter=1
	while @counter <= @nrRows
	begin
		insert into Item values (@counter, @counter, (CAST(RAND()*@nrRows AS INT))%@nrRows+1)
		insert into Shop values (@counter, (CAST(RAND()*@nrRows AS INT))%@nrRows+1)
		set @counter = @counter+1
	end

	set @counter=1
	while @counter <= @nrRows
	begin
		insert into Company values (@counter, (CAST(RAND()*@nrRows AS INT))%@nrRows+1, (CAST(RAND()*@nrRows AS INT))%@nrRows+1)
		set @counter = @counter+1
	end
go

exec populate_tables 1000

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

create nonclustered index indexItem on Item(number);
drop index if exists indexItem on Item;

create nonclustered index indexShop on Shop(size);
drop index if exists indexShop on Shop;

exec sp_helpindex Item;
exec sp_helpindex Shop;
exec sp_helpindex Company;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--clustered index scan
select * from Item where item_id%5 = 0 

--clustered index seek
select * from Item where item_id > 100 

--nonclustered index scan
select price from Item where price%3 = 1

--nonclustered index seek
select price from Item where price >= 25 and price <= 50

--key lookup
select price from Item where number >= 200 and price = 50;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--without nonclustered key: 0.0058
select * from Shop where size=10

--with nonclustered key: 0.0032
create nonclustered index indexItem on Shop(size);
drop index if exists indexItem on Shop;

select * from Shop where size=10

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

create or alter view test_view as
	select Company.company_id, Item.item_id, Item.price, Shop.shop_id, Shop.size
	from Company
	join Item on Company.item_id = Item.item_id
	join Shop on Company.shop_id = Shop.shop_id;

select * from test_view

create nonclustered index indexItem on Item(number);
drop index if exists indexItem on Item;
create nonclustered index indexShop on Shop(size);
drop index if exists indexShop on Shop;
select * from test_view
