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
import {UserPropertyFilterTable} from '../tables/users';
import { USER_COLUMN_DEFINITIONS, USER_FILTERING_PROPERTIES, USER_DEFAULT_PREFERENCES } from '../tables/usersConfig';


export function PFollowingTab(){
    const [userColumnDefinitions, userSaveWidths] = useColumnWidths('React-TableServerSide-Widths', USER_COLUMN_DEFINITIONS);
    const [userPreferences, userSetPreferences] = useLocalStorage('React-DistributionsTable-Preferences', USER_DEFAULT_PREFERENCES);
    const [toolsOpen, setToolsOpen] = React.useState(false);
    const appLayout = React.useRef();
    //Vector for user inputs
    const [user, setUser] = React.useState("");
    const [pFriends, setPFriends] = React.useState([]);
    const [error, setError] = React.useState(false);
    const [selectedItems, setSelectedItems] = React.useState([]);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch(
                '/user/' + user + '/potentialFriends'
            );
            const data = await response.json();
            setPFriends(data.data)
        } catch (e) {
            console.error(e);
        }
    }
  

    const onLikeConfirm = (event) => {
        console.log("User: ", user);
        console.log("selectedItems: ", selectedItems);

        if(selectedItems.length >0){
            for(var follows = 0; follows < selectedItems.length; follows++){
                try{
                    event.preventDefault();
                    fetch('/user/' + user + '/follow/' + selectedItems[follows].id + "?follow=true", {
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
        
        //setPFriends(pFriends.filter(d => !selectedItems.includes(d)));
        //setSelectedItems([]);
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
            data={pFriends}
            selectedItems={selectedItems}
            onSelectionChange={event => setSelectedItems(event.detail.selectedItems)}
            loadHelpPanelContent={() => {
            setToolsOpen(true);
            appLayout.current?.focusToolsClose();
            }}
            columnDefinitions={userColumnDefinitions}
            saveWidths={userSaveWidths}
            preferences={userPreferences}
            setPreferences={userSetPreferences}
            filteringProperties={USER_FILTERING_PROPERTIES}
            onFollow={onLikeConfirm}
        />
    </Box>
  
  )
}
