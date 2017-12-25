var orm = require("orm");
var jwt  = require('jsonwebtoken');
var mysql = require('mysql');


module.exports.controller = function(app){
  app.post('/login',function(req,res){

   /* if(req.session.page_views){
      req.session.page_views++;
    }
    else{
      req.session.page_views = 1;


    }
    console.log(req.session.page_views );
    */

   if(req.session.semail){

      return res.json({success: true, token: "NA" });
    }
  


  //if (!req.body) return res.sendStatus(400)
  //res.send('login');
  console.log(req.body);

  console.log(req.body.uname);

  console.log(req.body.pwd);
  if(req.session.semail){
    console.log("Session already in use");
  }
  else{
    console.log("Session not defined yet");
  }
  


  //var par = JSON.parse(req.body)

  //console.log(typeof(req.body));
  //console.log(par);



orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
  if (err) throw err;

    var StudyCor = db.define("StudyCoordinator", {
        semail      : {type:'text', unique:true, key:true},
        fname   : String,
        lname : String,
        pwd : String
    });

    //console.log(Admin);

    db.sync(function(err) {
        if (err) throw err;

                StudyCor.find({ semail: req.body.uname, pwd: req.body.pwd }, function (err, people) {

                	if (err) throw err;

                    console.log("Scor found: %d", people.length);

                    if(people.length> 0){
                      var usr = { 'uname':req.body.uname};


        var accessToken = jwt.sign(usr, 'MyKey', {
                //Set the expiration
                expiresIn: 3600 //we are setting the expiration time of 1 hr.
            });


        

        // console.log(req.session.semail);

        if(req.session.semail){
          console.log("Session created");
        }
        else{
          req.session.semail = 1;

        }
         

        return res.json({success: true, token: accessToken   });



                    }
                    else{
                      return res.json({ success: false, 'message':"user does not exist"  });
                    }


                });



            });


    });


});

/*
app.post('/logout',function(req,res){

  req.session.destroy(function(){
    return res.json({ success: true, 'message':"Study Coordinator Logged out"  });
 });
});


 app.post('/loggedin',function(req,res){

  console.log(req.session.semail);
  
    if(req.session.semail){

      return res.json({ success: true, 'message':"Study Coordinator Logged in"  });
    }
    else{

      return res.json({ success: false, 'message':"Study Coordinator Logged out"  });

    }
  

  //res.send('login');

});
*/
}
