##
##  Type annotations
##  Type checking 
##

def add (a: int, b: int) -> int:
    return a + b

print(add(2, 5))
print(add.__annotations__)

# 7
# {'a': <class 'int'>, 'b': <class 'int'>, 'return': <class 'int'>}

