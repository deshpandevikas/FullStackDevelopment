
var orm = require("orm");
var mysql = require('mysql');



module.exports.controller = function(app){

  app.post('/displayquestionsperstudy',function(req,res){

      var sql1 = require('promise-mysql');
      var name12 = req.body.sid;
      var connection1;
      var t ={te:[]};

      var arr =[];

      sql1.createConnection({
          host: "localhost",
          user: "root",
          password: "root",
          database : "smartcare"
      }).then(function(conn){
          connection1 = conn;
          return connection1.query('select * from questions qu  where qu.sid="' + name12 + '"');
      }).then(function(rows4){
          return res.json({success: true, 'message': rows4});
      }).catch(function(error){
          if(connection1 && connection1.end) connection1.end();
          console.log("Errorr iss  " + error);
      });
  });


}
