loginApp.config(function($routeProvider){
     $routeProvider.when('/',
       {        templateUrl : 'login.html',        controller  : 'loginController'})
       .when('/datatables',{        templateUrl : 'datatables.html',        controller  : 'usersController'})
       .when('/createuser',{        templateUrl : 'createuser.html',controller  : 'createUserController'});

     });
