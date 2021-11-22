def strCodes(str):
    return map(ord, str)


def strSum(str):
    return sum(strCodes(str))


def zipLists(*lists):
    return zip(*lists) # Note: this uses *args call syntax which essentially is just zip(lists[0], ..., lists[n])


def zipListsFun(fun, *lists):
    return map(lambda x : fun(*x), zipLists(*lists))


def dictinvert(d):
    inv = {}
    for k, v in d.items():
        keys = inv.setdefault(v, [])
        keys.append(k)
    return inv
    # return dict(zip(d.values(), d.keys())) functional but breaks on non unique values


def flatten(lst):
    tmp = []

    for i in lst:
        if type(i) is list or type(i) is tuple:
            for j in flatten(i): tmp.append(j)
            
            # list(map(tmp.append, flatten(i)))
            # list is required to force map to do the calculation (wack)
        else:
            tmp.append(i)

    return tmp


def main():
    nl = "\n" + "=" * 80 + "\n"

    print('strCodes("Hallo"): ' + str(list(strCodes("Hallo"))))
    print(nl)

    print('strSum("Hallo"): ' + str(strSum("Hallo")))
    print(nl)

    print("zipLists([1,2,3], [5,6,7], ['a', 'b', 'c']): ")
    print(str(list(zipLists([1,2,3], [5,6,7], ['a', 'b', 'c']))))
    print(nl)

    def combine(a,b,c): return str(a+b) + c
    print("zipListsFun(combine, [1,2,3], [5,6,7], ['a', 'b', 'c']): ")
    print(str(list(zipListsFun(combine, [1,2,3], [5,6,7], ['a', 'b', 'c']))))
    print(nl)

    print("dictinvert({'a': 55, 'b': 55, 'c': 88}):")
    print(str(dictinvert({'a': 55, 'b': 55, 'c': 88}) ))
    print(nl)

    print("flatten([[[1, 2], 3], [[4]], (5, 6), ([7])]):")
    print(str(flatten([[[1, 2], 3], [[4]], (5, 6), ([7])])))


if __name__ == "__main__":
    main()
