use Practic

create table tablou
(
	fk1 int,
	fk2 int, 
	c1 varchar (250),
	c2 varchar (25),
	c3 float, 
	c4 int, 
	c5 varchar(250)
);

Insert into tablou
    select 5, 9, 'Spider Plant', 'Indirect light', 250.99,190,'Moderate'
union all
select 2, 5, 'Succulents', 'Sunny and well-drained', 199.99,230,'Low'
union all
select 7, 3, 'Fiddle Leaf Fig', 'Indirect light', 219.49,185,'Moderate'
union all
select 1, 9, 'Lavender', 'Sunny and well-drained', 189.99,205,'Low'
union all
select 5,3, 'Snake Plant', 'Indirect light', 205.99,183,'Low'
union all
select 10, 5, 'Peace Lily', 'Shade to indirect light', 349.59,203,'High'
union all
select 3, 3, 'Rosemary Plant', 'Sunny and well-drained', 197.99,205,'Low'
union all
select 6, 9, 'Bamboo', 'Shade to indirect light', 195.49,303,'Moderate'
union all
select 9, 9, 'Monstera Deliciosa', 'Indirect light', 234.55,340,'Moderate'
union all
select 4,6, 'Orchid', 'Shade to indirect light', 189.99,280,'High'
union all
select 5, 8, 'Aloe Vera', 'Sunny and well-drained', 248.99,196,'Low'
union all
select 9, 5, 'Pothos Plant', 'Direct light', 199.99,265,'Low'

select * from tablou
delete from tablou

--1. 
Select c2, c5, min(c3) MinC3, AVG(c4) avgc4
from tablou
where c3 > 190 or c1 like '%e%'
group by c5, c2
having min(c3)>=189.99

Select *
from tablou
where c3 > 190 or c1 like '%e%'

--2.
select * from
(select fk1, fk2, c4*10-c3 Calc
from tablou
where fk1 < fk2) r1
right join
(select fk1, fk2, c3
from tablou
where c1 like '%Plant' or c4<200) r2
on r1.fk1 = r2.fk1 and r1.fk2 = r2.fk2

--3.
create or alter trigger tr
on tablou
for update
as

PRINT 'Deleted Table:';
SELECT * FROM deleted;

PRINT 'Inserted Table:';
SELECT * FROM inserted;

declare @total int = 0
select @total = d.c4*10 - i.c4
from deleted d
inner join inserted i
on d.fk1 = i.fk1 and d.fk2 = i.fk2
where d.c4 > i.c4 or d.c4+50 < i.c4
print @total

update tablou
set c4 = 250
where fk1 <= fk2


--1.
CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY,
    CategoryName NVARCHAR(255) UNIQUE NOT NULL,
    CategoryDescription NVARCHAR(MAX)
);

CREATE TABLE Locations (
    LocationID INT PRIMARY KEY,
    LocationName NVARCHAR(255) UNIQUE NOT NULL,
	LocationDescription NVARCHAR(255)
);

CREATE TABLE Plants (
    PlantID INT PRIMARY KEY,
    PlantName NVARCHAR(255) UNIQUE NOT NULL,
    PlantDescription NVARCHAR(255),
    AcquisitionDate DATETIME,
    LastWateredDate DATETIME,
    LocationID INT,
    FOREIGN KEY (LocationID) REFERENCES Locations(LocationID)
);

CREATE TABLE Images (
    ImageID INT PRIMARY KEY,
    ImagePath NVARCHAR(255) NOT NULL,
    Caption NVARCHAR(255),
    UploadDate DATETIME
);

CREATE TABLE PlantImages (
    PlantID INT,
    ImageID INT,
    PRIMARY KEY (PlantID, ImageID),
    FOREIGN KEY (PlantID) REFERENCES Plants(PlantID),
    FOREIGN KEY (ImageID) REFERENCES Images(ImageID)
);

CREATE TABLE PlantCategories (
    PlantID INT,
    CategoryID INT,
    PRIMARY KEY (PlantID, CategoryID),
    FOREIGN KEY (PlantID) REFERENCES Plants(PlantID),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

CREATE TABLE CareHistory (
    CareID INT PRIMARY KEY,
    PlantID INT,
    WateringDate DATETIME,
    Notes NVARCHAR(255),
    FOREIGN KEY (PlantID) REFERENCES Plants(PlantID)
);

--2.

CREATE OR ALTER PROCEDURE AddPlant
    @PlantId INT,
    @PlantName NVARCHAR(255),
    @Description NVARCHAR(255),
    @AcquisitionDate DATETIME,
    @LastWateredDate DATETIME,
    @LocationID INT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM Plants WHERE PlantName = @PlantName)
    BEGIN
        UPDATE Plants
        SET AcquisitionDate = @AcquisitionDate
        WHERE PlantName = @PlantName;
    END
    ELSE
    BEGIN
        INSERT INTO Plants (PlantId, PlantName, PlantDescription, AcquisitionDate, LastWateredDate, LocationID)
        VALUES (@PlantId, @PlantName, @Description, @AcquisitionDate, @LastWateredDate, @LocationID);
    END
END;

EXEC AddPlant
	@PlantId = 1,
    @PlantName = 'Rose',
    @Description = 'Beautiful flowering plant',
    @AcquisitionDate = '2024-07-16 08:00:00',
    @LastWateredDate = '2024-01-20 08:00:00',
    @LocationID = 1; 

EXEC AddPlant
	@PlantId = 2,
    @PlantName = 'Rose1',
    @Description = 'Beautiful flowering plant',
    @AcquisitionDate = '2024-05-16 08:00:00',
    @LastWateredDate = '2024-01-20 08:00:00',
    @LocationID = 1; 


select * from Plants

select * from Locations

insert into Locations
	select 1, 'A', 'a'

--3

CREATE OR ALTER VIEW PlantCountByLocation AS
SELECT
    L.LocationID,
    L.LocationName,
    COUNT(P.PlantID) AS TotalPlants,
    MAX(P.AcquisitionDate) AS LatestAcquisitionDate
FROM
    Locations L
LEFT JOIN -- we want all locations, even if they don't have a plant
    Plants P ON L.LocationID = P.LocationID
GROUP BY
    L.LocationID, L.LocationName;

SELECT * FROM PlantCountByLocation;

--4

CREATE OR ALTER FUNCTION GetPlantWithMinImagesAndRecentCare
    (@MinImageCount INT)
RETURNS NVARCHAR(255)
AS
BEGIN
    DECLARE @PlantName NVARCHAR(255);

    SELECT TOP 1 @PlantName = P.PlantName
    FROM Plants P
    INNER JOIN PlantImages P1 ON P.PlantID = P1.PlantID
    INNER JOIN CareHistory CH ON P.PlantID = CH.PlantID
    GROUP BY P.PlantID, P.PlantName
    HAVING COUNT(P1.ImageID) >= @MinImageCount
    ORDER BY MAX(CH.WateringDate) DESC, COUNT(P1.ImageID) ASC;

    RETURN @PlantName;
END;

DECLARE @MinImageCount INT = 3; -- Set the minimum image count
DECLARE @PlantName NVARCHAR(255);
SET @PlantName = dbo.GetPlantWithMinImagesAndRecentCare(@MinImageCount);
PRINT 'Plant with the minimum ' + CAST(@MinImageCount AS NVARCHAR(10)) + ' images and cared for most recently: ' + ISNULL(@PlantName, 'No plant found.');

create table S
(fk1 int, 
fk2 int,
primary key(fk1,fk2),
a varchar(20),
b varchar(20),
c varchar(20),
d int,
e int);

delete from S

insert into s values(1,1,'a1','b1','c1',7,2)
insert into s values(2,2,'a_','b3','c1',5,2)
insert into s values(3,3,'a2','b1','c2',null,2)
insert into s values(4,1,'a3','b3','c2',null,100)
insert into s values(5,2,'a3','b3','c2',null,100)

select distinct * from S s1 left join S s2 on s1.fk1 = s2.fk1