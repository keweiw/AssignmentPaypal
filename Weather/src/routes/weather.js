var restclient = require("../utils/restcall");
var logging = require("../utils/logging");

// route function for list the weather page
exports.list = function(req, res){

    var citiesData = [];
   
    var cities = ["Campbell", "Austin", "Timonium"];
    var states = ["CA", "TX", "MD"];
    var counter = 0;
    for (var i = 0; i < cities.length; i++) {
       var weatherService = new restclient.RestClient(states[i], cities[i]);
    
       weatherService.performRequest(function(data){
           citiesData[counter] = {
              name: data.current_observation.display_location.full,
              weather: data.current_observation.weather + " " + data.current_observation.temperature_string,
              link: data.current_observation.icon_url,
              updated: true
           };
           counter++;
           if (counter == cities.length) {
                res.render('weather', {
                    title: 'Weather of Cities',
                    session: req.session,
                    cities: citiesData
                });
	  }
	}, function(){
           // when error happens simplely do nothing, I should do more by setting default value for the citys has failing calls.
           // did not complete the logic for error case. 
           counter++;
           if (counter == cities.length) {
                res.render('weather', {
                    title: 'Weather of Cities',
                    session: req.session,
                    cities: citiesData
                });
	});
    }

};

