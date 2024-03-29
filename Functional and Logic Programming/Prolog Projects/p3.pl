% 14. Write a program to generate the list of all subsets of sum S with the elements of a list (S - given).
%     Eg: [1,2,3,4,5,6,10] si S=10 => [[1,2,3,4], [1,4,5], [2,3,5], [4,6], [10]] (not necessary in this order)

%===============================================================================================================================================================================%
% subsets(l1l2...ln) = 
% = [], if n = 0
% = {l1} U subsets(l2...ln), if n >= 1
% = subsets(l2...ln), if n >= 1

% subsets(L:list , R:list)
% (i,o)

subsets([],[]).
subsets([H|T],[H|R]):-
    subsets(T,R).
subsets([_|T],R):-
    subsets(T,R).


% sum(l1l2...ln) = 
% = 0, if n = 0
% = l1 + sum(l2...ln), otherwise

% sum(L:list, R:number)
% (i,o)

sum([],0).
sum([H|T],R1):-
    sum(T,R),
    R1 is R + H.

% checkSum(s, n)
% = true, if s == n
% = false, otherwise

% checkSum(S:number, N:number)
% (i,i)

checkSum(S,N):-S=:=N.

% oneSol(L:list, R:list)
% (i,o)

oneSol(L,S,R):-
    subsets(L,R),
    sum(R,RR),
    checkSum(RR,S).

allSols(L,S,R):-
    findall(RPartial, oneSol(L,S,RPartial), R).
%===============================================================================================================================================================================%