import * as React from "react";

import {
    FormField,
    Autosuggest,
    Input,
    Box,
    Select,
    Multiselect,
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
import { REST_COLUMN_DEFINITIONS, REST_FILTERING_PROPERTIES, REST_DEFAULT_PREFERENCES } from '../tables/restTable-property-filter-config';


export function UserPreferences(){
    /* Collection Hooks */
    const [columnDefinitions, saveWidths] = useColumnWidths('React-TableServerSide-Widths', REST_COLUMN_DEFINITIONS);
    const [preferences, setPreferences] = useLocalStorage('React-DistributionsTable-Preferences', REST_DEFAULT_PREFERENCES);
    const [toolsOpen, setToolsOpen] = React.useState(false);
    const [
      selectedOption,
      setSelectedOption
    ] = React.useState({});
    const appLayout = React.useRef();
        //Populate table with fetch data example
    const [cuisines, setCuisines] = React.useState([])
    const [restaurants, setRestaurants] = React.useState([])


  /*
  {"alcoholServed":{"isAlcoholServed":"True", "weight": 1 },
"cuisines":[{"cuisineId":22, "weight": 1 }],
"ambiences":[{"ambienceId": 6112, "weight": 1}],
"creditCardAccepted":{"isCreditCardAccepted": "True", "weight": 1},
"wifiTypeAvailable":{"wifiType": "Free", "weight": 1},
"minimumRating":{"minRating":3, "weight":1},
"outdoorSeating":{"isOutdoorSeatingAvailable": "True", "weight":1 }
}
  */
//Need to finish this + return restaurants
    const [userID, setuserID] = React.useState("");
    const [alcoholServed, setalcoholServed] = React.useState("");
    const [cuisinesWeight, setCuisinesWeight] = React.useState("");
    const [cuisinesSelected, setCuisinesSelected] = React.useState([]);
    //Multiselect example
    const [ambience, setAmbValue] = React.useState([]);
    const [ambienceWeight, setAmbienceWeight] = React.useState("");
    const [creditCardAccepted, setCredit] = React.useState("");
    const [wifiTypeAvailable, setWifi] = React.useState("");
    const [minimumRating, setMinRating] = React.useState("");
    const [outdoorSeating, setOutDoorSeating] = React.useState("");
    const [cuisineList, setCuisineList] = React.useState([]);
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
      console.log(data);
      setCuisines(data.data);
      
    }
    
    if(pullCuisine === true ){
        //setCuisineList([]); Set this at submit?
        setPullCuisine(false);
        fetchData();
    }
    if(cuisines.length != cuisineList.length){
      setUpdateCuisine(true);
    }

    if(updateCuisine === true){
        var tempArr = []
        console.log("Help");
        for(var x = 0; x < cuisines.length; x++){
          var y = {"label": cuisines[x].name, "value": cuisines[x].id};
          //console.log("Y: ", y);
          tempArr.push(y)
          //setCuisineList(old => [...old, y]);
          //console.log(cuisineList);

        }
        console.log(tempArr);
        setCuisineList([...tempArr]);
        console.log(cuisineList);
        setUpdateCuisine(false);
    }
    

    const [vegVal, setVegVal] = React.useState('');
    const handleAlcoRadioChange = (event) => {
        setalcoholServed(event.target.value);
        console.log(event.target.value)
        setError(false);
    };
    const handleVegRadioChange = (event) => {
        setVegVal(event.target.value);
        console.log(event.target.value)
        setHelperText(' ');
        setError(false);
    };
    const handleWifiRadioChange = (event) => {
        setWifi(event.target.value);
        console.log(event.target.value)
        setHelperText(' ');
        setError(false);
    };
    const handleOutDoorRadioChange = (event) => {
        setOutDoorSeating(event.target.value);
        console.log(event.target.value)
        setHelperText(' ');
        setError(false);
    };
    const handleAmbienceRadioChange = (event) => {
      setAmbienceWeight(event.target.value);
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
    
    const handleCuisinesRadioChange = (event) => {
      setCuisinesWeight(event.target.value);
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
            "/user/" + userID + "/potentialRestaurants"
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
        console.log("ehllo");
        console.log(selectedItems);
        setRestaurants(data.data)

    
    } catch (e) {
        console.error(e);
    }
}

  const onLikeConfirm = (event) => {
      console.log("selectedItems: ", selectedItems);

      if(selectedItems.length >0){
          for(var likes = 0; likes < selectedItems.length; likes++){
              try{
                  event.preventDefault();
                  fetch('/user/likeRestaurant/' + userID + '/' + selectedItems[likes].id + "?like=true", {
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
      
      setRestaurants(restaurants.filter(d => !selectedItems.includes(d)));
      setSelectedItems([]);
  };
  
  
  //Replace this with user entity node creation
  //Probably want to set the user vector and send a post request to the endpoints
  const handleSubmit = (event) => {

      event.preventDefault();
      console.log(userID, alcoholServed, cuisinesSelected, cuisinesWeight, ambience, ambienceWeight, creditCardAccepted, wifiTypeAvailable, minimumRating, outdoorSeating);
      setError(false);
      var iAS = {"isAlcoholServed":alcoholYN, "weight": alcoholServed};
      var wifiTypes = {"wifiType":wifiPF, "weight": wifiTypeAvailable};
      var minRate = {"minRating": minimumRating, "weight": ratingsWeight};
      var outdoor = {"isOutdoorSeatingAvailable": odSeat, "weight": outdoorSeating};
      var credit = {"isCreditCardAccepted": creditCard, "weight": creditCardAccepted};
      var cuis = [];
      for(var l = 0; l < cuisinesSelected.length; l++){
        cuis[l] = {"cuisineId": cuisinesSelected[l], "weight": cuisinesWeight};
      }
      var amb = [];
      for(var k = 0; k < ambience.length; k++){
        amb[k] = {"ambienceId": ambience[k], "weight": ambienceWeight};
      }

/*
{"alcoholServed":{"isAlcoholServed":"True", "weight": 1 },
"cuisines":[{"cuisineId":22, "weight": 1 }],
"ambiences":[{"ambienceId": 6112, "weight": 1}],
"creditCardAccepted":{"isCreditCardAccepted": "True", "weight": 1},
"wifiTypeAvailable":{"wifiType": "Free", "weight": 1},
"minimumRating":{"minRating":3, "weight":1},
"outdoorSeating":{"isOutdoorSeatingAvailable": "True", "weight":1 }
}
*/
      try{
        event.preventDefault();
        fetch('/user/' + userID + "/createPreference", {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ iAS, wifiTypes, minRate, outdoor, credit, amb, cuis})
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
    <form onSubmit={handleSubmit}>

        <FormControl sx={{ m: 3 }} error={error} variant="standard">

        <FormField
            description="Enter ID."
            label="id"
        >
        <Input
            value={userID}
            onChange={event =>
                setuserID(event.detail.value)
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

            2. How important is that Choice to you?
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

            2. How important is Vegetarianism to you?
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleVegRadioChange}
            >
            <FormControlLabel value="5" control={<Radio />} label="Exclusively Vegetarian" />
            <FormControlLabel value="4" control={<Radio />} label="Mostly Vegetarian" />
            <FormControlLabel value="3" control={<Radio />} label="Open to Vegetarian" />
            <FormControlLabel value="2" control={<Radio />} label="Indifferent" />
            <FormControlLabel value="1" control={<Radio />} label="Prefers non-Vegetarian" />
            </RadioGroup>
            3. Would you like Free or Paid Wifi?
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
            4. How important is that choice to you?
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
            5. Would you prefer having Outdoor Seating?
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
            6. How important is that choice to you?
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
            7. Would you like to use Credit Card?
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
            5. How important is that choice to you?
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
            6. How important is Ambience to you?
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleAmbienceRadioChange}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>
            7. Select your 3 most preferred type of ambience:
            <Multiselect
              selectedOptions={ambience}
              onChange={({ detail }) =>
                setAmbValue(detail.selectedOptions)
              }
              options={[
                {
                  label: "touristy",
                  value: "touristy"
                },
                {
                  label: "hipster",
                  value: "hipster"
                },
                {
                  label: "romantic",
                  value: "romantic"
                },
                { label: "divey", 
                  value: "divey"
                },
                { label: "intimate", 
                  value: "intimate"
                },
                { label: "trendy", 
                  value: "trendy"
                },
                { label: "upscale", 
                  value: "upscale"
                },
                { label: "classy", 
                  value: "classy"
                },
                { label: "casual", 
                  value: "casual"
                }
                
              ]}
              placeholder="Choose options"
              selectedAriaLabel="Selected"
              tokenLimit={3}
            />
            
            8. How important are Ratings to you?
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
            9. How important is Cuisine to you?
            <RadioGroup row
                aria-labelledby="demo-radio-buttons-group-label"
                name="veg-buttons-group"
                onChange={handleCuisinesRadioChange}
            >
            <FormControlLabel value="5" control={<Radio />} label="Must Have" />
            <FormControlLabel value="4" control={<Radio />} label="Strongly Prefer" />
            <FormControlLabel value="3" control={<Radio />} label="Moderately Prefer" />
            <FormControlLabel value="2" control={<Radio />} label="Mildly Prefer" />
            <FormControlLabel value="1" control={<Radio />} label="Indifferent" />
            </RadioGroup>
            10. Select your 3 most preferred type of Cuisine:
            <Multiselect
              selectedOptions={cuisinesSelected}
              onChange={({ detail }) =>[
                  setCuisinesSelected(detail.selectedOptions),
                  setCuisinesSelected(detail.selectedOptions)
                ]
              }
              options={cuisineList}
              placeholder="Choose options"
              selectedAriaLabel="Selected"
              tokenLimit={3}
            />
            <Button sx={{ mt: 1, mr: 1 }} type="submit" variant="outlined">
                Submit
            </Button>
            <FormHelperText>{helperText}</FormHelperText>
        </FormControl>
        </form>
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
        filteringProperties={REST_FILTERING_PROPERTIES}
        onLike={onLikeConfirm}
        />
          </ExpandableSection>
        
      </Box>
    )
}


