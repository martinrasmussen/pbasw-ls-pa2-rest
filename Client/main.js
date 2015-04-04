var country = null;
var map = null;

$(document).ready(function() {

	$.ajax({
        url: 'http://api.worldbank.org/country/dk?format=jsonP',
        dataType: 'jsonp',
        jsonp: "prefix",
        jsonpCallback: "getdata", // This value doesnt really matter
        success: function(data, status){
            console.log("Success");


            country = data[1][0];
            console.log(country);

            var mapOptions = {
              zoom: 6
            };
            map = new google.maps.Map(document.getElementById('map-canvas'),
                mapOptions);
        },
        error: function(xhr, status, error){
            console.log("Error");
            console.log(xhr.statusText);
            console.log(xhr.responseText);
            console.log(xhr.status);
            console.log(error);
        }
    })
});

$('#infoModal').on('show.bs.modal', function(event) {
	 var modal = $(this)
  modal.find('.modal-title').text('Displaying information about  ' + country.name);
  modal.find('#Country').html("<b>Country: </b><br>		" + country.name );
  modal.find('#Capital').html("<b>Capital: </b><br>		" + country.capitalCity );
  modal.find('#ISO2').html("<b>ISO2: </b><br>		" + country.iso2Code );
  modal.find('#Lat').html("<b>Lat: </b><br>		" + country.latitude );
  modal.find('#Lng').html("<b>Lng: </b><br>		" + country.longitude );
});

$('#mapModal').on('show.bs.modal', function(event) {
	 var modal = $(this)
  		modal.find('.modal-title').text('Displaying map for  ' + country.name);		


});

$('#mapModal').on('shown.bs.modal', function(event) {
	console.log("RESIZING")
	google.maps.event.trigger(map, "resize");/* Act on the event */
	map.setCenter(new google.maps.LatLng(parseInt(country.latitude), parseInt(country.longitude)));
});