#
# function wrapper (decorator)
#  
import time
from functools import wraps

def timethis(func):
    '''
    Decorator that reports the execution time.
    '''
    @wraps(func)
    def wrapper(*args, **kwargs):
        start = time.time()
        result = func(*args, **kwargs)
        end = time.time()
        print(func.__name__, end-start)
        return result
    return wrapper


# apply wrapper
#
@timethis
def countdown(n):
    '''
    Counts down
    '''
    while n > 0:
        n -= 1


# @timethis to apply the decorator, same as:
# countdown = timethis(countdown)


# >>> countdown(1000000)
# countdown 0.04070091247558594
#
# >>> countdown.__wrapped__(10000000)
# (no timer, no output)


# Whenever you define a decorator, you should always remember 
# to apply the @wraps decorator from the functools library to 
# the underlying wrapper function. Otherwise important metadata 
# such as the name, doc string, annotations, and calling 
# signature are lost.