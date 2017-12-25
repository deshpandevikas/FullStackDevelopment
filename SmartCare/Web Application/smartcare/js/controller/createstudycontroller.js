
loginApp.controller('createStudyController',function($scope, $http,$cookies)
{

  var tkn =  $cookies.get('tokenS');
  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }


$scope.createStudy  = function(){

  form1 = {
sname : $scope.sname,
sdescr : $scope.sdescr

};

  $http.post('http://18.216.218.221:1337/createstudy', form1)
      .success(function(data) {
          $scope.error = data;
          if(data.success){
          //  alert("user create Successfully !! ");
            window.location.href = "liststudies.html";
          }
          else{
              $scope.error = "error";
                $scope.status = "Study already created";
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


  $scope.logout = function(){
    
    
            $cookies.remove('tokenS');
            window.location.href = "studycoordinatorlogin.html";


  }         





});
