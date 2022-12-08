import json

from py2neo import Graph
from py2neo import Node, Relationship

graph = Graph('neo4j://localhost:11003', user="", password="")

#graph = Graph('neo4j://localhost:11003', user="neo4j", password="123")

rest_node = []

with open("philadelphia.json", 'r') as fo:
    rest = json.load(fo)

tx = graph.begin()

#Creates the nodes, examples of single entry creation below
for i in rest:
    rest_node.append(Node("Restaurant", business_id = i["business_id"], name = i["name"], address = i["address"], latitude = i["latitude"], longitude = i["longitude"], stars = i["stars"], review_count = i["review_count"], is_open = i["is_open"]))
    tx.create(rest_node[-1])

tx.commit()
""" 
TheMatrix = Node("Movie", title='The Matrix', released=1999, tagline='Welcome to the Real World')
Keanu = Node("Person", name='Keanu Reeves', born=1964)
Carrie = Node("Person", name='Carrie-Anne Moss', born=1967)
Laurence = Node("Person", name='Laurence Fishburne', born=1961)
Hugo = Node("Person", name='Hugo Weaving', born=1960)
LillyW = Node("Person", name='Lilly Wachowski', born=1967)
LanaW = Node("Person", name='Lana Wachowski', born=1965)
JoelS = Node("Person", name='Joel Silver', born=1952)
Emil = Node("Person", name="Emil Eifrem", born=1978)

# Relationships
LillyWTheMatrix = Relationship(LillyW, "DIRECTED", TheMatrix)
LanaWTheMatrix = Relationship(LanaW, "DIRECTED", TheMatrix)
JoelSTheMatrix = Relationship(JoelS, "PRODUCED", TheMatrix)
KeanuTheMatrix = Relationship(Keanu, "ACTED_IN", TheMatrix)
KeanuTheMatrix['roles'] = ['Neo']
CarrieTheMatrix = Relationship(Carrie, "ACTED_IN", TheMatrix)
CarrieTheMatrix['roles'] = ['Trinity']
LaurenceTheMatrix = Relationship(Laurence, "ACTED_IN", TheMatrix)
LaurenceTheMatrix['roles'] = ['Morpheus']
HugoTheMatrix = Relationship(Hugo, "ACTED_IN", TheMatrix)
HugoTheMatrix['roles'] = ['Agent Smith']
EmilTheMatrix = Relationship(Emil, "ACTED_IN", TheMatrix)
EmilTheMatrix['roles'] = ['Emil']


tx = graph.begin()
tx.create(TheMatrix)
tx.create(Keanu)
tx.create(Carrie)
tx.create(Laurence)
tx.create(Hugo)
tx.create(LillyW)
tx.create(LanaW)
tx.create(JoelS)
tx.create(Emil)
tx.create(KeanuTheMatrix)
tx.create(CarrieTheMatrix)
tx.create(LaurenceTheMatrix)
tx.create(HugoTheMatrix)
tx.create(LillyWTheMatrix)
tx.create(LanaWTheMatrix)
tx.create(JoelSTheMatrix)
tx.create(EmilTheMatrix)
tx.commit() """