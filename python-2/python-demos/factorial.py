# Factorial implementations
# 

def factorialR(n):
    "Recursive factorial function"
    assert isinstance(n, int) and n >= 1 
    return 1 if n <= 1 else n * factorialR(n-1)


def factorialI(n):
    "Iterative factorial function" 
    assert isinstance(n, int) and n >= 1 
    product = 1
    while n >= 1:
        product *= n
        n-=1 
    return product


from functools import reduce 
from operator import mul 

def factorialF(n):
    "Functional style factorial"
    return reduce(mul, range(1, n+1), 1)


if __name__ == '__main__':
    print(factorialR(10))
    print(factorialI(10))
    print(factorialF(10))
