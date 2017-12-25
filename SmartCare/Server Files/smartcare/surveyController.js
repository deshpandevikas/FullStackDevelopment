var orm = require("orm");
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');


module.exports.controller = function(app){




  app.post('/setsurvey',function(req,res){



    //if (!req.body) return res.sendStatus(400)
    //res.send('login');
    console.log(req.body);

    console.log(req.body.general);

    console.log(req.body.fname);


      var bdy = req.body;


      var token = req.body.token || req.query.token || req.headers['x-access-token'];
      var decoded = jwt.decode(token, 'MyKey');

        var email = decoded.uname;


    //var par = JSON.parse(req.body)

  console.log(typeof(req.body));
    //console.log(par);


  orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
    if (err) throw err;

    var general = db.define("GeneralQuestions", {
        uemail      : {type:'text', unique:true, key:true},
        q1   : Number,
        q2   : Number

    });

    var smoking = db.define("smoking", {
        uemail      : {type:'text', unique:true, key:true},
        q21   : Number,
        q22   : Number

    });


      var drinking = db.define("drinking", {
          uemail      : {type:'text', unique:true, key:true},
          q33   : Number,
          q34   : Number,
          q35   : Number


      });


        var weight = db.define("weight", {
          uemail      : {type:'text', unique:true, key:true},
          q23   : Number,
          q24   : Number,
          q25   : Number,
  	q26   : Number,
          q27   : Number,
          q28   : Number,
  	q29   : Number,
          q30   : Number,
          q31   : Number,
  	q32  : Number


      });

  	 var physical = db.define("Physical", {
          uemail      : {type:'text', unique:true, key:true},
          q17   : Number,
          q18   : Number,
          q19   : Number,
  	q20   : Number


      });


  	 var medication = db.define("MedicationUsage", {
          uemail      : {type:'text', unique:true, key:true},
          q3   : Number,
          q4   : Number,
          q5   : Number

      });


  	var diet = db.define("Diet", {
          uemail      : {type:'text', unique:true, key:true},
          q6   : Number,
          q7   : Number,
          q8   : Number,
  	      q9   : Number,
          q10   : Number,
          q11   : Number,
  	      q12   : Number,
          q13   : Number,
          q14   : Number,
  	     q15   : Number,
       	q16   : Number


      });



      var HScale = db.define("HScale", {
            uemail      : {type:'text', unique:true, key:true},
            medication   : String,
            diet  : String,
            physical   : String,
    	      weight   : String,
            alcohol   : String,
            smoking   : String

        });





      //console.log(Admin);

      db.sync(function(err) {
          if (err) throw err;

                  general.create({ uemail: email, q1:bdy.general.q1,
                  q2: bdy.general.q2}, function (err) {

                  	if (err) throw err;


                      //  return res.json({ success: true, 'message':"user created"  });


                  });


                  smoking.create({ uemail: email, q21:bdy.smoking.q21,
                  q22: bdy.smoking.q22}, function (err) {

                  	if (err) throw err;


                      //  return res.json({ success: true, 'message':"user created"  });


                  });


                  drinking.create({ uemail: email, q33:bdy.alcohol.q33,
                  q34: bdy.alcohol.q34,   q35: bdy.alcohol.q35 }, function (err) {

                    if (err) throw err;


                      //  return res.json({ success: true, 'message':"user created"  });


                  });




                  weight.create({ uemail: email, q23:bdy.weight.q23,
                  q24: bdy.weight.q24,   q25: bdy.weight.q25, q26:bdy.weight.q26,
  		q27: bdy.weight.q27,   q28: bdy.weight.q28, q29:bdy.weight.q29,
  		q30: bdy.weight.q30,   q31: bdy.weight.q31, q32:bdy.weight.q32}, function (err) {

                    if (err) throw err;


                      //  return res.json({ success: true, 'message':"user created"  });


                  });



                  physical.create({ uemail: email, q17:bdy.physical.q17,
                  q18: bdy.physical.q18,   q19: bdy.physical.q19,  q20: bdy.physical.q20 }, function (err) {

                    if (err) throw err;


                      //  return res.json({ success: true, 'message':"user created"  });


                  });


  		medication.create({ uemail: email, q3:bdy.medication.q3,
                  q4: bdy.medication.q4,   q5: bdy.medication.q5 }, function (err) {

                    if (err) throw err;


                      //  return res.json({ success: true, 'message':"user created"  });


                  });




                  HScale.create({ uemail: email, medication:bdy.HScale.medication,
                  diet: bdy.HScale.diet,   physical: bdy.HScale.physical,
                  weight: bdy.HScale.weight,   alcohol: bdy.HScale.alcohol,
                  smoking: bdy.HScale.smoking
                 }, function (err) {

                    if (err) throw err;


                      //  return res.json({ success: true, 'message':"user created"  });


                  });




  		diet.create({ uemail: email, q6:bdy.diet.q6,
                  q7: bdy.diet.q7, q8:bdy.diet.q8,
                  q9: bdy.diet.q9,
  		q10:bdy.diet.q10,
                  q11: bdy.diet.q11,
  		q12: bdy.diet.q12,
  		q13: bdy.diet.q13,
                  q14: bdy.diet.q14,
  		q15: bdy.diet.q15,
  		q16: bdy.diet.q16
                 	}, function (err) {

                  	if (err) throw err;


                      return res.json({ success: true, 'message':"resposes recorded"  });


                  });





              });


      });


  });



  app.post('/checksurvey',function(req,res){

  	


  	var con = mysql.createConnection({
      host: "localhost",
      user: "root",
      password: "root",
      database : "smartcare"
    });


    var token = req.body.token || req.query.token || req.headers['x-access-token'];


    con.connect(function(err) {

  	  console.log("I am in checksurvey outside 4");

  	var decoded = jwt.decode(token, 'MyKey');
      var name1 = decoded.uname;

  	var usr = { 'uname':name1};
      var accessToken = jwt.sign(usr, 'MyKey', {
                  //Set the expiration
                  expiresIn: 3600 //we are setting the expiration time of 1 hr.
              });


      if (err) throw err;
      if(name1!=null){


      con.query("SELECT * FROM GeneralQuestions WHERE uemail = ? LIMIT 1", [name1],function (err, result, fields) {
        if (err) throw err;

  	  if(result.length==1)
  	  {
  		return res.json({token: accessToken, success: true, 'message': 'Survey already completed'});
  	  }
  	  else{
  		  return res.json({token: accessToken, success: false, message: 'Survey waiting for response'});
  	  }

  });
  }
  });
  });



}
