import json

from py2neo import Graph
from py2neo import Node, Relationship


#graph = Graph('neo4j://localhost:11003', user="", password="")

# graph = Graph('neo4j+s://ab6bbbca.databases.neo4j.io:7687', user="neo4j", password="qeARmHp0V_OSxravPEZC-yot-unRoFsI5YgYy5Ws-Hs")
graph = Graph('bolt://localhost:7687', user="neo4j", password="local123")

with open("philadelphia.json", 'r') as fo:
    rest = json.load(fo)

tx = graph.begin()

for i in rest:
#for node in graph.query("MATCH (n:Stars) RETURN n LIMIT 25"):
    #print(node)
    b_id = i["business_id"]
    q = "MATCH (n:Restaurant) WHERE n.business_id = \""+b_id+ "\" RETURN n"
    #print(q)
    restaurant = Node(graph.query(q))
    #print(restaurant)

    star_q = "MATCH (n:Restaurant) WHERE n.name= \""+str(i["stars"])+ "\" RETURN n"
    #print(star_q)
    stars = Node(graph.query(star_q))

    rel_q = "MATCH (n:Restaurant),(s:Stars) WHERE n.business_id =  \""+b_id+ "\" AND s.name = "+str(i["stars"])+ " CREATE (n)-[r:HAS_RATING]->(s) RETURN type(r)"

    graph.run(rel_q)

    #ab = Relationship(restaurant, "RATING", stars)
    #tx.create(ab)
    #break

tx.commit()
###


    

#print(result)

    