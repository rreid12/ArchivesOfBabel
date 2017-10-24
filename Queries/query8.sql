INSERT INTO DVD
VALUES 
	(
		'Boondock Saints',									--Title of DVD
		'Troy Duffy',										--Producer of DVD
		(SELECT MAX(DVDID)+1 FROM DVD), 					--Gives new DVDID
		20.00,												--Price of DVD
		5,													--Quantity of DVDs
		(SELECT MAX(LocationID)+1 FROM Location)			--Gives new LocationID
	);
	
INSERT INTO LOCATION
VALUES
	(
		(SELECT MAX(Location.LocationID)+1 FROM Location),	--New Location 
		'Action',											--Movie Genre
		2,													--Section Number
		2,													--Row Number
		2													--Shelf Number
	);

