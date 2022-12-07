import './App.css';
import * as React from "react";
import {
  Tabs,
  Autosuggest
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

import {PropertyFilterTable} from "./property-filter-table";
import { useLocalStorage } from './localStorage';
import { useColumnWidths } from './use-column-widths';
import { COLUMN_DEFINITIONS, FILTERING_PROPERTIES, DEFAULT_PREFERENCES } from './table-property-filter-config';


function App() {

  /* Collection Hooks */
  const [columnDefinitions, saveWidths] = useColumnWidths('React-TableServerSide-Widths', COLUMN_DEFINITIONS);
  const [preferences, setPreferences] = useLocalStorage('React-DistributionsTable-Preferences', DEFAULT_PREFERENCES);
  const [toolsOpen, setToolsOpen] = React.useState(false);
  //Multiselect example
  const [ambvalue1, setAmbValue1] = React.useState("");
  const [ambvalue2, setAmbValue2] = React.useState("");
  const [ambvalue3, setAmbValue3] = React.useState("");

  const appLayout = React.useRef();
  //Populate table with fetch data example
  const [cuisine, setCuisine] = React.useState([])

  //getting radio values example
  const [value1, setValue1] = React.useState('');
  const [vegVal, setVegVal] = React.useState('');
  const [error, setError] = React.useState(false);

  //Sets some text
  const [helperText, setHelperText] = React.useState(' ');

  //Test Get API
  const fetchData = async () => {

    const response = await fetch("/cuisine")

    const data = await response.json()
    console.log(data)
    setCuisine(data.data)

  }

  React.useEffect(() => {

    fetchData()

  }, [])

  //Vector for user inputs
  var newUser = [];

  const handleRadioChange1 = (event) => {
    setValue1(event.target.value);
    console.log(event.target.value)
    setError(false);
  };
  const handleVegRadioChange = (event) => {
    setVegVal(event.target.value);
    console.log(event.target.value)
    setHelperText(' ');
    setError(false);
  };

  //Replace this with user entity node creation
  //Probably want to set the user vector and send a post request to the endpoints
  const handleSubmit = (event) => {
    newUser[0] = parseFloat(vegVal);
    newUser.push(ambvalue1);
    event.preventDefault();
    setHelperText(newUser[0]);
    setError(false);
    /*
    if (vegVal === 'veg1') {
      setHelperText(vegVal);
      setError(false);
    } else if (value1 === '2' || ambvalue1 === "Suggestion 1") {
      setHelperText('2!');
      setError(true);
    } else {
      setHelperText('Please select an option.');
      setError(true);
    }*/
  };
  const [
    selectedItems,
    setSelectedItems
  ] = React.useState([{ name: "Item 2" }]);
  
  return (
    <div className="App">

    <Tabs
      tabs={[
        {
          label: "User Signup",
          id: "uSign",
          content:  
            <form onSubmit={handleSubmit}>
              <FormControl sx={{ m: 3 }} error={error} variant="standard">

              <FormLabel id="demo-radio-buttons-group-label">Test</FormLabel>
                <RadioGroup row
                  aria-labelledby="demo-radio-buttons-group-label"
                  name="radio-buttons-group"
                  onChange={handleRadioChange1}
                >
                  <FormControlLabel value="1" control={<Radio />} label="1" />
                  <FormControlLabel value="2" control={<Radio />} label="2" />
                  <FormControlLabel value="3" control={<Radio />} label="Other" />
                </RadioGroup>
                How important is Vegetarianism to you?
                <RadioGroup row
                  aria-labelledby="demo-radio-buttons-group-label"
                  name="veg-buttons-group"
                  onChange={handleVegRadioChange}
                >
                  <FormControlLabel value="1" control={<Radio />} label="Exclusively Vegetarian" />
                  <FormControlLabel value="0.75" control={<Radio />} label="Mostly Vegetarian" />
                  <FormControlLabel value="0.5" control={<Radio />} label="Open to Vegetarian" />
                  <FormControlLabel value="0.25" control={<Radio />} label="Indifferent" />
                  <FormControlLabel value="0" control={<Radio />} label="Prefers non-Vegetarian" />
                </RadioGroup>

                Select your 3 most preferred type of ambiance:
                <Autosuggest
                onChange={({ detail }) => setAmbValue1(detail.value)}
                value={ambvalue1}
                options={[
                  { value: "Suggestion 1" },
                  { value: "Suggestion 2" },
                  { value: "Suggestion 3" },
                  { value: "Suggestion 4" },
                  { value: "Hello" }
                ]}
                enteredTextLabel={value => `Use: "${value}"`}
                ariaLabel="Ambiance1"
                placeholder="Enter desired ambiance"
                empty="No matches found"
                />

                <Autosuggest
                onChange={({ detail }) => setAmbValue2(detail.value)}
                value={ambvalue2}
                options={[
                  { value: "Suggestion 1" },
                  { value: "Suggestion 2" },
                  { value: "Suggestion 3" },
                  { value: "Suggestion 4" },
                  { value: "Hello" }
                ]}
                enteredTextLabel={value => `Use: "${value}"`}
                ariaLabel="Ambiance2"
                placeholder="Enter desired ambiance"
                empty="No matches found"
                />
                <Autosuggest
                onChange={({ detail }) => setAmbValue3(detail.ambvalue3)}
                value={ambvalue3}
                options={[
                  { value: "Suggestion 1" },
                  { value: "Suggestion 2" },
                  { value: "Suggestion 3" },
                  { value: "Suggestion 4" },
                  { value: "Hello" }
                ]}
                enteredTextLabel={value => `Use: "${value}"`}
                ariaLabel="Ambiance3"
                placeholder="Enter value"
                empty="No matches found"
                />
        
                <Button sx={{ mt: 1, mr: 1 }} type="submit" variant="outlined">
                        Submit
                      </Button>
                      <FormHelperText>{helperText}</FormHelperText>
              </FormControl>
            </form>
        },
        {
          label: "Second tab label",
          id: "second",
          content: "test"
        }
      ]}
    />
    
    <PropertyFilterTable
          data={cuisine}
          loadHelpPanelContent={() => {
            setToolsOpen(true);
            appLayout.current?.focusToolsClose();
          }}
          columnDefinitions={columnDefinitions}
          saveWidths={saveWidths}
          preferences={preferences}
          setPreferences={setPreferences}
          filteringProperties={FILTERING_PROPERTIES}
        />

    </div>
  );
}



export default App;
