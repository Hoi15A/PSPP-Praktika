from functools import wraps


def logger(func):

    @wraps(func)
    def inner(*args, **kwargs):
        print("Arguments were: %s, %s" % (args, kwargs))
        return func(*args, **kwargs)
    return inner



@logger
def foo(x, y=1):
    return x * y



# Beispiel
# 
# >>> from simplelogger import *
# >>> foo(1)
# Arguments were: (1,), {}
# 1
# >>> foo(1,2)
# Arguments were: (1, 2), {}
# 2
# >>> foo(4, y=5)
# Arguments were: (4,), {'y': 5}
# 20
# >>> foo(y=5, x=4)
# Arguments were: (), {'x': 4, 'y': 5}
# 20
