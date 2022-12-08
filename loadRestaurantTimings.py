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

    b_id = i["business_id"]
    if(i["hours"] ):
        if("Monday" in i["hours"]):
            rel_q = "MATCH (n:Restaurant) WHERE n.business_id =  \""+b_id+ "\" SET n.hours_mon = \""+i["hours"]["Monday"] +"\" RETURN n"
            graph.run(rel_q)
            #print(i["hours"]["Monday"])
        if("Tuesday" in i["hours"]):
            rel_q = "MATCH (n:Restaurant) WHERE n.business_id =  \""+b_id+ "\" SET n.hours_tue = \""+i["hours"]["Tuesday"] +"\" RETURN n"
            graph.run(rel_q)
            #print(i["hours"]["Tuesday"])
        if("Wednesday" in i["hours"]):
            rel_q = "MATCH (n:Restaurant) WHERE n.business_id =  \""+b_id+ "\" SET n.hours_wed = \""+i["hours"]["Wednesday"] +"\" RETURN n"
            graph.run(rel_q)
            #print(i["hours"]["Wednesday"])

        if("Thursday" in i["hours"]):
            rel_q = "MATCH (n:Restaurant) WHERE n.business_id =  \""+b_id+ "\" SET n.hours_thu = \""+i["hours"]["Thursday"] +"\" RETURN n"
            graph.run(rel_q)
            #print(i["hours"]["Thursday"])

        if("Friday" in i["hours"]):
            rel_q = "MATCH (n:Restaurant) WHERE n.business_id =  \""+b_id+ "\" SET n.hours_fri = \""+i["hours"]["Friday"] +"\" RETURN n"
            graph.run(rel_q)
            #print(i["hours"]["Friday"])

        if("Saturday" in i["hours"]):
            rel_q = "MATCH (n:Restaurant) WHERE n.business_id =  \""+b_id+ "\" SET n.hours_sat = \""+i["hours"]["Saturday"] +"\" RETURN n"
            graph.run(rel_q)
            #print(i["hours"]["Saturday"])

        if("Sunday" in i["hours"]):
            rel_q = "MATCH (n:Restaurant) WHERE n.business_id =  \""+b_id+ "\" SET n.hours_sun = \""+i["hours"]["Sunday"] +"\" RETURN n"
            graph.run(rel_q)
            #print(i["hours"]["Sunday"])

        

            
