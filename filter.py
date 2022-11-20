import json
from collections import defaultdict, Counter

#Gets the most common attributes in the json list
attributes = defaultdict(dict)

print("Enter the city to examine: ")
city = input().lower()

with open(city+".json", 'r') as fo:
    rest = json.load(fo)

for i in rest:
    if i["attributes"]:
        for attribute in i["attributes"]:
            if(attribute not in attributes):
                attributes[attribute] = 1
            else:
                attributes[attribute] += 1 


c = Counter(attributes)

most_common = c.most_common(10)

print(most_common)
#print(output_json)
