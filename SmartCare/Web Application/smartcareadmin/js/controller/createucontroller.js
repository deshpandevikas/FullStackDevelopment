
loginApp.controller('createUserController',function($scope, $cookies,$http)
{

  $scope.logout =  function(){
    
            $cookies.remove("tokenA");
            window.location.href = "adminlogin.html";
    
    
        }
    
    
    
    
    
        if(!$cookies.get("tokenA")){
         window.location.href = "adminlogin.html";
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

  $http.post('http://18.216.218.221:8080/createuser', form1)
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
          $scope.status = "Something Went Wrong";
        //  alert(data);
      });


      }
    }




});
