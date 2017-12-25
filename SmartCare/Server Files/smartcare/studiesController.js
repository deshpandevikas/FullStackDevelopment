var orm = require("orm");
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');


module.exports.controller = function(app){

  app.post('/getStudies',function(req,res){



    //if (!req.body) return res.sendStatus(400)
    //res.send('login');
    console.log(req.body);


  orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
    if (err) throw err;

      var studies = db.define("Studies", {
          sid      : {type:'number', unique:true, key:true},
          sname   : String,
          sdescription   : String

      });

      //console.log(Admin);

      db.sync(function(err) {
          if (err) throw err;

                  studies.find( function (err, study) {

                  	if (err) throw err;

                      console.log("Number of studies found: %d", study.length);

                      if(study.length> 0){

                        return res.json(study);


                      }
                      else{
                        return res.json({ success: false, 'message':"no studies present"  });
                      }


                  });



              });


      });


  });






  app.post('/createstudy',function(req,res){



    //if (!req.body) return res.sendStatus(400)
    //res.send('login');
    console.log(req.body);

    console.log(req.body.sname);

    console.log(req.body.sdescr);


  console.log(typeof(req.body));
  

  orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
    if (err) throw err;

    var study= db.define("Studies", {
      sname   : String,
      sdescription   : String

  });

      //console.log(Admin);

      db.sync(function(err) {
          if (err) throw err;

                  study.create({ sname:req.body.sname,
                    sdescription:req.body.sdescr}, function (err) {

                  	if (err) throw err;


                        return res.json({ success: true, 'message':"study created"  });


                  });



              });


      });


  });






}
