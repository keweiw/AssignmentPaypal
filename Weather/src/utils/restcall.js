var querystring = require('querystring');
var http = require('http');
var logging = require("../utils/logging");
// Constructor an REST Service client 
function RestClient(state, city) {
    this.options = {
        host:"api.wunderground.com",
        path:"/api/4ba04736b3cbef41/conditions/q/" + state + "/" + city + ".json",
        method:"GET",
    };
    return this;
}

exports.RestClient = RestClient;

// Perform the call.
RestClient.prototype.performRequest = function (success, failed) {
  var self = this;  
  var req = http.request(self.options, function(res) {
    res.setEncoding('utf-8');

    var responseString = '';
    // set data call back
    res.on('data', function(data) {
      responseString += data;
    });

    // set end call back
    res.on('end', function() {
      var responseObject = JSON.parse(responseString);
      success(responseObject);
    });

    // set error call back
    res.on('error', function(error) {
      logging.debug(error);
      failed();
    });
  });

  req.write("");
  req.end();
}
