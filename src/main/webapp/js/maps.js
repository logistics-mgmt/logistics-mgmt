function pollForLocation(model, id, map, marker){
/**
    Args:
        model (driver/vehicle) - defines if driver or vehicle location will be polled
        id - driver or vehicle id
**/
    if (model == 'driver'){
        getDriver(id).done(function(result) {
            if (result.latitude == null || result.longitude == null){
                console.log("Null location.")
            }
            else {
                var location = new google.maps.LatLng(result.latitude, result.longitude);
                moveMarker(map, marker, location);
            }

        }).fail(function() {
            console.log("Failed to get Driver with id: "+id);
            return -1;
        });
    }
    else if (model == 'vehicle'){
        getVehicle(id).done(function(result) {
            if (result.latitude == null || result.longitude == null){
                console.log("Null location.")
            }
            else {
                var location = new google.maps.LatLng(result.latitude, result.longitude);
                moveMarker(map, marker, location);
            }

        }).fail(function() {
            console.log("Failed to get Vehicle with id: "+id);
            return -1;
        });
    }


};


function initializeMap(initialLocation){

    var location = initialLocation,
        options = {
            zoom: 4,
            center: location,
            mapTypeId: google.maps.MapTypeId.ROADMAP
            },
        map = new google.maps.Map(document.getElementById('map-canvas'), options);

    return map;
};

function initializeMarker(map, initialLocation){
    var marker = new google.maps.Marker({position: initialLocation, map: map});

    return marker;
};

function moveMarker(map, marker, location) {
    console.log(location);
    marker.setPosition(location);
    map.panTo(location);
};

function initializeMapWithDirections(initialLocation, startLocation, endLocation){
    var directionsDisplay = new google.maps.DirectionsRenderer();
    var directionsService = new google.maps.DirectionsService();
    var location = initialLocation,
        options = {
            zoom: 4,
            center: location,
            mapTypeId: google.maps.MapTypeId.ROADMAP
            },
        map = new google.maps.Map(document.getElementById('map-canvas'), options);

    directionsDisplay.setMap(map);

    calculateDirectionsRoute(startLocation, endLocation, directionsService, directionsDisplay);

    return map;
};

function calculateDirectionsRoute(startLocation, endLocation, directionsService, directionsDisplay) {
  var start = startLocation;
  var end = endLocation;
  var request = {
    origin:startLocation,
    destination:endLocation,
    travelMode: google.maps.TravelMode.DRIVING
  };
  directionsService.route(request, function(result, status) {
    if (status == google.maps.DirectionsStatus.OK) {
      directionsDisplay.setDirections(result);
    }
  });
};


