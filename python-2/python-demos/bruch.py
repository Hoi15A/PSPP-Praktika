## Aufgabe:
##
## Schreiben Sie eine Funktion ggt(a,b), die den groessten gemeinsamen
## Teiler bestimmt 
##   - Tipp: Euklidischer Algorithmus, ggf. Wikipedia
##   - ggf. Hinweis zum Aufbau einer Funktion
##
## Schreiben Sie eine Funktion kuerzen(bruch), die einen Bruch kuerzt
##   - Der Bruch soll als Tupel oder Liste von Zaehler und Nenner
##     uebergeben werden
##
## Beide Funktionen in eine Datei schreiben und die verschiedenen Import
## Varianten ausprobieren
##   - Datei euclid.pyc entsteht
##

def ggt(a, b):
	if b==0: return a
	return ggt(b, a % b)


def kuerzen(bruch):
	z = bruch[0]
	n = bruch[1]
	g = ggt(z, n)
	return z/g, n/g





