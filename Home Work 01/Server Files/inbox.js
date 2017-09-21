var expr = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');
var bcrypt = require('bcrypt');
var jwt  = require('jsonwebtoken');

var app = expr();

var urlencodedParser = bodyParser.urlencoded({ extended: false })

app.post('/inbox', urlencodedParser, function (req, res) {
  if (!req.body) return res.sendStatus(400)

  var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "HW01"
  });
  
  var token = req.body.token || req.query.token || req.headers['x-access-token'];
   
  
  con.connect(function(err) {
	  
	  // decode token
	var decoded = jwt.decode(token, 'MyKey');
    var name1 = decoded.uname;
	
	var usr = { 'uname':name1};
    var accessToken = jwt.sign(usr, 'MyKey', {
                //Set the expiration
                expiresIn: 3600 //we are setting the expiration time of 1 hr.
            });


    if (err) throw err;
    if(name1!=null){
		
		console.log(req.body.region);
    con.query("SELECT * FROM inbox i join users u on i.sender = u.uname WHERE receiver = ? ORDER BY dtime DESC", [name1],function (err, result, fields) {
      if (err) throw err;
      console.log(result);

	  
		return res.json({token: accessToken, success: true, 'data': result});  
	  


    });
  }

  });

});
///

app.post('/markread', urlencodedParser, function (req, res) {
  if (!req.body) return res.sendStatus(400)

  var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "HW01"
  });
  
  var token = req.body.token || req.query.token || req.headers['x-access-token'];
  
  con.connect(function(err) {
    
	var decoded = jwt.decode(token, 'MyKey');
    var name1 = decoded.uname;
	
	var usr = { 'uname':name1};
    var accessToken = jwt.sign(usr, 'MyKey', {
                //Set the expiration
                expiresIn: 3600 //we are setting the expiration time of 1 hr.
            });
	
	
	var id = req.body.id


    if (err) throw err;
    if(id!=null){
		
    con.query("update inbox set isread = 1 WHERE id = ? and receiver = ?", [id,name1],function (err, result, fields) {
      if (err) throw err;
      console.log(result);
	  
	  if(result.affectedRows==1)
	  {
		return res.json({token: accessToken, success: true, 'data': result});  
	  }
	  else{
		  return res.json({token: accessToken, success: false, message: 'Unknown error'});
	  }


    });
  }

  });

});

app.post('/unlockmsg', urlencodedParser, function (req, res) {
  if (!req.body) return res.sendStatus(400)

  var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "HW01"
  });
  
  var token = req.body.token || req.query.token || req.headers['x-access-token'];
  
  con.connect(function(err) {
    
	var decoded = jwt.decode(token, 'MyKey');
    var name1 = decoded.uname;
	
	var usr = { 'uname':name1};
    var accessToken = jwt.sign(usr, 'MyKey', {
                //Set the expiration
                expiresIn: 3600 //we are setting the expiration time of 1 hr.
            });
	
	
	var region = req.body.region


    if (err) throw err;
    if(region!=null){
		
    con.query("update inbox set locked = 0 WHERE region LIKE ? and receiver = ?", [region,name1],function (err, result, fields) {
      if (err) throw err;
      console.log(result);
	  
	
		
	  if(result.affectedRows>=1)
	  {
		return res.json({token: accessToken, success: true, 'data': result});  
	  }
	  else{
		  return res.json({token: accessToken, success: false, message: 'Nothing to update'});
	  }
	  


    });
  }

  });

});


app.post('/createmsg', urlencodedParser, function (req, res) {
  if (!req.body) return res.sendStatus(400)
	  
   var token = req.body.token || req.query.token || req.headers['x-access-token'];

  var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "HW01"
  });
  
  
  con.connect(function(err) {
	  
	var decoded = jwt.decode(token, 'MyKey');
    var name1 = decoded.uname;
	
	var usr = { 'uname':name1};
    var accessToken = jwt.sign(usr, 'MyKey', {
                //Set the expiration
                expiresIn: 3600 //we are setting the expiration time of 1 hr.
            });


    if (err) throw err;
    if(req.body.receiver!=null){
		
    con.query("INSERT INTO inbox (sender,receiver,message,region) VALUES (?,?,?,?)", [name1,req.body.receiver,req.body.message,req.body.region],function (err, result, fields) {
      if (err) throw err;
      console.log(result);
	  
	  if(result.affectedRows==1)
	  {
		return res.json({token: accessToken, success: true, 'data': result});  
	  }
	  else{
		  return res.json({token: accessToken, success: false, message: 'Unknown error'});
	  }

        


    });
  }

  });

});


app.post('/login', urlencodedParser, function(req, res) {
  var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "HW01"
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

    con.query("SELECT * FROM  users WHERE UNAME = ? LIMIT 1", [uname],function (err, result, fields) {
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

        console.log(pwdDB)
        console.log(result)

        if (!bcrypt.compareSync(pwd,pwdDB)) {
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

app.post('/register', urlencodedParser, function(req, res) {
  var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "HW01"
  });

  console.log(req.body.uname);
  con.connect(function(err) {

    if(err)
    throw err


  });

  var uname = req.body.uname
  var lname = req.body.lname
  var fname = req.body.fname

  var pwd = bcrypt.hashSync(req.body.pwd, 10);

    con.query("INSERT INTO users VALUES (?, ?, ?, ?) ", [uname,fname,lname,pwd],function (err, result, fields) {
      if (err){
		  
		  if(err.errno==1062)
		  {	  
			  return res.json({ success: false, 'message':"User already exists"  });
		  }
		  else{
			  
			return res.json({ success: false, 'message':"Unknown error occured" ,"error": err });  
		  }
         


      }
      
      console.log(result);

      var json1 = {

        "uname" : uname,
        "lname" : lname,
        "fname" : fname,
      }


	return res.json({ success: true, 'Data':json1  });
      
    });


});

////
app.post('/getusers', urlencodedParser, function (req, res) {
  if (!req.body) return res.sendStatus(400)

  var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "HW01"
  });
  
  var token = req.body.token || req.query.token || req.headers['x-access-token'];
   
  
  con.connect(function(err) {
	  
	  // decode token
	var decoded = jwt.decode(token, 'MyKey');
    
	
	var name1 = decoded.uname;
	
	var usr = { 'uname': name1};
    var accessToken = jwt.sign(usr, 'MyKey', {
                //Set the expiration
                expiresIn: 3600 //we are setting the expiration time of 1 hr.
            });


    if (err) throw err;
    if(name1!=null){
		
    con.query("SELECT uname, fname, lname FROM users WHERE uname NOT LIKE ? ORDER BY fname ASC", [name1],function (err, result, fields) {
      if (err) throw err;
      console.log(result);

	  
	  return res.json({token: accessToken, success: true, 'data': result});  
	  


    });
  }

  });

});


app.post('/deletemsg', urlencodedParser, function (req, res) {
  if (!req.body) return res.sendStatus(400)

  var con = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "root",
    database : "HW01"
  });
  
  var token = req.body.token || req.query.token || req.headers['x-access-token'];
   
  
  con.connect(function(err) {
	  
	  // decode token
	var decoded = jwt.decode(token, 'MyKey');
    
	
	var name1 = decoded.uname;
	
	var usr = { 'uname': name1};
    var accessToken = jwt.sign(usr, 'MyKey', {
                //Set the expiration
                expiresIn: 3600 //we are setting the expiration time of 1 hr.
            });


    if (err) throw err;
    if(name1!=null){
		
    con.query("delete FROM inbox WHERE ID = ?", [req.body.id],function (err, result, fields) {
      if (err) throw err;
      console.log(result);

	   if(result.affectedRows==1)
	  {
		return res.json({token: accessToken, success: true, 'data': result});  
	  }
	  else{
		  return res.json({token: accessToken, success: false, message: 'Unknown error'});
	  }
	  
	  


    });
  }

  });

});


app.listen(8080);
