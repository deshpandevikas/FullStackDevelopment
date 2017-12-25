
var orm = require("orm");
var express = require('express');
var bodyParser = require('body-parser');
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');
var gcm = require('node-gcm');




module.exports.controller = function(app){

  app.post('/getQuestionsForMobile',function(req,res){

	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 var token = req.body.token || req.query.token || req.headers['x-access-token'];



	 var decoded = jwt.decode(token, 'MyKey');
	 var name2 = decoded.uname;
	 
	 var sid = req.body.sid;

		


		var request = require('request');

		// Set the headers
		var headers = {
			'User-Agent':       'Super Agent/0.0.1',
			'Content-Type':     'application/x-www-form-urlencoded'
		}

		// Configure the request
		var options = {
			url: 'http://18.216.218.221/finalPHP/getQuestions.php',
			method: 'POST',
			 headers: headers,
			form: {'name1': name2, 'sid': sid}
		}

		// Start the request
		request(options, function (error, response, body) {
			if (!error && response.statusCode == 200) {
				 //return res.send(body);
				 return res.json(JSON.parse(body));
			}
		});


});

app.post('/submitresponse',function(req,res){
	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 var token = req.body.token || req.query.token || req.headers['x-access-token'];

	 var decoded = jwt.decode(token, 'MyKey');
	 var name1 = decoded.uname;



	var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "smartcare"
  });

  if(name1!=null){

	  var qid = req.body.qid;
	  var timeid = req.body.timeid;
	  var optionid = req.body.optionid;

	  var params11 = [qid,timeid,name1,optionid];

	  con.query("INSERT INTO questionsResponse VALUES (?, ?, ?, ?)", params11,function (err, result, fields) {
      if (err){

		  if(err.errno==1062)
		  {
			  return res.json({ success: true, 'message':"Previously answered this question"  });
		  }
		  else{

			return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });
		  }

      }



	return res.json({ success: true, 'message':"Answer Submitted Successfully"   });

    });

  }


});


app.post('/getuserprofile',function(req,res){

	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 var token = req.body.token || req.query.token || req.headers['x-access-token'];

	 var decoded = jwt.decode(token, 'MyKey');
	 var name1 = decoded.uname;



	var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "smartcare"
  });

  if(name1!=null){



	  con.query("Select * from USERS where uemail = ? ", [name1],function (err, result, fields) {
      if (err){

		  return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });


      }

	  console.log(result);

	return res.json({ success: true, 'fname':result[0].fname, 'lname':result[0].lname, 'uemail':result[0].uemail,'age':result[0].age  });

    });

}

});


app.post('/getStudiesForMobile',function(req,res){

	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 var token = req.body.token || req.query.token || req.headers['x-access-token'];

	 var decoded = jwt.decode(token, 'MyKey');
	 var name1 = decoded.uname;



	var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "smartcare"
  });

  if(name1!=null){

	  con.query("select s.sid, s.sdescription, s.sname, s.sname, su.uemail FROM StudiesUsers as su INNER JOIN Studies as s on su.sid = s.sid WHERE su.uemail = ? ", [name1],function (err, result, fields) {
      if (err){

		  return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });

      }
	
	  console.log(result);

	return res.json({ success: true, 'Studies':result  });

    });

}

});






app.post('/addSurveyAnswers',function(req,res){


	var urlencodedParser = bodyParser.urlencoded({ extended: false })
	

	var surveyResponse = JSON.parse(req.body.surveyResponse);
	
	console.log(surveyResponse);
	
	var token = surveyResponse.token;
	
	
	console.log(token);
	
	var surveyid = surveyResponse.surveyid;
	
	var resultArray = surveyResponse.resultArray;
	
	 var decoded = jwt.decode(token, 'MyKey');
	 var name1 = decoded.uname;
	 
	 	var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "smartcare"
  });

  if(name1!=null){
	  
	  for (var i=0; i<resultArray.length; i++){
	  
			var qid = resultArray[i].qid;
			var questionType = resultArray[i].questionType;
			
			if(questionType == 'mcq')
			{
				var optionid = resultArray[i].choiceId;
				
				var params11 = [qid,surveyid,name1,optionid];
		
				con.query("INSERT INTO surveyquestionsResponse (qid,surveyid,uemail,optionid) VALUES (?, ?, ?, ?)", params11,function (err, result, fields) 
				{
					if (err){

						return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });

					}


				});
			}
			else if(questionType == 'text')
			{
				var responseText = resultArray[i].responseText;
				
				var params11 = [qid,surveyid,responseText,name1];
		
				con.query("INSERT INTO surveyquestionsResponse (qid,surveyid,responseText,uemail) VALUES (?, ?, ?, ?)", params11,function (err, result, fields) 
				{
					if (err){

						return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });

					}


				});
			}
	  
	  }
	  
	  return res.json({ success: true, 'message':"Surver response result submitted successfully"  });

	}

});


app.post('/getSurveyQuestions',function(req,res){

	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 var token = req.body.token || req.query.token || req.headers['x-access-token'];



	 var decoded = jwt.decode(token, 'MyKey');
	 var name2 = decoded.uname;
	 
	 var surveyid = req.body.surveyid;

		


		var request = require('request');

		// Set the headers
		var headers = {
			'User-Agent':       'Super Agent/0.0.1',
			'Content-Type':     'application/x-www-form-urlencoded'
		}

		// Configure the request
		var options = {
			url: 'http://18.216.218.221/finalPHP/getSurveyQuestions.php',
			method: 'POST',
			 headers: headers,
			form: {'surveyid': surveyid}
		}

		// Start the request
		request(options, function (error, response, body) {
			if (!error && response.statusCode == 200) {
				 //return res.send(body);
				 
				 return res.json(JSON.parse(body));
			}
		});


});




}
