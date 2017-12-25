
loginApp.controller('createscController',function($scope, $http,$cookies)
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
semail : $scope.semail1,
fname : $scope.fname,
lname : $scope.lname,
pwd : $scope.pwd

};

//alert($scope.semail1);

//alert(JSON.stringify(form1));

console.log(JSON.stringify(form1));

if($scope.pwd !=  $scope.cpwd){

                $scope.error = "error";
                $scope.status = "Password and confirm password should match";


}
else{

  $http.post('http://18.216.218.221:8080/createstudycoordinator', form1)
      .success(function(data) {
          $scope.error = data;
          if(data.success){
          //  alert("user create Successfully !! ");
            window.location.href = "liststudycoordinators.html";
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
