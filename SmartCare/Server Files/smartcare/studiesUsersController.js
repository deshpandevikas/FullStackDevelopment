var orm = require("orm");
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');


module.exports.controller = function(app){


  app.post('/getUsersStdyFirstTime',function(req,res){


    //if (!req.body) return res.sendStatus(400)
    //res.send('login');
    console.log(req.body);

    


  orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
    if (err) throw err;

      /*var studyUsers = db.define("StudiesUsers", {
          sid      :Number,
          uemail   : String

      });*/

      db.driver.execQuery(
        "Select * from USERS where uemail not in ( SELECT uemail FROM StudiesUsers WHERE sid LIKE ? ) ",
        [req.body.sid],
        function (err, users) {

          if (err) throw err;
          
          console.log("Number of users in study found: %d", users.length);
          
               if(users.length> 0){
          
                 return res.json(users);
          
          
                   }
                     else{
                   return res.json({ success: false, 'message':"no studies present"  });
                  }
         
         }
      );

      });


  });




  app.post('/getUsersForStudy',function(req,res){



    //if (!req.body) return res.sendStatus(400)
    //res.send('login');
    console.log(req.body);

    


  orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
    if (err) throw err;

      /*var studyUsers = db.define("StudiesUsers", {
          sid      :Number,
          uemail   : String

      });*/

      db.driver.execQuery(
        "SELECT * FROM StudiesUsers WHERE sid LIKE ? ",
        [req.body.sid],
        function (err, users) {

          if (err) throw err;
          
          console.log("Number of users in study found: %d", users.length);
          
               if(users.length> 0){
          
                 return res.json(users);
          
          
                   }
                     else{
                   return res.json({ success: false, 'message':"no studies present"  });
                  }
         
         }
      );

      });


  });


  app.post('/createstudyusers',function(req,res){



    //if (!req.body) return res.sendStatus(400)
    //res.send('login');


  console.log(req.body);




  
  orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
    if (err) throw err;

    var studyUsers= db.define("StudiesUsers", {
      sid   : Number,
      uemail   : String

  });



      //console.log(Admin);

      db.sync(function(err) {
        if (err) throw err;

           studyUsers.create(req.body, function (err) {

                  if (err) throw err;


                      return res.json({ success: true, 'message':"users created for study"  });


                });



            });



     

   






  });





});
}
