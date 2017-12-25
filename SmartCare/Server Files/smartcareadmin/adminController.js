var orm = require("orm");
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');


module.exports.controller = function(app){
  app.post('/login',function(req,res){



  //if (!req.body) return res.sendStatus(400)
  //res.send('login');
  console.log(req.body);

  console.log(req.body.uname);

  console.log(req.body.pwd);


  //var par = JSON.parse(req.body)

console.log(typeof(req.body));
  //console.log(par);



orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
  if (err) throw err;

    var Admin = db.define("admin", {
        username      : {type:'text', unique:true, key:true},
        password   : String,
    });

    //console.log(Admin);

    db.sync(function(err) {
        if (err) throw err;

                Admin.find({ username: req.body.uname, password: req.body.pwd }, function (err, people) {

                	if (err) throw err;

                    console.log("Admins found: %d", people.length);

                    if(people.length> 0){
                      var usr = { 'uname':req.body.uname};


        var accessToken = jwt.sign(usr, 'MyKey', {
                //Set the expiration
                expiresIn: 3600 //we are setting the expiration time of 1 hr.
            });



        return res.json({success: true, token: accessToken   });



                    }
                    else{
                      return res.json({ success: false, 'message':"user does not exist"  });
                    }


                });



            });


    });


});


app.get('/login',function(req,res){



  res.send("welcome");
  //res.send('login');

});
}
