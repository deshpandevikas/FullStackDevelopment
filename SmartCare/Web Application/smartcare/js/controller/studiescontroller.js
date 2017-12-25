loginApp.controller('studiesController',function($scope, $http, uEmailService, $cookies)
{

    
  var tkn =  $cookies.get('tokenS');
  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }

  $scope.goToStudy = function(stid){

    $cookies.put("studyid", stid.sid);
    $cookies.put("studyname", stid.sname);
    window.location.href="createusersforstudy.html";


  }


    $scope.logout = function(){


        $cookies.remove('tokenS');
        window.location.href = "studycoordinatorlogin.html";

        /*form2= {

        }

        $http.post('http://18.216.218.221:1337/logout', form2)
        .success(function(data) {
            $scope.error = data;
            if(data.success){
            
              window.location.href = "studycoordinatorlogin.html";
            }
            else{
                $scope.error = "error";
                  $scope.status = "Problem Logging out";
            }
  
            console.log(data);
        })
        .error(function(data) {
            console.log('Error: ' + data);
            $scope.error = "error";
            $scope.status = "Something Went Wrong";
          //  alert(data);
        });
        */
    

    }

  form1 = {

  };

 /* $http.post('http://18.216.218.221:1337/loggedin', form1)
  .success(function(data) {
      $scope.error = data;
      console.log(data);

      if(!data.success){
      
        //window.location.href = "studycoordinatorlogin.html";
        console.log(data.success);
      }
      else{
          */



  $http.post('http://18.216.218.221:1337/getStudies', form1)
  .success(function(data) {
      $scope.studies = data;
      //alert("fetched");
      if(data.success){
       alert(" fetched all studies");
      }
      else{
          $scope.error = "error";
      }

      console.log(data);
  })
  .error(function(data) {
      console.log('Error: ' + data);
      alert(data);
  });




    
  });
