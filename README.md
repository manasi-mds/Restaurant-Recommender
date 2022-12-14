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

## List of files by us

###### Python files for the data
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
```relationWifi.py```


###### Backend Files
```ResponseBody.java```   ```ResponseGenerator.java```
```UserController.java```   ```CuisineController.java```   ```AmbienceController.java```   ```RestaurantController.java```   ```MultiUserRecommenderController.java```   ```SingleUserRecommenderController.java```   ```FollowUser.java```   ```LikeRestaurant.java```   ```DislikeRestaurant.java```   ```AlcoholPreference.java```
```UserDTO.java```   ```CuisineDTO.java```   ```AmbienceDTO.java```   ```RestaurantDTO.java```   ```RestaurantUserDTO.java```   ```UserPreferenceDTO.java```   ```WifiPreferenceDTO.java```   ```WifiPreferenceDTO.java```   ```RatingPreferenceDTO.java```   ```CuisinePreferenceDTO.java```   ```AmbiencePreferenceDTO.java```   ```CreditCardPreferenceDTO.java```   ```AlcoholServedPreferenceDTO.java```   ```OutdoorSeatingPreferenceDTO.java```   ```UserRepository.java```   ```WifiRepository.java```   ```RatingRepository.java```   ```AlcoholRepository.java```   ```CuisineRepository.java```   ```AmbienceRepository.java```   ```CreditCardRepository.java```   ```RestaurantRepository.java```   ```ResponseGenerator.java```   ```OutdoorSeatingRepository.java```   ```CustomExceptionHandler.java```   ```OutdoorSeatingEntity.java```   ```OutdoorSeatingPreference.java```   ```WifiType.java```   ```ResponseBody.java```   ```StarRating.java```   ```BaseEntity.java```   ```FollowUser.java```   ```UserEntity.java```   ```WifiEntity.java```   ```UserMapper.java```   ```RatingEntity.java```   ```AlcoholEntity.java```   ```CuisineEntity.java```   ```CuisineMapper.java```   ```AmbienceEntity.java```   ```LikeRestaurant.java```   ```WifiPreference.java```   ```UserService.java```   ```utility/DistanceUtil.java```   ```AmbienceMapper.java```   ```IUserService.java```   ```CreditCardEntity.java``` ```BaseEntity.java```   ```FollowUser.java```   ```UserEntity.java```   ```WifiEntity.java```   ```RatingEntity.java```   ```AlcoholEntity.java```   ```CuisineEntity.java```   ```AmbienceEntity.java```   ```LikeRestaurant.java```   ```WifiPreference.java```   ```CreditCardEntity.java```   ```RatingPreference.java```   ```RestaurantEntity.java```   ```AlcoholPreference.java```   ```CuisinePreference.java```   ```DislikeRestaurant.java```   ```AmbiencePreference.java```   ```CreditCardPreference.java```   ```OutdoorSeatingEntity.java```   ```OutdoorSeatingPreference.java``` ```utility/DistanceUtil.java``` ```StarRating.java``` ```WifiType.java```  ```CustomExceptionHandler.java```
```UserMapper.java```   ```StarsMapper.java```   ```CuisineMapper.java```   ```AmbienceMapper.java```   ```RestaurantMapper.java```   ```UserPreferenceMapper.java``` ```UserRepository.java```   ```WifiRepository.java```   ```RatingRepository.java```   ```AlcoholRepository.java```   ```CuisineRepository.java```   ```AmbienceRepository.java```   ```CreditCardRepository.java```   ```RestaurantRepository.java```   ```OutdoorSeatingRepository.java``` ```UserService.java```   ```IUserService.java```   ```CuisineService.java```   ```AmbienceService.java```   ```ICuisineService.java```   ```IAmbienceService.java```   ```RestaurantService.java```   ```IRestaurantService.java``` ```RestaurantRecommenderApplication.java```


###### Frontend Files
```App.js``` 
```users.jsx```   
```restTable.jsx```   
```recRestTable.jsx```   
```sample_table.jsx```   
```prefRestTable.jsx```
```restTable-property-filter-config.js```   
```recRestTable-property-filter-config.js```   
```sample_table-property-filter-config.js```   
```prefRestTable-property-filter-config.js```
```likedRest.js```   
```userQuery.js```   
```userSignup.js```   
```userUpdate.js```   
```generalRest.js```   
```potentialFriend.js```

## Screenshot of the Application
![Screenshot](screenshot/examplequery.png)

