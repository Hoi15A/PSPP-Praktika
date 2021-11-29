# iterate function as a generator
#
def iterate(f, init):
    n = f(init)
    while True:
        yield n
        n = f(n)

# >>> it = iterate(partial(operator.add, 1), 0)
# >>> next(it)
# 1
# >>> next(it)
# 2
# >>> next(it)
# 3
# >>> next(it)
# 4


# take next n elements of iterator
#
def take(n, it):
    res = []
    for i in range(n):
        res += [next(it)]
    return res

# >>> take(10, iterate(partial(operator.mul, 2), 1))
# [2, 4, 8, 16, 32, 64, 128, 256, 512, 1024]


# again: fibonacci
#
def fibpair(tup):
    return (tup[1], tup[0]+tup[1])

# >>> fibpair((3,5))
# (5, 8)

# >>> a = iterate(fibpair, (0,1))
# >>> next(a)
# (1, 1)
# >>> next(a)
# (1, 2)
# >>> next(a)
# (2, 3)

allfibs = map(lambda el: el[0], iterate(fibpair, (0,1)))

# >>> allfibs
# <map object at 0x104dec5e0>
# 
# >>> take(15, allfibs)
# [1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610]
