def format_bands(bands): 
    for band in bands: 
        band['country'] = 'Canada' 
        band['name'] = band['name'].replace('.', '') 
        band['name'] = band['name'].title()


def pipeline_each(data, fns): 
    import functools 
    return functools.reduce(lambda a, fn: list(map(fn, a)), fns, data)


def assoc(_d, key, value): 
    from copy import deepcopy 
    d = deepcopy(_d) 
    d[key] = value 
    return d


def set_canada_as_country(band):
    return assoc(band, 'country', 'Canada')


def strip_punctuation_from_name(band):
    return assoc(band, 'name', band["name"].replace('.', ''))


def capitalize_names(band):
    return assoc(band, 'name', band["name"].title())


def call(fn, key): 
    def apply_fn(record): 
        return assoc(record, key, fn(record.get(key))) 
    return apply_fn


def main():
    # Original
    bands = [{'name': 'sunset rubdown', 'country': 'UK', 'active': False}, 
            {'name': 'women', 'country': 'Germany', 'active': False}, 
            {'name': 'a silver mt. zion', 'country': 'Spain', 'active': True}]
    print(f"\n{'='*20}\nOriginal Format")
    format_bands(bands)
    print(bands)


    # Pipeline
    bands = [{'name': 'sunset rubdown', 'country': 'UK', 'active': False}, 
            {'name': 'women', 'country': 'Germany', 'active': False}, 
            {'name': 'a silver mt. zion', 'country': 'Spain', 'active': True}]
    
    print(f"\n{'='*20}\nPipeline Format")
    bands = pipeline_each(bands, [set_canada_as_country, 
                        strip_punctuation_from_name, 
                        capitalize_names])
    print(bands)


    # Pipeline + call
    bands = [{'name': 'sunset rubdown', 'country': 'UK', 'active': False}, 
            {'name': 'women', 'country': 'Germany', 'active': False}, 
            {'name': 'a silver mt. zion', 'country': 'Spain', 'active': True}]

    set_canada_as_country_new = call(lambda x: 'Canada', 'country')
    strip_punctuation_from_name_new = call(lambda x: x.replace('.', ''), 'name')
    capitalize_names_new = call(lambda x: x.title(), 'name')

    print(f"\n{'='*20}\nPipeline Format with call")
    bands = pipeline_each(bands, [set_canada_as_country_new, 
                        strip_punctuation_from_name_new, 
                        capitalize_names_new])
    print(bands)


if __name__ == "__main__":
    main()
