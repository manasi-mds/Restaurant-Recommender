import json

from py2neo import Graph
from py2neo import Node, Relationship

cuisines_to_remove = ['Outdoor Gear', 'Airport Shuttles', 'Tennis', 'Recreation Centers', 'Property Management', 'Tabletop Games',
'Hospitals', 'Mass Media', 'Axe Throwing', 'Art Museums', 'Bowling', 'Bartenders', 'Playgrounds', 'Music & Video',
'Home & Garden', 'Furniture Reupholstery', 'Religious Organizations', 'Shopping', 'General Dentistry', 'Cannabis Dispensaries', 'Hotels', 
'Banks & Credit Unions', 'Internet Cafes', 'Furniture Stores', 'Escape Games', 'Beauty & Spas', 'Specialty Schools',
'Massage', 'Real Estate Agents', 'Hair Salons', 'Social Clubs', 'Cooking Classes', 'Dance Clubs', 'Museums', 'Kitchen Supplies',
 'Tours', 'Buddhist Temples', 'Kitchen & Bath', 'Event Planning & Services', 'Music Venues', 'Community Service/Non-Profit',
 'Vinyl Records', 'Boating', 'Eyewear & Opticians', 'Gay Bars', 'Brasseries', 'Professional Services', 'Art Galleries',
 'Meat Shops', 'Gyms',  'Plumbing', 'Used Bookstore', 'Department Stores', 'Ticket Sales', 'Newspapers & Magazines', 'Public Transportation', 
 'Landmarks & Historical Buildings', 'Souvenir Shops', 'Jewelry', 'Tobacco Shops','Appliances', 'Venues & Event Spaces',
 'Vintage & Consignment', "Men's Clothing", 'Arcades', 'Print Media', 'Airport Lounges', 'Public Services & Government', 'Used', 'Bookstores',
 'Indoor Playcentre', 'Hookah Bars', 'Toy Stores', 'Florists', 'Music & DVDs', 'Nurseries & Gardening', 'Active Life',
 'IT Services & Computer Repair', 'Vitamins & Supplements', 'Party & Event Planning', 'Party Equipment Rentals', 'Doctors',
 'Taxis', 'Videos & Video Game Rental', 'Adult Education', 'Real Estate', 'Apartments', 'Pet Adoption', 'Karaoke',
 'Financial Services', 'Gift Shops', 'Cosmetics & Beauty Supply', 'Historical Tours', 'Parks', 'Local Services', 'Cannabis Clinics',
 'Hotels & Travel', 'Herbs & Spices', 'Food Delivery Services', 'Butcher','Supper Clubs', 'Swimming Pools',
 'Hunting & Fishing Supplies', 'Local Services', 'Convenience Stores', 'Golf', 'Watches', 'Costumes','Massage Therapy',
 'Financial Advising', 'Performing Arts', 'Visitor Centers', 'Boat Charters', 'Mobile Phone Repair', 'Education',
 'Sports Betting', 'Dive Bars', 'Business Consulting', 'Automotive', 'Art Classes', 'Sports Clubs', 'Skating Rinks',
 'Home Services', 'Boat Tours', 'Fitness & Instruction', 'Flowers & Gifts', 'Kids Activities','Train Stations',
 'Home Decor', 'Festivals', 'Wedding Planning', 'Transportation', 'Nutritionists', 'Travel Services', 'Dentists',
 'Adult Entertainment', 'Candle Stores', 'Shipping Centers', 'Books', 'Barbers', "Golf Lessons"]
#graph = Graph('neo4j://localhost:11003', user="", password="")

# graph = Graph('neo4j+s://ab6bbbca.databases.neo4j.io:7687', user="neo4j", password="qeARmHp0V_OSxravPEZC-yot-unRoFsI5YgYy5Ws-Hs")
graph = Graph('bolt://localhost:7687', user="neo4j", password="local123")

with open("philadelphia.json", 'r') as fo:
    rest = json.load(fo)

for i in rest:
    b_id = i["business_id"]
    if(i["categories"]):
        x = i["categories"].split(',')
        for j in x:
            if(j not in cuisines_to_remove):
                new_string = j.strip()
                rel_q = "MATCH (n:Restaurant),(s:Cuisine) WHERE n.business_id =  \""+b_id+ "\" AND s.name = \""+new_string+"\" CREATE (n)-[r:HAS_CUISINE]->(s) RETURN type(r)"
                graph.run(rel_q)