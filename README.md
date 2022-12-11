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

This line would have to be modified in the scripts to load the data to your local neo4j instance.
```graph = Graph('bolt://localhost:7687', user="neo4j", password="local123")```
