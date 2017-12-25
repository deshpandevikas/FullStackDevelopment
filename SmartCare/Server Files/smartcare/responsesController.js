var orm = require("orm");
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');


module.exports.controller = function(app){



  app.post('/viewresponses',function(req,res){



    console.log(req.body);

    console.log(req.body.uemail);

    var uemail = req.body.uemail;


    //var par = JSON.parse(req.body)

  console.log(typeof(req.body));
    //console.log(par);

  var con = mysql.createConnection({
      host: "localhost",
      user: "root",
      password: "root",
      database : "smartcare"
    });


  con.connect(function(err) {

        if(err)
        throw err


      });


    con.query("SELECT * FROM  Diet INNER JOIN drinking USING (uemail) INNER JOIN smoking USING (uemail) INNER JOIN weight USING (uemail) INNER JOIN Physical USING (uemail)  INNER JOIN GeneralQuestions USING (uemail)  INNER JOIN MedicationUsage USING (uemail)  INNER JOIN HScale USING (uemail)     WHERE uemail = ? LIMIT 1", [uemail],function (err, result, fields) {
        if (err){
  		  console.log(err);
  		  return res.json({ success: false, 'message':"Unknown error occured"  });


        }
        else if(result.length == 0){
                   console.log("user doent exist");

  		  return res.json({ success: false, 'message':"user does not exist"  });

        }
        else{

  			return res.json({ success: true, userresponse:result[0]  });

        }

        console.log(result);

      });








  });




}
