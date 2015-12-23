function getFormData($form){
    var unindexed_array = $form.serializeArray();
        var indexed_array = {};

        $.map(unindexed_array, function(n, i){
            indexed_array[n['name']] = n['value'];
            // Parse checkbox values
            if(n['value'] == "on" )
                indexed_array[n['name']] = "true";
            else if(n['value'] == "off" )
                indexed_array[n['name']] = "false";
        });

    return indexed_array;
}