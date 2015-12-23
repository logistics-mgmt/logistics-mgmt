
function addDriver(){
    // send ajax
    var driver = getFormData($("#add_driver_form"));
    getAddress(driver.addressId).done(function(result) {

        // Build final Driver JSON after receiving Address response
        driver.address=result;
        delete driver["addressId"];
        console.log(JSON.stringify(driver));

        $.ajax({
            url: '/api/drivers/',
            type : "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType : 'json',
            data : JSON.stringify(driver),
            success : function(result) {
                console.log(result);
                window.location.href = '/drivers';
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        })
    }).fail(function() {
        // TODO: Handle error of getting Address response
        console.log("Failed to get Address with id:"+driver.addressId);
        return -1;
        });

    return false;
};


function editDriver(){
    // send ajax
    var driver = getFormData($("#edit_driver_form"));
    getAddress(driver.addressId).done(function(result) {
        // Build final Driver JSON after receiving Address response
        driver.address=result;
        delete driver["addressId"];
        console.log(JSON.stringify(driver));
        $.ajax({
            url: '/api/drivers/${driver.id}',
            type : "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType : 'json',
            data : JSON.stringify(driver),
            success : function(result) {
                console.log(result);
                window.location.href = '/drivers';
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        })
    }).fail(function() {
            // TODO: Handle error of getting Address response
            console.log("Failed to get Address with id:"+driver.addressId);
            return -1;
        });

    return false;
};


function deleteDriver(driverId){
    // send ajax
    $.ajax({
        url: '/api/drivers/'+ driverId,
        type : "DELETE",
        success : function(result) {
            console.log(result);
            window.location.href = '/drivers';
        },
        error: function(xhr, resp, text) {
            console.log(xhr, resp, text);
        }
    })

    return false;
};