import json

from py2neo import Graph
from py2neo import Node, Relationship


#graph = Graph('neo4j://localhost:11003', user="", password="")

graph = Graph('neo4j+s://ab6bbbca.databases.neo4j.io:7687', user="neo4j", password="qeARmHp0V_OSxravPEZC-yot-unRoFsI5YgYy5Ws-Hs")

with open("philadelphia.json", 'r') as fo:
    rest = json.load(fo)

tx = graph.begin()

for i in rest:

    b_id = i["business_id"]


    if(i["attributes"] and "WiFi" in i["attributes"]  ):
        if("paid" in i["attributes"]["WiFi"]):
            rel_q = "MATCH (n:Restaurant),(s:Wifi) WHERE n.business_id =  \""+b_id+ "\" AND s.type = \"paid\" CREATE (n)-[r:HAS_WIFI]->(s) RETURN type(r)"
            graph.run(rel_q)
            print(i["name"])
        elif("free" in i["attributes"]["WiFi"]):
            rel_q = "MATCH (n:Restaurant),(s:Wifi) WHERE n.business_id =  \""+b_id+ "\" AND s.type = \"free\" CREATE (n)-[r:HAS_WIFI]->(s) RETURN type(r)"
            graph.run(rel_q)
            print(i["name"])
