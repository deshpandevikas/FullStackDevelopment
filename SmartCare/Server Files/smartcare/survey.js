
var orm = require("orm");
var express = require('express');
var bodyParser = require('body-parser');
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');




module.exports.controller = function(app){


app.post('/getSurveys',function(req,res){
	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 
	 var sid = req.body.sid;



	var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "smartcare"
  });

  if(sid!=null){

		con.query("SELECT * FROM Survey as s WHERE s.sid = ? ", [sid],function (err, result, fields) {
			  if (err){

				  return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });

			  }
			
			  console.log(result);

			return res.json({ success: true, 'Surveys':result  });

			});

  }


});


app.post('/getEachSurveyUsers',function(req,res){
	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 
	 var surveyid = req.body.surveyid;
	 var sid = req.body.sid;


	var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "smartcare"
  });

  if(sid!=null){

		con.query("SELECT * FROM StudiesUsers as s WHERE s.sid = ? ", [sid],function (err, result, fields) {
			  if (err){

				  return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });

			  }
			
			  console.log(result);

			return res.json({ success: true, 'SurveyUsers':result  });

			});

  }


});


app.post('/getEachSurveyResult',function(req,res){
	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 
	 var surveyid = req.body.surveyid;
	 var sid = req.body.sid;
	 var uemail = req.body.uemail;


	var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "smartcare"
  });

  if(sid!=null){

		con.query("SELECT sqr.surveyid, sqr.qid, sq.questionname, sqr.optionid, sqo.optionname, sqr.responseText FROM surveyquestions as sq INNER JOIN surveyquestionsResponse as sqr on sq.qid = sqr.qid LEFT JOIN surveyquestionsOptions as sqo on sqr.surveyid = sqo.surveyid and sqr.qid = sqo.qid and sqr.optionid = sqo.optionid WHERE sqr.uemail = ? and sqr.surveyid = ?", [uemail,surveyid],function (err, result, fields) {
			  if (err){

				  return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });

			  }
			
			  console.log(result);

			return res.json({ success: true, 'SurveyUserResponse':result  });

			});

  }


});



app.post('/addSurveyQuestions',function(req,res){


	 var bodyFrom = req.body.addQuestion;
	 
	 
	 var sid = bodyFrom.sid;
	 var SurveyQuestions = bodyFrom.SurveyQuestions;
	 
	 var surveyname = bodyFrom.surveyname;

	
		var request = require('request');

		// Set the headers
		var headers = {
			'User-Agent':       'Super Agent/0.0.1',
			'Content-Type':     'application/x-www-form-urlencoded'
		}

		// Configure the request
		var options = {
			url: 'http://18.216.218.221/finalPHP/addSurveyQuestions.php',
			method: 'POST',
			 headers: headers,
			form: {'sid': sid,
			'surveyname': surveyname,
			'SurveyQuestions': SurveyQuestions,
			}
		}

		
		// Start the request
		request(options, function (error, response, body) {
			if (!error && response.statusCode == 200) {
				 //return res.send(body);
				 
				 console.log(body);
				 //return res.send(body);
				 return res.json(JSON.parse(body));
			}
		});


});



app.post('/getQuestionsForResponse',function(req,res){
	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 

	 var sid = req.body.sid;
	 


	var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "smartcare"
  });

  if(sid!=null){

		con.query("SELECT DISTINCT q.qid,  q.question from questions as q INNER JOIN questionsOptions as qo on q.qid=qo.qid where q.sid = ? and qo.optionid != -1; ", [sid],function (err, result, fields) {
			  if (err){

				  return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });

			  }
			
			  console.log(result);

			return res.json({ success: true, 'StudyQuestions':result  });

			});

  }


});


app.post('/getQuestionResponseForAll',function(req,res){
	var urlencodedParser = bodyParser.urlencoded({ extended: false })

	 

	 var qid = req.body.qid;
	 


	var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "smartcare"
  });

  if(qid!=null){

		con.query("SELECT  qr.uemail, qr.qid, q.question, qr.optionid, qo.optionname FROM questions as q INNER JOIN questionsResponse as qr on q.qid = qr.qid LEFT JOIN questionsOptions as qo on qr.qid = qo.qid and qr.optionid = qo.optionid WHERE  q.qid =  ?", [qid],function (err, result, fields) {
			  if (err){

				  return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });

			  }
			
			  console.log(result);

			return res.json({ success: true, 'StudyQuestions':result  });

			});

  }


});

}
