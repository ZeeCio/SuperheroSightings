drop database if exists SuperheroDB;

create database SuperheroDB;

use SuperheroDB;

-- Superhero
create table Superhero(
	SuperheroId int primary key auto_increment,
    SuperheroName varchar(50) not null,
    SuperheroDescription varchar(255)
);

-- Superpower
create table Superpower(
	SuperpowerId int primary key auto_increment,
    SuperpowerName varchar(50) not null,
    SuperpowerDescription varchar(255)
);

-- SuperheroSuperpower
create table SuperheroSuperpower(
	SuperheroId int not null,
    SuperpowerId int not null,
    PRIMARY KEY pk_SuperheroSuperpower (SuperheroId, SuperpowerId),
    foreign key fk_SuperheroSuperpower_SuperheroId (SuperheroId)
		references Superhero(SuperheroId),
	foreign key fk_SuperheroSuperpower_SuperpowerId (SuperpowerId)
		references Superpower(SuperpowerId)
);


-- Location
create table Location(
	LocationId int primary key auto_increment,
    LocationName varchar(50) not null,
    Latitude DECIMAL(10,8) not null,
    Longitude DECIMAL(11,8) not null,
    LocationDescription varchar(255),
    AddressInfo varchar(255)
);

-- Sighting
create table Sighting(
	SightingId int primary key auto_increment,
	SuperheroId int not null,
    LocationId int not null,
    Date datetime not null,
    foreign key fk_Sighting_SuperheroId (SuperheroId)
		references Superhero(SuperheroId),
	foreign key fk_Sighting_LocationId (LocationId)
		references Location(LocationId)
);

insert into Superhero values
 ("1","Spiderman","A weirdo with red leggings and cobweb issues"),
 ("2","Captain America","Crazy american addicted to blue"),
 ("3","Thor","Tall hairy guy with daddy issues"),
 ("4","Hulk","Green well waxed angry doctor");
 
 
 Insert into Superpower values 
 ("1","Climbing and web-shooting","Ability to stick to and climb walls and othet surfaces, uses self designed web-shooters allowing him to fire and swing from sticky webs"),
 ("2","Strength","carries a concave shield composed of vibranium and steel alloy"),
 ("3","Super strength","Fighting ability, super strength, and nigh invulnerability"),
 ("4","superhuman strength","superhuman strength and nothing really hurts him -- from bullets to tank shells to a bomb. He can also jump a really long distance and has the brain size of a walnut.");

insert into SuperheroSuperpower values 
(1,1),
(2,2),
(3,3),
(4,4);

Insert into Location values
 ("2","New York",40.7128,74.0060, "On the street", "New York"),
 ("3","Tokio",35.6762,139.6503,"On the street","Tokio"),
 ("4","Toronto",43.6532, 79.3832,"On the street","Toronto"),
 ("5","Istanbul",41.0082,28.9784,"On the street","Istanbul");
 

select * from sighting;
