(load "more-functional.lisp")

(defun wrap-fn (f pre &optional (post #'(lambda (arg) arg)))
    #'(lambda (&rest args)
        (cond ((equal args '(:orig)) f)
              (t (funcall post (apply f (apply pre args)))))))

;; Aufgabe 1:
;; Die Funktion always enthält eine Closure

;; 1. Frage: Die Funktion funktioniert folgendermassen: der Funktion repeatedly wird das
;; Symbol answer gegeben, an dieses ist die Funktion always mit parameter 'hello gebunden
;; In Repeatedly wird fun (hier always) für jedes element in der range-liste aufgerufen.

;; 2. Frage: Nein, always nimmt keine funktion als parameter

(setfun return-hello (always 'HELLO))

; Anlegen einer Liste, die 5mal das Symbol HELLO enthaelt (also wie oben mit repeat).
(print (repeatedly 5 #'return-hello))


; Eine Liste, die 5 zufallige Wurfelergebnisse (1..6) enthalt.
;   Hinweis: Funktion random, Beschreibung im CLTL2.
(defun randnr (val) (lambda (&rest r) (+ 1 (random val))))
(setfun randdice (randnr 6))
(print (repeatedly 5 #'randdice))

;   Eine Liste mit 5 IDs: ("id0" "id1" "id2" "id3" "id4")
;   Beachten Sie dazu die folgende Interaktion in der REPL:

(defun idlist () (lambda (arg) 
  (concatenate 'string "id" (write-to-string arg))))
(setfun idlists (idlist))
(print (repeatedly 5 #'idlists))


;; Aufgabe 2:
;; Die Funktion num macht einen dispatch auf den angehängten funktionen. 
;; Der Dispatch führt die als Parameter übergebenen Funktionen der Reihe nach
;; aus bis die erste nicht Nil zurück gibt.
(setfun num (dispatch #'parse-int #'parse-float #'identity))

;; parse-int
(print (num 10))

;; parse-float
(print (num 81.25))

;; identity
(print (num '(why)))

(defun num-args (&rest args)
    (mapcar #'num args))
    
(setfun add (wrap-fn #'+ #'num-args))
(print (add 12 "8" 1))
(print (add 2 2 "1"))
(print (add 1 2 3 4 5))

;; Aufgabe 3

(defun factorial (n &optional (fact 1))
    (if (<= n 1) fact
        (factorial (- n 1) (* n fact))))
        
; Es braucht:
; - Abbruchbedingung
; - rekusiv (lineare Zeit)
(print (factorial 3))

;; Angepasste funktion mit Partial
(defun facp (n &optional (fact 1))
    (if (<= n 1) fact
        (partial #'facp (- n 1) (* n fact))))

(print (funcall (funcall (facp 3))))



(defun trampoline (fun &rest args)
    (let ((result (apply fun args)))
        (loop while (functionp result) do
            (setq result (funcall result)))
        result))

(print (trampoline #'facp 5))