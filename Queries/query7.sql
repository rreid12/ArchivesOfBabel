/*
	This query will add a book to the book table
	and also add the necessary entries into the 
	foreign keys. 
*/

INSERT INTO Book
VALUES (
	(select MAX(Book.bookid) from book)+1,
	'The Catcher and the Rye',							--Book Title
	5, 													--Amount of Books
	(select MAX(Location.LocationId) from Location)+1, 	--New LocationID
	5.00, 												--Book Price
	(SELECT MAX(Author.AuthorID) FROM Author) +1);		--New Author ID
	
INSERT INTO Location
VALUES (
	(SELECT MAX(Location.LocationID)+1 FROM Location),	--Creates New Location
	'Nonfiction',										--Book Genre
	1,													--New Section Number
	1,													--New Row Number
	1);													--New Shelf Number
	
INSERT INTO Author
	Values (
		(SELECT MAX(Author.AuthorID)+1 FROM Author),	--Creates new author
		'J.D.',											--Book Author First Name
		'Salinger');									--Book Author Last Name