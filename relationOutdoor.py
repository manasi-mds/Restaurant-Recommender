import json
from py2neo import Graph
from py2neo import Node, Relationship


#graph = Graph('neo4j://localhost:11003', user="", password="")

# graph = Graph('neo4j+s://ab6bbbca.databases.neo4j.io:7687', user="neo4j", password="qeARmHp0V_OSxravPEZC-yot-unRoFsI5YgYy5Ws-Hs")
graph = Graph('bolt://localhost:7687', user="neo4j", password="local123")

with open("philadelphia.json", 'r') as fo:
    rest = json.load(fo)

for i in rest:
#for node in graph.query("MATCH (n:Stars) RETURN n LIMIT 25"):
    #print(node)
    b_id = i["business_id"]
    if(i["attributes"] and "OutdoorSeating" in i["attributes"]  ):
        if(i["attributes"]["OutdoorSeating"] == "True"):
            rel_q = "MATCH (n:Restaurant),(s:Outdoor_Seating) WHERE n.business_id =  \""+b_id+ "\" CREATE (n)-[r:HAS_OUTDOOR_SEATING]->(s) RETURN type(r)"
            graph.run(rel_q)