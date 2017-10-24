/*
	This query can tell employees which members are the worst offenders
	of returning books late. It totals up the number of days each member
	has been late on all of their returns.
	
	5-pt Query
	
	1-pt - Motivation/Justification - 
	1-pt - 2 tables joined
	1-pt - Aggregate function
	1-pt - Grouping
	1-pt - >1 ordering fields
*/
SELECT Member.MemberID, Member.FirstName, Member.LastName, sum(Return.DaysLate) AS days_late
FROM Member INNER JOIN Return ON Member.MemberID = Return.MemberID
WHERE Return.LateFee > 0
GROUP BY Member.MemberID
ORDER BY days_late DESC, Member.LastName ASC, Member.MemberID ASC;
