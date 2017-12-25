
loginApp.controller('createsurveyController',function($scope, $http,$cookies)
{


  /*var tkn =  $cookies.get('tokenS');
  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }
  if(!$cookies.get('studyid')){
     window.location.href = "liststudies.html";
  }

  */


    $scope.surQuestions = [];

    $scope.addMoptions= function(eh){
      var index = $scope.surQuestions.indexOf(eh);
      $scope.surQuestions[index].options = [];
      for(i=0;i<$scope.surQuestions[index].numberofoptions;i++){
        
        $scope.surQuestions[index].options.push({});
            //    console.log($scope.peos);
        
              }
      



    }
    


    $scope.addQuestion = function() {
      
      $scope.surQuestions.push({});

    }   
    
    $scope.removerQuestion = function(ech) {
      var index = $scope.surQuestions.indexOf(ech);
      $scope.surQuestions.splice(index, 1); 
    }

    $scope.addOptions1 = function(){
      
      console.log($scope.ncount);
            for(i=0;i<$scope.ncount;i++){
      
              $scope.peos.push({});
              console.log($scope.peos);
      
            }
      
      
          }
    


$scope.logout = function(){
  
  
          $cookies.remove('tokenS');
          $cookies.remove('studyid');
          $cookies.remove('studyname');
          window.location.href = "studycoordinatorlogin.html";



}         

$scope.submitSurvey = function(){
  //console.log(JSON.stringify($scope.surQuestions));


  form1 ={addQuestion: {
    sid : $cookies.get("studyid"),
    surveyname : $scope.surname,
    SurveyQuestions: $scope.surQuestions


  }};

  console.log(JSON.stringify(form1));


  $http.post('http://18.216.218.221:1337/addSurveyQuestions', form1)
      .success(function(data) {
          if(data.success){
            console.log("MCQ Question Created!!");
           //alert(" Created Question");
           window.location.href = "listsurveysforstudy.html";


          }
          else{
              $scope.error = "error";
              $scope.status  = "Failed to create question";
          }


          console.log(data);
      })
      .error(function(data) {
          console.log('Error: ' + data);
          //alert(data);
      });



}



$scope.submitQuestion  = function(){




    for(i=0;i<$scope.ncount;i++){
      console.log($scope.peos[i].txt);
    }

    console.log($scope.rdg);
    if($scope.rdg){

    if($scope.rdg == 'everyhour'){

      console.log("-1");
      $scope.hh1 = "-1";
      $scope.mm1 = "-1";
      $scope.hh2 = "-1";
      $scope.mm2 = "-1";



    }
    else if($scope.rdg == 'twicedaily'){

      console.log($scope.t1.getHours());
      console.log($scope.t1.getMinutes());
      console.log($scope.t2);

      $scope.hh1 = $scope.t1.getHours();
      $scope.mm1 = $scope.t1.getMinutes();
      $scope.hh2 = $scope.t2.getHours();
      $scope.mm2 = $scope.t2.getMinutes();



    }
    else{
      console.log($scope.t3);
      $scope.hh1 = $scope.t3.getHours();
      $scope.mm1 = $scope.t3.getMinutes();
      $scope.hh2 = "-1";
      $scope.mm2 = "-1";


    }

     var form1 = {
       'question' : $scope.question,
       'option1' :  $scope.option1,
       'option2' :  $scope.option2,
       'addoptions' : $scope.peos,
       'hh1' : $scope.hh1,
       'mm1' : $scope.mm1 ,
       'hh2'  : $scope.hh2 ,
       'mm2' : $scope.mm2,
       'sid':$cookies.get("studyid") 

     };


     console.log(JSON.stringify(form1));
    
     $http.post('http://18.216.218.221:1337/addSurveyQuestions', form1)
         .success(function(data) {
             if(data.success){
               console.log("MCQ Question Created!!");
              //alert(" Created Question");


             }
             else{
                 $scope.error = "error";
                 $scope.status  = "Failed to create question";
             }


             console.log(data);
         })
         .error(function(data) {
             console.log('Error: ' + data);
             //alert(data);
         });


  }
    else{

      $scope.error = "error";
      $scope.status  = "Please Select atleast one time slot to schedule";

    }



    }

   




});
