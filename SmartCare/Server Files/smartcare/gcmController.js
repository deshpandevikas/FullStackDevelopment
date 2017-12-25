
var orm = require("orm");
var express = require('express');
var bodyParser = require('body-parser');
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');
var gcm = require('node-gcm');




module.exports.controller = function(app){

  app.post('/addidforgcm',function(req,res){

	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 var token = req.body.token || req.query.token || req.headers['x-access-token'];

	 var decoded = jwt.decode(token, 'MyKey');
	 var name1 = decoded.uname;

	 var mydevid = req.body.mydevid;



	var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "smartcare"
  });

  if(mydevid!=null && name1!=null){



	  con.query("INSERT INTO gcmids VALUES (?, ?) ", [mydevid,name1],function (err, result, fields) {
      if (err){

		  if(err.errno==1062)
		  {
			  return res.json({ success: true, 'message':"Device already added"  });
		  }
		  else{

			return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });
		  }

      }



	return res.json({ success: true, 'message':"Device added for receiving notifications"   });

    });

}

});


app.post('/deleteidforgcm',function(req,res){

	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 var token = req.body.token || req.query.token || req.headers['x-access-token'];

	 var decoded = jwt.decode(token, 'MyKey');
	 var name1 = decoded.uname;

	 var mydevid = req.body.mydevid;

	var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "smartcare"
  });

  if(mydevid!=null && name1!=null){

	  con.query("delete from gcmids WHERE devid = ? and uemail = ? LIMIT 1", [mydevid,name1],function (err, result, fields) {
      if (err){

		 return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });

      }

	return res.json({ success: true, 'message':"Device removed from receiving notifications"   });

    });

}

});



app.post('/sendnotification',function(req,res){

	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	var sender = new gcm.Sender('AAAAb7Y6oBY:APA91bExTVJO-qqxU6Tw8VH5jYTZKx5om95Jy2rGG0VkVnK3JSCVvVn1MI3XJEueSUwUf2kqmCFsLZ4ZW8f66lq0re17sI15ISAJ-2i3V46bTFKZSvJIjsJxnydATTSCnEZ-zLf33q8g');


	/*var message = new gcm.Message({
    data: { key1: 'msg1' }
	});*/

	var message = new gcm.Message({
    collapseKey: 'demo',
    priority: 'high',
    contentAvailable: true,
    delayWhileIdle: true,
    timeToLive: 3,
    dryRun: false,
    data: {
        key1: 'message1',
        key2: 'message2'
    },
    notification: {
        title: "Got a new question.",
        icon: "ic_launcher",
        body: "A new question is posted in the messages, answer it with priority."
    }
	});

	var qid = req.body.qid;

	var registrationTokens = [];

		var con = mysql.createConnection({
		host: "localhost",
		user: "root",
		password: "root",
		database : "smartcare"
	});


	 con.query("SELECT * FROM gcmids g inner join questionsUsers u on g.uemail = u.uemail where u.qid = ?",[qid],function (err, result, fields) {
      if (err)
	  {
		  throw err;
	  }

	  if(result.length==0)
	  {
		return res.json({success: false, 'message': 'No users present to send notifications'});
	  }
	  else{

		  for(var i = 0; i < result.length; i++)
		  {
			  registrationTokens.push(result[i].devid);

		  }

		  	sender.send(message, { registrationTokens: registrationTokens }, 10, function (err, response) {
			if(err)
			{
				console.error(err);
			}
			else{

				return res.json({ success: true, userresponse:response  });
			}
			});
	  }

	});



});






}
