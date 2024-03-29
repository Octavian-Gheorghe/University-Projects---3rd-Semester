;%===============================================================================================================================================================================%

; a) Write a function to return the union of two sets.

; removeFirstOcc(l1l2...ln, elem) =
; = nil, if n = 0
; = l2...ln, if l1 = elem
; = {l1} U removeFirstOcc(l2...ln, elem) , otherwise

(defun removeFirstOcc(l elem)
  (cond
    ((null l) nil)
    ((equal (car l) elem) (cdr l))
    (t (cons (car l) (removeFirstOcc (cdr l) elem)))
  )
)


; unionSets(l1l2...ln, p1p2...pm) = 
; = nil if n = 0 and m = 0
; = l1l2...ln, if m = 0
; = p1p2...pm, if n = 0
; = {l1} U unionSets(l2...ln, removeFirstOcc(p1p2...pm, l1)) , otherwise

(defun unionSets(l1 l2)
  (cond
    ((and (null l1) (null l2)) nil)
    ((null l1) l2)
    ((null l2) l1)
    (t (cons (car l1) (unionSets (cdr l1) (removeFirstOcc l2 (car l1)))))
  )
)

;%===============================================================================================================================================================================

; b) Write a function to return the product of all numerical atoms in a list, at any leve

; product(l1l2...ln) = 
; = nil, if n = 0
; l1 * product(l2...ln), if l1 is a number
; product(content(l1)) * product(l2...ln), if l1 is a list
; proudct (l2...ln), otherwise

(defun product(l)
(cond
  ((null l) 1)
  ((numberp (car l)) (* (car l) (product (cdr l))))
  ((listp (car l)) (* (product (car l)) (product (cdr l))))
  (t (product (cdr l)))
))

;%===============================================================================================================================================================================%

;c) Write a function to sort a linear list with keeping the double values.

; insertOk(l1l2...ln, elem) = 
; = list(elem), if n = 0
; = {elem} U l1l2...ln, if elem < l1 
; = {l1} U insertOk(l2...ln, elem) , otherwise

(defun insertOk(l elem)
  (cond
    ((null l) (list elem))
    ((< elem (car l)) (cons elem l))
    (t (cons (car l) (insertOk (cdr l) elem)))
  )
)

; mySort(l1l2...ln) = 
; = nil , if n = 0
; = mySort(insertOk(l2...ln, l1)) , otherwise

(defun mySort(l)
  (cond
    ((null l) nil)
    (t (insertOk (mySort (cdr l)) (car l)))
  )
)

;%===============================================================================================================================================================================%

; d) Build a list which contains positions of a minimum numeric element from a given linear list.

; myMin(a, b) = 
; = nil , if a is not a number and b is not a number
; = a , if b is not a number
; = b , if a is not a number
; = a , if a < b
; = b , otherwise

(defun myMin(a b)
  (cond
    ((and (not (numberp a)) (not (numberp b))) nil)
    ((not (numberp a)) b)
    ((not (numberp b)) a)
    ((< a b) a)
    (t b)
  )
)


; minList(l1l2...ln) = 
; = l1 , is n = 1 and l1 is an atom
; = myMin(minList(l1), minList(l2...ln)), if l1 is a list; = myMin(l1, minList(l2...ln)), otherwise

(defun minList(l)
  (cond 
    ((and (null (cdr l)) (atom (car l))) (car l))
    ((listp (car l)) (myMin (minList (car l)) (minList (cdr l))))
    (t (myMin (car l) (minList (cdr l))))
  )
)


; minPos(l1l2...ln, min, pos) = 
; = nil, if n = 0
; = pos U minPos(l2...ln, min, pos + 1) , if l1 = min
; = minPos(l2...ln, min, pos + 1), otherwise

(defun minPos (l min pos)
  (cond
    ((null l) nil)
    ((equal (car l) min) (cons pos (minPos (cdr l) min (+ 1 pos))))
    (t (minPos (cdr l) min (+ 1 pos)))
  )
)


(defun mainD (l)
  (minPos l (minList l) 0)
)

;%===============================================================================================================================================================================%
