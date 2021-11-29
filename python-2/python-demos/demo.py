##
## Sequenzen
##

def car(seq):
  return seq[0]

def cdr(seq):
  return seq[1:]

#print(  car([1,2,3,4,5])  )
#print(  cdr([1,2,3,4,5])  )


##
## Strings
##

s = "Python ist einfach"
s.replace("einfach", "sehr einfach")

#print(  s  )
#print(  s.replace("einfach", "sehr einfach")  )

#for c in s: print(c, end=' ')
#print()
#
#print(  'y' in s      )
#print(  s[0]          )
#print(  'J' + s[1:]   )
#print(  4 * 'Ja! '    )
#print(  s.split(' ')  )

#print(  '☃'.encode()                        )
#print(  '☃'.encode(encoding="utf-8")        )
#print(  'Ä'.encode(encoding="utf-8")         )
#print(  'Ä'.encode(encoding="iso8859-1")     )


##
## Collections
##

liste = [1, 2, 3, 4, 'fünf']
dict  = {'eins': 1, 'zwei': 2}

tupel = (1, 2, 3, liste, dict)

#  tupel[2:4]
#  tupel[3][:-1]
#  tupel[0] = 99
#  tupel[3][0] = 99
#  tupel[4]['drei'] = 3


##
## Funktionen
##

def fakultaet(n):
  if n<2:
    return 1
  else:
    return n * fakultaet(n-1)

#print(  fakultaet(50)  )
#print(  type(fakultaet)  )
#print(  callable(fakultaet)  )
#print(  callable(50)  )



def qsort(seq):
  if seq == []:
    return []
  else:
    elem = seq[0]; rest = seq[1:]
    return ( qsort( [y for y in rest if y < elem] ) 
             + [elem] 
             + qsort( [z for z in rest if z >= elem] ) )


#print(  qsort([61, 12, 95, 2, 99, 33, 17, 45, 7, 36, 57])  )


