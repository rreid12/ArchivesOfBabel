/*
	Lists all employees by first name and last name that lent out books
	and how many books they each lent out. Ordered by amount of books
	lent out. This query helps management see which employees have been
	most productive.
	
	6-pt Query
	
	1 pt - Motivation/Justification 
	2-pts - >2 tables joined
	1-pt - Aggregate function
	1-pt - Grouping
	1-pt - Ordering fields
	
*/

SELECT
	Employee.EmployeeID AS ID, Employee.FirstName AS First_Name, Employee.LastName AS Last_Name,
	COUNT(BookLine.BookLineID) as Books_Lent
FROM
	(Employee INNER JOIN Loan ON Employee.EmployeeID=Loan.LibrarianID)
	INNER JOIN BookLine ON Loan.LoanID=BookLine.LoanID
GROUP BY
	Employee.EmployeeID
ORDER BY Books_Lent DESC;