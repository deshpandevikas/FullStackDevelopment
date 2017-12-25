
loginApp.controller('createUserController',function($scope, $http,$cookies)
{

  var tkn =  $cookies.get('tokenS');
  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }


$scope.createUser  = function(){

  form1 = {
uemail : $scope.uemail,
fname : $scope.fname,
lname : $scope.lname,
pwd : $scope.pwd,
age : $scope.age,

};

if($scope.pwd !=  $scope.cpwd){

        $scope.error = "error";
        $scope.status = "Password and confirm password should match";


}
else{

  $http.post('http://18.216.218.221:1337/createuser', form1)
      .success(function(data) {
          $scope.error = data;
          if(data.success){
          //  alert("user create Successfully !! ");
            window.location.href = "listusers.html";
          }
          else{
              $scope.error = "error";
                $scope.status = "Email Already in use";
          }

          console.log(data);
      })
      .error(function(data) {
          console.log('Error: ' + data);
          $scope.error = "error";
          $scope.status = "All Fields are required";
        //  alert(data);
      });


      }
    }

 
    $scope.logout = function(){
      
      
              $cookies.remove('tokenS');
              window.location.href = "studycoordinatorlogin.html";
  
  
    }         
     




});
