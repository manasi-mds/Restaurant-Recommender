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

import {UserSignup} from './TabContents/userSignup';
import { UserPreferences } from './TabContents/userUpdate';
import {GenRestTab} from './TabContents/generalRest';
import {GetFollowersTab} from './TabContents/userQuery';
import {LikedRestTab} from './TabContents/likedRest';
import {PFollowingTab} from './TabContents/potentialFriend';

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
          label: "User Preferences",
          id: "uUpdate",
          content:  
          UserPreferences()
        },
        {
          label: "maptest",
          id: "second",
          content: 
          SimpleMap(39.97465381516658,-75.20428157754901 )
          
        },
        {
          label: "General Restaurants",
          id: "genRest",
          content:
          GenRestTab()
          
        },
        {
          label: "Liked Restaurants",
          id: "likeRest",
          content:
          LikedRestTab()  
        },
        {
          label: "Following",
          id: "following",
          content:
          GetFollowersTab()
          
        },
        {
          label: "Find People to Follow",
          id: "follower",
          content:
          PFollowingTab()
          
        }
      ]}
    />
    
    </div>
  );
}



export default App;
