# Restaurant-Recommender

## Data Preparation and Setup 
Neo4j. Installation is not necessary, as our dataset is hosted remotely, but the neo4j browser can be downloaded via https://neo4j.com/download/

## Data Download
The original dataset is present in philadelphia.json (cleaned from https://www.yelp.com/dataset) in the github repository.

## Loading the Data
The following python files have to be executed in the given order.

```loadRestaurant.py```
```loadAlcohol.py```
```loadAmbience.py```
```loadCredit.py```
```loadCuisine.py```
```loadOutdoor.py```
```loadRestaurantTimings.py```
```loadStar.py```
```loadWifi.py```
```relationAlcohol.py```
```relationAmbience.py```
```relationCredit.py```
```relationCuisine.py```
```relationOutdoor.py```
```relationStar.py```
```relationWifi.py```\

This line would have to be modified in the scripts to load the data to your local neo4j instance.\
```graph = Graph('bolt://localhost:7687', user="neo4j", password="local123")```

## Programming language(s) and version(s) 
Java 17\
Node.js 18\
Postman Link:
https://api.postman.com/collections/21978038-aa3bd017-b380-40fb-aa4f-d3edb67e4330?access_key=PMAT-01GKQCH23XBD8YHTKGF7ZMTPBD


## Third-party libraries
MySQL/neo4j connector for Python)\
Maven (backend libraries detailed in pom.xml)\
Install Maven (Optional) : https://maven.apache.org/install.html

###### Backend Dependencies (already included in pom.xml):
```spring-boot-starter-data-neo4j```
```spring-boot-starter-web```
```spring-boot-devtools```
```lombok```
```spring-boot-starter-test```
```commons-lang3```
```json-simple```
```spring-boot-starter-data-rest```
```Spring-boot-starter-web```

###### Backend Plugins (already included in pom.xml):
```spring-boot-maven-plugin```
```frontend-maven-plugin```
```maven-resources-plugin```

## To run GUI

Run ```lazystart.sh``` 

This file runs 
1. ```mvn generate```
2. ```java -jar target/RestaurantRecommender-0.0.1-SNAPSHOT.jar```


## Other sources
###### For the frontend
UI elements based on and sourced from https://github.com/cloudscape-design/demos, featured in https://cloudscape.design/demos/overview/. 
Map code based on the example in https://github.com/google-map-react/google-map-react.
Both are licensed under the MIT license. 

###### For the backend
None

## Changes to the frontend code referenced 
Changed the code to use our data, combined filter feature from property filter table with deletion and button functionality of simple delete table. 


