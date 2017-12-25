loginApp.controller('studycoordinatorsController',function($scope, $http, uEmailService, $cookies, )
{



    $scope.logout =  function(){
        
                $cookies.remove("tokenA");
                window.location.href = "adminlogin.html";
        
        
            }
        
        
        
        
        
            if(!$cookies.get("tokenA")){
             window.location.href = "adminlogin.html";
            }
        





  form1 = {

  };


  $http.post('http://18.216.218.221:8080/getStudyCoordinators', form1)
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
