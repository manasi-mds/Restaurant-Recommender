import * as React from "react";
import GoogleMapReact from 'google-map-react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import {
    FormField,
    Autosuggest,
    Input,
    Box,
    Select,
    ExpandableSection
    
} from "@cloudscape-design/components";

import { 

    RadioGroup, 
    FormControl, 
    FormControlLabel, 
    FormLabel, 
    Radio, 
    Button, 
    FormHelperText
    } 
    from '@mui/material';

import { useLocalStorage } from '../commons/localStorage';
import { useColumnWidths } from '../commons/use-column-widths';
import {RestPropertyFilterTable} from '../tables/restTable';
import { PREF_REST_COLUMN_DEFINITIONS, PREF_REST_FILTERING_PROPERTIES, PREF_REST_DEFAULT_PREFERENCES } from '../tables/prefRestTable-property-filter-config';
import { useId } from "react";


export function UserPreferences(){

  const API_KEY = "AIzaSyBJPrEYIojDH_794kMGjcDrTzihHa6HvwY";
  //***************** Map Stuff */
  const[lat, setLat] = React.useState(39.97465381516658);
  const[longt, setLong] = React.useState(-75.20428157754901);
  
  const Wrapper = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  width: 18px;
  height: 18px;
  background-color: #000;
  border: 2px solid #fff;
  border-radius: 100%;
  user-select: none;
  transform: translate(-50%, -50%);
  cursor: ${(props) => (props.onClick ? 'pointer' : 'default')};
  &:hover {
    z-index: 1;
  }
  `;
  
  const Marker = ({ text, onClick }) => (
  <Wrapper
    alt={text}
    onClick={ev => {console.log(text)}}
  />
  );
  
  Marker.propTypes = {
  onClick: PropTypes.func,
  text: PropTypes.string.isRequired,
  };
    const defaultProps = {
      center: {
        lat: lat,
        lng: longt
      },
      zoom: 11,
    };

  //****************************Map Stuff End */
    const ambList = [{
      label: "touristy: (6109)",
      value: "touristy: (6109)"
    },
    {
      label: "hipster: (6110)",
      value: "hipster: (6110)"
    },
    {
      label: "romantic: (6111)",
      value: "romantic: (6111)"
    },
    { label: "divey: (6112)", 
      value: "divey: (6112)"
    },
    { label: "intimate: (6113)", 
      value: "intimate: (6113)"
    },
    { label: "trendy: (6114)", 
      value: "trendy: (6114)"
    },
    { label: "upscale: (6115)", 
      value: "pscale: (6115)"
    },
    { label: "classy: (6116)", 
      value: "classy: (6116)"
    },
    { label: "casual: (6117)", 
      value: "casual: (6117)"
    }]
    /* Collection Hooks */
    const [columnDefinitions, saveWidths] = useColumnWidths('React-TableServerSide-Widths', PREF_REST_COLUMN_DEFINITIONS);
    const [preferences, setPreferences] = useLocalStorage('React-DistributionsTable-Preferences', PREF_REST_DEFAULT_PREFERENCES);
    const [toolsOpen, setToolsOpen] = React.useState(false);
    const [
      selectedOption,
      setSelectedOption
    ] = React.useState({});
    const appLayout = React.useRef();
        //Populate table with fetch data example
    const [cuisines, setCuisines] = React.useState([]);
    const [restaurants, setRestaurants] = React.useState([]);

    const [maxDist, setMaxDist] = React.useState("");

    const [userID, setuserID] = React.useState("");
    const [alcoholWeight, setalcoholWeight] = React.useState("");
    const [cuisinesWeight1, setCuisinesWeight1] = React.useState("");
    const [cuisinesWeight2, setCuisinesWeight2] = React.useState("");
    const [cuisinesWeight3, setCuisinesWeight3] = React.useState("");
    const [cuisineSelected1, setCuisineSelected1] = React.useState("");
    const [cuisineSelected2, setCuisineSelected2] = React.useState("");
    const [cuisineSelected3, setCuisineSelected3] = React.useState("");
    const [ambience1, setAmbValue1] = React.useState("");
    const [ambience1Weight, setAmbience1Weight] = React.useState("");
    const [ambience2, setAmbValue2] = React.useState("");
    const [ambience2Weight, setAmbience2Weight] = React.useState("");
    const [ambience3, setAmbValue3] = React.useState("");
    const [ambience3Weight, setAmbience3Weight] = React.useState("");
    const [creditCardAccepted, setCredit] = React.useState("");
    const [wifiWeight, setWifiWeight] = React.useState("");
    const [minimumRating, setMinRating] = React.useState({});
    const [outdoorSeatingWeight, setOutDoorSeatingWeight] = React.useState("");
    const [cuisineOptions, setCuisineOptions] = React.useState([]);
    const [ratingsWeight, setRatingsWeight] = React.useState("");
    const [pullCuisine, setPullCuisine] = React.useState(true);
    const [updateCuisine, setUpdateCuisine] = React.useState(false);
    const [alcoholYN, setAlcoholYN] = React.useState({});
    const [wifiPF, setWifiPF] = React.useState({});
    const [odSeat, setODSeat] = React.useState({});
    
    const [creditCard, setCreditCard] = React.useState({});
        //Test Get API
    const fetchData = async () => {

      const response = await fetch("/cuisine")

      const data = await response.json();
      setCuisines(data.data);
      console.log(cuisines);
      
    }
    
    if(pullCuisine === true ){
        //setCuisineList([]); Set this at submit?
        setPullCuisine(false);
        fetchData();
    }
    
    if(cuisines.length - cuisineOptions.length > 50){
      setUpdateCuisine(true);
    }

    const filter = ["Cooking Schools", "Sporting Goods", "Mini Golf", "Unofficial Yelp Events"];
    if(updateCuisine === true){
        var tempArr = []
        for(var x = 0; x < cuisines.length; x++){
          if(!filter.includes(cuisines[x].name)){
            var y = {"value": cuisines[x].name + ' '.repeat(7) + cuisines[x].id.toString(), "label": cuisines[x].name + ' '.repeat(7) + cuisines[x].id.toString()};
            tempArr.push(y)
          }
          
          //setCuisineList(old => [...old, y]);

        }
        setCuisineOptions([...tempArr]);
        setUpdateCuisine(false);
    }
    

    const handleAlcoRadioChange = (event) => {
        setalcoholWeight(event.target.value);
        console.log(event.target.value)
        setError(false);
    };

    const handleWifiRadioChange = (event) => {
        setWifiWeight(event.target.value);
        console.log(event.target.value)
        setHelperText(' ');
        setError(false);
    };
    const handleOutDoorRadioChange = (event) => {
        setOutDoorSeatingWeight(event.target.value);
        console.log(event.target.value)
        setHelperText(' ');
        setError(false);
    };
    const handleAmbience1RadioChange = (event) => {
      setAmbience1Weight(event.target.value);
      console.log(event.target.value)
      setHelperText(' ');
      setError(false);
    };
    const handleAmbience2RadioChange = (event) => {
      setAmbience2Weight(event.target.value);
      console.log(event.target.value)
      setHelperText(' ');
      setError(false);
    };
    const handleAmbience3RadioChange = (event) => {
      setAmbience3Weight(event.target.value);
      console.log(event.target.value)
      setHelperText(' ');
      setError(false);
    };
    const handleCreditCardRadioChange = (event) => {
        setCredit(event.target.value);
        console.log(event.target.value)
        setHelperText(' ');
        setError(false);
    };
    const handleRatingsRadioChange = (event) => {
      setRatingsWeight(event.target.value);
      console.log(event.target.value)
      setHelperText(' ');
      setError(false);
    };
    
    const handleCuisinesRadioChange1 = (event) => {
      setCuisinesWeight1(event.target.value);
      console.log(event.target.value)
      setHelperText(' ');
      setError(false);
    };
    const handleCuisinesRadioChange2 = (event) => {
      setCuisinesWeight2(event.target.value);
      console.log(event.target.value)
      setHelperText(' ');
      setError(false);
    };    const handleCuisinesRadioChange3 = (event) => {
      setCuisinesWeight3(event.target.value);
      console.log(event.target.value)
      setHelperText(' ');
      setError(false);
    };

  const [error, setError] = React.useState(false);

  //Sets some text
  const [helperText, setHelperText] = React.useState(' ');
  const [selectedItems, setSelectedItems] = React.useState([]);

  const fetchRestData = async (event) => {
    event.preventDefault();

    try {
        const response = await fetch(
          //restaurant/getPreferredRestaurants/6134?lat=39.963042&lon=-75.1741858944
            "restaurant/getPreferredRestaurants/" + userID + "?lat=" +lat +"&lon=" + longt
        );
        const data = await response.json();
        
        for(var i = 0; i < data.data.length; i++){
            data.data[i].isAlcoholServed = data.data[i].isAlcoholServed.toString();
            data.data[i].isOpen = data.data[i].isOpen.toString();
            var cuisineList = "";
            for(var j = 0; j < data.data[i].cuisines.length; j++){
                cuisineList += data.data[i].cuisines[j].name;
                if(j < data.data[i].cuisines.length - 1){
                    cuisineList+=", ";
                }
            }
            data.data[i].cuisines = cuisineList;
        }
        console.log(data.data);
        console.log(selectedItems);
        setRestaurants(data.data);

    
    } catch (e) {
        console.error(e);
    }
}

const onLikeConfirm = (event) => {
  console.log("User: ", userID);
  console.log("selectedItems: ", selectedItems);

  if(selectedItems.length >0){
      for(var likes = 0; likes < selectedItems.length; likes++){
          if(selectedItems[likes].likeDislike === "Disliked"){
              selectedItems[likes].likeDislike = "Not Selected";
              try{
                  event.preventDefault();
                  console.log("select: ", selectedItems[likes]);
                  fetch('/user/dislikeRestaurant/' + userID + '/' + selectedItems[likes].id + "?" + "dislike=false", {
                  method: 'PUT',
                  headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
                  }
                  })
                  .then(response => response.json())
                  .then(response => console.log(JSON.stringify(response)))
              }
              catch (e) {
                  console.error(e);
              }
          }
          else{
              try{
                  event.preventDefault();
                  var likeState = "like=true";
                  console.log("select: ", selectedItems[likes]);
                  if(selectedItems[likes].likeDislike === "Liked"){
                      likeState = "like=false";
                      console.log("Thou art false");
                      selectedItems[likes].likeDislike = "Not Selected";
                  }
                  else{
                      selectedItems[likes].likeDislike = "Liked";
                  }
                  fetch('/user/likeRestaurant/' + userID + '/' + selectedItems[likes].id + "?" + likeState, {
                  method: 'PUT',
                  headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
                  }
                  })
                  .then(response => response.json())
                  .then(response => console.log(JSON.stringify(response)))
              }
              catch (e) {
                  console.error(e);
              }
          }
          
      }
  }
  
  //setRestaurants(restaurants.filter(d => !selectedItems.includes(d)));
  setSelectedItems([]);
};
  
  
  //Replace this with user entity node creation
  //Probably want to set the user vector and send a post request to the endpoints
  const handleSubmit = (event) => {
      console.log(cuisineSelected1.slice(-7));
      console.log(ambience1.slice(-5,-1));
      event.preventDefault();
      setError(false);
      var iAS = alcoholYN.value == "True" ? {"isAlcoholServed":"True", "weight": parseInt(alcoholWeight)} : {"isAlcoholServed":"False", "weight": parseInt(alcoholWeight)};
      var wifiTypes = {"wifiType":wifiPF.value, "weight": parseInt(wifiWeight)};
      var minRate = {"minRating": parseInt(minimumRating.value), "weight": parseInt(ratingsWeight)};
      var outdoor = odSeat.value  == "True" ? {"isOutdoorSeatingAvailable": true, "weight": parseInt(outdoorSeatingWeight)} : {"isOutdoorSeatingAvailable": false, "weight": parseInt(outdoorSeatingWeight)};
      var credit = creditCard.value  == "True" ? {"isCreditCardAccepted": true, "weight": parseInt(creditCardAccepted)} : {"isCreditCardAccepted": false, "weight": parseInt(creditCardAccepted)};
      var cuis = [];
      cuis[0] = {"cuisineId": parseInt(cuisineSelected1.slice(-7)), "weight": parseInt(cuisinesWeight1)};
      cuis[1] = {"cuisineId": parseInt(cuisineSelected2.slice(-7)), "weight": parseInt(cuisinesWeight2)};
      cuis[2] = {"cuisineId": parseInt(cuisineSelected3.slice(-7)), "weight": parseInt(cuisinesWeight3)};

      var amb = [];
      
      amb[0] = {"ambienceId": ambience1.slice(-5,-1), "weight": parseInt(ambience1Weight)};
      amb[1] = {"ambienceId": ambience2.slice(-5,-1), "weight": parseInt(ambience2Weight)};
      amb[2] = {"ambienceId": ambience3.slice(-5,-1), "weight": parseInt(ambience3Weight)};
      console.log({iAS, wifiTypes, minRate, outdoor, credit, amb, cuis});

      try{
        event.preventDefault();
        fetch('/user/' + userID + "/createPreference", {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ "distance": parseFloat(maxDist),"alcoholServed":iAS, "wifiTypeAvailable":wifiTypes, "minimumRating":minRate, "outdoorSeating":outdoor, "creditCardAccepted":credit, "ambiences":amb, "cuisines":cuis})
        })
        .then(response => response.json())
        .then(response => console.log(JSON.stringify(response)))
    }
    catch (e) {
        console.error(e);
    }
          
  };
    return (
    <Box>
      
      <Input
            value={userID}
            onChange={event =>
                setuserID(event.detail.value)
            }
        />
                    <div style={{ height: '50vh', width: '50%' }}>
      <GoogleMapReact
          onClick={ev => {
            console.log("latitide = ", ev.lat);
            setLat(ev.lat);
            console.log("longitude = ", ev.lng);
            setLong(ev.lng);

          }}
        bootstrapURLKeys={{ key:  API_KEY}}
        defaultCenter={defaultProps.center}
        defaultZoom={defaultProps.zoom}
        
      >
                <Marker
                key="Name"
                text="text"
                lat={lat}
                lng={longt}
              />
      </GoogleMapReact>
    </div>
      <ExpandableSection headerText="User Preference Update">
    <form onSubmit={handleSubmit}>

        <FormControl sx={{ m: 3 }} error={error} variant="standard">
        <FormField
            description="Max Distance (Miles) to Travel"
            label="Max Distance"
        >
        <Input
            value={maxDist}
            onChange={event =>
                setMaxDist(event.detail.value)
            }
        />
        </FormField>
        <FormLabel id="demo-radio-buttons-group-label">User Preferences</FormLabel>
          
            1. Would you like Alcohol?
            <Select
              selectedOption={alcoholYN}
              onChange={({ detail }) =>
                setAlcoholYN(detail.selectedOption)
              }
              options={[
                { label: "Yes", value: "True" },
                { label: "No", value: "False" }
              ]}
              selectedAriaLabel="Selected"
            />

            1.1 How important is that Choice to you?
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="radio-buttons-group"
                onChange={handleAlcoRadioChange}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>

            2. Would you like Free or Paid Wifi?
            <Select
              selectedOption={wifiPF}
              onChange={({ detail }) =>
                setWifiPF(detail.selectedOption)
              }
              options={[
                { label: "Free", value: "Free" },
                { label: "Paid", value: "Paid" }
              ]}
              selectedAriaLabel="Selected"
            />
            2.1 How important is that choice to you?
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleWifiRadioChange}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>
            3. Would you prefer having Outdoor Seating?
            <Select
              selectedOption={odSeat}
              onChange={({ detail }) =>
                setODSeat(detail.selectedOption)
              }
              options={[
                { label: "Yes", value: "True" },
                { label: "No", value: "False" }
              ]}
              selectedAriaLabel="Selected"
            />
            3.1 How important is that choice to you?
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleOutDoorRadioChange}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>
            4. Would you like to use Credit Card?
            <Select
              selectedOption={creditCard}
              onChange={({ detail }) =>
                setCreditCard(detail.selectedOption)
              }
              options={[
                { label: "Yes", value: "True" },
                { label: "No", value: "False" }
              ]}
              selectedAriaLabel="Selected"
            />
            4.1 How important is that choice to you?
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleCreditCardRadioChange}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>

            5. Select your 3 most preferred types of ambience and their importance to you
            
            5.1
            <Autosuggest
              onChange={({ detail }) => setAmbValue1(detail.value)}
              value={ambience1}
              options={ambList}
              enteredTextLabel={value => `Use: "${value}"`}
              ariaLabel="Autosuggest example with suggestions"
              placeholder="Enter ambience type"
              empty="No matches found"
            />
            5.2
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleAmbience1RadioChange}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>
            5.3
            <Autosuggest
              onChange={({ detail }) => setAmbValue2(detail.value)}
              value={ambience2}
              options={ambList}
              enteredTextLabel={value => `Use: "${value}"`}
              ariaLabel="Autosuggest example with suggestions"
              placeholder="Enter ambience type"
              empty="No matches found"
            />
            5.4
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleAmbience2RadioChange}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>
            5.5
            <Autosuggest
              onChange={({ detail }) => setAmbValue3(detail.value)}
              value={ambience3}
              options={ambList}
              enteredTextLabel={value => `Use: "${value}"`}
              ariaLabel="Autosuggest example with suggestions"
              placeholder="Enter ambience type"
              empty="No matches found"
            />
            5.6
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleAmbience3RadioChange}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>
            6. How important are Ratings to you?
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleRatingsRadioChange}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>
            6.1 Select your Minimum allowed Rating
            <Select
                selectedOption={selectedOption}
                onChange={({ detail }) =>
                    [setSelectedOption(detail.selectedOption),
                    setMinRating(detail.selectedOption)]
                }
                options={[
                    { label: "0", value: "0" },
                    { label: "0.5", value: "0.5" },
                    { label: "1", value: "1" },
                    { label: "1.5", value: "1.5" },
                    { label: "2", value: "2" },
                    { label: "2.5", value: "2.5" },
                    { label: "3", value: "3" },
                    { label: "3.5", value: "3.5" },
                    { label: "4", value: "4" },
                    { label: "4.5", value: "4.5" },
                    { label: "5", value: "5" }
                ]}
                selectedAriaLabel="Selected"
            />

            7. Select your 3 most preferred type of Cuisine:
            7.1
            <Autosuggest
              onChange={({ detail }) => setCuisineSelected1(detail.value)}
              value={cuisineSelected1}
              options={cuisineOptions}
              enteredTextLabel={value => `Use: "${value}"`}
              ariaLabel="Autosuggest example with suggestions"
              placeholder="Enter cuisine type"
              empty="No matches found"
            />
            7.2 How important is this cuisine to you?
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleCuisinesRadioChange1}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>            
            7.3
            <Autosuggest
              onChange={({ detail }) => setCuisineSelected2(detail.value)}
              value={cuisineSelected2}
              options={cuisineOptions}
              enteredTextLabel={value => `Use: "${value}"`}
              ariaLabel="Autosuggest example with suggestions"
              placeholder="Enter cuisine type"
              empty="No matches found"
            />
            7.4. How important is this cuisine to you?
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleCuisinesRadioChange2}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup> 
            7.5          
            <Autosuggest
              onChange={({ detail }) => setCuisineSelected3(detail.value)}
              value={cuisineSelected3}
              options={cuisineOptions}
              enteredTextLabel={value => `Use: "${value}"`}
              ariaLabel="Autosuggest example with suggestions"
              placeholder="Enter cuisine type"
              empty="No matches found"
            />
            7.6 How important is this cuisine to you?
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleCuisinesRadioChange3}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>

            <Button sx={{ mt: 1, mr: 1 }} type="submit" variant="outlined">
                Submit
            </Button>
            <FormHelperText>{helperText}</FormHelperText>
        </FormControl>

        </form>
        </ExpandableSection>
        <ExpandableSection headerText="Recommended Restaurants">
        <form onSubmit={fetchRestData}>

<FormControl sx={{ m: 3 }} error={error} variant="standard">
    <Button sx={{ mt: 1, mr: 1 }} type="submit" variant="outlined">
    Query
    </Button>
</FormControl>
</form>
        <RestPropertyFilterTable
        data={restaurants}
        selectedItems={selectedItems}
        onSelectionChange={event => setSelectedItems(event.detail.selectedItems)}
        loadHelpPanelContent={() => {
            setToolsOpen(true);
            appLayout.current?.focusToolsClose();
        }}
        columnDefinitions={columnDefinitions}
        saveWidths={saveWidths}
        preferences={preferences}
        setPreferences={setPreferences}
        filteringProperties={PREF_REST_FILTERING_PROPERTIES}
        onLike={onLikeConfirm}
        />
          </ExpandableSection>
        
      </Box>
    )
}


