
loginApp.controller('viewQuestionsController',function($scope, $http,  $cookies)
{


  var tkn =  $cookies.get('tokenS');
  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }

var em =  $cookies.get('uem');
$cookies.remove('uem');



$scope.logout = function(){
    
    
            $cookies.remove('tokenS');
            window.location.href = "studycoordinatorlogin.html";


  }         




console.log(em);
  form1 = {

    uemail: em

  };

  $http.post('http://18.216.218.221:1337/displayQuestionsToAdminPromise', form1)
      .success(function(data) {
          $scope.respose = data.message;

          if(data.success){
          //  alert(" fetched all users");
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
