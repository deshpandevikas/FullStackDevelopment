
loginApp.controller('loginController',function($scope, $http, $location, $cookies)
{


$scope.getLogin  = function(){

  form1 = {
uname : $scope.uname,
pwd : $scope.pwd};


  $http.post('http://18.216.218.221:8080/login', form1)
      .success(function(data) {
          $scope.error = data;
          if(data.success){
          //  alert("login successful !! ");
          $cookies.put('tokenA',data.token);
            window.location.href = "listusers.html";
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





      }




});
