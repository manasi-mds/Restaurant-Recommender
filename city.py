import json
from collections import defaultdict, Counter
to_be_saved = []

rest = []
for line in open('yelp_academic_dataset_business.json', 'r'):
    rest.append(json.loads(line))

#This bit just counts cities to see which ones are listed the most often
city = defaultdict(dict)

for i in rest:
    if city[i['city']] :
        if i['categories'] != None and 'Restaurants' in i['categories']:
            city[i['city']] += 1
    else:
        city[i['city']] = 1

c = Counter(city)

most_common = c.most_common(10)

print(most_common)

#This saves the json entries of restaurants in a certain city to a list 
print("Select city to write to file: ")
selection = input().lower()

for j in rest:
    if selection == j["city"].lower() and j['categories'] != None and 'Restaurants' in j['categories']:
      to_be_saved.append(j)

print(len(to_be_saved))

with open(selection+".json", "w") as output_data:
    output_data.write(json.dumps(to_be_saved, indent=2))