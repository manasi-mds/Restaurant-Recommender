import * as React from "react";

import {
    FormField,
    ExpandableSection,
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
import {UserPropertyFilterTable} from '../tables/users';
import {RecRestPropertyFilterTable} from '../tables/recRestTable';


import { REC_REST_COLUMN_DEFINITIONS, REC_REST_FILTERING_PROPERTIES, REC_REST_DEFAULT_PREFERENCES } from '../tables/recRestTable-property-filter-config';
import { USER_COLUMN_DEFINITIONS, USER_FILTERING_PROPERTIES, USER_DEFAULT_PREFERENCES } from '../tables/usersConfig';


export function GetFollowersTab(){
    const [restColumnDefinitions, restSaveWidths] = useColumnWidths('React-TableServerSide-Widths', REC_REST_COLUMN_DEFINITIONS);
    const [restPreferences, restSetPreferences] = useLocalStorage('React-DistributionsTable-Preferences', REC_REST_DEFAULT_PREFERENCES);
    const [userColumnDefinitions, userSaveWidths] = useColumnWidths('React-TableServerSide-Widths', USER_COLUMN_DEFINITIONS);
    const [userPreferences, userSetPreferences] = useLocalStorage('React-DistributionsTable-Preferences', USER_DEFAULT_PREFERENCES);
    const [toolsOpen, setToolsOpen] = React.useState(false);
    const appLayout = React.useRef();
    //Vector for user inputs
    const [user, setUser] = React.useState("")
    const [followers, setFollowers] = React.useState([]);
    const [error, setError] = React.useState(false);
    const [restaurants, setRestaurants] = React.useState([])
    const [selectedItems, setSelectedItems] = React.useState([]);
    const [selectedUsers, setSelectedUsers] = React.useState([]);

    const handleSubmit = async (event) => {
    event.preventDefault();
        try {
            const response = await fetch(
                '/user/'+user + '/followedUsers'
            );
            const data = await response.json();
            setFollowers(data.data)
            
        
        } catch (e) {
            console.error(e);
        }
    }
    const handleSecondSubmit = async (event) => {
        event.preventDefault();
        for(var j = 0; j < followers.length; j++){
            try{
                const res = await fetch(
                    '/user/' + followers[j].id + '/likedRestaurants'
                );
                const d = await res.json();
                
                for(var i = 0; i < d.data.length; i++){
                    

                    d.data[i].isAlcoholServed = d.data[i].isAlcoholServed.toString();
                    d.data[i].isOpen = d.data[i].isOpen.toString();
                    var cuisineList = "";
                    for(var k = 0; k < d.data[i].cuisines.length; k++){
                        cuisineList += d.data[i].cuisines[k].name;
                        if(k < d.data[i].cuisines.length - 1){
                            cuisineList+=", ";
                        }
                    }
                    d.data[i].cuisines = cuisineList;
                    d.data[i].recommender = followers[j].name;

                }
                console.log(d.data);
                setRestaurants(old => [...old, ...d.data])
                //setRestaurants(restaurants.concat(d.data));
                console.log(restaurants);
            } catch (e) {
                console.error(e);
            }
        }
    }
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
            <UserPropertyFilterTable
            selectedItems={selectedUsers}
            onSelectionChange={event => setSelectedUsers(event.detail.selectedItems)}
                data={followers}
                loadHelpPanelContent={() => {
                setToolsOpen(true);
                appLayout.current?.focusToolsClose();
                }}
                columnDefinitions={userColumnDefinitions}
                saveWidths={userSaveWidths}
                preferences={userPreferences}
                setPreferences={userSetPreferences}
                filteringProperties={USER_FILTERING_PROPERTIES}
            />
            <ExpandableSection headerText="Recommended Restaurants">

            <form onSubmit={handleSecondSubmit}>
                <FormControl sx={{ m: 3 }} error={error} variant="standard">
                    <Button sx={{ mt: 1, mr: 1 }} type="submit" variant="outlined">
                        
                        Get the Users' Liked Restaurants
                        </Button>
                </FormControl>
            </form>
            <RecRestPropertyFilterTable
            data={restaurants}
            selectedItems={selectedItems}
            onSelectionChange={event => setSelectedItems(event.detail.selectedItems)}
            loadHelpPanelContent={() => {
                setToolsOpen(true);
                appLayout.current?.focusToolsClose();
            }}
            columnDefinitions={restColumnDefinitions}
            saveWidths={restSaveWidths}
            preferences={restPreferences}
            setPreferences={restSetPreferences}
            filteringProperties={REC_REST_FILTERING_PROPERTIES}
            onLike={onLikeConfirm}
            />
        </ExpandableSection>

        </Box>
    
  )
}
