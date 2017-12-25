loginApp.controller('usersController',function($scope, $http, uEmailService, $cookies, )
{

    
  var tkn =  $cookies.get('tokenS');
  if(tkn){

  }
  else{
      window.location.href = "studycoordinatorlogin.html";
  }


  $scope.logout = function(){
    
    
            $cookies.remove('tokenS');
            window.location.href = "studycoordinatorlogin.html";


  }         


  $scope.getUResp  = function(data){

  //  alert(data);

     $cookies.put('uem',data);
    uEmailService.set(data);

    window.location.href="responses.html";


  }

  $scope.getQuestionsUser  = function(data){

  //  alert(data);

     $cookies.put('uem',data);
    uEmailService.set(data);

    window.location.href="Questions.html";


  }







  form1 = {

  };


  $http.post('http://18.216.218.221:1337/getUsers', form1)
      .success(function(data) {
          $scope.persons = data;
          //alert("fetched");
          if(data.success){
           alert(" fetched all users");
          }
          else{
              $scope.error = "error";
          }

        /*  $(document).ready(function() {
              $('#example').DataTable();
          } );

          */



          console.log(data);
      })
      .error(function(data) {
          console.log('Error: ' + data);
          alert(data);
      });






});
