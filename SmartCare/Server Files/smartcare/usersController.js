var orm = require("orm");
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');


module.exports.controller = function(app){

  app.post('/getUsers',function(req,res){



    //if (!req.body) return res.sendStatus(400)
    //res.send('login');
    console.log(req.body);
    console.log(req.session.semail);

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

      var users = db.define("USERS", {
          uemail      : {type:'text', unique:true, key:true},
          fname   : String,
          lname   : String,
          pwd   : String,
          age : Number

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
                        return res.json({ success: false, 'message':"user does not exist"  });
                      }


                  });



              });


      });


  });






  app.post('/createuser',function(req,res){



    //if (!req.body) return res.sendStatus(400)
    //res.send('login');
    console.log(req.body);

    console.log(req.body.uemail);

    console.log(req.body.fname);


    //var par = JSON.parse(req.body)

  console.log(typeof(req.body));
    //console.log(par);



  orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
    if (err) throw err;

    var user = db.define("USERS", {
        uemail      : {type:'text', unique:true, key:true},
        fname   : String,
        lname   : String,
        pwd   : String,
        age : Number

    });

      //console.log(Admin);

      db.sync(function(err) {
          if (err) throw err;

                  user.create({ uemail: req.body.uemail, fname:req.body.fname,
                    lname:req.body.lname,pwd: req.body.pwd, age:req.body.age }, function (err) {

                  	if (err) throw err;


                        return res.json({ success: true, 'message':"user created"  });


                  });



              });


      });


  });


  app.post('/userlogin',function(req,res){




    var con = mysql.createConnection({
      host: "localhost",
      user: "root",
      password: "root",
      database : "smartcare"
    });

    var token = req.body.token || req.query.token || req.headers['x-access-token'];

    // decode token
    if (token) {

      jwt.verify(token, 'MyKey', function(err, decoded) {
        if (err) {
          return res.json({ success: false, message: 'Failed to authenticate token.' });
        } else {

          var decoded = jwt.decode(token, 'MyKey');
          console.log(decoded);

          return res.json({ success: true, 'decodedData':decoded  });
        }
      });

    } else {

      var uname = req.body.uname;
      var pwd = req.body.pwd;


      con.connect(function(err) {

        if(err)
        throw err


      });

      con.query("SELECT * FROM  USERS WHERE uemail = ? LIMIT 1", [uname],function (err, result, fields) {
        if (err){
  		  console.log(err);
  		  return res.json({ success: false, 'message':"Unknown error occured"  });


        }
        else if(result.length == 0){
                   console.log("user doent exist");

  		  return res.json({ success: false, 'message':"user does not exist"  });

        }
        else{
          var pwdDB = result[0].pwd;



          if (pwd != pwdDB) {

  			return res.json({ success: false, 'message':"Authentication failed. Wrong password"  });

        } else {
            var usr = { 'uname':uname};


          var accessToken = jwt.sign(usr, 'MyKey', {
                  //Set the expiration
                  expiresIn: 3600 //we are setting the expiration time of 1 hr.
              });



          return res.json({success: true, token: accessToken   });
        }
      }
        console.log(result);

      });
    }

  });




}
