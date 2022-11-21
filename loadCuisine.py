import json

from py2neo import Graph
from py2neo import Node, Relationship


#cuisines_to_remove = ['Colleges & Universities', ' Bowling', ' Train Stations',
# ' Shopping', 'Comedy Clubs', ' Performing Arts', ' Parks', ' Convenience Stores', ' Karaoke', 
# ' Swimming Pools', 'Public Services & Government', ' Nightlife', ' Local Services', ' Public Markets'
# , 'Used Bookstore', ' Health & Medical', ' Restaurants', ' Boat Charters', ' Sports Betting', ' Fitness & Instruction',
# ' Real Estate Agents', " Men's Clothing", ' Comedy Clubs', ' Taxis', ' Beauty & Spas', ' Outdoor Gear', ' Fishing', ' Tennis',
# 'Kitchen & Bath', ' Massage Therapy', ' Toy Stores', ' Pets', ' General Dentistry', ' Mass Media', ' Wedding Planning', ' Souvenir Shops',
# ' Arts & Crafts', ' Visitor Centers', ' Hotels & Travel', ' Golf', 'Hotels', ' Hunting & Fishing Supplies', ' Florists', ' Home & Garden',
# ' Furniture Stores', ' Farmers Market', ' Beaches', ' Community Service/Non-Profit', ' Recreation Centers', 
# ' Adult Entertainment', 'Active Life', ' Candle Stores', 'Books', 'International Grocery', ' Nurseries & Gardening',
# ' Cooking Schools', ' Hospitals', ' Banks & Credit Unions', 'Health Markets', ' Public Services & Government',
# ' Property Management', ' Unofficial Yelp Events', ' Hobby Shops', ' Costumes', 'Buddhist Temples', ' Colleges & Universities', ' Playgrounds',
# ' Museums', ' Cannabis Dispensaries', ' Professional Services', ' Party Equipment Rentals', ' Mini Golf', ' Fashion', ' Jewelry',
# ]

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




graph = Graph('neo4j+s://ab6bbbca.databases.neo4j.io:7687', user="neo4j", password="qeARmHp0V_OSxravPEZC-yot-unRoFsI5YgYy5Ws-Hs")

cuisine_node = []

with open("philadelphia.json", 'r') as fo:
    rest = json.load(fo)


cuisine_list = []
for i in rest:
    if("categories" in i and "Restaurant" in i["categories"] ):
        x = i["categories"].split(',')
        cat = []
        for j in x:
            #print(j)
            new_string = j.strip()
            #print(new_string)
            if(new_string not in cuisines_to_remove):
                cat.append(new_string)
        cuisine_list = cuisine_list+cat


cuisine_new = map(str, cuisine_list)
#print(set(cuisine_list))

cuisine_unique = set(cuisine_list)

tx = graph.begin()
cuisine_node = []
for i in cuisine_unique:
    cuisine_node.append(Node("Cuisine", name = i) )
#tx.create(wifi_node[0])
#tx.create(wifi_node[1])
    tx.create(cuisine_node[-1])
    

tx.commit()




