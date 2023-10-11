import React, { useEffect, useState } from 'react'
import { GoogleMap, useJsApiLoader } from '@react-google-maps/api';

const containerStyle = {
  width: '400px',
  height: '400px'
};
// const center = {
//   lat: 10.8166161,
//   lng: 106.675992
// };

function GoogleMapAPI(props) {

  const [center, setCenter] = useState({
    lat: 10, lng: 106
  });
  const { location } = props;

  const { isLoaded } = useJsApiLoader({
    id: 'google-map-script',
    googleMapsApiKey: "AIzaSyB-M500zF9hEI3OoOPyK_dVHfWDyZcx5fI"
  })
  console.log(location)

  const [map, setMap] = React.useState(null)
  const [zoom, setZoom] = useState(16);

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
    var latlng = new window.google.maps.LatLng(10, 106);
    var mapOptions = {
      // zoom: 16,
      center: latlng
    }
    // map1 = new window.google.maps.Map(document.getElementById('map'), mapOptions);
    console.log(mapOptions)
  }

  function codeAddress() {
    // var address = document.getElementById('address').value;
    geocoder.geocode({ 'address': location }, function (results, status) {

      // console.log(results[0].geometry.location.lng);
      if (status == 'OK') {
        var locationData = results[0].geometry.location;
        var lat = locationData.lat();
        var lng = locationData.lng();

        var marker = new window.google.maps.Marker({
          map: map,
          position: results[0].geometry.location
        });
      } else {
        alert('Geocode was not successful for the following reason: ' + status);
      }
    });
  }

  function locate() { // lấy vị trí người dùng
    // const posStatus = document.querySelector('#posStatus');
    // const locInfo = document.querySelector('#locInfo');
    // posStatus.innerHTML = 'Đang lấy vị trí...'
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        const lat = position.coords.latitude;
        const long = position.coords.longitude;
        // posStatus.innerHTML = 'Vị trí của bạn là: ';      // Display Latitude and Logitude
        let location = `Latitude: ${lat}, Longitude: ${long}`;      // Create the link. Use map=15-19 for zooming out and in
        // Pass lat and long to openstreetmap
        console.log(location)
          `https://www.openstreetmap.org/#map=19/${lat}/${long}`;
      });
    }
  }

  useEffect(() => {
    if (isLoaded) {
      initialize();
      codeAddress()
    }
    // locate();

  })

  return isLoaded ? (
    <GoogleMap
      mapContainerStyle={containerStyle}
      center={center}
      zoom={zoom}
      onLoad={onLoad}
      onUnmount={onUnmount}
    >
      { /* Child components, such as markers, info windows, etc. */}
      <></>
    </GoogleMap>
  ) : <></>
}

export default GoogleMapAPI;