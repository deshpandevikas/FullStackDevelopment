var mysql = require('mysql');

module.exports.controller = function(app){

app.post('/resetscheduler',function(req,res){


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

  con.query("UPDATE questionsTime set isscheduled = ? ", [0],function (err, result, fields) {
    if (err){
    console.log(err);
    return res.json({ success: false, 'message':"Unknown error occured"  });


    }
else{

  return res.json({ success: true, 'message':"Job Scheduelr Reset Done"  });

}

  });


});


}
