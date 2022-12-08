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
    const [selectedItems, setSelectedItems] = React.useState([]);

    const onLikeConfirm = (event) => {
        console.log("User: ", user);
        console.log("selectedItems: ", selectedItems);

        if(selectedItems.length >0){
            for(var likes = 0; likes < selectedItems.length; likes++){
                if(selectedItems[likes].likeDislike === "Disliked"){
                    selectedItems[likes].likeDislike = "Not Selected";
                    try{
                        event.preventDefault();
                        console.log("select: ", selectedItems[likes]);
                        fetch('/user/dislikeRestaurant/' + user + '/' + selectedItems[likes].id + "?" + "dislike=false", {
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
                        fetch('/user/likeRestaurant/' + user + '/' + selectedItems[likes].id + "?" + likeState, {
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
                if(data.data[i].wifi != null){
                    data.data[i].wifi = data.data[i].wifi.toString();
                  }
                  else{
                    data.data[i].wifi = "None";
                  }
                  if(data.data[i].isOutdoorSeatingAvailable != null){
                    data.data[i].isOutdoorSeatingAvailable = data.data[i].isOutdoorSeatingAvailable.toString();
                  }
                  else{
                    data.data[i].isOutdoorSeatingAvailable = "None";
                  }
                  if(data.data[i].isCreditCardAccepted != null){
                    data.data[i].isCreditCardAccepted = data.data[i].isCreditCardAccepted.toString();
                  }
                  else{
                    data.data[i].isCreditCardAccepted = "None";
                  }
                data.data[i].likeDislike = "Liked";

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
        onLike={onLikeConfirm}
        filteringProperties={REST_FILTERING_PROPERTIES}
        />
    </Box>
  
  )
}
