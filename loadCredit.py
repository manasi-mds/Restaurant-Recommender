import json

from py2neo import Graph
from py2neo import Node, Relationship


graph = Graph('neo4j+s://ab6bbbca.databases.neo4j.io:7687', user="neo4j", password="qeARmHp0V_OSxravPEZC-yot-unRoFsI5YgYy5Ws-Hs")


tx = graph.begin()

tx.create(Node("Credit_Card", name = "yes") )
tx.commit()