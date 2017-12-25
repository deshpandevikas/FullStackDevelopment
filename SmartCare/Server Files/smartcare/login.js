var express = require('express');
var path = require('path');
var app = express();
var orm = require("orm");
var bodyParser = require('body-parser');
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');
var cookieParser = require('cookie-parser');
var session = require('express-session');

console.log(__dirname);

app.use(bodyParser.urlencoded({'extended':'true'}));            // parse application/x-www-form-urlencoded
app.use(bodyParser.json());                                     // parse application/json
app.use(bodyParser.json({ type: 'application/vnd.api+json' })); // parse application/vnd.api+json as json
app.use(cookieParser());
app.use(session({secret: "Shh, its a secret!",resave:true,saveUninitialized:true}));


var scor = require('./studyCoordinatorController');
scor.controller(app);

var study= require('./studiesController');
study.controller(app);


var users = require('./usersController');
users.controller(app);

var responses = require('./responsesController');
responses.controller(app);

var survey = require('./surveyController');
survey.controller(app);

var jobs = require('./Schedulejobs');
jobs.controller(app);

var resetjobs = require('./resetScheduler');
resetjobs.controller(app);


var mcqquestion = require('./createmcqquestion');
mcqquestion.controller(app);



var infquestion = require('./createinformationquestion');
infquestion.controller(app);


var infquestion = require('./createinformationquestion');
infquestion.controller(app);

var survey1 = require('./survey');
survey1.controller(app);





var gcmController  = require('./gcmController');
gcmController.controller(app);


var mbController  = require('./mobilequestionsController');
mbController.controller(app);


var usersStudies  = require('./studiesUsersController');
usersStudies.controller(app);





app.listen(1337);
