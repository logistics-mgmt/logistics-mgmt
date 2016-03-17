
function addFreightTransport(){

    var transport = getFormData($("#add_transport_form"));
    getAddress(transport.loadAddressId).done(function(loadAddresResult) {


    	transport.loadAddress=loadAddresResult;
        delete transport["loadAddressId"];
        console.log(JSON.stringify(transport));

    }).fail(function() {

        console.log("Failed to get Address with id:"+transport.loadAddressId);
        return -1;
        });
    getAddress(transport.unloadAddressId).done(function(unloadAddresResult) {


    	transport.unloadAddress=unloadAddresResult;
        delete transport["unloadAddressId"];
        console.log(JSON.stringify(transport));


    }).fail(function() {

        console.log("Failed to get Address with id:"+transport.unloadAddressId);
        return -1;
        });
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
            }
        })
    }).fail(function() {

        console.log("Failed to get Client with id:"+transport.clients);
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

    }).fail(function() {

        console.log("Failed to get Address with id:"+transport.loadAddressId);
        return -1;
        });
    getAddress(transport.unloadAddressId).done(function(unloadAddresResult) {


    	transport.unloadAddress=unloadAddresResult;
        delete transport["unloadAddressId"];
        console.log(JSON.stringify(transport));


    }).fail(function() {

        console.log("Failed to get Address with id:"+transport.unloadAddressId);
        return -1;
        });
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
            }
        })
    }).fail(function() {

        console.log("Failed to get Client with id:"+transport.clients);
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
}
