
var transportPlan;

function addFreightTransport(){

    if(transportPlan !== undefined){
        $.ajax({
            url: '/api/transports/',
            type : "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType : 'json',
            data : JSON.stringify(transportPlan),
            success : function(clientResult) {
                console.log(clientResult);
                window.location.href = '/transports';
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
                alert("Dodawanie transportu nie powiodło się.");
            }
        })

        return false;
    }

    var transport = getFormData($("#add_transport_form"));
    getAddress(transport.loadAddressId).done(function(loadAddresResult) {


    	transport.loadAddress=loadAddresResult;
        delete transport["loadAddressId"];
        console.log(JSON.stringify(transport));

        getAddress(transport.unloadAddressId).done(function(unloadAddresResult) {


        transport.unloadAddress=unloadAddresResult;
        delete transport["unloadAddressId"];
        console.log(JSON.stringify(transport));

        getClient(transport.clientId).done(function(clientResult) {


        transport.client=clientResult;
        delete transport["clientId"];
        console.log(JSON.stringify(transport));

        $.ajax({
            url: '/api/transports/',
            type : "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType : 'json',
            data : JSON.stringify(transport),
            success : function(clientResult) {
                console.log(clientResult);
                window.location.href = '/transports';
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
                alert("Dodawanie transportu nie powiodło się.");
            }
        })
    }).fail(function() {

        console.log("Failed to get Client with id:"+transport.clients);
        return -1;
        });


    }).fail(function() {

        console.log("Failed to get Address with id:"+transport.unloadAddressId);
        return -1;
        });
    }).fail(function() {

        console.log("Failed to get Address with id:"+transport.loadAddressId);
        return -1;
        });
    
   
    return false;
};

function editFreightTransport(){

    var transport = getFormData($("#edit_transport_form"));
    getAddress(transport.loadAddressId).done(function(loadAddresResult) {


    	transport.loadAddress=loadAddresResult;
        delete transport["loadAddressId"];
        console.log(JSON.stringify(transport));

        getAddress(transport.unloadAddressId).done(function(unloadAddresResult) {


        transport.unloadAddress=unloadAddresResult;
        delete transport["unloadAddressId"];
        console.log(JSON.stringify(transport));

        getClient(transport.clientId).done(function(clientResult) {


        transport.client=clientResult;
        delete transport["clientId"];
        console.log(JSON.stringify(transport));

        $.ajax({
            url: '/api/transports/${transport.id}',
            type : "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType : 'json',
            data : JSON.stringify(transport),
            success : function(clientResult) {
                console.log(clientResult);
                window.location.href = '/transports';
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
                alert("Edycja transportu nie powiodło się.");
            }
        })
    }).fail(function() {

        console.log("Failed to get Client with id:"+transport.clients);
        return -1;
        });


    }).fail(function() {

        console.log("Failed to get Address with id:"+transport.unloadAddressId);
        return -1;
        });
    }).fail(function() {

        console.log("Failed to get Address with id:"+transport.loadAddressId);
        return -1;
        });
    
   
    return false;
};
function deleteFreightTransport(transportId){
    $.ajax({
        url: '/api/transports/'+ transportId,
        type : "DELETE",
        success : function(result) {
            console.log(result);
            window.location.href = '/transports';
        },
        error: function(xhr, resp, text) {
            console.log(xhr, resp, text);
            alert("Usuwanie transportu nie powiodło się.");
        }
    })

    return false;
};
function getFreightTransport(id){
    return $.ajax({
        url: '/api/transports/' + id,
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
};


// ASAP spaghetti code
function planTransport(){
    var transport = getFormData($("#add_transport_form"));
    console.log("Transport: " + transport);
    getAddress(transport.loadAddressId).done(function(loadAddresResult) {


        transport.loadAddress=loadAddresResult;
        delete transport["loadAddressId"];

        getAddress(transport.unloadAddressId).done(function(unloadAddresResult) {


        transport.unloadAddress=unloadAddresResult;
        delete transport["unloadAddressId"];

    getClient(transport.clientId).done(function(clientResult) {


        transport.client=clientResult;
        delete transport["clientId"];
        var transportJson=JSON.stringify(transport);
        console.log(transportJson);
        //return transportJson;

        // Magnificent haxs 
        planTransportCall(transportJson).done( function(plannedTransport) {

        var vehiclesList = $('#vehicles_list');
        vehiclesList.empty();
        vehiclesList.append('<div class="list-group-item"><h4 class="list-group-item-heading">Pojazdy:</h4></div>');
        for(var key in plannedTransport.vehicles){
            var vehicle = plannedTransport.vehicles[key];
            vehicleListItem = '<a href="/vehicles/' + vehicle.id +'" class="list-group-item">' +
                            '<h4 class="list-group-item-text">' + vehicle.brand + ' ' + vehicle.model + ' ' + vehicle.vin +'</h4></a>';
            vehiclesList.append(vehicleListItem);
        }

        var driversList = $('#drivers_list');
        driversList.empty();
        driversList.append('<div class="list-group-item"><h4 class="list-group-item-heading">Kierowcy:</h4></div>');
        for(var key in plannedTransport.drivers){
            var driver = plannedTransport.drivers[key];
            driverListItem = '<a href="/drivers/' + driver.id +'" class="list-group-item">' +
                            '<h4 class="list-group-item-text">' + driver.firstName + ' ' + driver.lastName + ' ' + driver.pesel +'</h4></a>';
            driversList.append(driverListItem);
        }

        // save transportPlan object
        transportPlan = plannedTransport;

        }).fail(function() {

        console.log("Failed to plan transport.");
        alert("Planowanie transportu nie powiodło się.");
        return -1;
        });

    }).fail(function() {

        console.log("Failed to get Client with id:"+transport.clients);
        return null;
        });

    }).fail(function() {

        console.log("Failed to get Address with id:"+transport.loadAddressId);
        return -1;
        });
    }).fail(function() {

        console.log("Failed to get Address with id:"+transport.unloadAddressId);
        return -1;
        });
};

function planTransportCall(transportJson){
        return $.ajax({
            url: '/api/transports/plan',
            type : "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType : 'json',
            data : transportJson,
            success : function(planningResult) {
                console.log(planningResult);
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        });
};
