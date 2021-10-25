; functions

; 2
; a
(defun double (n) (* 2 n))

(defun list-double (list)
    (mapcar 'double list)
)

; b
(defun sign (n)
    (cond   ((> n 0) 1)
            ((< n 0) -1)
            ((= n 0) 0)
    )
)

; c
(defun list-sign (list)
    (mapcar 'sign list)
)


; test
(format t "Aufgabe 2~%")
(format t "(list-double '(1 2 3 4)) => ~S~%" (list-double '(1 2 3 4)))
(format t "(sign -62) => ~S~%" (sign -62))
(format t "(sign 62) => ~S~%" (sign 62))
(format t "(sign 0) => ~S~%" (sign 0))
(format t "(list-sign '(0 -62 62)) => ~S~%" (list-sign '(0 -62 62)))

; 3
(defun map-list (f lst)
    (if (null lst) nil
        ;; else
        (cons (funcall f (car lst)) (map-list f (cdr lst)))
    )
)

(defun list-double (list) (map-list #'double list))
(defun list-sqr (list) (map-list #'sqrt list))
(defun list-sign (list) (map-list #'sign list))

; test
(format t "~%Aufgabe 3~%")
(format t "(list-sign '(5 2 -3 -1 0 3 -2)) => ~S~%" (list-sign '(5 2 -3 -1 0 3 -2)))
(format t "(list-double '(1 2 3 4)) => ~S~%" (list-double '(1 2 3 4)))
(format t "(list-sqr '(36 9 4)) => ~S~%" (list-sqr '(36 9 4)))


; 4
(defun list-sum (list) (apply #'+ list))
(defun list-mult (list) (apply #'* list))

(defun all-true (list) 
    (if (null list)
        t
        (and (car list) (all-true (cdr list)))))


; test
(format t "~%Aufgabe 4~%")
(format t "(list-sum '(36 9 4)) => ~S~%" (list-sum '(36 9 4)))
(format t "(list-mult '(2 3 4)) => ~S~%" (list-mult '(2 3 4)))

(format t "(all-true '()) => ~S~%" (all-true '()))
(format t "(all-true '(34 hallo (7))) => ~S~%" (all-true '(34 hallo (7))))
(format t "(all-true '(34 hallo ())) => ~S~%" (all-true '(34 hallo ())))