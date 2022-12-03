import './App.css';
import * as React from "react";
import ReactDOM from 'react-dom';

import {
  Tabs,
  FormField,
  Autosuggest,
  Input,
  Box
  
  } from "@cloudscape-design/components";

import {SimpleMap} from './map.js';

import {UserSignup} from './userSignup';
import {GenRestTab} from './generalRest'
function App() {
  
  
  return (
    <div className="App">

    <Tabs
      tabs={[
        {
          label: "User Signup",
          id: "uSign",
          content:  
          UserSignup()
        },
        {
          label: "Second tab label",
          id: "second",
          content: 
          SimpleMap(75.1652,39.9526)
          
        },
        {
          label: "General Restaurants",
          id: "third",
          content:
          GenRestTab()
          
        }
      ]}
    />
    
    </div>
  );
}



export default App;
