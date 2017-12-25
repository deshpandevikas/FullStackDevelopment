
loginApp.controller('createusersForStudyController',function($scope, $http,$cookies)
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


    $http.post('http://18.216.218.221:1337/getUsersStdyFirstTime',{sid: $cookies.get("studyid")})
        .success(function(data) {
            $scope.persons = data;
         
            if(data){
            // alert(" fetched all users");
            
            }
            else{
                $scope.error = "error";
                $scope.status = "No users found";
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




$scope.submitUsers  = function(){

  //$scope.



  console.log($scope.multipleSelect);

  if($scope.multipleSelect){
    for(i=0;i<$scope.multipleSelect.length;i++){
      $scope.peos.push({
        'sid': $cookies.get("studyid"),
        'uemail':$scope.multipleSelect[i] });
    }


     console.log(JSON.stringify($scope.peos));

     $http.post('http://18.216.218.221:1337/createstudyusers', $scope.peos)
         .success(function(data) {
             if(data.success){
              alert(" Created Users");
              window.location.href = "listusersforstudy.html";
            
             }
             else{
                 $scope.error = "error";
                 $scope.status  = "Failed to create users";
             }

             console.log(data);
         })
         .error(function(data) {
             console.log('Error: ' + data);
            
         });





  }

}




});
