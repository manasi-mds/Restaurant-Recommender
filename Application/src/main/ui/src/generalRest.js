import * as React from "react";

import {
    FormField,
    Toggle,
    Input,
    Box
    
} from "@cloudscape-design/components";
import { useLocalStorage } from './commons/localStorage';
import { useColumnWidths } from './commons/use-column-widths';
import {RestPropertyFilterTable} from './tables/restTable';
import { REST_COLUMN_DEFINITIONS, REST_FILTERING_PROPERTIES, REST_DEFAULT_PREFERENCES } from './tables/restTable-property-filter-config';


export function GenRestTab(){
    const [columnDefinitions, saveWidths] = useColumnWidths('React-TableServerSide-Widths', REST_COLUMN_DEFINITIONS);
    const [preferences, setPreferences] = useLocalStorage('React-DistributionsTable-Preferences', REST_DEFAULT_PREFERENCES);
    const [toolsOpen, setToolsOpen] = React.useState(false);
    const appLayout = React.useRef();
    //Vector for user inputs
    const [restaurants, setRestaurants] = React.useState([])
    const [checked, setChecked] = React.useState(false);

    const fetchRestData = async () => {
    try {
        const response = await fetch(
            '/restaurant'
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

    if(checked == true){
        fetchRestData();
    }
  
  return(
    <Box>

    <Toggle
    onChange={({ detail }) =>
      setChecked(detail.checked)
    }
    checked={checked}
  >
    Toggle
  </Toggle>
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
