
loginApp.controller('listResponsesForQuestionController',function($scope, $http,$cookies)
{




  $scope.studyname = $cookies.get("studyid");
  console.log($scope.studyname);


  var tkn =  $cookies.get('tokenS');

  if(!$cookies.get("studyid")){
      window.location.href= "liststudies.html";
  }

  

  if(!$cookies.get("questionid")){
      window.location.href= "listquestionsforstudy.html";
  }





  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }


form1= {
    qid: $cookies.get("questionid")

};

    $http.post('http://18.216.218.221:1337/getQuestionResponseForAll',form1)
        .success(function(data) {
            $scope.responses = data.StudyQuestions;

            console.log(data);
         
            if(data.success){
            // alert(" fetched all users");
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
