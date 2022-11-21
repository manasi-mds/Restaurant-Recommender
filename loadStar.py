import json

from py2neo import Graph
from py2neo import Node, Relationship


graph = Graph('neo4j+s://ab6bbbca.databases.neo4j.io:7687', user="neo4j", password="qeARmHp0V_OSxravPEZC-yot-unRoFsI5YgYy5Ws-Hs")

star_node = []

with open("philadelphia.json", 'r') as fo:
    rest = json.load(fo)


star_list = []
for i in rest:
    if("star" in i ):
        star_list.append((i["star"]))

print(set(star_list))

