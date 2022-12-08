import json

from py2neo import Graph
from py2neo import Node, Relationship


graph = Graph('neo4j+s://ab6bbbca.databases.neo4j.io:7687', user="neo4j", password="qeARmHp0V_OSxravPEZC-yot-unRoFsI5YgYy5Ws-Hs")

ambience_node = []

with open("philadelphia.json", 'r') as fo:
    rest = json.load(fo)


ambience_list = []

for i in rest:
    if(i["attributes"] and "Ambience" in i["attributes"]  ):
       # print(i["stars"])
        #attr = i["attributes"]
        #if("ambience" in i["attributes"]):
        amb = i["attributes"]["Ambience"]
        amb_dict = eval(amb)
        for j in amb_dict:
            ambience_list.append(j)
        break

#print(ambience_list)

tx = graph.begin()
amb_node = []
for i in ambience_list:
    amb_node.append(Node("Ambience", name = i) )
    tx.create(amb_node[-1])

tx.commit()
