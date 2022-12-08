import * as React from "react";

import {
    FormField,
    Autosuggest,
    Input,
    Box
    
} from "@cloudscape-design/components";

import { 
    FormControl, 
    Button
} from '@mui/material';

export function UserSignup(){
  /* Collection Hooks */
  const appLayout = React.useRef();
      //Populate table with fetch data example
  const [password, setPassword] = React.useState("")
  const [name, setName] = React.useState("")
  const [error, setError] = React.useState(false);

  const [userName, setuserName] = React.useState("");
  
  //Replace this with user entity node creation
  //Probably want to set the user vector and send a post request to the endpoints
  const handleSubmit = (event) => {
    event.preventDefault();
    fetch('/user', {
    method: 'POST',
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({ "name": name, "email": userName, "password": password })
    })
    .then(response => response.json())
    .then(response => console.log(JSON.stringify(response)))

  };
    return (
    <Box>
      <form onSubmit={handleSubmit}>

          <FormControl sx={{ m: 3 }} error={error} variant="standard">
            <FormField
              description="Enter Name."
              label="Name"
            >
              <Input
                value={name}
                onChange={event =>
                  setName(event.detail.value)
                }
              />
            </FormField>
            <FormField
              description="Enter Email."
              label="Email"
            >
                <Input
                value={userName}
                onChange={event =>
                  setuserName(event.detail.value)
                }
              />
            </FormField>
            <FormField
              description="Enter Password."
              label="Password"
            >
              <Input
                value={password}
                onChange={event =>
                  setPassword(event.detail.value)
                }
              />
            </FormField>
            <Button sx={{ mt: 1, mr: 1 }} type="submit" variant="outlined">
              
              Submit
            </Button>
            </FormControl>
            </form>
            
      </Box>
    )
}


