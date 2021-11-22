(load "more-functional.lisp")

; Aufgabe 1
(defun read-json (file) 
    (with-open-file (stream file :direction :input) (read stream))
)

;(print (read-json "tasks.lisp"))

; Aufgabe 2
(print "Aufgabe 2")
(defun getprop-fn (key list) 
    (cdr (assoc key list)))


(print (getprop-fn :result '((:RESULT . "SUCCESS") (:INTERFACE-VERSION . "1.0.3"))))
(print (getprop-fn :requested (read-json "tasks.lisp")))

; Aufgabe 3
(print "Aufgabe 3")
(setfun getprop (curry-n #'getprop-fn 2))

(print (getprop :tasks (read-json "tasks.lisp")))
(print "-----------------------------")

(print (getprop :tasks))
(print "-----------------------------")

(defvar *tasks* (getprop :tasks (read-json "tasks.lisp")))
(print *tasks*)
(print "-----------------------------")


; Aufgabe 4
(print "Aufgabe 4")
(defun filter-fn (f seq)
	(remove-if-not f seq))

(defun reject-fn (f seq)
	(remove-if f seq))

(defun prop-eq (prop val)
	(pipeline (getprop prop) (partial #'equal val)))


(setfun filter (curry #'filter-fn 2))
(setfun reject (curry #'reject-fn 2))

(print (funcall (prop-eq :RESULT "SUCCESS") (read-json "tasks.lisp")))

(print (filter (prop-eq :member "Scott") *tasks*))
;(print (filter (prop-eq :member "Scott")))


(defun pick-fn (attrs obj)
    (remove-if-not #'(lambda (el) (member (car el) attrs)) obj)
)
(setfun pick (curry #'pick-fn 2))
(setfun forall (curry #'mapcar 2))

(print (forall (pick '(:complete :title)) *tasks*))
(print "-----------------------------")

; Aufgabe 5
(print "Aufgabe 5")

(defun date-to-universal (str)
	(encode-universal-time 0 0 0 
		(parse-integer (second (string-split #\/ str))) 
		(parse-integer (first (string-split #\/ str))) 
		(parse-integer (third (string-split #\/ str)))))

(print (date-to-universal "01/01/2012"))

(defun sort-by-fn (f seq)
    (sort (copy-list seq)
    (lambda (a b) (< (funcall f a) (funcall f b))))
)
(setfun sort-by (curry #'sort-by-fn 2))

(defun open-tasks (name)
    (pipeline
        (getprop :tasks)
        (filter (prop-eq :member name))
        (reject (prop-eq :complete t))
        (forall (pick '(:id :due-date :title :priority)))
        (sort-by (pipeline (getprop :due-date) #'date-to-universal))
    )
)

(print (funcall (open-tasks "Scott") (read-json "tasks.lisp")))
(print (funcall (open-tasks "Lena") (read-json "tasks.lisp")))
