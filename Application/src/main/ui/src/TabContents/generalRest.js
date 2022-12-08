import * as React from "react";

import {
    FormField,
    Toggle,
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


export function GenRestTab(){
    const [columnDefinitions, saveWidths] = useColumnWidths('React-TableServerSide-Widths', REST_COLUMN_DEFINITIONS);
    const [preferences, setPreferences] = useLocalStorage('React-DistributionsTable-Preferences', REST_DEFAULT_PREFERENCES);
    const [toolsOpen, setToolsOpen] = React.useState(false);
    const appLayout = React.useRef();
    //Vector for user inputs
    const [restaurants, setRestaurants] = React.useState([])
    const [user, setUser] = React.useState("")
    const [error, setError] = React.useState(false);

    const [selectedItems, setSelectedItems] = React.useState([]);

    const onLikeConfirm = (event) => {
        console.log("User: ", user);
        console.log("selectedItems: ", selectedItems);

        if(selectedItems.length >0){
            for(var likes = 0; likes < selectedItems.length; likes++){
                try{
                    event.preventDefault();
                    fetch('/user/likeRestaurant/' + user + '/' + selectedItems[likes].id + "?like=true", {
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
        
        //setRestaurants(restaurants.filter(d => !selectedItems.includes(d)));
        //setSelectedItems([]);
    };

    const fetchRestData = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch(
                '/restaurant/'+user
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
            console.log(selectedItems);
            setRestaurants(data.data)

        
        } catch (e) {
            console.error(e);
        }
    }
  
  return(
    <Box>
        <form onSubmit={fetchRestData}>

            <FormControl sx={{ m: 3 }} error={error} variant="standard">
                <FormField
                description="Enter User."
                label="User"
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
    </Box>
  
  )
}
