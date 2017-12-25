
var orm = require("orm");
var mysql = require('mysql');




module.exports.controller = function(app){

app.post('/createmcqquestion',function(req,res){



  //var par = JSON.parse(req.body)

console.log(typeof(req.body));
console.log(JSON.stringify(req.body));
  //console.log(par);



orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
  if (err) throw err;


  
  db.driver.execQuery(
    "SELECT * FROM StudiesUsers WHERE sid LIKE ? ",
    [req.body.sid],
    function (err, users) {

      var usrlist = [];

      if (err) throw err;
      
      console.log("Number of users in study found: %d", users.length);

           
      
           if(users.length> 0){

            


            for(i=0;i<users.length;i++){
              usrlist.push(users[i].uemail);
            }
             



  var questions1 = db.define("questions", {
    sid:Number,
    qid : {type: 'number', unique:true,key:true},
      question : String

  });




    var Scheduler1 = db.define("questionsTime", {
      qid : {type: 'number',key:true},
      qtimehh    : {type: 'number',key:true},
        qtimemm    : {type: 'number',key:true},
        isscheduled : Number

    });




    db.sync(function(err) {
        if (err) throw err;


        questions1.create({ sid:req.body.sid,question: req.body.question}, function (err,data) {

          if (err){
              console.log(err);

          }
          else{

            Scheduler1.create({ qid: data.qid,qtimehh: req.body.hh1,
            qtimemm : req.body.mm1, isscheduled : 0 }, function (err) {

              if (err){
                  console.log(err);
                  throw err;

              }
              else{

                if(req.body.hh2 != -1){

                  Scheduler1.create({ qid: data.qid,qtimehh: req.body.hh2,
                  qtimemm : req.body.mm2, isscheduled : 0 }, function (err) {

                    if (err){
                        console.log(err);
                        throw err;

                    }

                  });


                }





              }




            });

            console.log(data.qid);

            var qid = data.qid;

            var lisarray = req.body.addoptions;
            var optionlist = [];

            optionlist.push(
              [qid,1,req.body.option1]

            );

            optionlist.push(
              [qid,2,req.body.option2]

            );



            for(i=0 ; i<lisarray.length; i++){

              optionlist.push([qid, (i+3) ,lisarray[i].txt]);
            }


              var con = mysql.createConnection({
                host: "localhost",
                port : 3306,
                user: "root",
                password: "root",
                database : "smartcare"
              });

              console.log(optionlist);


              con.connect(function(err) {
                if (err) throw err;
                console.log("Connected!");
                var sql = "INSERT INTO questionsOptions (qid, optionid, optionname) VALUES ?";
                var values = optionlist;
                con.query(sql, [values], function (err, result) {
                    if (err) throw err;
                    console.log("Number of records inserted: " + result.affectedRows);
                  });

                  var ulist = [];
                  var tp = usrlist;
                  console.log(tp);
                  for(j = 0 ; j < usrlist.length ; j++){
                    ulist.push([ qid, tp[j] ]);

                  }
                  console.log(ulist);

                  var sql = "INSERT INTO questionsUsers (qid, uemail) VALUES ?";
                  var values = ulist;
                  con.query(sql, [values], function (err, result) {
                      if (err) throw err;
                      console.log("Number of records inserted: " + result.affectedRows);

                        return res.json({ success: true, 'message':"Quesiton inserted"  });



                    });



                });





          }







        });





    });
            




      
             //return res.json(users);
      
      
               }
                 else{

               return res.json({ success: false, 'message':"no studies present"  });
              }
     
     }
  );





});


});

}
