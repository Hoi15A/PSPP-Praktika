# First class functions
# 
sqr = lambda n: n*n
# >>> sqr(4)
# 16
add = lambda a, b: a+ b
# >>> add(3,5)
# 8


# Higher order functions
#
def splat (f):
    return lambda seq: f(*seq)

def unsplat (f):
    return lambda *args: f(args)

# >>> add(12, 5)
# >>> splat(add)([12, 5])
# >>> unsplat(splat(add))(12, 5)


# Function composition
#
def simple_compose (f, g):
    def fun (*args, **key):
        return f(g(*args, **key))
    return fun 

add_and_sqr = simple_compose(sqr, add)

# >>> add_and_sqr(1, 2)
# 9
# >>> simple_compose(unsplat, splat)(add)(12, 5)
# 17


# Closure
# 
def makeadder (n):
    def adder (m):
        return n+m
    return adder

# >>> adder1 = makeadder(12)
# >>> adder1(5)
# 17


# partial() is used for partial function application which "freezes" some portion 
# of a function's arguments
#
def partial (f, *args):
    def fun (*moreargs):
       newargs = args + moreargs
       return f(*newargs)
    return fun

def mul3 (a,b,c):
    return a*b*c

# >>> mul3(2,3,4)
# 24
# >>> partial(mul3,10)
# <function partial.<locals>.fun at 0x10930ba70>
# >>> partial(mul3,10)(3,4)
# 120
# >>> partial(mul3,10,3)(4)
# 120


# Map, filter, reduce
# 
numbers = [1, 8, 3, 6, 2, 9]
# 
# >>> map(sqr, numbers)
# <map object at 0x10931e650>
# >>> list(map(sqr, numbers))
# [1, 64, 9, 36, 4, 81]
# 
# >>> list(filter(lambda n: n%2==0, numbers))
# [8, 6, 2]
# 
# >>> import functools
# >>> functools.reduce(add,numbers)
# 29








