
loginApp.controller('listSurvyesForStudyController',function($scope, $http,$cookies)
{




  $scope.studyname = $cookies.get("studyid");
  console.log($scope.studyname);


  var tkn =  $cookies.get('tokenS');

  if(!$cookies.get("studyid")){
      window.location.href= "liststudies.html";
  }

  




  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }



    $http.post('http://18.216.218.221:1337/getSurveys',{"sid":$cookies.get("studyid")})
        .success(function(data) {
            $scope.surveys = data.Surveys;
         
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


$scope.goToSurvey = function(survey){

    $cookies.put('surveyid', survey.surveyid);
    window.location.href= "listusersforstudypersurvey.html"


}



});
