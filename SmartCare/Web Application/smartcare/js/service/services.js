loginApp.factory('uEmailService', function() {
 var savedData = {}
 function set(data) {
   savedData = data;
  // alert(savedData);
 }
 function get() {
  // alert(savedData);
  return savedData;
 }

 return {
  set: set,
  get: get
 }

});
