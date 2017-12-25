
loginApp.controller('listquestionsForStudyController',function($scope, $http,$cookies)
{


  $scope.studyname = $cookies.get("studyname");
  console.log($scope.studyname);


  var tkn =  $cookies.get('tokenS');
  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }

  if(!$cookies.get('studyid')){

    window.location.href = "liststudies.html";
  }


    $scope.peos = [];
    


    $http.post('http://18.216.218.221:1337/getQuestionsForResponse',{"sid":$cookies.get("studyid")})
        .success(function(data) {
            $scope.questions = data.StudyQuestions;
         
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


$scope.viewResponses = function(question){

    $cookies.put("questionid", question.qid);
    window.location.href = "listresponsesforquestion.html";

}



});
