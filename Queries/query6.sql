/*
This query brings up information for employees based on the last name of a member. 
This is useful for librarians to look up loan/return information, either for the 
member themselves, or for internal use.

5-pt Query

1-pt - Motivation/justification - used in the GUI to display the overall use of the database
2-pt - >2 tables joined
1-pt - >1 ordering fields
1-pt - non-aggregation functions in select/where
*/
SELECT Book.Title, Author.FirstName, Author.LastName, Loan.LoanDate, Member.FirstName, Member.LastName
FROM Book INNER JOIN Author ON Book.AuthorID = Author.AuthorID 
INNER JOIN BookLine ON Book.BookID = BookLine.BookID
INNER JOIN Loan ON BookLine.LoanID = Loan.LoanID
INNER JOIN Member ON Loan.MemberID = Member.MemberID
WHERE Member.LastName LIKE 'Mencken'
ORDER BY Loan.LoanDate DESC, Author.LastName DESC, Book.Title DESC;