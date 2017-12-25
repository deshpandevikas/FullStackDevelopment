var express = require('express');
var path = require('path');
var app = express();
var orm = require("orm");
var bodyParser = require('body-parser');
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');

console.log(__dirname);

app.use(bodyParser.urlencoded({'extended':'true'}));            // parse application/x-www-form-urlencoded
app.use(bodyParser.json());                                     // parse application/json
app.use(bodyParser.json({ type: 'application/vnd.api+json' })); // parse application/vnd.api+json as json


var adm = require('./adminController');
adm.controller(app);


var users = require('./usersController');
users.controller(app);


var scors = require('./studycoordinatorController');
scors.controller(app);









app.listen(8080);
