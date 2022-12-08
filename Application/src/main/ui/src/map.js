import React from "react";
import GoogleMapReact from 'google-map-react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

export function SimpleMap(lati, longi){

const Wrapper = styled.div`
position: absolute;
top: 50%;
left: 50%;
width: 18px;
height: 18px;
background-color: #000;
border: 2px solid #fff;
border-radius: 100%;
user-select: none;
transform: translate(-50%, -50%);
cursor: ${(props) => (props.onClick ? 'pointer' : 'default')};
&:hover {
  z-index: 1;
}
`;

const Marker = ({ text, onClick }) => (
<Wrapper
  alt={text}
  //onClick={onClick}
/>
);

//Marker.defaultProps = {
//onClick: console.log("Hello World"),
//};

Marker.propTypes = {
onClick: PropTypes.func,
text: PropTypes.string.isRequired,
};
  const defaultProps = {
    center: {
      lat: lati,
      lng: longi
    },
    zoom: 11,
  };

  return (
    // Important! Always set the container height explicitly
    <div style={{ height: '50vh', width: '50%' }}>
      <GoogleMapReact
          onClick={ev => {
            console.log("latitide = ", ev.lat);
            console.log("longitude = ", ev.lng);
          }}
        bootstrapURLKeys={{ key: "AIzaSyBJPrEYIojDH_794kMGjcDrTzihHa6HvwY" }}
        defaultCenter={defaultProps.center}
        defaultZoom={defaultProps.zoom}
        
      >
                <Marker
                key="Name"
                text="text"
                lat={lati}
                lng={longi}
              />
      </GoogleMapReact>
    </div>
  );
}