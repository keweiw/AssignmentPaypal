var cluster = require('cluster');
var logging = require("./utils/logging");
var url = require('url');

// enable cluster or not
USE_CLUSTERS = false;

var cpuCount = require('os').cpus().length;

// main app
if (USE_CLUSTERS && cluster.isMaster)
{
    // if cluster enabled
    for (var i = 0; i < cpuCount; i++) {
        cluster.fork();
    }
} 
else
{
    var express = require('express'),
    http = require('http'),
    routes = require('./routes'),
    weather = require('./routes/weather'),
    hbs = require('express3-handlebars'),
    path = require('path');
    
    var app = express();
    // set up apps
    function initExpress() {
        app.set('port', process.env.PORT || 3000);
        app.set('views', __dirname + '/views');
        app.engine('.hbs', hbs({extname: ".hbs"}));
        app.set('view engine', '.hbs');
	app.use(express.favicon());
        app.use(express.bodyParser());
        app.use(express.methodOverride());
        // following middleware if for logging all parameters
        app.use(function(req, res, next){
            logging.debug(url.parse(req.url, true).query);
            next();
        });
        app.use(app.router);
        app.use(express.json());
        app.use(express.urlencoded);
        app.use("/static", express.static(path.join(__dirname, 'static')));
    }

    app.configure(initExpress);
    app.configure('development', function(){
        app.use(express.errorHandler());
    });
    // router for url, 
    app.get('/', routes.index);
    app.get('/weather', weather.list);
    http.createServer(app).listen(app.get('port'), function(){
        logging.debug("Express server listen on port" + app.get('port'));
    });
}
