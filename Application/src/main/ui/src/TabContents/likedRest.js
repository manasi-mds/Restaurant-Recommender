import * as React from "react";

import {
    FormField,
    Input,
    Box
    
} from "@cloudscape-design/components";

import { 
    FormControl, 
    Button, 
    FormHelperText
} from '@mui/material';
import { useLocalStorage } from '../commons/localStorage';
import { useColumnWidths } from '../commons/use-column-widths';
import {RestPropertyFilterTable} from '../tables/restTable';
import { REST_COLUMN_DEFINITIONS, REST_FILTERING_PROPERTIES, REST_DEFAULT_PREFERENCES } from '../tables/restTable-property-filter-config';


export function LikedRestTab(){
    const [columnDefinitions, saveWidths] = useColumnWidths('React-TableServerSide-Widths', REST_COLUMN_DEFINITIONS);
    const [preferences, setPreferences] = useLocalStorage('React-DistributionsTable-Preferences', REST_DEFAULT_PREFERENCES);
    const [toolsOpen, setToolsOpen] = React.useState(false);
    const appLayout = React.useRef();
    //Vector for user inputs
    const [restaurants, setRestaurants] = React.useState([])
    const [user, setUser] = React.useState("");
    const [error, setError] = React.useState(false);


    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch(
                '/user/' + user + '/likedRestaurants'
            );
            const data = await response.json();
            
            for(var i = 0; i < data.data.length; i++){
                //
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
            setRestaurants(data.data)

        
        } catch (e) {
            console.error(e);
        }
    }
  
  return(
    <Box>

        <form onSubmit={handleSubmit}>
            <FormControl sx={{ m: 3 }} error={error} variant="standard">

                <FormField
                    description="Enter userID."
                    label="userID"
                >
                    <Input
                        value={user}
                        onChange={event =>
                            setUser(event.detail.value)
                        }
                    />
                </FormField>
                <Button sx={{ mt: 1, mr: 1 }} type="submit" variant="outlined">
                    
                    Submit
                    </Button>
            </FormControl>
        </form>
        <RestPropertyFilterTable
        data={restaurants}
        loadHelpPanelContent={() => {
        setToolsOpen(true);
        appLayout.current?.focusToolsClose();
        }}
        columnDefinitions={columnDefinitions}
        saveWidths={saveWidths}
        preferences={preferences}
        setPreferences={setPreferences}
        filteringProperties={REST_FILTERING_PROPERTIES}
        />
    </Box>
  
  )
}
