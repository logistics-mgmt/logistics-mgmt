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