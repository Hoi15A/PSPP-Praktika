class Date(object):

    day = 0
    month = 0
    year = 0

    def __init__(self, day=0, month=0, year=0):
        self.day = day
        self.month = month
        self.year = year
    
    def __repr__(self):
        return str(self.day) + '-' + str(self.month) + '-' + str(self.year)
    
    @classmethod
    def from_string(cls, date_as_string):
        day, month, year = map(int, date_as_string.split('-'))
        date1 = cls(day, month, year)
        return date1
    
    @staticmethod
    def is_date_valid(date_as_string):
        day, month, year = map(int, date_as_string.split('-'))
        return day <= 31 and month <= 12 and year <= 3999


# @classmethod wendet den Decorator an, wie:
# from_string = classmethod(from_string)


# Beispiel
#
# >>> date1 = Date(20,5,2014)
# >>> date1
# 20-5-2014
# >>> date2 = Date.from_string('11-09-2012')
# >>> date2
# 11-9-2012
# >>> Date.is_date_valid('11-09-2012')
# True


