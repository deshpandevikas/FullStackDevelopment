
loginApp.controller('listResponsesForSurveyController',function($scope, $http,$cookies)
{




  $scope.studyname = $cookies.get("studyid");
  console.log($scope.studyname);


  var tkn =  $cookies.get('tokenS');

  if(!$cookies.get("studyid")){
      window.location.href= "liststudies.html";
  }

  if(!$cookies.get("surveyid")){
      window.location.href= "listsurveysforstudy.html";
  }

  if(!$cookies.get("useremail")){
      window.location.href= "listusersforstudypersurvey.html";
  }






  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }


form1= {
    uemail : $cookies.get("useremail"),
    surveyid : $cookies.get("surveyid"),
    sid: $cookies.get("studyid")

};

    $http.post('http://18.216.218.221:1337/getEachSurveyResult',form1)
        .success(function(data) {
            $scope.responses = data.SurveyUserResponse;
         
            if(data.success){
             //alert(" fetched all users");
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
