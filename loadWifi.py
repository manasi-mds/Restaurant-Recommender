import json

from py2neo import Graph
from py2neo import Node, Relationship


graph = Graph('neo4j+s://ab6bbbca.databases.neo4j.io:7687', user="neo4j", password="qeARmHp0V_OSxravPEZC-yot-unRoFsI5YgYy5Ws-Hs")

wifi_node = []

with open("philadelphia.json", 'r') as fo:
    rest = json.load(fo)


wifi_list = []
for i in rest:
    if(i["attributes"] and "WiFi" in i["attributes"]  ):
        wifi_list.append((i["attributes"]["WiFi"]))

#wifi_new = map(str, wifi_list)
print(set(wifi_list))

#print("Hello\n")

#wifi_node_no = []

wifi_node.append(Node("Wifi", type = "paid"))
wifi_node.append(Node("Wifi", type = "free"))

tx = graph.begin()

tx.create(wifi_node[0])
tx.create(wifi_node[1])
#tx.create(wifi_node_no[-1])

tx.commit()