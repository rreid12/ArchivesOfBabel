/*
	Where was the book located in the library that Kendall
	Jenner took out on September 1st, 2016?
	
	4-pt Query
	
	1-pt - Motivation/Justification - When a book gets returned, an 
	employee can usethis query to place the book back on the proper shelf
	2-pts - >2 tables joined
	1-pt - where/having conditions not for joins
*/

SELECT 
	Book.Title AS Book_Title, 
	Location.Section AS Section, Location.RowNumber AS Row, Location.Shelf AS Shelf
FROM 
	(((Member INNER JOIN Loan ON Member.MemberID=Loan.MemberID)
	INNER JOIN BookLine ON Loan.LoanID=BookLine.LoanID)
	INNER JOIN Book ON BookLine.BookID=Book.BookID)
	INNER JOIN Location ON Book.LocationID=Location.LocationID
WHERE
	Member.FirstName LIKE 'Kendall' AND
	Member.LastName LIKE 'Jenner' AND
	Loan.LoanDate LIKE '2016-09-01';