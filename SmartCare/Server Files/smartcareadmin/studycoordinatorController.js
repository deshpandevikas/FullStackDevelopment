var orm = require("orm");
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');


module.exports.controller = function(app){

  app.post('/getStudyCoordinators',function(req,res){



    //if (!req.body) return res.sendStatus(400)
    //res.send('login');
    console.log(req.body);
  //  console.log(req.session.semail);

   /*if(req.session.semail){
    
          console.log("session persisted");
          req.session.semail = 1;
        }
        else{
          console.log("session persisted");
          req.session.semail = 1;
        }
        */
      


  orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
    if (err) throw err;

      var users = db.define("StudyCoordinator", {
          semail      : {type:'text', unique:true, key:true},
          fname   : String,
          lname   : String,
          pwd   : String,
          
      });

      //console.log(Admin);

      db.sync(function(err) {
          if (err) throw err;

                  users.find( function (err, people) {

                  	if (err) throw err;

                      console.log("Number of users found: %d", people.length);

                      if(people.length> 0){

                        return res.json(people);


                      }
                      else{
                        return res.json({ success: false, 'message':"study coor does not exist"  });
                      }


                  });



              });


      });


  });






  app.post('/createstudycoordinator',function(req,res){



    //if (!req.body) return res.sendStatus(400)
    //res.send('login');
    console.log(req.body);

    //console.log(req.body.semail);

   // console.log(req.body.fname);


    //var par = JSON.parse(req.body)

  console.log(typeof(req.body));
    //console.log(par);



  orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
    if (err) throw err;

    var user = db.define("StudyCoordinator", {
        semail      : {type:'text', unique:true, key:true},
        fname   : String,
        lname   : String,
        pwd   : String,
        
    });

      //console.log(Admin);

      db.sync(function(err) {
          if (err) throw err;

                  user.create({ semail: req.body.semail, fname:req.body.fname,
                    lname:req.body.lname,pwd: req.body.pwd }, function (err) {

                  	if (err) throw err;


                        return res.json({ success: true, 'message':"study cord created"  });


                  });



              });


      });


  });


}
