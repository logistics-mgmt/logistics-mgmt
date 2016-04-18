function getAddress(id){
    return $.ajax({
        url: '/api/addresses/' + id,
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
function addAddress(){
    // send ajax
    var address = getFormData($("#add_address_form"));

        console.log(JSON.stringify(address));

        $.ajax({
            url: '/api/addresses/',
            type : "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType : 'json',
            data : JSON.stringify(address),
            success : function(result) {
                console.log(result);
                window.location.href = '/addresses';
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        })
    

    return false;
};
function editAddress(){
    // send ajax
    var address = getFormData($("#edit_address_form"));

    console.log(JSON.stringify(address));

    $.ajax({
        url: '/api/addresses/${address.id}',
        type : "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        dataType : 'json',
        data : JSON.stringify(address),
        success : function(result) {
            console.log(result);
            window.location.href = '/addresses';
        },
        error: function(xhr, resp, text) {
            console.log(xhr, resp, text);
        }
    })


return false;
};

function deleteAddress(addressId){
    // send ajax
    $.ajax({
        url: '/api/addresses/'+ addressId,
        type : "DELETE",
        success : function(result) {
            console.log(result);
            window.location.href = '/addresses';
        },
        error: function(xhr, resp, text) {
            console.log(xhr, resp, text);
        }
    })

    return false;
};