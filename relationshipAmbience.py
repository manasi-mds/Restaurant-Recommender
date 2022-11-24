import json

from py2neo import Graph
from py2neo import Node, Relationship


#graph = Graph('neo4j://localhost:11003', user="", password="")

graph = Graph('neo4j+s://ab6bbbca.databases.neo4j.io:7687', user="neo4j", password="qeARmHp0V_OSxravPEZC-yot-unRoFsI5YgYy5Ws-Hs")

with open("philadelphia.json", 'r') as fo:
    rest = json.load(fo)

tx = graph.begin()

ambience_list = []

for i in rest:
    b_id = i["business_id"]
    ambience_list = []
    if(i["attributes"] and "Ambience" in i["attributes"]  ):
       # print(i["stars"])
        #attr = i["attributes"]
        #if("ambience" in i["attributes"]):
        amb = i["attributes"]["Ambience"]
        amb_dict = eval(amb)
        if amb_dict is not None:
            for j in amb_dict:
                if(amb_dict[j]==True):
                    ambience_list.append(j)
    
    for j in ambience_list:
        rel_q = "MATCH (n:Restaurant),(a:Ambience) WHERE n.business_id =  \""+b_id+ "\" AND a.name = \""+j+ "\" CREATE (n)-[r:AMBIENCE]->(a) RETURN type(r)"
        graph.run(rel_q)

print(ambience_list)