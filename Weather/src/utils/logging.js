// Logging method for all modules
var _ = require("underscore");

exports.debug = function()
{
    var args = arguments;
    var keys = _.map(Object.keys(arguments), function (key) { return Number(key); });
    var values = _.map(keys, function(key) { return args["" + key]; });
    console.log.apply(null, values);
}
