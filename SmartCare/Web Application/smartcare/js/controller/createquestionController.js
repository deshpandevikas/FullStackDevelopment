
loginApp.controller('createquestionController',function($scope, $http,$cookies)
{


  var tkn =  $cookies.get('tokenS');
  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }

    $scope.peos = [];
    


    $http.post('http://18.216.218.221:1337/getUsers')
        .success(function(data) {
            $scope.persons = data;
          //  alert(data);
          //  alert("fetched");
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

$scope.scheduleJob = function(){


  $http.post('http://18.216.218.221:1337/schedulemessages')
      .success(function(data) {
          if(data.success){
        //   alert(" Created Question");

            window.location.href = "datatables.html";
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
          window.location.href = "studycoordinatorlogin.html";


}         




$scope.submitQuestion  = function(){



  console.log($scope.multipleSelect);

  if($scope.multipleSelect){
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
       'mm2' : $scope.mm2 ,
       'users' : $scope.multipleSelect

     };


     console.log(JSON.stringify(form1));

     $http.post('http://34.235.151.74:8080/createquestion', form1)
         .success(function(data) {
             if(data.success){
              alert(" Created Question");
              $scope.scheduleJob();


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
  else{
    $scope.error = "error";
    $scope.status  = "Please Select atleast one user";



  }







    }

    $scope.addOptions1 = function(){

console.log($scope.ncount);
      for(i=0;i<$scope.ncount;i++){

        $scope.peos.push({});
        console.log($scope.peos);

      }


    }




});
