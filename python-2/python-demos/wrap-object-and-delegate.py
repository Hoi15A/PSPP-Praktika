# Beispiel zeigt, wie eine vordefinierte Klasse erweitert werden kann mit Hilfe
# von Delegation
#
# Normalerweise kann die Cursor-Klasse nicht erweitert werden, es soll aber eine
# eigene execute-Methode implementiert werden
#
# Lösung: eigene Klasse MyCursor, ihre Instanz packt ein Cursor-Objekt ein, 
# verwendet aber eine eigene execute-Methode, alle anderen Methoden werden an
# das Original-Cursor-Objekt delegiert
#
# bkrt, 07.01.2020, diverse Quellen im Web

import sqlite3
from datetime import date, datetime

class MyCursor:
    def __init__(self, cursor):
        self.cursor = cursor

    def execute(self, *args):
        self.cursor.execute(*args)

    def __getattr__(self, name):
        f = self.cursor.__getattribute__(name)
        def func(*args):
            return f(*args)
        return func
 
db = sqlite3.connect(':memory:')
cur = db.cursor()

cur = MyCursor(cur)


cur.execute('CREATE TABLE beispiel(id INTEGER PRIMARY KEY, datum DATE)')

stmt = 'INSERT INTO beispiel(datum) VALUES(:datum)'
cur.execute(stmt, {'datum': date.today() })
db.commit()

cur.execute('SELECT datum FROM beispiel')
row = cur.fetchone()
print(row[0])
# --> u'2020-01-06'
