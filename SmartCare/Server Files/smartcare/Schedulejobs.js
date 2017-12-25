var orm = require("orm");
var mysql = require('mysql');
var schedule = require('node-schedule');

module.exports.controller = function(app){

app.post('/schedulemessages',function(req,res){



  //var par = JSON.parse(req.body)

console.log(typeof(req.body));
  //console.log(par);



orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
  if (err) throw err;

    var Scheduler1 = db.define("questionsTime", {
      qid : {type: 'number', unique:true,key:true},
      qtimehh    : Number,
        qtimemm    : Number,
        isscheduled : Number

    });

    db.sync(function(err) {
        if (err) throw err;

                Scheduler1.find(function (err, sch) {

                	if (err) throw err;

                    console.log("found: %d", sch.length);

                    if(sch.length> 0){

                      for( i = 0 ; i< sch.length ; i++ ){

                          console.log("found", sch);

                          console.log("Is it scheduled?? ", sch[i].isscheduled);

                          


                      if(sch[i].isscheduled == 0){

                        sch[i].isscheduled = 1;

                        sch[i].save(function (err) {
                            console.log("saved!");

                        });

                        var dayweek = " * * 0-6";

                        var everytime = "00 "+sch[i].qtimemm+" "+sch[i].qtimehh;


                       if(sch[i].qtimehh  ==  -1){
                            var date = new Date();
                            var min = date.getMinutes();
                            var sec = date.getSeconds();
                           everytime = "* "+ min + " *";
                            //debug
                          // everytime = sec+" * "+ "*";
                           console.log(everytime + dayweek );



                        }
                      //  min = date.getMinutes();
                      //  sec = date.getSeconds();
                      //  console.log(new Date());
                      //  console.log(min);
                      //  console.log(sec);




                       var s = everytime + dayweek ;

                       console.log('job scheduleat at');
                       console.log(s);


                       var s2 = sch[i].qid;
                      console.log(s2);


                        var j = schedule.scheduleJob(s,function(y){

                          console.log('scheduled at ');
                          var currdatetime = new Date();
                          console.log(currdatetime);
                          console.log(y);

                          var mysql = require('mysql');
                          var orm = require("orm");


                          orm.connect("mysql://root:root@localhost/smartcare", function (err, db) {
                            if (err) throw err;

                              var Scheduler1 = db.define("questionsTime", {
                                qid : {type: 'number', unique:true,key:true},
                                qtimehh    : Number,
                                  qtimemm    : Number,
                                  isscheduled : Number

                              });
                              var questionTimesPosted = db.define("questionsTimesPosted", {
                                timeid : {type: 'number', unique:true,key:true},
                                qid    : Number
                              });


                              db.sync(function(err) {
                                  if (err) throw err;


                                  questionTimesPosted.create({ qid: y}, function (err) {

                                  	if (err){
                                        console.log(err);

                                    }



                                        var request = require('request');

		// Set the headers
		              var headers = {
			         'User-Agent':       'Super Agent/0.0.1',
			                 'Content-Type':     'application/x-www-form-urlencoded'
		                      }

		                            // Configure the request
		                                  var options = {
			                                       url: 'http://18.216.218.221:1337/sendnotification',
			                                             method: 'POST',
			                                                   headers: headers,
			                                                         form: {'qid': y}
		                                                 }

		                                                    // Start the request
		      request(options, function (error, response, body) {
			         if (!error && response.statusCode == 200) {
				             //return res.send(body);
				                 //return res.json(JSON.parse(body));
                         console.log(JSON.parse(body));
			                    }
		                      });


                                  });


                                });






                            });










                        }.bind(null,s2));


                      }
                    }

                      return res.json({ success: true, 'message':"Messages Scheduled"  });


                    }
                    else{
                      return res.json({ success: false, 'message':"Nothing to schedule"  });
                    }


                });



            });


    });


});


}
