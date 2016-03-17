
function addClient(){

    var client = getFormData($("#add_client_form"));
    getAddress(client.addressId).done(function(result) {


        client.address=result;
        delete client["addressId"];
        console.log(JSON.stringify(client));
        if (client.nip == "") {client.nip = null;}
        $.ajax({
            url: '/api/clients/',
            type : "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType : 'json',
            data : JSON.stringify(client),
            success : function(result) {
                console.log(result);
                window.location.href = '/clients';
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        })
    }).fail(function() {
        console.log("Failed to get Address with id:"+client.addressId);
        return -1;
        });

    return false;
};


function editClient(){

    var client = getFormData($("#edit_client_form"));
    getAddress(client.addressId).done(function(result) {

        client.address=result;
        delete client["addressId"];
        console.log(JSON.stringify(client));
        if (client.nip == "") {client.nip = null;}
        $.ajax({
            url: '/api/clients/${client.id}',
            type : "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType : 'json',
            data : JSON.stringify(client),
            success : function(result) {
                console.log(result);
                window.location.href = '/clients';
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        })
    }).fail(function() {

            console.log("Failed to get Address with id:"+client.addressId);
            return -1;
        });

    return false;
};


function deleteClient(clientId){

    $.ajax({
        url: '/api/clients/'+ clientId,
        type : "DELETE",
        success : function(result) {
            console.log(result);
            window.location.href = '/clients';
        },
        error: function(xhr, resp, text) {
            console.log(xhr, resp, text);
        }
    })

    return false;
};

function getClient(id){
    return $.ajax({
        url: '/api/clients/' + id,
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