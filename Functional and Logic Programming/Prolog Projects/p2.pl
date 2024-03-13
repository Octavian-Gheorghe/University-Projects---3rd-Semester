%===============================================================================================================================================================================%
% a. Define a predicate to determine the longest sequences of consecutive even numbers
%   (if exist more maximal sequences one of them).

% even(n) = 
% = true, if n % 2 = 0
% = false, otherwise

% even(N:number)
% (i)

%N - number

even(N):-N mod 2 =:= 0.
odd(N):- N mod 2 =:= 1.

% myLengthLab3(l1l2...ln) = 
% = 0, if n = 0
% = 1 + myLengthLab3(l2...ln)

% myLengthLab3(L:list, R:number)
% (i,o)

%R1 - calculated result
%R - result
%T - tail

myLengthLab3([],0).
myLengthLab3([_|T],R1):-
    myLengthLab3(T,R),
    R1 is R+1.


% myAppend(l1l2...ln, elem) = 
% = [elem], if n = 0
% = {l1} U myAppend(l2...ln, elem), otherwise

% myAppend(L:list, E:number, R:list)
% (i,i,o)

%E - element to be added
%H - head
%T - tail
%R - result

myAppend([],E,[E]).
myAppend([H|T],E,[H|R]):-
    myAppend(T,E,R).

% consecutive(l1l2...ln, p1p2...pm, r1r2...rk) = 
% = p1p2...pm, if n = 0 and m >= k
% = r1r2...rm, if n = 0 and k > m
% = consecutive(l2...ln, p1p2...pm, {l1} U r1r2...rk), if even(l1) = true
% = consecutive(l2...ln, p1p2...pm, []), if even(l1) = false and m >= k
% = consecutive(l2...ln, r1r2...rl, []), if even(l1) = false and k > m

% consecutive(L:list, C:list, AUX:list, R:list)
% (i,i,i,o)

%AUX - auxilary list of consecutive even elements
%C - currently definitive (currently longest) list of consecutive even elements
%RC - nr of elements in C
%RAUX - nr of elements in AUX
%H - head
%T - tail

consecutive([],C,AUX,C):- %the original list is empty, time to check the length!
    myLengthLab3(C,RC),
    myLengthLab3(AUX,RAUX),
    RC >= RAUX. 
consecutive([],C,AUX,AUX):-
    myLengthLab3(C,RC),
    myLengthLab3(AUX,RAUX),
    RC < RAUX.
consecutive([H|T],C,AUX,R):-%even
    even(H),
    !,
    myAppend(AUX,H,RAUX),
    consecutive(T,C,RAUX,R).
consecutive([_|T],C,AUX,R):-%odd, the C list is longer
    myLengthLab3(C,RC),
    myLengthLab3(AUX,RAUX),
    RC >= RAUX,
    consecutive(T,C,[],R).
consecutive([_|T],C,AUX,R):-%odd, the Aux list is longer
    myLengthLab3(C,RC),
    myLengthLab3(AUX,RAUX),
    RC < RAUX,
    consecutive(T,AUX,[],R).
    
%===============================================================================================================================================================================%
% b. For a heterogeneous list, formed from integer numbers and list of numbers,
%    define a predicate to replace every sublist with the longest sequences of even
%    numbers from that sublist.
% Eg.: [1, [2, 1, 4, 6, 7], 5, [1, 2, 3, 4], 2, [1, 4, 6, 8, 3], 2, [1, 5], 3] =>
%    [1, [4, 6], 5, [2], 2, [4, 6, 8], 2, [], 3]


% heteroList(l1l2...ln) = 
% = [], if n = 0
% = consecutive(l1, [], []) U heteroList(l2...ln), if l1 is a list
% = {l1} U heteroList(l2...ln), otherwise

% heteroList(L:list, R:list)
% (i,o)

heteroList([],[]).
heteroList([H|T],[RC|R]):-
    is_list(H),
    !,
    consecutive(H,[],[],RC),
    heteroList(T,R).
heteroList([H|T], [H|R]):-
    heteroList(T,R).
%===============================================================================================================================================================================%