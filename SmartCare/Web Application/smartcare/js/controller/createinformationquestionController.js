
loginApp.controller('createinformationquestionController',function($scope, $http,$cookies)
{


  var tkn =  $cookies.get('tokenS');
  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }
  if(!$cookies.get('studyid')){
     window.location.href = "liststudies.html";
  }


    

$scope.scheduleJob = function(){


  $http.post('http://18.216.218.221:1337/schedulemessages')
      .success(function(data) {
          if(data.success){
        //   alert(" Created Question");

            window.location.href = "listquestionsforstudy.html";
          }
          else{
              $scope.error = "error";
              $scope.status  = "Failed to create scheduling ";
          }


          console.log(data);
      })
      .error(function(data) {
          console.log('Error: ' + data);
          //alert(data);
      });


}


$scope.logout = function(){
  
  
          $cookies.remove('tokenS');
          $cookies.remove('studyid');
          window.location.href = "studycoordinatorlogin.html";



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
       'hh1' : $scope.hh1,
       'mm1' : $scope.mm1 ,
       'hh2'  : $scope.hh2 ,
       'mm2' : $scope.mm2,
       'sid':$cookies.get("studyid") 

     };


     console.log(JSON.stringify(form1));

     $http.post('http://18.216.218.221:1337/createinformationquestion', form1)
         .success(function(data) {
             if(data.success){
               console.log("MCQ Question Created!!");
              //alert(" Created Question");
              $scope.scheduleJob();


             }
             else{
                 $scope.error = "error";
                 $scope.status  = "Failed to create information question";
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
