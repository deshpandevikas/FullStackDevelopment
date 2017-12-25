
loginApp.controller('listusersForStudyController',function($scope, $http,$cookies)
{


  $scope.studyname = $cookies.get("studyname");
  console.log($scope.studyname);


  var tkn =  $cookies.get('tokenS');
  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }

    $scope.peos = [];
    


    $http.post('http://18.216.218.221:1337/getUsersForStudy',{"sid":$cookies.get("studyid")})
        .success(function(data) {
            $scope.persons = data;
         
            if(data.success){
             alert(" fetched all users");
            }
            else{
                $scope.error = "error";
            }


            console.log(data);
        })
        .error(function(data) {
            console.log('Error: ' + data);
            //alert(data);
        });


$scope.logout = function(){
  
  
          $cookies.remove('tokenS');
          window.location.href = "studycoordinatorlogin.html";


}



});
