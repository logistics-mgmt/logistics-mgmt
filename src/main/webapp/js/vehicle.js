
function addVehicle(){
    // send ajax
    var vehicle = getFormData($("#add_vehicle_form"));
    $.ajax({
        url: '/api/vehicles/',
        type : "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        dataType : 'json',
        data : JSON.stringify(vehicle),
        success : function(result) {
            console.log(result);
            window.location.href = '/vehicles';
        },
        error: function(xhr, resp, text) {
            console.log(xhr, resp, text);
        }
    })

    return false;
};


function editVehicle(){
    // send ajax
    var vehicle = getFormData($("#edit_vehicle_form"));
    $.ajax({
        url: '/api/vehicles/${vehicle.id}',
        type : "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        dataType : 'json',
        data : JSON.stringify(vehicle),
        success : function(result) {
            console.log(result);
            window.location.href = '/vehicles';
        },
        error: function(xhr, resp, text) {
            console.log(xhr, resp, text);
        }
     })
    return false;
};


function deleteVehicle(vehicleId){
    // send ajax
    $.ajax({
        url: '/api/vehicles/'+ vehicleId,
        type : "DELETE",
        success : function(result) {
            console.log(result);
            window.location.href = '/vehicles';
        },
        error: function(xhr, resp, text) {
            console.log(xhr, resp, text);
        }
    })

    return false;
};

function getVehicle(id){
    return $.ajax({
        url: '/api/vehicles/' + id,
        type : "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success : function(result) {
            console.log(result);
        },
        error: function(xhr, resp, text) {
            console.log(xhr, resp, text);
        }
    })
}