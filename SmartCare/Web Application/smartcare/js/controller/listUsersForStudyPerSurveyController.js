
loginApp.controller('listUsersForStudyPerSurveyController',function($scope, $http,$cookies)
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



  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }


form1= {
    surveyid : $cookies.get("surveyid"),
    sid: $cookies.get("studyid")

};

    $http.post('http://18.216.218.221:1337/getEachSurveyUsers',form1)
        .success(function(data) {
            $scope.persons = data.SurveyUsers;
         
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


$scope.goToSurveyResponses = function(person){

    $cookies.put('useremail', person.uemail);
    window.location.href= "listresponsesofsurvey.html"


}



});
