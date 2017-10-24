/*
	This query can tell users if the ArchivesOfBabel is the right library
	for them. This query could potentially be used for the GUI of the 
	database, allowing users to enter their favorite genres and then see how
	many books this library has available for those genres.
	
	6-pt Query
	
	1-pt - Motivation/Justification
	1-pt - 2 tables joined
	1-pt - Aggregate function
	1-pt - Grouping
	1-pt - Ordering fields >1
	1-pt - Non-aggregation functions in where
	
*/

SELECT Location.Genre AS genre, COUNT(Book.BookID) AS number_of_books
FROM Location INNER JOIN Book ON Location.LocationID = Book.LocationID
WHERE Location.Genre LIKE 'Lifestyle' OR
	Location.Genre LIKE 'Food' OR
	Location.Genre LIKE 'Mystery'
GROUP BY Location.Genre
ORDER BY number_of_books DESC, genre DESC;
