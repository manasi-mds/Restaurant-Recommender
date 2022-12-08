import json

from py2neo import Graph
from py2neo import Node, Relationship


# graph = Graph('neo4j+s://ab6bbbca.databases.neo4j.io:7687', user="neo4j", password="qeARmHp0V_OSxravPEZC-yot-unRoFsI5YgYy5Ws-Hs")
graph = Graph('bolt://localhost:7687', user="neo4j", password="local123")

star_node = []

with open("philadelphia.json", 'r') as fo:
    rest = json.load(fo)


star_list = []
for i in rest:
    if("stars" in i ):
       # print(i["stars"])
        star_list.append((i["stars"]))

star_unique = set(star_list)
star_node = []
tx = graph.begin()

for i in star_unique:
    star_node.append(Node("Stars", name = i) )
    tx.create(star_node[-1])

tx.commit()

