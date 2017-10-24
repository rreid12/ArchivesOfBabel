/*
	Shows books and their genres which have been loaned more than once 
	recently, based on the loans still in the database
	
	6-pt Query
	
	1-pt - Motivation/Justification - helps librarians/members see the 
	most popular books
	2-pts - >2 tables joined
	1-pt - Grouping
	1-pt - Having condition not for join
	1-pt - Order fields
*/

SELECT Book.Title as book_title, Location.Genre AS book_genre, COUNT(Loan.LoanID) AS number_of_loans
FROM Location INNER JOIN Book ON Location.LocationID = Book.BookID
	INNER JOIN BookLine ON Book.BookID = BookLine.BookID
	INNER JOIN Loan ON BookLine.LoanID = Loan.LoanID
GROUP BY Book.BookID
HAVING COUNT(Loan.LoanID) > 1
ORDER BY COUNT(Loan.LoanID) DESC, Book.Title ASC;