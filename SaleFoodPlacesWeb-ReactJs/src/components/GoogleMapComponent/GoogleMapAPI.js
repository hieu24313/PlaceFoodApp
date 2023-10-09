import React, { useEffect } from 'react'
import { GoogleMap, useJsApiLoader } from '@react-google-maps/api';

const containerStyle = {
  width: '400px',
  height: '400px'
};

const center = {
  lat: 10.8166161,
  lng: 106.675992
};

function GoogleMapAPI() {
  const { isLoaded } = useJsApiLoader({
    id: 'google-map-script',
    googleMapsApiKey: "AIzaSyB-M500zF9hEI3OoOPyK_dVHfWDyZcx5fI"
  })

  const [map, setMap] = React.useState(null)

  const onLoad = React.useCallback(function callback(map) {
    // This is just an example of getting and using the map instance!!! don't just blindly copy!
    const bounds = new window.google.maps.LatLngBounds(center);
    map.fitBounds(bounds);

    setMap(map)
  }, [])

  const onUnmount = React.useCallback(function callback(map) {
    setMap(null)
  }, [])

  var geocoder;
  var map1;

  function initialize() {
    geocoder = new window.google.maps.Geocoder();
    var latlng = new window.google.maps.LatLng(-34.397, 150.644);
    var mapOptions = {
      zoom: 8,
      center: latlng
    }
    // map1 = new window.google.maps.Map(document.getElementById('map'), mapOptions);
    console.log( mapOptions)
  }

  function codeAddress() {
    // var address = document.getElementById('address').value;
    geocoder.geocode( { 'address': "371 nguyễn kiệm"}, function(results, status) {
      console.log("kết quả")
      console.log(results);
      if (status == 'OK') {
        // map.setCenter(results[0].geometry.location);
        var marker = new window.google.maps.Marker({
            map: map,
            position: results[0].geometry.location
        });
      } else {
        alert('Geocode was not successful for the following reason: ' + status);
      }
    });
  }

  useEffect(()=> {
    initialize();
    codeAddress()
  })

  return isLoaded ? (
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={center}
        zoom={15}
        onLoad={onLoad}
        onUnmount={onUnmount}
      >
        { /* Child components, such as markers, info windows, etc. */ }
        <></>
      </GoogleMap>
  ) : <></>
}

export default GoogleMapAPI;