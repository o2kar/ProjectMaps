/* Author: Christian Schröter
 * Ermittelt die aktuelle Position des Users
 */

navigator.geolocation.getCurrentPosition(function(position) {
	document.getElementById('geolocation').innerHTML = 'Latitude: ' + position.coords.latitude + ' / Longitude: ' + position.coords.longitude;
}, function() {
	document.getElementById('geolocation').innerHTML = 'Deine Position konnte leider nicht ermittelt werden';
});